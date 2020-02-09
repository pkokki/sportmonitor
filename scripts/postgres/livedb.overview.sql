CREATE TABLE event_master_data (
  eventId           BIGINT PRIMARY KEY,
  regionid          VARCHAR(16),
  regionname        VARCHAR(128),
  leagueid          VARCHAR(16),
  leaguename        VARCHAR(128),
  betradarid        BIGINT,
  betradarlink      VARCHAR(128),
  title             VARCHAR(128),
  shorttitle        VARCHAR(128),
  starttime         VARCHAR(128),
  starttimeticks    BIGINT,
  liveeventlink     VARCHAR(128),
  hometeam          VARCHAR(128),
  awayteam          VARCHAR(128)
);

CREATE TABLE event_data (
    eventid     BIGINT,
    timestamp   BIGINT,
    clocktime   VARCHAR(8),
    issuspended BOOLEAN,
    homescore   VARCHAR(2),
    homeredcards    INT,
    awayscore   VARCHAR(2),
    awayredcards    INT
);

CREATE TABLE market_data (
    eventid     BIGINT,
    timestamp   BIGINT,
    marketid    BIGINT,
    description VARCHAR(128),
    type        VARCHAR(8),
    handicap    REAL,
    issuspended BOOLEAN
);

CREATE TABLE selection_data (
    eventid     BIGINT,
    timestamp   BIGINT,
    marketid    BIGINT,
    description VARCHAR(128),
    price    REAL
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
