CREATE TABLE match_timeline_events (
    id          INT PRIMARY KEY,
    matchid     INT NOT NULL,
    uts         INT NOT NULL,
    time        INT,
    seconds     INT,
    typeid      VARCHAR(16) NOT NULL,
    type        VARCHAR(128),
    team        VARCHAR(1)
);

CREATE TABLE match_situation_events (
    id                  INT PRIMARY KEY,
    matchid             INT NOT NULL,
    time                INT,
    injurytime          INT,
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

CREATE TABLE match_detail_events (
    id          SERIAL PRIMARY KEY,
    matchid     INT NOT NULL,
    timestamp   INT NOT NULL,
    key         VARCHAR(64) NOT NULL,
    home        INT,
    away        INT,
    hometext    VARCHAR(64),
    awaytext    VARCHAR(64)
);

CREATE TABLE match_detail_types (
    key           VARCHAR(64) PRIMARY KEY,
    name          VARCHAR(128) NOT NULL
);

