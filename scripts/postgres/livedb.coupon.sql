DROP TABLE IF EXISTS coupon_events;
CREATE TABLE coupon_events (
    eventid     INT NOT NULL,
    stamp       INT NOT NULL,
    title       VARCHAR(256) NOT NULL,
    href        VARCHAR(256) NOT NULL,
    country     VARCHAR(128) NOT NULL,
    league      VARCHAR(128) NOT NULL,
    eventtime   INT NOT NULL,
    live        BOOLEAN NOT NULL,
    betradarid  INT,
    stats       VARCHAR(256),
    PRIMARY KEY (eventid, stamp)
);

DROP TABLE IF EXISTS coupon_markets;
CREATE TABLE coupon_markets (
    marketid    INT NOT NULL,
    eventid     INT NOT NULL,
    stamp       INT NOT NULL,
    type        VARCHAR(4) NOT NULL,
    handicap    REAL,
    PRIMARY KEY (marketid, stamp)
);

-- coupon_selections (id, marketid, stamp, type, price)
DROP TABLE IF EXISTS coupon_selections;
CREATE TABLE coupon_selections (
    id          INT NOT NULL,
    marketid    INT NOT NULL,
    stamp       INT NOT NULL,
    type        VARCHAR(4) NOT NULL,
    price       REAL NOT NULL,
    PRIMARY KEY (id, stamp)
);
