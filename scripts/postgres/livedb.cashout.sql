CREATE TABLE active_selections (
  selectionid       INT PRIMARY KEY,
  eventstamp        INT NOT NULL,
  currentprice      REAL NOT NULL
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