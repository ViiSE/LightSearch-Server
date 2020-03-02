/* 
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.server.exception;

/**
 *
 * @author ViiSE
 */
public class CommandExecutorException extends Exception {
    
    private final String messageRU;
    
    public CommandExecutorException(String message, String messageRU) {
        super(message);
        this.messageRU = messageRU;
    }

    public CommandExecutorException(Exception ex) {
        super(ex);
        this.messageRU = ex.getMessage();
    }
    
    public String getMessageRU() {
        return messageRU;
    }
}
