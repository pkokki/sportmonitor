DROP TABLE IF EXISTS match_timeline_events;
CREATE TABLE match_timeline_events (
    id          INT PRIMARY KEY,
    matchid     INT NOT NULL,
    eventstamp  INT NOT NULL,
    gameminute  INT,
    gameseconds INT,
    typeid      INT NOT NULL,
    team        VARCHAR(1)
);
DROP TABLE IF EXISTS match_timeline_types;
CREATE TABLE match_timeline_types (
    typeid      INT         PRIMARY KEY,
    description VARCHAR(128) NOT NULL
);

DROP TABLE IF EXISTS match_situation_events;
CREATE TABLE match_situation_events (
    id                  BIGINT PRIMARY KEY,
    matchid             INT NOT NULL,
    gametime            INT NOT NULL,
    injurytime          INT NOT NULL,
    eventstamp          INT NOT NULL,
    safe                INT,
    safecount           INT,
    homeattack          INT,
    homedangerous       INT,
    homesafe            INT,
    homeattackcount     INT,
    homedangerouscount  INT,
    homesafecount       INT,
    awayattack          INT,
    awaydangerous       INT,
    awaysafe            INT,
    awayattackcount     INT,
    awaydangerouscount  INT,
    awaysafecount       INT
);

DROP TABLE IF EXISTS match_detail_events;
CREATE TABLE match_detail_events (
    id          SERIAL PRIMARY KEY,
    matchid     INT NOT NULL,
    eventstamp  INT NOT NULL,
    typeid      VARCHAR(64) NOT NULL,
    home        INT,
    away        INT,
    hometext    VARCHAR(64),
    awaytext    VARCHAR(64)
);

DROP TABLE IF EXISTS match_detail_types;
CREATE TABLE match_detail_types (
    typeid      VARCHAR(64) PRIMARY KEY,
    description VARCHAR(128) NOT NULL
);

