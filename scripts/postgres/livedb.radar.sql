CREATE TABLE match_timeline_events (
  id      BIGINT PRIMARY KEY,
  matchid BIGINT NOT NULL,
  uts     BIGINT NOT NULL,
  time    INT,
  seconds BIGINT,
  typeid  VARCHAR(16) NOT NULL,
  type    VARCHAR(128),
  team    VARCHAR(1)
);

CREATE TABLE match_situation_events (
  id      BIGINT PRIMARY KEY,
  matchid BIGINT NOT NULL,
  time INT,
  injurytime INT,
  safe INT,
  safecount INT,
  homeattack INT,
  homedangerous INT,
  homesafe INT,
  homeattackcount INT,
  homedangerouscount INT,
  homesafecount INT,
  awayattack INT,
  awaydangerous INT,
  awaysafe INT,
  awayattackcount INT,
  awaydangerouscount INT,
  awaysafecount INT
);

