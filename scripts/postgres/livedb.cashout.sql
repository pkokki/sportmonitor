CREATE TABLE active_selections (
  selectionid       INT PRIMARY KEY,
  description       VARCHAR(64),
  eventid           INT NOT NULL,
  marketid          INT NOT NULL,
  timestamp         INT NOT NULL,
  isactive          BOOLEAN NOT NULL,
  prevprice         REAL NOT NULL,
  logprevprice      REAL NOT NULL,
  currentprice      REAL NOT NULL,
  logcurrentprice   REAL NOT NULL,
  pricediff         REAL NOT NULL
);

CREATE TABLE bets (
    betid           VARCHAR(36) PRIMARY KEY,
    betstamp        INT NOT NULL,
    selections      INT NOT NULL,
    initialprice    REAL NOT NULL,
    cashoutprice    REAL NOT NULL,
    logcashoutprice REAL NOT NULL
);

CREATE TABLE bet_selections (
    selectionid     INT NOT NULL,
    betid           VARCHAR(36) NOT NULL,
    price           REAL NOT NULL,
    logprice        REAL NOT NULL
);