--
-- Файл сгенерирован с помощью SQLiteStudio v3.1.1 в Ср дек 13 17:26:44 2017
--
-- Использованная кодировка текста: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Таблица: CONTACT
CREATE TABLE `CONTACT` (`ID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `CREATED_TIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);

-- Таблица: MESSAGE
CREATE TABLE MESSAGE (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ADDRESSEE_USER_ID INTEGER NOT NULL REFERENCES USER (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, DESTINATION_USER_ID INTEGER NOT NULL REFERENCES USER (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, CONTACT_ID INTEGER NOT NULL REFERENCES CONTACT (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, MESSAGE TEXT);

-- Таблица: MESSAGE_FILE
CREATE TABLE MESSAGE_FILE (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, MESSAGE_ID INTEGER NOT NULL REFERENCES MESSAGE (ID), CONTENT MEDIUMBLOB NOT NULL, EXTENSION VARCHAR (10));

-- Таблица: USER
CREATE TABLE USER (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, USER_NAME VARCHAR (255) NOT NULL, EMAIL VARCHAR (255), PHONE VARCHAR (45), USER_ROLE VARCHAR (45) DEFAULT UNAUTHORIZED, PASSWORD VARCHAR (45));

-- Таблица: USER_CONTACTS
CREATE TABLE USER_CONTACTS (
USER_ID INTEGER KEY NOT NULL
                REFERENCES USER (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, 
CONTACT_USER_ID INTEGER KEY NOT NULL 
                REFERENCES USER (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, 
CONTACT_ID INTEGER NOT NULL 
                REFERENCES CONTACT (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
PRIMARY KEY (USER_ID, CONTACT_USER_ID)                
);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
