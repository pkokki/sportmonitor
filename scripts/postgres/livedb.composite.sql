DROP TABLE IF EXISTS data_points;
CREATE TABLE data_points (
  matchid       INT NOT NULL,
  stamp         INT NOT NULL,
  key           VARCHAR(64) NOT NULL,
  value         REAL NOT NULL,
  diff          REAL,
  PRIMARY KEY (matchid, stamp, key)
);