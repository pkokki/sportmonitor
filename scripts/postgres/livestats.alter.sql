ALTER TABLE match ADD COLUMN dbfa boolean;
ALTER TABLE match ADD COLUMN status TYPE varchar(256);
ALTER TABLE match ALTER COLUMN comment TYPE varchar(1024);
ALTER TABLE stats_season_meta ADD COLUMN stats_coverage_cup_roster boolean;
ALTER TABLE cup_round ALTER COLUMN name TYPE varchar(64);
