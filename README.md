[![Build Status](https://travis-ci.com/viise/Lightsearch-Server.svg?branch=master)](https://travis-ci.com/viise/Lightsearch-Server)
<p align="center"> 
<img src="https://user-images.githubusercontent.com/43209824/64838878-905c6e00-d638-11e9-8026-e7b04d1af80f.png"
     width="300" height="300">
</p>

LightSearch Server
==================

LightSearch Server - RESTful сервер, обеспечивающий общение между клиентами LightSearch и программой, реализующей 
бизнес-логику, и реализующий необходимые функции для предприятия.

Принцип работы
--------------
Клиенты общаются с LightSearch Server через REST. Полная документация доступна при запуске сервера через Swagger.

LightSearch Server записывает команду клиента в таблицу LS_REQUEST и ожидает ответа от программы бизнес-логики в таблице
LS_RESPONSE. Однозначное соответствие команды и клиента осуществляется через поле LS_CODE, значение которого - 
уникальный SHA-256 хэш. Таким образом, сервер можно быстро и безболезненно внедрять в предприятие.

Вся бизнес-логика делегирована предприятию, таким образом, любое изменение в программе предприятия никак не повлияет на 
работу программного комплекса LightSearch.

<p align="center"> 
<img src="https://user-images.githubusercontent.com/43209824/75654977-a0cad880-5cac-11ea-883d-1db2585bfd01.png">
</p>

Пользователи
------------
LightSearch Server имеет три роли пользователей - admin, dev и user.
1) admin - Администратор LightSearch Server. Доступны черный список, список текущих клиентов, изменения параметров 
сервера, такие как время жизни jwt токена, параметры подключения к базе данных, и др.
2) dev - Разработчик LightSearch. Разработчику доступны все возможности администратора и документация Swagger.
3) user - Клиенты LightSearch. Используют доступные функции LightSearch.

Команды клиента
---------------
При подключении клиенты авторизуются в POST точке /clients/login. После успешной авторизации они получают jwt токен, 
который они используют, помещая в заголовок http, для вызова других команд сервера. На сервере для клиента доступны 
следующие команды:
- Авторизация (POST /clients/login)
- Зашифрованная авторизация (POST /clients/login/encrypted)
- Ключ шифрования для зашифрованной авторизации (GET /clients/login/key)
- Поиск (GET /clients/products?barcode=''&tk=''&sklad=''):
     * barcode - строка поиска. Может быть наименованием, частью наименования, коротким кодом, полным кодом, или 
     заводским штрих-кодом товара
     * tk - выбранный торговый комплекс
     * sklad - выбранный склад
- Привязка товара к заводскому штрих-коду (POST /clients/products/actions/bind)
- Проверка привязки товара к заводскому штрих-коду (POST /clients/products/actions/bind_check)
- Отвязка товара от неверного заводского штрих-кода (POST /clients/products/actions/unbind)
- Проверка отвязки товара от заводского штрих-кода (POST /clients/products/actions/unbind_check)
- Открытие мягкого чека (POST /clients/softChecks/actions/open)
- Отмена мягкого чека (POST /clients/softChecks/actions/cancel)
- Подтверждение товаров мягкого чека (POST /clients/softChecks/actions/confirm-products)
- Закрытие мягкого чека (POST /clients/softChecks/actions/close)
- Выгрузка списка складов (GET /clients/skladList)
- Выгрузка списка ТК (GET /clients/tkList)
- Проверка авторизации клиента (GET /clients/checkAuth)

## Зашифрованная авторизация
Зашифрованная авторизация доступна через точку POST /clients/login/encrypted. Для ее использования необходимо запросить
через точку GET /clients/login/key ключ шифрования. Эта точка выгружает ключ, тип алгоритма и сам алгоритм. На данный 
момент сделана реализация алгоритма RSA (RSA/ECB/OAEPWithSHA1AndMGF1Padding).

Команды администратора
----------------------
Администратор и разработчик подключаются к серверу через url /login. В форме они вводят авторизационные данные, и если 
они верны, то администратора перенаправляют на страницу панели администратора (admin-session.html), а разработчика - на 
страницу с документацией (swagger-ui.html).

Для администратора доступны следующие команды:
- Запрос черного списка (GET /admins/commands/blacklist)
- Добавление клиента в черный список (POST /admins/commands/blacklist)
- Удаление клиента из черного списка (DELETE /admins/commands/blacklist)
- Запрос списка текущих клиентов (POST /admins/commands/clients)
- Удаление клиента из списка текущих клиентов (DELETE /admins/commands/clients)
- Изменение времени тайм-аута клиента (время жизни jwt токена) (PUT /admins/commands/clients/timeout)
- Изменение параметров подключения к базе данных (PUT /admins/commands/datasource)
- Перезагрузка LightSearch Server (GET /admins/commands/restart)

Файл application.properties
---------------------------
Поместите файл application.properties в папку /config в той же директории, что и jar файл LightSearch Server. Настройки
типичные для любого приложения Spring Boot. Но есть некоторые особенности:
1) LightSearch Server не использует ORM. Поэтому включать его в настройках не нужно;
2) В файле application.properties определены некоторые уникальные свойства:
 - lightsearch.server.admin.username - имя администратора (для входа через точку /login);
 - lightsearch.server.dev.username - имя разработчика (для входа через точку /login);
 - lightsearch.server.admin.password - пароль администратора в формате bcrypt (для входа через точку /login);
 - lightsearch.server.dev.password - пароль разработчика в формате bcrypt (для входа через точку /login);
 - lightsearch.server.jwt-valid-day-count - время жизни JWT-токена, в днях;
 - lightsearch.server.jwt-secret - приватный ключ для JWT-токена (например, RSA);

Поддержка Docker <img src="https://user-images.githubusercontent.com/43209824/89869724-1a6dd780-dbf8-11ea-8a1b-2cae7cb9db14.png" width="371" height="88"/>
---------------------------
LightSearch поддерживает Docker. Перед сборкой контейнера не забудьте собрать проект при помощи `pom.xml`:

```bash
mvn clean package
```

Для сборки образа введите команду:
```bash
docker build --tag <tag_name> --file Dockerfile-lightsearch .
``` 
где `<tag_name>` - тэг образа, например, lightsearch-server:3.7.0.

Затем запустите образ:
```bash
docker run -p 8000:50000 --name <name> <tag_name>.
```
где `<name>` - имя контейнера,

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`<tag_name>` - тэг образа, созданного при помощи предыдущей команды.

В этой команде вы можете изменить порты `-p`, но второй порт обязательно должен указывать на порт, на котором запущен 
LightSearch внутри контейнера (порт, указанный в `application.properties`).  
#### Docker Compose <img src="https://user-images.githubusercontent.com/43209824/89871634-11cad080-dbfb-11ea-8cc7-e7580446c69a.png" width="87" height="111"/>
LightSearch можно запускать через Docker Compose. Для этого выполните команду:
```bash
docker-compose up
```
Docker Compose запустит два сервиса: `nginx` и `lightsearch-server`. Nginx служит прокси-сервером для LightSearch: весь
трафик проходит через него. Nginx позволяет работать LightSearch через HTTPS. Для HTTPS необходимо в папке 
`ls-nginx/key-selfsigned/` поместить сертификат, ключ, и DH сертификат. Для тестирования можно использовать скрипт 
`create-ssl-cert.sh`, который создаст папку `ls-nginx/key-selfsigned/`, в которой будет содержаться все необходимое для 
HTTPS.

Все настройки для Nginx лежат в папке `ls-nginx`.

Для запуска Docker Compose необходимо создать папки `lightsearch-v` и `nginx-v`. В папку lightsearch-v необходимо 
разместить файл `blacklist`, а в папку `nginx-v` - `nginx.conf`. В этом репозитории есть примеры этих папок.

После запуска Docker Compose в разделах `lightsearch-v` и `nginx-v` создаются директория `logs`, в которой содержатся 
логи LightSearch Server, и директория `log`, в которой содержатся логи Nginx, соответственно.

Для остановки сервисов необходимо ввести следующую команду:

```bash
docker-compose down
```

После любых изменений в файлах `docker-compose.yml`, `Dockerfile-lightsearch`, `Dockerfile-nginx`, Docker Compose 
необходимо запускать с ключом `--build`:
```bash
docker-compose up --build
```  

Это пересоберет все образы и пересоздаст контейнеры.

Ссылки
------
История проекта LightSearch доступна в [документах](https://github.com/ViiSE/LightSearch/tree/master/Documents/Project%20history)
и в [заметках разработки](https://github.com/ViiSE/LightSearch/blob/master/Dev%20notes).

Используемые библиотеки и технологии
------------------------------------
- [Spring Framework](https://github.com/spring-projects/spring-framework)
- [Spring Boot](https://github.com/spring-projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html)
- [Spring Security](https://github.com/spring-projects/spring-security)
- [TestNG](https://testng.org/doc/)
- [Thymeleaf](https://github.com/thymeleaf)
- [JsonWebToken](https://jwt.io)
- [Jaybird](https://github.com/FirebirdSQL/jaybird)
- [SpringFox](https://springfox.github.io/springfox)
- [Surefire](https://maven.apache.org/surefire/index.html)
- [Nginx](https://github.com/nginx/nginx)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://github.com/docker/compose)
