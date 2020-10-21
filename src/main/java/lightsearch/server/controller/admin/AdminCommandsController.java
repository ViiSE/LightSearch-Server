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

package lightsearch.server.controller.admin;

import io.swagger.annotations.*;
import lightsearch.server.cmd.Processes;
import lightsearch.server.constants.AdminCommands;
import lightsearch.server.data.*;
import lightsearch.server.entity.AdminCommand;
import lightsearch.server.entity.AdminCommandResult;
import lightsearch.server.exception.AdminErrorException;
import lightsearch.server.producer.entity.AdminCommandProducer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags="Admin Commands Controller", description = "Контроллер точек для работы с командами администратора")
@RestController
public class AdminCommandsController {

    private final Processes<AdminCommand, AdminCommandResult> processes;
    private final AdminCommandProducer commandProducer;

    public AdminCommandsController(
            Processes<AdminCommand, AdminCommandResult> processes,
            AdminCommandProducer commandProducer) {
        this.processes = processes;
        this.commandProducer = commandProducer;
    }

    @ApiOperation(value = "Выгружает черный список клиентов")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Черный список выгружен.")})
    @GetMapping("/admins/commands/blacklist")
    public AdminCommandResultWithBlacklistDTO requestBlacklist() throws AdminErrorException {
        return (AdminCommandResultWithBlacklistDTO) getCommandResult(AdminCommands.BLACKLIST, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Добавляет клиентов в черный список")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Клиенты добавлены в черный список.")})
    @PostMapping("/admins/commands/blacklist")
    public AdminCommandAddBlacklistResultDTO addToBlacklist(
            @ApiParam(required = true, value = "<code><b>adminCommand{imei}</b></code> можно указывать как в чистом " +
                    "виде, так и в виде хэш-строки.")
            @RequestBody AdminAddBlacklistCommandDTO commandDTO) throws AdminErrorException {
        AdminCommand cmd = commandProducer.getAdminCommandAddBlacklistInstance(commandDTO.getIMEIList());
        return (AdminCommandAddBlacklistResultDTO) getCommandResult(AdminCommands.ADD_BLACKLIST, cmd, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Удаляет клиентов из черного списка")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Клиенты удалены из черного списка."),
            @ApiResponse(code = 404,
                    message = "Клиенты не найдены.")})
    @DeleteMapping("/admins/commands/blacklist")
    public AdminCommandSimpleResultDTO delFromBlacklist(
            @ApiParam(required = true, value = "<code><b>adminCommand{imei}</b></code> можно указывать как в чистом " +
                    "виде, так и в виде хэш-строки.")
            @RequestParam(name = "imei", required = false) String IMEI,
            @RequestParam(required = false, name = "imeiList") List<String> imeiList) throws AdminErrorException {
        AdminCommand cmd;
        if(imeiList != null) {
            cmd = commandProducer.getAdminCommandDelBlacklistInstance(imeiList);
            return (AdminCommandSimpleResultDTO) getCommandResult(AdminCommands.DEL_BLACKLIST_LIST, cmd, HttpStatus.NOT_FOUND);
        } else {
            cmd = commandProducer.getAdminCommandDelBlacklistInstance(IMEI);
            return (AdminCommandSimpleResultDTO) getCommandResult(AdminCommands.DEL_BLACKLIST, cmd, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Выгружает список текущих клиентов")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Список клиентов выгружен.")})
    @GetMapping("/admins/commands/clients")
    public AdminCommandResultWithClientsDTO requestClient() throws AdminErrorException {
        return (AdminCommandResultWithClientsDTO) getCommandResult(AdminCommands.CLIENT_LIST, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Удаляет клиентов из списка текущих клиетов")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Клиенты удалены из списка текущих клиетов."),
            @ApiResponse(code = 404,
                    message = "Клиенты не найдены.")})
    @DeleteMapping("/admins/commands/clients")
    public AdminCommandSimpleResultDTO delClient(
            @ApiParam(required = true, value = "<code><b>adminCommand{imei}</b></code> можно указывать как в чистом " +
                    "виде, так и в виде хэш-строки.")
            @RequestParam(name = "imei", required = false) String IMEI,
            @RequestParam(required = false, name = "imeiList") List<String> imeiList) throws AdminErrorException {
        AdminCommand cmd;
        if(imeiList != null) {
            cmd = commandProducer.getAdminCommandClientKickInstance(imeiList);
            return (AdminCommandSimpleResultDTO) getCommandResult(AdminCommands.KICK_LIST, cmd, HttpStatus.NOT_FOUND);
        } else {
            cmd = commandProducer.getAdminCommandClientKickInstance(IMEI);
            return (AdminCommandSimpleResultDTO) getCommandResult(AdminCommands.KICK, cmd, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Выгружает время таймаута клиента")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Время таймаута клиента выгружено.")})
    @GetMapping("/admins/commands/clients/timeout")
    public AdminCommandResultWithClientTimeoutDTO getClientsTimeout() throws AdminErrorException {
        return (AdminCommandResultWithClientTimeoutDTO) getCommandResult(AdminCommands.CLIENT_TIMEOUT, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Изменяет время таймаута клиента")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Время таймаута клиента изменен."),
            @ApiResponse(code = 400,
                    message = "Время таймаута имеет неверное значение.")})
    @PutMapping("/admins/commands/clients/timeout")
    public AdminCommandSimpleResultDTO changeClientsTimeout(
            @ApiParam(required = true, value = "<code><b>adminCommand{client_timeout}</b></code> указывается в " +
                    "количествах днях жизни токена клиента. Изменения вступят в силу только для новых клиентов.")
            @RequestBody AdminClientTimeoutCommandDTO commandDTO) throws AdminErrorException {
        AdminCommand cmd = commandProducer.getAdminCommandClientTimeoutInstance(commandDTO.getClientTimeout());
        return (AdminCommandSimpleResultDTO) getCommandResult(AdminCommands.CHANGE_CLIENT_TIMEOUT, cmd, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Выгружает параметры подключения к базе данных")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Параметры подключения к базе данных выгружены.")})
    @GetMapping("/admins/commands/datasource")
    public AdminCommandResultWithDatasourceDTO getDatasource() throws AdminErrorException {
        return (AdminCommandResultWithDatasourceDTO) getCommandResult(AdminCommands.DATASOURCE, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Изменяет параметры подключения к базе данных")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Параметры подключения к базе данных изменены."),
            @ApiResponse(code = 400,
                    message = "Тело запроса не удовлетворило некоторым критериям. Пример критериев:\n" +
                            "- Строковые поля имеют пустое значение,\n" +
                            "- Строковые поля имеют нулевое значение,\n" +
                            "- Порт имеет невалидное значение,\n" +
                            "- IP-адрес имеет невалидное значение.\n")})
    @PutMapping("/admins/commands/datasource")
    public AdminCommandSimpleResultDTO changeDatasource(
            @ApiParam(required = true, value = "Для вступления изменений в силу необходимо перезагрузить сервер")
            @RequestBody AdminChangeDatabaseCommandDTO commandDTO) throws AdminErrorException {
        AdminCommand cmd = commandProducer.getAdminCommandDatabaseInstance(commandDTO);
        return (AdminCommandSimpleResultDTO) getCommandResult(AdminCommands.CHANGE_DATABASE, cmd, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Выгружает логи сервера")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Логи выгружены.")})
    @GetMapping("/admins/commands/logs")
    public AdminCommandResultWithLogsDTO requestLogs() throws AdminErrorException {
        return (AdminCommandResultWithLogsDTO) getCommandResult(AdminCommands.LOG, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Перезагружает LightSearch Server")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Перезагрузка прошла успешно.")})
    @GetMapping("/admins/commands/restart")
    public AdminCommandSimpleResultDTO restart() throws AdminErrorException {
        return (AdminCommandSimpleResultDTO) getCommandResult(AdminCommands.RESTART, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Object getCommandResult(String cmdName, AdminCommand cmd, HttpStatus status) throws AdminErrorException {
        AdminCommandResult cmdRes = processes.get(cmdName).apply(cmd);
        if(!cmdRes.isDone())
            throw new AdminErrorException(status, cmdRes.message());
        else
            return cmdRes.formForSend();
    }
}
