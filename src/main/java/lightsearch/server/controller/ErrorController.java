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

import lightsearch.server.log.LoggerServer;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@ControllerAdvice
public class ErrorController {

    private final LoggerServer logger;

    public ErrorController(LoggerServer logger) {
        this.logger = logger;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        String logMsg;
        String errMsg;

        if(throwable != null) {
            logMsg = "Exception during execution of SpringSecurity application: " + throwable.getMessage();
            errMsg = throwable.getMessage();
        } else {
            logMsg = "Exception during execution of SpringSecurity application: Unknown error";
            errMsg = "Unknown error";
        }

        logger.error(ErrorController.class, logMsg);
        model.addAttribute("errorMessage", errMsg);
        return "error";
    }
}
