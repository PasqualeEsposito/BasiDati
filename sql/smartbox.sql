DROP DATABASE if exists Smartbox;

CREATE DATABASE Smartbox;

USE Smartbox;

CREATE TABLE Cliente (
	CF char(16) PRIMARY KEY,
    Nome varchar(30) NOT NULL,
    Cognome varchar(30) NOT NULL,
    email varchar(60) NOT NULL,
    Ddn date NOT NULL,
    Cellulare decimal(10)
);

CREATE TABLE Agenzia (
	Codice int PRIMARY KEY,
    Citta varchar(30) NOT NULL
);

CREATE TABLE Azienda (
	Codice int PRIMARY KEY,
    Tipo varchar(30) NOT NULL,
    Citta varchar(20) NOT NULL
);

CREATE TABLE Smartbox (
	ID int PRIMARY KEY,
    Cliente char(16) NOT NULL,
    Agenzia int NOT NULL,
    Ddi date NOT NULL,
    Ddf date NOT NULL,
    Prezzo decimal(5, 2) NOT NULL,
    FOREIGN KEY (Cliente) references Cliente(CF)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (Agenzia) references Agenzia(Codice)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE Servizio (
	Tipo varchar(30) NOT NULL,
    Smartbox int NOT NULL,
    Costo decimal(2) NOT NULL,
    PRIMARY KEY (Tipo, Smartbox),
    FOREIGN KEY (Smartbox) references Smartbox(ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Prenotazione (
	Smartbox int NOT NULL,
    Azienda int NOT NULL,
    PRIMARY KEY (Smartbox, Azienda),
    FOREIGN KEY (Smartbox) references Smartbox(ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    FOREIGN KEY (Azienda) references Azienda(Codice)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

INSERT INTO Cliente VALUES
('SPSPQL99P25G190F', 'Pasquale', 'Esposito', 'esposito.pascal@gmail.com', '1999-09-25', 3925739204),
('NPLSVR99D21G795M', 'Saverio', 'Napolitano', 'saverio.apolitano@gmail.com', '1999-04-21', 3332221133),
('SPSFLC73A18C495U', 'Felice', 'Esposito', 'felice.esposito@libero.com', '1973-01-18', NULL),
('SPSRBT00C29R456P', 'Roberto', 'Esposito', 'roberto.esposito@icloud.com', '2000-03-30', 3214354678),
('NNNFRN67E30T349K', 'Francesca', 'Annunziata', 'francesca.annunaizata@libero.com', '1983-05-30', 3336587302),
('NNNNTN99E22G190Y', 'Antonio', 'Annunziata', 'antonio.annunziata@icloud.it', '1999-04-22', 3274592094),
('SRRNCL81T13T543L', 'Nicola', 'Serra', 'nicola.serra@icloud.it', '1981-07-11', NULL);

INSERT INTO Agenzia VALUES
(1, 'Napoli'),
(2, 'Salerno'),
(3, 'Roma'),
(4, 'Torino'),
(5, 'Salerno'),
(6, 'Campobasso');

INSERT INTO Azienda VALUES
(1, 'Piscina', 'Firenze'),
(2, 'Ristorante', 'Matera'),
(3, 'Hotel', 'Foggia'),
(4, 'Museo', 'Napoli'),
(5, 'Teatro', 'Venezia'),
(6, 'Cinema', 'Milano'),
(7, 'Sala massaggi', 'Roma'),
(8, 'Palestra', 'Torino'),
(9, 'Concerto', 'Brescia');

INSERT INTO Smartbox VALUES
(1, 'SPSPQL99P25G190F', 1, '2017-05-26', '2019-05-26', 150),
(2, 'SPSRBT00C29R456P', 4, '2018-07-13', '2020-07-13', 200),
(3, 'NNNNTN99E22G190Y', 6, '2016-12-20', '2018-12-20', 50),
(4, 'SPSPQL99P25G190F', 2, '2017-12-30', '2019-12-30', 250),
(5, 'SRRNCL81T13T543L', 4, '2019-03-13', '2021-03-13', 500),
(6, 'SPSFLC73A18C495U', 3, '2018-11-12', '2020-11-12', 300),
(7, 'SRRNCL81T13T543L', 5, '2017-06-25', '2019-06-25', 100),
(8, 'SPSPQL99P25G190F', 6, '2014-03-30', '2018-03-30', 200),
(9, 'SPSRBT00C29R456P', 2, '2015-10-18', '2019-10-18', 150),
(10, 'SRRNCL81T13T543L', 1, '2014-08-11', '2017-08-11', 100),
(11, 'NPLSVR99D21G795M', 3, '2015-02-11', '2019-02-11', 150),
(12, 'NNNNTN99E22G190Y', 4, '2017-02-27', '2020-02-27', 250),
(13, 'NNNFRN67E30T349K', 5, '2018-01-10', '2021-01-10', 500),
(14, 'NPLSVR99D21G795M', 5, '2015-04-20', '2019-04-20', 300),
(15, 'SPSFLC73A18C495U', 2, '2018-07-14', '2021-07-14', 400),
(16, 'SRRNCL81T13T543L', 1, '2016-12-22', '2018-12-22', 600),
(17, 'NPLSVR99D21G795M', 5, '2019-03-01', '2022-03-01', 700),
(18, 'SPSPQL99P25G190F', 6, '2017-02-11', '2020-02-11', 500),
(19, 'SRRNCL81T13T543L', 2, '2015-12-11', '2019-12-11', 100),
(20, 'NNNFRN67E30T349K', 3, '2017-10-31', '2020-10-31', 50),
(21, 'SPSFLC73A18C495U', 4, '2015-12-14', '2017-12-14', 50),
(22, 'NPLSVR99D21G795M', 4, '2014-12-30', '2018-12-30', 50);

INSERT INTO Servizio VALUES
('Garanzia 1 anno', 2, 25),
('Garanzia 1 anno', 5, 25),
('Rimborso', 21, 30),
('Cancellazione prenotazione', 13, 20),
('Rimborso', 11, 30),
('Garanzia 3 anni', 6, 90),
('Garanzia 3 anni', 4, 90),
('Rimborso', 7, 30),
('Cancellazione prenotazione', 10, 20),
('Cancellazione prenotazione', 17, 20);

INSERT INTO Prenotazione VALUES
(1, 8),
(2, 2),
(3, 4),
(4, 6),
(7, 9),
(8, 1),
(10, 2),
(11, 5),
(12, 7),
(13, 5),
(15, 8),
(16, 9),
(17, 2),
(19, 3),
(21, 4);