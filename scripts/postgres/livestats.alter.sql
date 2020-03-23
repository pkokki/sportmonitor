ALTER TABLE match ADD COLUMN dbfa boolean;
ALTER TABLE match ADD COLUMN status TYPE varchar(256);
ALTER TABLE match ALTER COLUMN comment TYPE varchar(1024);
ALTER TABLE stats_season_meta ADD COLUMN stats_coverage_cup_roster boolean;
ALTER TABLE cup_round ALTER COLUMN name TYPE varchar(64);
ALTER TABLE player ADD COLUMN market_value bigint;

ALTER TABLE player ADD COLUMN height int;
ALTER TABLE player ADD COLUMN weight int;
ALTER TABLE player ADD COLUMN foot varchar(16);
ALTER TABLE player ADD COLUMN birth_country_id bigint;
ALTER TABLE player ADD COLUMN twitter varchar(128);
ALTER TABLE player ADD COLUMN facebook varchar(128);
ALTER TABLE player ADD COLUMN birth_place varchar(16);
