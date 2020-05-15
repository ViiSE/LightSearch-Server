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
 *
 */

package lightsearch.server.controller.client;

import io.swagger.annotations.*;
import lightsearch.server.cmd.Processes;
import lightsearch.server.constants.ClientCommands;
import lightsearch.server.constants.Commands;
import lightsearch.server.data.*;
import lightsearch.server.entity.ClientCommand;
import lightsearch.server.entity.ClientCommandResult;
import lightsearch.server.entity.Product;
import lightsearch.server.exception.ClientErrorException;
import lightsearch.server.producer.entity.ClientCommandProducer;
import lightsearch.server.producer.entity.ProductProducer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags="Clients Commands Controller", description = "Контроллер точек для работы с клиентами")
@RestController
public class ClientsCommandsController {

    private final Processes<ClientCommand, ClientCommandResult> processes;
    private final ClientCommandProducer cmdProducer;
    private final ProductProducer productProducer;

    public ClientsCommandsController(
            Processes<ClientCommand, ClientCommandResult> processes,
            ClientCommandProducer cmdProducer,
            ProductProducer productProducer) {
        this.processes = processes;
        this.cmdProducer = cmdProducer;
        this.productProducer = productProducer;
    }

    @ApiOperation(value = "Проверка авторизации пользователей LightSearch")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Пользователь авторизован."),
            @ApiResponse(code = 401,
                    message = "Пользователь не авторизован.")})
    @GetMapping("clients/checkAuth")
    public ClientCheckAuthCommandResultDTO checkAuth() {
        return ((ClientCheckAuthCommandResultDTO)
                processes.get(ClientCommands.CHECK_AUTH).apply(null).formForSend());
    }
    @ApiOperation(value = "Запрос ключа для авторизации в LightSearch (для точки clients/login/encrypted)",
            notes = "При вызове данной точки токен в HTTP заголовке указывать не нужно!")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ключ был передан")})
    @GetMapping("clients/login/key")
    public ClientCommandResultKeyDTO keyCommand() {
        return ((ClientCommandResultKeyDTO)
                processes.get(ClientCommands.KEY).apply(null).formForSend());
    }

    @ApiOperation(value = "Авторизация пользователей LightSearch")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "При успешной авторизации LightSearch выдает токен пользователю, с помощью которого он " +
                            "может вызывать другие команды, передавая его в HTTP заголовок, а также списки текущих " +
                            "складов и торговых комплексов (ТК)."),
            @ApiResponse(code = 401,
                    message = "Регистрационные данные, указанные клиентом, недействительны.")})
    @PostMapping("clients/login")
    public ClientLoginCommandResultDTO loginCommand(
            @ApiParam(required = true, value =
                    "<code><b>clientCommand{username}</b></code> - имя пользователя;\n" +
                    "<code><b>clientCommand{password}</b></code> - пароль пользователя;\n" +
                    "<code><b>clientCommand{ip}</b></code> - IP-адрес устройства пользователя;\n" +
                    "<code><b>clientCommand{model}</b></code> - название модели устройства пользователя;\n" +
                    "<code><b>clientCommand{os}</b></code> - название ОС устройства пользователя;\n" +
                    "<code><b>clientCommand{user_ident}</b></code> - идентификатор пользователя. Если идентификатор " +
                    "передавать не нужно, то необходимо поставить значение <code><b>0</b></code>;\n" +
                    "<code><b>clientCommand{imei}</b></code> - IMEI устройства пользователя." +
                    "\n___" +
                    "\n<i>Примечание: при вызове данной точки токен в HTTP заголовке указывать не нужно!</i>")
            @RequestBody ClientLoginCommandDTO cmdDTO) throws ClientErrorException {
        ClientCommand cmd = cmdProducer
                .getClientCommandWithUsernameAndPasswordInstance(
                        cmdProducer.getClientCommandWithIpInstance(
                                cmdProducer.getClientCommandWithModelInstance(
                                        cmdProducer.getClientCommandWithOsInstance(
                                                cmdProducer.getClientCommandWithUserIdentifierInstance(
                                                        cmdProducer.getClientCommandWithIMEIInstance(
                                                                cmdProducer.getClientCommandSimpleInstance(
                                                                        ClientCommands.LOGIN),
                                                                cmdDTO.getIMEI()),
                                                        cmdDTO.getUserIdentifier()),
                                                cmdDTO.getOs()),
                                        cmdDTO.getModel()),
                                cmdDTO.getIp()),
                        cmdDTO.getUsername(),
                        cmdDTO.getPassword());
        return (ClientLoginCommandResultDTO) getCommandResult(ClientCommands.LOGIN, cmd, HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "Авторизация пользователей LightSearch (зашифрованная)",
            notes = "Для использования этой точки необходимо запросить открытый ключ для шифрования через " +
                    "/clients/login/key.")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "При успешной авторизации LightSearch выдает токен пользователю, с помощью которого он " +
                            "может вызывать другие команды, передавая его в HTTP заголовок, а также списки текущих " +
                            "складов и торговых комплексов (ТК)."),
            @ApiResponse(code = 401,
                    message = "Регистрационные данные, указанные клиентом, недействительны.")})
    @PostMapping("clients/login/encrypted")
    public ClientLoginCommandResultDTO loginEncryptedCommand(
            @ApiParam(required = true, value =
                    "В поле <code><b>clientCommand{data}</b></code> необходимо поместить зашифрованную " +
                            "информацию о клиенте. Это поле должно содержать те же поля, что и в " +
                            "<code>clients/login</code>, зашифрованные при помощи ключа, который можно получить в " +
                            "<code>clients/login/key</code>." +
                            "\n___" +
                            "\n<i>Примечание: при вызове данной точки токен в HTTP заголовке указывать не нужно!</i>")
            @RequestBody ClientLoginEncryptedCommandDTO cmdDTO) throws ClientErrorException {
        ClientCommand cmd = cmdProducer
                .getClientCommandWithEncryptedDataInstance(
                        cmdProducer.getClientCommandSimpleInstance(
                                ClientCommands.LOGIN_ENCRYPTED),
                        cmdDTO.getData());
        return (ClientLoginCommandResultDTO) getCommandResult(ClientCommands.LOGIN_ENCRYPTED, cmd, HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "Поиск товаров", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Выгружает все товары, найденные по запросу."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>."),
            @ApiResponse(code = 500,
                    message = "Произошла внутренняя ошибка в LightSearch.")})
    @GetMapping("/clients/products")
    public ClientSearchCommandResultDTO requestProducts(
            @ApiParam(required = true, value = "Можно указывать штрих-код, короткий код, наименование или часть " +
                    "наименования товара.")
            @RequestParam String barcode,
            @ApiParam(value = "Доступные значения: \"null\" - поиск не идет по складам, \"all\" - поиск по всем " +
                    "складам, или конкретное название склада. <i>По умолчанию - все склады.</i>")
            @RequestParam(required = false, defaultValue = Commands.ALL) String sklad,
            @ApiParam(value = "Доступные значения: \"null\" - поиск не идет по ТК, \"all\" - поиск по всем ТК, или " +
                    "конкретное название ТК. <i>По умолчанию - все ТК.</i>")
            @RequestParam(required = false, defaultValue = Commands.ALL, name = "tk") String TK) throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandWithBarcodeInstance(
                cmdProducer.getClientCommandWithSkladInstance(
                        cmdProducer.getClientCommandWithTKInstance(
                                cmdProducer.getClientCommandSimpleInstance(
                                        ClientCommands.SEARCH),
                                TK),
                        sklad),
                barcode);
        return (ClientSearchCommandResultDTO) getCommandResult(ClientCommands.SEARCH, cmd, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Проверка привязки товара к заводскому штрих-коду", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Выгружает все товары, к которым необходимо привязать заводской штрих-код, или товар, " +
                            "который имеет привязку по данному заводскому штрих-коду."),
            @ApiResponse(code = 400,
                    message = "Если поле <b>clientCommand{check_ean13}</b> имело значение <b>true</b> в теле запроса," +
                            " и если в поле <b>clientCommand{barcode}</b> был передан заводской штрих-код, не " +
                            "являющимся стандартом EAN-13."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>.")})
    @PostMapping("/clients/products/actions/bind_check")
    public ClientBindCheckCommandResultDTO bindCheck(
            @ApiParam(required = true, value = "Если поле <code><b>clientCommand{check_ean13}</b></code> имеет " +
                    "значение <code><b>true</b></code>, то будет осуществляться поиск привязаного к этому " +
                    "заводскому штрих-коду, указанному в поле <code><b>clientCommand{barcode}</b></code>, товара. " +
                    "Иначе будет осуществляться поиск товара, к которому можно привязать заводской штрих-код. В этом " +
                    "случае в поле <code><b>clientCommand{barcode}</b></code> необходимо указывать наименование, " +
                    "часть наименования или короткий штрих-код товара.\n" +
                    "Если при проверке привязки товара к указанному заводскому штрих-коду в поле " +
                    "<code><b>clientCommand{barcode}</b></code> не будет найден привязанный товар к этому штрих-коду," +
                    " то в поле <code><b>clientCommand{data}</b></code> придет пустой список.")
            @RequestBody ClientBindCheckCommandDTO cmdDTO) throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandWithBarcodeInstance(
                cmdProducer.getClientCommandWithCheckEAN13Instance(
                        cmdProducer.getClientCommandSimpleInstance(
                                ClientCommands.BIND_CHECK),
                        cmdDTO.getCheckEan13()),
                cmdDTO.getBarcode());
        return (ClientBindCheckCommandResultDTO) getCommandResult(ClientCommands.BIND_CHECK, cmd, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Привязка товара к заводскому штрих-коду", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Товар привязан успешно."),
            @ApiResponse(code = 400,
                    message = "Тело запроса не удовлетворило некоторым критериям. Пример критериев:\n" +
                            "- Заводской штрих-код не является стандартом EAN-13,\n" +
                            "- Заводской штрих-код уже привязан к существующему товару.\n"),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>.")})
    @PostMapping("/clients/products/actions/bind")
    public ClientBindCommandResultDTO bind(
            @ApiParam(required = true, value = "" +
                    "<code><b>clientCommand{barcode}</b></code> - короткий код товара;\n" +
                    "<code><b>clientCommand{factory_barcode}</b></code> - заводской штрих-код, который привязывается " +
                    "к товару;\n" +
                    "<code><b>clientCommand{user_ident}</b></code> - уникальный идентификатор пользователя.")
            @RequestBody ClientBindCommandDTO cmdDTO) throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandWithFactoryBarcodeInstance(
                cmdProducer.getClientCommandWithBarcodeInstance(
                        cmdProducer.getClientCommandWithUserIdentifierInstance(
                                cmdProducer.getClientCommandSimpleInstance(
                                        ClientCommands.BIND),
                                cmdDTO.getUserIdentifier()),
                        cmdDTO.getBarcode()),
                cmdDTO.getFactoryBarcode());
        return (ClientBindCommandResultDTO) getCommandResult(ClientCommands.BIND, cmd, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Проверка отвязки товара от заводского штрих-кода", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Выгружает товар, к которому привязан заводской штрих-код."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>.")})
    @PostMapping("/clients/products/actions/unbind_check")
    public ClientUnbindCheckCommandResultDTO unbindCheck(
            @ApiParam(required = true, value = "Будет осуществляться поиск привязаного к заводскому штрих-коду, " +
                    "указанному в поле <code><b>clientCommand{barcode}</b></code>, товара. Если при проверке товара " +
                    "к указанному заводскому штрих-коду в поле <code><b>clientCommand{barcode}</b></code> не будет " +
                    "найден привязанный товар к этому штрих-коду, то в поле <code><b>clientCommand{data}</b></code> " +
                    "придет пустой список.")
            @RequestBody ClientUnbindCheckCommandDTO cmdDTO) throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandWithBarcodeInstance(
                cmdProducer.getClientCommandSimpleInstance(
                        ClientCommands.UNBIND_CHECK),
                cmdDTO.getBarcode());
        return (ClientUnbindCheckCommandResultDTO) getCommandResult(ClientCommands.UNBIND_CHECK, cmd, HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "Отвязка товара от заводского штрих-кода", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Товар отвязан успешно."),
            @ApiResponse(code = 400,
                    message = "Тело запроса не удовлетворило некоторым критериям. Пример критериев:\n" +
                            "- Заводской штрих-код не привязан ни к одному из существующих товаров.\n"),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>.")})
    @PostMapping("/clients/products/actions/unbind")
    public ClientUnbindCommandResultDTO unbind(
            @ApiParam(required = true, value =
                    "<code><b>clientCommand{factory_barcode}</b></code> - заводской штрих-код товара;\n" +
                    "<code><b>clientCommand{user_ident}</b></code> - уникальный идентификатор пользователя.")
            @RequestBody ClientUnbindCommandDTO cmdDTO) throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandWithFactoryBarcodeInstance(
                cmdProducer.getClientCommandWithUserIdentifierInstance(
                        cmdProducer.getClientCommandSimpleInstance(
                                ClientCommands.UNBIND),
                        cmdDTO.getUserIdentifier()),
                cmdDTO.getFactoryBarcode());
        return (ClientUnbindCommandResultDTO) getCommandResult(ClientCommands.UNBIND, cmd, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Открытие мягкого чека", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Мягкий чек открыт."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>."),
            @ApiResponse(code = 500,
                    message = "Не удалось открыть мягкий чек.")})
    @PostMapping("/clients/softChecks/actions/open")
    public ClientOpenSoftCheckCommandResultDTO openSoftCheck(
            @ApiParam(required = true, value = "" +
                    "<code><b>clientCommand{card_code}</b></code> - код карточки, за которой будет закреплен мягкий " +
                    "чек;\n" +
                    "<code><b>clientCommand{user_ident}</b></code> - уникальный идентификатор пользователя.\n" +
                    "Мягкий чек может быть открыт только в том случае, если за карточкой " +
                    "<code><b>clientCommand{card_code}</b></code> не закреплен существующий мягкий чек.")
            @RequestBody ClientOpenSoftCheckCommandDTO cmdDTO) throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandWithUserIdentifierInstance(
                cmdProducer.getClientCommandWithCardCodeInstance(
                        cmdProducer.getClientCommandSimpleInstance(ClientCommands.OPEN_SOFT_CHECK),
                        cmdDTO.getCardCode()),
                cmdDTO.getUserIdentifier());
        return (ClientOpenSoftCheckCommandResultDTO) getCommandResult(ClientCommands.OPEN_SOFT_CHECK, cmd, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Отмена мягкого чека", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Мягкий чек отменен."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>."),
            @ApiResponse(code = 500,
                    message = "Не удалось отменить мягкий чек.")})
    @PostMapping("/clients/softChecks/actions/cancel")
    public ClientCancelSoftCheckCommandResultDTO cancelSoftCheck(
            @ApiParam(required = true, value = "" +
                    "<code><b>clientCommand{card_code}</b></code> - код карточки, за которой закреплен существующий " +
                    " мягкий чек;\n" +
                    "<code><b>clientCommand{user_ident}</b></code> - уникальный идентификатор пользователя.\n" +
                    "Мягкий чек может быть отменен только в том случае, если за карточкой " +
                    "<code><b>clientCommand{card_code}</b></code> закреплен существующий мягкий чек.")
            @RequestBody ClientCancelSoftCheckCommandDTO cmdDTO) throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandWithUserIdentifierInstance(
                cmdProducer.getClientCommandWithCardCodeInstance(
                        cmdProducer.getClientCommandSimpleInstance(ClientCommands.CANCEL_SOFT_CHECK),
                        cmdDTO.getCardCode()),
                cmdDTO.getUserIdentifier());
        return (ClientCancelSoftCheckCommandResultDTO) getCommandResult(ClientCommands.CANCEL_SOFT_CHECK, cmd, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Обновление остатков товаров мягкого чека", authorizations = {@Authorization(value = "Bearer")},
            notes = "Запрашивает остатки указанных товаров мягкого чека. Мягкий чек должен быть открыт!")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Выгружает новые остатки товаров мягкого чека."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>."),
            @ApiResponse(code = 500,
                    message = "Произошла внутренняя ошибка в LightSearch.")})
    @GetMapping("/clients/softChecks/actions/update-products")
    public ClientSoftCheckUpdateProductsCommandResultDTO updateProducts(
            @ApiParam(required = true, value = "Уникальный идентификатор пользователя, за которым закреплен мягкий чек.")
            @RequestParam(name = "user_ident") String userIdent,
            @ApiParam(required = true, value = "Код карточки, за которым закреплен мягкий чек.")
            @RequestParam(name = "card_code") String cardCode,
            @ApiParam(required = true, value = "Список с id товаров, остатки которых нужно обновить")
            @RequestParam(name = "products_id") List<String> productsId) throws ClientErrorException {
        List<Product> products = new ArrayList<>();
        productsId.forEach(id -> {
            Product pr = productProducer.getProductSimpleInstance(id);
            products.add(pr);
        });
        ClientCommand cmd = cmdProducer.getClientCommandWithProductsInstance(
                cmdProducer.getClientCommandWithUserIdentifierInstance(
                        cmdProducer.getClientCommandWithCardCodeInstance(
                                cmdProducer.getClientCommandSimpleInstance(ClientCommands.UPDATE_SOFT_CHECK_PRODUCTS),
                                cardCode),
                        userIdent),
                products);
        return (ClientSoftCheckUpdateProductsCommandResultDTO) getCommandResult(ClientCommands.UPDATE_SOFT_CHECK_PRODUCTS, cmd, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Подтвердить товары мягкого чека", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Если поле <b>clientCommand{data}</b> - пустой список, то товары подтвердились успешно." +
                            " Иначе в мягком чеке присутствуют товары, не прошедшие проверку. Например, их количество " +
                            "превышает количество свободных отстатков на складах."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>.")})
    @PostMapping("/clients/softChecks/actions/confirm-products")
    public ClientConfirmSoftCheckProductsCommandResultDTO confirmSoftCheckProducts(
            @ApiParam(required = true, value = "" +
                    "<code><b>clientCommand{card_code}</b></code> - код карточки, за которой закреплен существующий " +
                    " мягкий чек;\n" +
                    "<code><b>clientCommand{user_ident}</b></code> - уникальный идентификатор пользователя;\n" +
                    "<code><b>clientCommand{data}</b></code> - товары мягкого чека.\n" +
                    "Товары мягкого чека могут быть подтверждены только в том случае, если за карточкой " +
                    "<code><b>clientCommand{card_code}</b></code> закреплен существующий мягкий чек.")
            @RequestBody ClientConfirmSoftCheckProductsCommandDTO cmdDTO) throws ClientErrorException {
        List<Product> products = new ArrayList<>();
        cmdDTO.getData().forEach(prDTO -> {
            Product pr = productProducer.getProductWithAmountInstance(
                    productProducer.getProductSimpleInstance(prDTO.getId()),
                    Float.parseFloat(prDTO.getAmount()));
            products.add(pr);
        });

        ClientCommand cmd = cmdProducer.getClientCommandWithProductsInstance(
                cmdProducer.getClientCommandWithUserIdentifierInstance(
                        cmdProducer.getClientCommandWithCardCodeInstance(
                                cmdProducer.getClientCommandSimpleInstance(
                                        ClientCommands.CONFIRM_SOFT_CHECK_PRODUCTS),
                                cmdDTO.getCardCode()),
                        cmdDTO.getUserIdentifier()),
                products);
        return (ClientConfirmSoftCheckProductsCommandResultDTO) getCommandResult(ClientCommands.CONFIRM_SOFT_CHECK_PRODUCTS, cmd, HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "Закрытие мягкого чека", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Мягкий чек закрыт."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>.")})
    @PostMapping("/clients/softChecks/actions/close")
    public ClientCloseSoftCheckCommandResultDTO closeSoftCheckProducts(
            @ApiParam(required = true, value = "" +
                    "<code><b>clientCommand{card_code}</b></code> - код карточки, за которой закреплен существующий " +
                    " мягкий чек;\n" +
                    "<code><b>clientCommand{user_ident}</b></code> - уникальный идентификатор пользователя;\n" +
                    "<code><b>clientCommand{delivery}</b></code> - способ доставки.\n" +
                    "Мягкий чек может быть закрыт только в том случае, если за карточкой " +
                    "<code><b>clientCommand{card_code}</b></code> закреплен существующий мягкий чек.")
            @RequestBody ClientCloseSoftCheckCommandDTO cmdDTO) throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandWithUserIdentifierInstance(
                cmdProducer.getClientCommandWithCardCodeInstance(
                        cmdProducer.getClientCommandWithDeliveryInstance(
                                cmdProducer.getClientCommandSimpleInstance(
                                        ClientCommands.CLOSE_SOFT_CHECK),
                                cmdDTO.getDelivery()),
                        cmdDTO.getCardCode()),
                cmdDTO.getUserIdentifier());
        return (ClientCloseSoftCheckCommandResultDTO) getCommandResult(ClientCommands.CLOSE_SOFT_CHECK, cmd, HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "Поиск товаров для мягкого чека", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Выгружает все товары, находящихся в том же подразделении, что и пользователь с именем " +
                            "<b>username</b>."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>."),
            @ApiResponse(code = 500,
                    message = "Произошла внутренняя ошибка в LightSearch.")})
    @GetMapping("/clients/softChecks/products")
    public ClientSearchCommandResultDTO requestSoftChecksProducts(
            @ApiParam(required = true, value = "Можно указывать штрих-код, короткий код, наименование или часть " +
                    "наименования товара.")
            @RequestParam String barcode,
            @ApiParam(required = true, value = "Имя пользователя")
            @RequestParam(required = false) String username) throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandWithBarcodeInstance(
                cmdProducer.getClientCommandWithUsernameInstance(
                        cmdProducer.getClientCommandSimpleInstance(
                                ClientCommands.SEARCH_SOFT_CHECK),
                        username),
                barcode);
        return (ClientSearchCommandResultDTO) getCommandResult(ClientCommands.SEARCH_SOFT_CHECK, cmd, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Выгружает список всех складов предприятия", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Выгружает все склады предприятия."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>."),
            @ApiResponse(code = 500,
                    message = "Произошла внутренняя ошибка в LightSearch.")})
    @GetMapping("/clients/skladList")
    public ClientSkladListCommandResultDTO requestSkladList() throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandSimpleInstance(ClientCommands.SKLAD_LIST);
        return (ClientSkladListCommandResultDTO) getCommandResult(ClientCommands.SKLAD_LIST, cmd, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "Выгружает список всех ТК предприятия", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Выгружает все ТК предприятия."),
            @ApiResponse(code = 401,
                    message = "Для использования данной точки необходимо авторизоваться в LightSearch через точку " +
                            "<b>/clients/login</b>."),
            @ApiResponse(code = 500,
                    message = "Произошла внутренняя ошибка в LightSearch.")})
    @GetMapping("/clients/tkList")
    public ClientTKListCommandResultDTO requestTKList() throws ClientErrorException {
        ClientCommand cmd = cmdProducer.getClientCommandSimpleInstance(ClientCommands.TK_LIST);
        return (ClientTKListCommandResultDTO) getCommandResult(ClientCommands.TK_LIST, cmd, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Object getCommandResult(String cmdName, ClientCommand cmd, HttpStatus status) throws ClientErrorException {
        ClientCommandResult cmdRes = processes.get(cmdName).apply(cmd);
        if(!cmdRes.isDone())
            throw new ClientErrorException(status, cmdRes.message());
        else
            return cmdRes.formForSend();
    }
}
