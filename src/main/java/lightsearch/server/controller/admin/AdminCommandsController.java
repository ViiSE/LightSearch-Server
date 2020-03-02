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
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.entity.AdminCommandProducer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Api(tags="Admin Commands Controller", description = "Контроллер точек для работы с командами администратора")
@RestController
public class AdminCommandsController {

    private final LoggerServer logger;
    private final Processes<AdminCommand, AdminCommandResult> processes;
    private final AdminCommandProducer commandProducer;

    public AdminCommandsController(
            LoggerServer logger,
            Processes<AdminCommand, AdminCommandResult> processes,
            AdminCommandProducer commandProducer) {
        this.logger = logger;
        this.processes = processes;
        this.commandProducer = commandProducer;
    }

    @ApiOperation(value = "Выгружает черный список клиентов")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Черный список выгружен.")})
    @GetMapping("/admins/commands/blacklist")
    public AdminCommandResultWithBlacklistDTO requestBlacklist() {
        logger.info(AdminCommandsController.class, "Admin request blacklist");
        AdminCommandResultDTO resDTO = getCommandResult(AdminCommands.BLACKLIST, null);
        AdminCommandResultWithBlacklistDTO result = new AdminCommandResultWithBlacklistDTO(
                resDTO.getIsDone(), resDTO.getMessage());
        result.setBlacklist(resDTO.getBlacklist());
        return result;
    }

    @ApiOperation(value = "Добавляет клиента в черный список")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Клиент добавлен в черный список.")})
    @PostMapping("/admins/commands/blacklist")
    public AdminCommandSimpleResultDTO addToBlacklist(
            @ApiParam(required = true, value = "<code><b>adminCommand{imei}</b></code> можно указывать как в чистом " +
                    "виде, так и в виде хэш-строки.")
            @RequestBody AdminAddBlacklistCommandDTO commandDTO) {
        AdminCommand cmd = commandProducer.getAdminCommandAddBlacklistInstance(commandDTO.getIMEI());
        AdminCommandResultDTO resDTO = getCommandResult(AdminCommands.ADD_BLACKLIST, cmd);
        return getSimpleResult(resDTO);
    }

    @ApiOperation(value = "Удаляет клиента из черного списка")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Клиент удален из черного списка."),
            @ApiResponse(code = 404,
                    message = "Клиент не найден.")})
    @DeleteMapping("/admins/commands/blacklist")
    public AdminCommandSimpleResultDTO delFromBlacklist(
            @ApiParam(required = true, value = "<code><b>adminCommand{imei}</b></code> можно указывать как в чистом " +
                    "виде, так и в виде хэш-строки.")
            @RequestBody AdminDelBlacklistCommandDTO commandDTO) {
        AdminCommand cmd = commandProducer.getAdminCommandDelBlacklistInstance(commandDTO.getIMEI());
        AdminCommandResultDTO resDTO = getCommandResult(AdminCommands.DEL_BLACKLIST, cmd);
        AdminCommandSimpleResultDTO result = getSimpleResult(resDTO);
        if (!resDTO.getIsDone())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, resDTO.getMessage());
        return result;
    }

    @ApiOperation(value = "Выгружает список текущих клиентов")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Список клиентов выгружен.")})
    @GetMapping("/admins/commands/clients")
    public AdminCommandResultWithClientsDTO requestClient() {
        AdminCommandResultDTO resDTO = getCommandResult(AdminCommands.CLIENT_LIST, null);
        AdminCommandResultWithClientsDTO result = new AdminCommandResultWithClientsDTO(
                resDTO.getIsDone(),
                resDTO.getMessage());
        result.setClients(resDTO.getClients());
        return result;
    }

    @ApiOperation(value = "Удаляет клиента из списка текущих клиетов")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Клиент удален списка текущих клиетов."),
            @ApiResponse(code = 404,
                    message = "Клиент не найден.")})
    @DeleteMapping("/admins/commands/clients")
    public AdminCommandSimpleResultDTO delClient(
            @ApiParam(required = true, value = "<code><b>adminCommand{imei}</b></code> можно указывать как в чистом " +
                    "виде, так и в виде хэш-строки.")
            @RequestBody AdminKickCommandDTO commandDTO) {
        AdminCommand cmd = commandProducer.getAdminCommandClientKickInstance(commandDTO.getIMEI());
        AdminCommandResultDTO resDTO = getCommandResult(AdminCommands.KICK, cmd);
        AdminCommandSimpleResultDTO result = getSimpleResult(resDTO);

        if (!resDTO.getIsDone())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, resDTO.getMessage());
        return result;
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
            @RequestBody AdminClientTimeoutCommandDTO commandDTO) {
        AdminCommand cmd = commandProducer.getAdminCommandClientTimeoutInstance(commandDTO.getClientTimeout());
        AdminCommandResultDTO resDTO = getCommandResult(AdminCommands.CLIENT_TIMEOUT, cmd);
        AdminCommandSimpleResultDTO result = getSimpleResult(resDTO);
        if (!resDTO.getIsDone())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, resDTO.getMessage());
        return result;
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
            @RequestBody AdminChangeDatabaseCommandDTO commandDTO) {
        AdminCommandDTO cmdDTO = new AdminCommandDTO();
        cmdDTO.setDbName(commandDTO.getDbName());
        cmdDTO.setIp(commandDTO.getIp());
        cmdDTO.setPort(commandDTO.getPort());
        cmdDTO.setUsername(commandDTO.getUsername());
        cmdDTO.setPassword(commandDTO.getPassword());

        AdminCommand cmd = commandProducer.getAdminCommandDatabaseInstance(cmdDTO);
        AdminCommandResultDTO resDTO = getCommandResult(AdminCommands.CHANGE_DATABASE, cmd);
        AdminCommandSimpleResultDTO result = getSimpleResult(resDTO);
        if (!resDTO.getIsDone())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, resDTO.getMessage());
        return result;
    }

    @ApiOperation(value = "Перезагружает LightSearch Server")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Перезагрузка прошла успешно.")})
    @GetMapping("/admins/commands/restart")
    public AdminCommandSimpleResultDTO restart() {
        logger.info(AdminCommandsController.class, "Admin started server reboot process");
        AdminCommandResultDTO resDTO = getCommandResult(AdminCommands.RESTART, null);
        return getSimpleResult(resDTO);
    }

    private AdminCommandResultDTO getCommandResult(String cmdName, AdminCommand cmd) {
        return (AdminCommandResultDTO) processes.get(cmdName).apply(cmd).formForSend();
    }

    private AdminCommandSimpleResultDTO getSimpleResult(AdminCommandResultDTO cmdDTO) {
        return new AdminCommandSimpleResultDTO(cmdDTO.getIsDone(), cmdDTO.getMessage());
    }
}
