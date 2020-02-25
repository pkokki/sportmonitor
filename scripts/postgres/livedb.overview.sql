DROP TABLE IF EXISTS event_master_data;
CREATE TABLE event_master_data (
    eventid           INT NOT NULL,
    sessionstamp      INT NOT NULL CHECK(sessionstamp > 0),
    regionid          VARCHAR(16),
    regionname        VARCHAR(128),
    leagueid          VARCHAR(16),
    leaguename        VARCHAR(128),
    betradarid        INT NOT NULL,
    title             VARCHAR(128),
    starttime         VARCHAR(128),
    starttimeticks    INT NOT NULL,
    hometeam          VARCHAR(128),
    awayteam          VARCHAR(128),
    PRIMARY KEY(eventid, sessionstamp)
);

DROP TABLE IF EXISTS event_data;
CREATE TABLE event_data (
    eventid      INT NOT NULL,
    eventstamp   INT NOT NULL CHECK(eventstamp > 0),
    clocktime    VARCHAR(8),
    suspended    BOOLEAN NOT NULL,
    homescore    INT,
    homeredcards INT,
    awayscore    INT,
    awayredcards INT,
    PRIMARY KEY(eventid, eventstamp)
);

DROP TABLE IF EXISTS market_master_data;
CREATE TABLE market_master_data (
    marketid        INT NOT NULL,
    eventid         INT NOT NULL,
    sessionstamp    INT NOT NULL CHECK(sessionstamp > 0),
    description     VARCHAR(128),
    type            VARCHAR(8) NOT NULL,
    handicap        REAL NOT NULL,
    PRIMARY KEY(marketid, sessionstamp)
);

DROP TABLE IF EXISTS selection_master_data;
CREATE TABLE selection_master_data (
    selectionid       INT NOT NULL,
    sessionstamp      INT NOT NULL CHECK(sessionstamp > 0),
    description       VARCHAR(64),
    marketid          INT NOT NULL,
    PRIMARY KEY(selectionid, sessionstamp)
);

DROP TABLE IF EXISTS selection_data;
CREATE TABLE selection_data (
    selectionid       INT NOT NULL,
    eventstamp        INT NOT NULL CHECK(eventstamp > 0),
    active            BOOLEAN NOT NULL,
    prevprice         REAL NOT NULL,
    logprevprice      REAL NOT NULL,
    currentprice      REAL NOT NULL,
    logcurrentprice   REAL NOT NULL,
    pricediff         REAL NOT NULL,
    PRIMARY KEY(selectionid, eventstamp)
);

DROP TABLE IF EXISTS event_score_changes;
CREATE TABLE event_score_changes (
    eventid           INT,
    eventstamp        INT NOT NULL CHECK(eventstamp > 0),
    clocktime         VARCHAR(8),
    home              INT,
    away              INT,
    homediff          INT,
    awaydiff          INT,
    PRIMARY KEY(eventid, eventstamp)
);
