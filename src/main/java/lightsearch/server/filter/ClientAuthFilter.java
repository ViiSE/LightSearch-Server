/*
 *  Copyright 2020 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package lightsearch.server.filter;

import io.jsonwebtoken.*;
import lightsearch.server.checker.Checker;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Enumeration;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ClientAuthFilter implements Filter {

    private final LoggerServer logger;
    private final String secret;
    private final Checker<String> checker;

    public ClientAuthFilter(
            LoggerServer logger,
            @Value("${lightsearch.server.jwt-secret}") String secret,
            @Qualifier("commandCheckerClientContains") Checker<String> checker) {
        this.checker = checker;
        this.logger = logger;
        this.secret = secret;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = ((HttpServletRequest)servletRequest).getRequestURI();

        if(url.startsWith("/clients")) {
            if(url.startsWith("/clients/login")) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            Enumeration<String> headerNames = (request).getHeaderNames();
            boolean isAuthHeader = false;

            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    if (name.equalsIgnoreCase("authorization")) {
                        isAuthHeader = true;
                        try {
                            String token = (request).getHeader(name).replaceFirst("Bearer ", "");

                            Claims claims = Jwts.parser()
                                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                                    .parseClaimsJws(token).getBody();
                            if(!claims.getIssuer().equals("lightsearch")) {
                                logger.error(ClientAuthFilter.class, "JWT Token: issuer is not lightsearch!");
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            }
                            checker.check(claims.getSubject());
                            filterChain.doFilter(servletRequest, servletResponse);
                            return;
                        } catch (ExpiredJwtException |
                                UnsupportedJwtException |
                                MalformedJwtException |
                                SignatureException |
                                IllegalArgumentException |
                                CheckerException ex) {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            logger.error(ClientAuthFilter.class, ex.getMessage());
                        }
                    }
                }

                if(!isAuthHeader)
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                logger.error(ClientAuthFilter.class, "Http header is null");
            }
        } else
            filterChain.doFilter(servletRequest, servletResponse);
    }
}
