DROP TABLE IF EXISTS country;
CREATE TABLE country (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	name text,
	code text,
	continent_id int,
	continent text,
	population bigint,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS sport;
CREATE TABLE sport (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	name text,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS match_type;
CREATE TABLE match_type (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	name text,
	set_type_id bigint,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS table_type;
CREATE TABLE table_type (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	name text,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS table_round;
CREATE TABLE table_round (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	name text,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS unique_team_goal_stats;
CREATE TABLE unique_team_goal_stats (
	unique_team_id bigint NOT NULL,
	ts bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	matches int,
	scored_sum int,
	scored0015 int,
	scored1630 int,
	scored3145 int,
	scored4660 int,
	scored6175 int,
	scored7690 int,
	conceded_sum int,
	conceded0015 int,
	conceded1630 int,
	conceded3145 int,
	conceded4660 int,
	conceded6175 int,
	conceded7690 int,
	first_goal int,
	last_goal int,
	penalty_success_count int,
	penalty_fail_count int,
	PRIMARY KEY (unique_team_id, ts)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS season_goal_stats;
CREATE TABLE season_goal_stats (
	season_id bigint NOT NULL,
	ts bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	matches int,
	scored_sum int,
	scored0015 int,
	scored1630 int,
	scored3145 int,
	scored4660 int,
	scored6175 int,
	scored7690 int,
	PRIMARY KEY (season_id, ts)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS season_league_summary;
CREATE TABLE season_league_summary (
	season_id bigint NOT NULL,
	ts bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	matches_played int,
	goals_total int,
	matches_home_wins real,
	matches_draws real,
	matches_away_wins real,
	goals_per_match real,
	goals_per_match_home real,
	goals_per_match_away real,
	over_under05 real,
	over_under15 real,
	over_under25 real,
	over_under35 real,
	over_under45 real,
	over_under55 real,
	PRIMARY KEY (season_id, ts)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS season_over_under;
CREATE TABLE season_over_under (
	season_id bigint NOT NULL,
	ts bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	matches int,
	goalsscored_ft_average real,
	goalsscored_ft_total int,
	goalsscored_ft_matches int,
	goalsscored_p1_average real,
	goalsscored_p1_total int,
	goalsscored_p1_matches int,
	goalsscored_p2_average real,
	goalsscored_p2_total int,
	goalsscored_p2_matches int,
	conceded_ft_average real,
	conceded_ft_total int,
	conceded_ft_matches int,
	conceded_p1_average real,
	conceded_p1_total int,
	conceded_p1_matches int,
	conceded_p2_average real,
	conceded_p2_total int,
	conceded_p2_matches int,
	ft05_totalover int,
	ft05_over int,
	ft05_under int,
	ft15_totalover int,
	ft15_over int,
	ft15_under int,
	ft25_totalover int,
	ft25_over int,
	ft25_under int,
	ft35_totalover int,
	ft35_over int,
	ft35_under int,
	ft45_totalover int,
	ft45_over int,
	ft45_under int,
	ft55_totalover int,
	ft55_over int,
	ft55_under int,
	p105_totalover int,
	p105_over int,
	p105_under int,
	p115_totalover int,
	p115_over int,
	p115_under int,
	p125_totalover int,
	p125_over int,
	p125_under int,
	p135_totalover int,
	p135_over int,
	p135_under int,
	p145_totalover int,
	p145_over int,
	p145_under int,
	p155_totalover int,
	p155_over int,
	p155_under int,
	p205_totalover int,
	p205_over int,
	p205_under int,
	p215_totalover int,
	p215_over int,
	p215_under int,
	p225_totalover int,
	p225_over int,
	p225_under int,
	p235_totalover int,
	p235_over int,
	p235_under int,
	p245_totalover int,
	p245_over int,
	p245_under int,
	p255_totalover int,
	p255_over int,
	p255_under int,
	PRIMARY KEY (season_id, ts)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS tie_break_rule;
CREATE TABLE tie_break_rule (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	name text,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS promotion;
CREATE TABLE promotion (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	name text,
	short_name text,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS stadium;
CREATE TABLE stadium (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	country_id bigint,
	name text,
	city text,
	country text,
	capacity text,
	constr_year text,
	googlecoords text,
	pitchsize_x int,
	pitchsize_y int,
	address text,
	url text,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (country_id) REFERENCES country (id)
);
DROP TABLE IF EXISTS stadium_unique_team;
CREATE TABLE stadium_unique_team (
	stadium_id bigint NOT NULL,
	unique_team_id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	PRIMARY KEY (stadium_id, unique_team_id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS top_list_entry;
CREATE TABLE top_list_entry (
	team_id bigint NOT NULL,
	player_id bigint NOT NULL,
	entry_type int NOT NULL,
	ts bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	total_assists int,
	total_matches int,
	substituted_in int,
	minutes_played int,
	total_yellow_cards int,
	total_yellow_red_cards int,
	total_red_cards int,
	total_first_half_cards int,
	total_second_half_cards int,
	total_goals int,
	total_penalties int,
	goal_points int,
	first_goals int,
	last_goals int,
	home_goals int,
	away_goals int,
	first_half_goals int,
	second_half_goals int,
	PRIMARY KEY (team_id, player_id, entry_type, ts)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS player_position_type;
CREATE TABLE player_position_type (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	type text,
	name text,
	short_name text,
	abbr text,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS cup_round;
CREATE TABLE cup_round (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	name text,
	short_name text,
	statistics_sort_order int,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS bookmaker;
CREATE TABLE bookmaker (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	name text,
	url text,
	exchange boolean,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS unique_team_player;
CREATE TABLE unique_team_player (
	team_id bigint NOT NULL,
	player_id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	start_time bigint,
	type int,
	active boolean,
	shirt text,
	end_time bigint,
	total_yellow_cards int,
	total_matches int,
	total_shots_off_goal int,
	total_minutes_played int,
	total_substituted_out int,
	total_team_scored int,
	total_team_conceded int,
	total_total_shots int,
	total_matches_won int,
	total_matches_lost int,
	total_matches_drawn int,
	total_own_goals int,
	total_number_of_cards1st_half int,
	total_number_of_cards2nd_half int,
	total_yellowred_cards int,
	total_shots_blocked int,
	total_substituted_in int,
	total_goals int,
	total_offside int,
	total_shots_on_goal int,
	total_goal_points int,
	total_first_goals int,
	home_goals int,
	first_half_goals int,
	second_half_goals int,
	total_goals_by_header int,
	total_corners int,
	total_last_goals int,
	away_goals int,
	total_assists int,
	total_red_cards int,
	total_penalties int,
	PRIMARY KEY (team_id, player_id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS unique_team_season;
CREATE TABLE unique_team_season (
	team_id bigint NOT NULL,
	season_id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	average_starting_xi_age real,
	PRIMARY KEY (team_id, season_id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS unique_team_season_player;
CREATE TABLE unique_team_season_player (
	team_id bigint NOT NULL,
	season_id bigint NOT NULL,
	player_id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	active boolean,
	last_event text,
	started int,
	yellow_cards int,
	yellowred_cards int,
	matches int,
	shots_off_goal int,
	minutes_played int,
	substituted_out int,
	team_scored int,
	team_conceded int,
	total_shots int,
	matches_won int,
	matches_lost int,
	matches_drawn int,
	own_goals int,
	number_of_cards1st_half int,
	number_of_cards2nd_half int,
	team_matches int,
	shots_blocked int,
	substituted_in int,
	goals int,
	offside int,
	shots_on_goal int,
	goal_points int,
	first_goals int,
	goals_by_header int,
	corners int,
	last_goals int,
	red_cards int,
	assists int,
	penalties int,
	PRIMARY KEY (team_id, season_id, player_id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
);
DROP TABLE IF EXISTS real_category;
CREATE TABLE real_category (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	name text,
	country_id bigint,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (country_id) REFERENCES country (id)
);
DROP TABLE IF EXISTS unique_tournament;
CREATE TABLE unique_tournament (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	real_category_id bigint,
	name text,
	friendly boolean,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (real_category_id) REFERENCES real_category (id)
);
DROP TABLE IF EXISTS odds;
CREATE TABLE odds (
	match_id bigint NOT NULL,
	ts bigint NOT NULL,
	ise int NOT NULL,
	source_id bigint,
	source_ts bigint,
	home_odds real,
	draw_odds real,
	away_odds real,
	bookmaker_id bigint,
	home_change real,
	away_change real,
	type text,
	odds_type_id int,
	exchange boolean,
	key text,
	draw_change real,
	extra text,
	closing_time text,
	PRIMARY KEY (match_id, ts, ise)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (bookmaker_id) REFERENCES bookmaker (id)
);
DROP TABLE IF EXISTS player;
CREATE TABLE player (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	nationality_id bigint,
	second_nationality_id bigint,
	position_id bigint,
	name text,
	full_name text,
	birth_date bigint,
	market_value bigint,
	height int,
	weight int,
	foot text,
	birth_country_id bigint,
	birth_place text,
	twitter text,
	facebook text,
	nickname text,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (nationality_id) REFERENCES country (id)
	-- , FOREIGN KEY (second_nationality_id) REFERENCES country (id)
	-- , FOREIGN KEY (position_id) REFERENCES player_position_type (id)
	-- , FOREIGN KEY (birth_country_id) REFERENCES country (id)
);
DROP TABLE IF EXISTS season;
CREATE TABLE season (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	unique_tournament_id bigint,
	name text,
	abbr text,
	start_date bigint,
	end_date bigint,
	neutral_ground boolean,
	friendly boolean,
	year text,
	coverage_lineups boolean,
	real_category_id bigint,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (unique_tournament_id) REFERENCES unique_tournament (id)
	-- , FOREIGN KEY (real_category_id) REFERENCES real_category (id)
);
DROP TABLE IF EXISTS tournament;
CREATE TABLE tournament (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	real_category_id bigint,
	season_id bigint,
	current_season_id bigint,
	isk bigint,
	season_type text,
	season_type_name text,
	season_type_unique text,
	year text,
	name text,
	abbr text,
	friendly boolean,
	round_by_round boolean,
	outdated boolean,
	live_table bigint,
	tournament_level_order bigint,
	tournament_level_name text,
	current_round int,
	cup_roster_id text,
	group_name text,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (real_category_id) REFERENCES real_category (id)
	-- , FOREIGN KEY (season_id) REFERENCES season (id)
	-- , FOREIGN KEY (current_season_id) REFERENCES season (id)
);
DROP TABLE IF EXISTS season_meta;
CREATE TABLE season_meta (
	season_id bigint NOT NULL,
	ts bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	real_category_id bigint,
	unique_tournament_id bigint,
	stats_coverage_complex_stat boolean,
	stats_coverage_live_table boolean,
	stats_coverage_halftime_table boolean,
	stats_coverage_over_under boolean,
	stats_coverage_over_under_halftime boolean,
	stats_coverage_fixtures boolean,
	stats_coverage_league_table boolean,
	stats_coverage_table_rules boolean,
	stats_coverage_head_to_head boolean,
	stats_coverage_form_table boolean,
	stats_coverage_secon_half_tables boolean,
	stats_coverage_division_view boolean,
	stats_match_details boolean,
	stats_coverage_lineups boolean,
	stats_coverage_formations boolean,
	stats_coverage_top_goals boolean,
	stats_coverage_top_assists boolean,
	stats_coverage_disciplinary boolean,
	stats_coverage_injury_list boolean,
	stats_coverage_red_cards boolean,
	stats_coverage_yellow_cards boolean,
	stats_coverage_goal_minute boolean,
	stats_coverage_goal_min_scorer boolean,
	stats_coverage_substitutions boolean,
	stats_coverage_squad_service boolean,
	stats_coverage_live_score_event_throwin boolean,
	stats_coverage_live_score_event_goalkick boolean,
	stats_coverage_live_score_event_freekick boolean,
	stats_coverage_live_score_event_shots_off_goal boolean,
	stats_coverage_live_score_event_shots_on_goal boolean,
	stats_coverage_live_score_event_goalkeeper_save boolean,
	stats_coverage_live_score_event_cornerkick boolean,
	stats_coverage_live_score_event_offside boolean,
	stats_coverage_live_score_event_fouls boolean,
	stats_coverage_live_score_event_possession boolean,
	stats_coverage_referee boolean,
	stats_coverage_stadium boolean,
	stats_coverage_staff_managers boolean,
	stats_coverage_staff_team_officials boolean,
	stats_coverage_staff_assistant_coaches boolean,
	stats_coverage_jerseys boolean,
	stats_coverage_cup_roster boolean,
	PRIMARY KEY (season_id, ts)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (real_category_id) REFERENCES real_category (id)
	-- , FOREIGN KEY (unique_tournament_id) REFERENCES unique_tournament (id)
);
DROP TABLE IF EXISTS unique_team;
CREATE TABLE unique_team (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	real_category_id bigint,
	name text,
	abbr text,
	medium_name text,
	is_country boolean,
	founded text,
	sex text,
	team_type_id bigint,
	country_code_id bigint,
	home_real_category_id bigint,
	stadium_id bigint,
	manager_id bigint,
	suffix text,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (real_category_id) REFERENCES real_category (id)
	-- , FOREIGN KEY (country_code_id) REFERENCES country (id)
	-- , FOREIGN KEY (home_real_category_id) REFERENCES real_category (id)
	-- , FOREIGN KEY (stadium_id) REFERENCES stadium (id)
	-- , FOREIGN KEY (manager_id) REFERENCES player (id)
);
DROP TABLE IF EXISTS unique_team_stats;
CREATE TABLE unique_team_stats (
	season_id bigint NOT NULL,
	team_id bigint NOT NULL,
	ts bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	unique_team_id bigint,
	goal_attempts int,
	shots_on_goal int,
	shots_off_goal int,
	corner_kicks int,
	ball_possession int,
	shots_blocked int,
	cards_given int,
	freekicks int,
	offside int,
	shots_on_post int,
	shots_on_bar int,
	goals_by_foot int,
	goals_by_head int,
	yellow_cards int,
	red_cards int,
	goals_scored int,
	goals_conceded int,
	yellow_red_cards int,
	shooting_efficiency int,
	penalty_success_count int,
	penalty_fail_count int,
	clean_sheet_count int,
	goal_attempts_avg real,
	shots_on_goal_avg real,
	shots_off_goal_avg real,
	corner_kicks_avg real,
	ball_possession_avg real,
	shots_blocked_avg real,
	cards_given_avg real,
	freekicks_avg real,
	offside_avg real,
	shots_on_post_avg real,
	shots_on_bar_avg real,
	goals_by_foot_avg real,
	goals_by_head_avg real,
	yellow_cards_avg real,
	red_cards_avg real,
	goals_scored_avg real,
	goals_conceded_avg real,
	yellow_red_cards_avg real,
	shooting_efficiency_avg text,
	goal_attempts_matches int,
	shots_on_goal_matches int,
	shots_off_goal_matches int,
	corner_kicks_matches int,
	ball_possession_matches int,
	shots_blocked_matches int,
	cards_given_matches int,
	freekicks_matches int,
	offside_matches int,
	shots_on_post_matches int,
	shots_on_bar_matches int,
	goals_by_foot_matches int,
	goals_by_head_matches int,
	yellow_cards_matches int,
	red_cards_matches int,
	goals_scored_matches int,
	goals_conceded_matches int,
	yellow_red_cards_matches int,
	shooting_efficiency_matches int,
	late_winning_goals int,
	PRIMARY KEY (season_id, team_id, ts)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (unique_team_id) REFERENCES unique_team (id)
);
DROP TABLE IF EXISTS entries;
CREATE TABLE entries (
	id bigint NOT NULL,
	ts bigint NOT NULL,
	season_id bigint,
	unique_team_id bigint,
	PRIMARY KEY (id, ts)
	-- , FOREIGN KEY (season_id) REFERENCES season (id)
	-- , FOREIGN KEY (unique_team_id) REFERENCES unique_team (id)
);
DROP TABLE IF EXISTS team;
CREATE TABLE team (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	uid_id bigint,
	name text,
	abbr text,
	medium_name text,
	is_country boolean,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (uid_id) REFERENCES unique_team (id)
);
DROP TABLE IF EXISTS match;
CREATE TABLE match (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	real_category_id bigint,
	tournament_id bigint,
	unique_tournament_id bigint,
	season_id bigint,
	team_home_id bigint,
	team_away_id bigint,
	round_name_id bigint,
	stadium_id bigint,
	time bigint,
	week text,
	round int,
	result_home int,
	result_away int,
	result_period text,
	neutral_ground boolean,
	comment text,
	to_be_announced boolean,
	postponed boolean,
	canceled boolean,
	inlivescore boolean,
	walkover boolean,
	retired boolean,
	disqualified boolean,
	p1_home int,
	p1_away int,
	ft_home int,
	ft_away int,
	result_winner text,
	result_betting_winner text,
	status text,
	next_match_id bigint,
	cup_round_match_number int,
	cup_round_number_of_matches int,
	ot_home int,
	ot_away int,
	manager_home_id bigint,
	manager_away_id bigint,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (real_category_id) REFERENCES real_category (id)
	-- , FOREIGN KEY (tournament_id) REFERENCES tournament (id)
	-- , FOREIGN KEY (unique_tournament_id) REFERENCES unique_tournament (id)
	-- , FOREIGN KEY (season_id) REFERENCES season (id)
	-- , FOREIGN KEY (team_home_id) REFERENCES team (id)
	-- , FOREIGN KEY (team_away_id) REFERENCES team (id)
	-- , FOREIGN KEY (round_name_id) REFERENCES table_round (id)
	-- , FOREIGN KEY (stadium_id) REFERENCES stadium (id)
	-- , FOREIGN KEY (next_match_id) REFERENCES match (id)
	-- , FOREIGN KEY (manager_home_id) REFERENCES player (id)
	-- , FOREIGN KEY (manager_away_id) REFERENCES player (id)
);
DROP TABLE IF EXISTS league_table;
CREATE TABLE league_table (
	id bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	season_id bigint,
	max_rounds int,
	name text,
	abbr text,
	season_type text,
	season_type_name text,
	season_type_unique text,
	season_start bigint,
	season_end bigint,
	tournament_id bigint,
	real_category_id bigint,
	rules_id bigint,
	current_round int,
	presentation_id int,
	total_rows int,
	PRIMARY KEY (id)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (season_id) REFERENCES season (id)
	-- , FOREIGN KEY (tournament_id) REFERENCES tournament (id)
	-- , FOREIGN KEY (real_category_id) REFERENCES real_category (id)
	-- , FOREIGN KEY (rules_id) REFERENCES tie_break_rule (id)
);
DROP TABLE IF EXISTS team_over_under;
CREATE TABLE team_over_under (
	team_id bigint NOT NULL,
	ts bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	matches int,
	goalsscored_ft_average real,
	goalsscored_ft_total int,
	goalsscored_ft_matches int,
	goalsscored_p1_average real,
	goalsscored_p1_total int,
	goalsscored_p1_matches int,
	goalsscored_p2_average real,
	goalsscored_p2_total int,
	goalsscored_p2_matches int,
	conceded_ft_average real,
	conceded_ft_total int,
	conceded_ft_matches int,
	conceded_p1_average real,
	conceded_p1_total int,
	conceded_p1_matches int,
	conceded_p2_average real,
	conceded_p2_total int,
	conceded_p2_matches int,
	PRIMARY KEY (team_id, ts)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (team_id) REFERENCES unique_team (id)
);
DROP TABLE IF EXISTS table_row;
CREATE TABLE table_row (
	league_table_id bigint NOT NULL,
	row_id bigint NOT NULL,
	ts bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	position_type_id bigint,
	team_id bigint,
	change_total int,
	change_home int,
	change_away int,
	draw_total int,
	draw_home int,
	draw_away int,
	goal_diff_total int,
	goal_diff_home int,
	goal_diff_away int,
	goals_against_total int,
	goals_against_home int,
	goals_against_away int,
	goals_for_total int,
	goals_for_home int,
	goals_for_away int,
	loss_total int,
	loss_home int,
	loss_away int,
	total int,
	home int,
	away int,
	points_total int,
	points_home int,
	points_away int,
	pos int,
	pos_home int,
	pos_away int,
	sort_position_total int,
	sort_position_home int,
	sort_position_away int,
	win_total int,
	win_home int,
	win_away int,
	PRIMARY KEY (league_table_id, row_id, ts)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (position_type_id) REFERENCES promotion (id)
	-- , FOREIGN KEY (team_id) REFERENCES team (id)
);
DROP TABLE IF EXISTS season_pos;
CREATE TABLE season_pos (
	season_id bigint NOT NULL,
	unique_team_id bigint NOT NULL,
	round int NOT NULL,
	source_id bigint,
	source_ts bigint,
	match_id bigint,
	position int,
	moved text,
	PRIMARY KEY (season_id, unique_team_id, round)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (season_id) REFERENCES season (id)
	-- , FOREIGN KEY (match_id) REFERENCES match (id)
);
DROP TABLE IF EXISTS unique_team_form;
CREATE TABLE unique_team_form (
	match_id bigint NOT NULL,
	team_id bigint NOT NULL,
	ts bigint NOT NULL,
	source_id bigint,
	source_ts bigint,
	unique_team_id bigint,
	home3 real,
	home5 real,
	home7 real,
	home9 real,
	away3 real,
	away5 real,
	away7 real,
	away9 real,
	total3 real,
	total5 real,
	total7 real,
	total9 real,
	PRIMARY KEY (match_id, team_id, ts)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (unique_team_id) REFERENCES unique_team (id)
	-- , FOREIGN KEY (match_id) REFERENCES match (id)
);
DROP TABLE IF EXISTS team_form_table;
CREATE TABLE team_form_table (
	season_id bigint NOT NULL,
	unique_team_id bigint NOT NULL,
	round int NOT NULL,
	source_id bigint,
	source_ts bigint,
	position_total int,
	position_home int,
	position_away int,
	played_total int,
	played_total_home int,
	played_total_away int,
	played_home int,
	played_away int,
	win_total int,
	win_total_home int,
	win_total_away int,
	win_home int,
	win_away int,
	draw_total int,
	draw_total_home int,
	draw_total_away int,
	draw_home int,
	draw_away int,
	loss_total int,
	loss_total_home int,
	loss_total_away int,
	loss_home int,
	loss_away int,
	goals_for_total int,
	goals_for_total_home int,
	goals_for_total_away int,
	goals_for_home int,
	goals_for_away int,
	goals_against_total int,
	goals_against_total_home int,
	goals_against_total_away int,
	goals_against_home int,
	goals_against_away int,
	goal_diff_total int,
	goal_diff_total_home int,
	goal_diff_total_away int,
	goal_diff_home int,
	goal_diff_away int,
	points_total int,
	points_total_home int,
	points_total_away int,
	points_home int,
	points_away int,
	next_opponent_team_id bigint,
	next_opponent_time bigint,
	PRIMARY KEY (season_id, unique_team_id, round)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (next_opponent_team_id) REFERENCES team (id)
);
DROP TABLE IF EXISTS team_form_entry;
CREATE TABLE team_form_entry (
	season_id bigint NOT NULL,
	unique_team_id bigint NOT NULL,
	round int NOT NULL,
	name text NOT NULL,
	index int NOT NULL,
	source_id bigint,
	source_ts bigint,
	type_id text,
	value text,
	home_match boolean,
	neutral_ground boolean,
	match_id bigint,
	PRIMARY KEY (season_id, unique_team_id, round, name, index)
	-- , FOREIGN KEY (source_id, source_ts) REFERENCES entries (id, ts)
	-- , FOREIGN KEY (match_id) REFERENCES match (id)
);

