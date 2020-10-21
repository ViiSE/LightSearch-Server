/*
 *  Copyright 2019 ViiSE.
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
 */

package lightsearch.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@Profile("dev")
@EnableWebSecurity
public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${lightsearch.server.admin.username}")
    private String adminName;

    @Value("${lightsearch.server.dev.username}")
    private String devName;

    @Value("${lightsearch.server.admin.password}")
    private String adminPassword;

    @Value("${lightsearch.server.dev.password}")
    private String devPassword;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername(adminName)
                .password(adminPassword)
                .roles("ADMIN").build();

        UserDetails dev = User.withUsername(devName)
                .password(devPassword)
                .roles("DEV").build();

        return new InMemoryUserDetailsManager(admin, dev);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/clients/**",
                "/static/**",
                "/favicon.ico",
                "/js/**",
                "/css/**"
        );
    }

    @Bean
    public AuthenticationSuccessHandler lightSearchAuthenticationSuccessHandler(){
        return new LightSearchAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/admins/commands/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/admins/commands/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/admins/commands/**")
                .permitAll()
                .antMatchers(HttpMethod.DELETE, "/admins/commands/**")
                .permitAll();
    }
}
