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

package lightsearch.server.controller;

import lightsearch.server.data.AdminErrorResponseDTO;
import lightsearch.server.exception.AdminErrorException;
import lightsearch.server.exception.ClientErrorException;
import lightsearch.server.log.LoggerServer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@ControllerAdvice
public class AdminErrorController {

    private final LoggerServer logger;

    public AdminErrorController(LoggerServer logger) {
        this.logger = logger;
    }

    @ExceptionHandler(AdminErrorException.class)
    public ResponseEntity<AdminErrorResponseDTO> handleException(AdminErrorException ex) {
        AdminErrorResponseDTO responseDTO = new AdminErrorResponseDTO() {{
            setCode(ex.getStatus().value());
            setStatus(ex.getStatus().value());
            setMessage(ex.getMessage()); }};

        logger.error(AdminErrorController.class, ex.getMessage());
        return new ResponseEntity<>(responseDTO, ex.getStatus());
    }
}
