DROP TABLE IF EXISTS LS_REQUEST;
DROP TABLE IF EXISTS LS_RESPONSE;

CREATE TABLE IF NOT EXISTS LS_REQUEST (
    KOD INT AUTO_INCREMENT PRIMARY KEY,
    LSCODE INT NOT NULL,
    DDOC TIMESTAMP NOT NULL,
    CMDIN VARCHAR, /* MUST BE BLOB! */
    STATE SMALLINT NOT NULL,
    ERR VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS LS_RESPONSE (
    KOD INT AUTO_INCREMENT PRIMARY KEY,
    LSCODE INT NOT NULL,
    DDOC TIMESTAMP NOT NULL,
    CMDOUT VARCHAR, /* MUST BE BLOB! */
    STATE SMALLINT NOT NULL,
    ERR VARCHAR(250)
);