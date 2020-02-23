CREATE TABLE event_master_data (
    eventid           INT PRIMARY KEY,
    timestamp         INT NOT NULL,
    regionid          VARCHAR(16),
    regionname        VARCHAR(128),
    leagueid          VARCHAR(16),
    leaguename        VARCHAR(128),
    betradarid        INT NOT NULL,
    title             VARCHAR(128),
    starttime         VARCHAR(128),
    starttimeticks    INT NOT NULL,
    hometeam          VARCHAR(128),
    awayteam          VARCHAR(128)
);
CREATE TABLE event_data (
    eventid      INT NOT NULL,
    timestamp    INT NOT NULL,
    clocktime    VARCHAR(8),
    suspended    BOOLEAN NOT NULL,
    homescore    INT,
    homeredcards INT,
    awayscore    INT,
    awayredcards INT,
    PRIMARY KEY(eventid, timestamp)
);

CREATE TABLE market_master_data (
    marketid    INT PRIMARY KEY,
    eventid     INT NOT NULL,
    timestamp   INT NOT NULL,
    description VARCHAR(128),
    type        VARCHAR(8) NOT NULL,
    handicap    REAL NOT NULL
);

CREATE TABLE selection_master_data (
    selectionid       INT PRIMARY KEY,
    timestamp         INT NOT NULL,
    description       VARCHAR(64),
    marketid          INT NOT NULL
);
CREATE TABLE selection_data (
    selectionid       INT NOT NULL,
    timestamp         INT NOT NULL,
    active          BOOLEAN NOT NULL,
    prevprice         REAL NOT NULL,
    logprevprice      REAL NOT NULL,
    currentprice      REAL NOT NULL,
    logcurrentprice   REAL NOT NULL,
    pricediff         REAL NOT NULL,
    PRIMARY KEY(selectionid, timestamp)
);

CREATE TABLE event_score_changes (
    eventid     INT,
    timestamp   INT,
    clocktime   VARCHAR(8),
    home        INT,
    away        INT,
    homediff    INT,
    awaydiff    INT
);
