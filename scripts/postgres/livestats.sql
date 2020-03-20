
DROP TABLE IF EXISTS bookmaker;
CREATE TABLE bookmaker (
	id bigint NOT NULL,
	name varchar(16),
	url varchar(32),
	exchange boolean,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS country;
CREATE TABLE country (
	id bigint NOT NULL,
	name varchar(32),
	code varchar(4),
	continent_id int,
	continent varchar(16),
	population bigint,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS cup_round;
CREATE TABLE cup_round (
	id bigint NOT NULL,
	name varchar(32),
	short_name varchar(4),
	statistics_sort_order int,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS league_table;
CREATE TABLE league_table (
	id bigint NOT NULL,
	season_id bigint,
	tournament_id bigint,
	real_category_id bigint,
	rules_id bigint,
	max_rounds int,
	current_round int,
	presentation_id int,
	name varchar(32),
	abbr varchar(16),
	total_rows int,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS league_table__match_types;
CREATE TABLE league_table__match_types (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS league_table__table_rows;
CREATE TABLE league_table__table_rows (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS league_table__table_types;
CREATE TABLE league_table__table_types (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS match;
CREATE TABLE match (
	id bigint NOT NULL,
	real_category_id bigint,
	tournament_id bigint,
	unique_tournament_id bigint,
	time bigint,
	week varchar(2),
	round int,
	result_home int,
	result_away int,
	season_id bigint,
	team_home_id bigint,
	team_away_id bigint,
	team_home_uid bigint,
	team_away_uid bigint,
	to_be_announced boolean,
	postponed boolean,
	stadiumid bigint,
	walkover boolean,
	round_name_id bigint,
	coverage_lineup int,
	coverage_formations int,
	coverage_live_table bigint,
	coverage_injuries int,
	coverage_ball_spotting boolean,
	coverage_corners_only boolean,
	coverage_multi_cast boolean,
	coverage_scout_match int,
	coverage_scout_coverage_status int,
	coverage_scout_connected boolean,
	coverage_live_odds boolean,
	coverage_deeper_coverage boolean,
	coverage_tactical_lineup boolean,
	coverage_basic_lineup boolean,
	coverage_has_stats boolean,
	coverage_in_live_score boolean,
	coverage_penalty_shootout int,
	coverage_scout_test boolean,
	coverage_lmt_support int,
	coverage_venue boolean,
	coverage_match_data_complete boolean,
	coverage_media_coverage boolean,
	coverage_substitutions boolean,
	updated_time bigint,
	ended_time bigint,
	p_time bigint,
	time_info_running boolean,
	removed boolean,
	facts boolean,
	local_derby boolean,
	distance int,
	wind_advantage int,
	match_status varchar(8),
	match_status_id bigint,
	cancelled boolean,
	result_period varchar(2),
	neutral_ground boolean,
	canceled boolean,
	inlivescore boolean,
	retired boolean,
	disqualified boolean,
	dbfa boolean,
	manager_home_id bigint,
	manager_away_id bigint,
	stadium_id bigint,
	history_previous_match_id bigint,
	result_winner varchar(4),
	p1_home int,
	p1_away int,
	ft_home int,
	ft_away int,
	weather int,
	pitch_condition int,
	result_betting_winner varchar(4),
	comment varchar(256),
	status varchar(16),
	next_matchi_id bigint,
	match_difficulty_rating_home int,
	match_difficulty_rating_away int,
	ot_home int,
	ot_away int,
	ap_home int,
	ap_away int,
	cup_round_match_number int,
	cup_round_number_of_matches int,
	odds_bookmaker_id bigint,
	odds_bookmaker_bet_id bigint,
	odds_type varchar(4),
	odds_type_short varchar(4),
	odds_type_id varchar(1),
	odds_livebet boolean,
	odds_is_match_odds boolean,
	odds_active boolean,
	odds_betstop boolean,
	odds_updated bigint,
	cards_home_yellow int,
	cards_home_red int,
	cards_away_yellow int,
	cards_away_red int,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS match__referees;
CREATE TABLE match__referees (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS match__team_forms;
CREATE TABLE match__team_forms (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS match_details_entry;
CREATE TABLE match_details_entry (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	code varchar(32),
	name varchar(32),
	value_home int,
	value_away int,
	value_home_p1 int,
	value_home_p2 int,
	value_away_p1 int,
	value_away_p2 int,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS match_details_extended;
CREATE TABLE match_details_extended (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	match_id bigint,
	team_home varchar(16),
	team_away varchar(16),
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS match_details_extended__entries;
CREATE TABLE match_details_extended__entries (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS match_event;
CREATE TABLE match_event (
	id bigint NOT NULL,
	match_id bigint,
	type_id int,
	minute int,
	seconds int,
	type varchar(32),
	name varchar(32),
	goal_type varchar(8),
	event_time bigint,
	updated_time bigint,
	disabled boolean,
	injury_time int,
	team varchar(4),
	status_id bigint,
	result_home int,
	result_away int,
	period int,
	scorer_id bigint,
	header boolean,
	own_goal boolean,
	penalty boolean,
	player_id bigint,
	card varchar(8),
	minutes int,
	period_name varchar(16),
	period_score_home int,
	period_score_away int,
	result_winner varchar(4),
	player_out_id bigint,
	player_in_id bigint,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS match_event__assists;
CREATE TABLE match_event__assists (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS match_fun_fact;
CREATE TABLE match_fun_fact (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	type_id bigint,
	sentence varchar(128),
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS match_fun_facts;
CREATE TABLE match_fun_facts (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS match_fun_facts__facts;
CREATE TABLE match_fun_facts__facts (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS match_situation_entry;
CREATE TABLE match_situation_entry (
	id bigint NOT NULL,
	time int,
	injury_time int,
	safe int,
	safe_count int,
	home_attack int,
	home_dangerous int,
	home_safe int,
	home_attack_count int,
	home_dangerous_count int,
	home_safe_count int,
	away_attack int,
	away_dangerous int,
	away_safe int,
	away_attack_count int,
	away_dangerous_count int,
	away_safe_count int,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS match_status;
CREATE TABLE match_status (
	id bigint NOT NULL,
	name varchar(16),
	PRIMARY KEY (id));
DROP TABLE IF EXISTS match_timeline;
CREATE TABLE match_timeline (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	match_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS match_timeline__events;
CREATE TABLE match_timeline__events (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS match_type;
CREATE TABLE match_type (
	id bigint NOT NULL,
	name varchar(32),
	set_type_id bigint,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS odds;
CREATE TABLE odds (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	match_id bigint,
	home_odds real,
	draw_odds real,
	away_odds real,
	bookmaker_id bigint,
	type varchar(16),
	odds_type_id int,
	draw_change real,
	away_change real,
	exchange boolean,
	key varchar(16),
	extra varchar(4),
	closing_time varchar(8),
	home_change real,
	home_tb_id bigint,
	home_odds_field_id int,
	draw_tb_id bigint,
	draw_odds_field_id int,
	away_tb_id bigint,
	away_odds_field_id int,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS player;
CREATE TABLE player (
	id bigint NOT NULL,
	name varchar(32),
	birth_date bigint,
	nationality_id bigint,
	position_id bigint,
	full_name varchar(64),
	second_nationality_id bigint,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS player_position_type;
CREATE TABLE player_position_type (
	id bigint NOT NULL,
	type varchar(1),
	name varchar(16),
	short_name varchar(4),
	abbr varchar(1),
	PRIMARY KEY (id));
DROP TABLE IF EXISTS player_status;
CREATE TABLE player_status (
	id bigint NOT NULL,
	player_id bigint,
	unique_team_id bigint,
	status_start bigint,
	status_id int,
	status_name varchar(16),
	status_missing int,
	status_doubtful int,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS promotion;
CREATE TABLE promotion (
	id bigint NOT NULL,
	code int,
	name varchar(32),
	short_name varchar(8),
	position int,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS real_category;
CREATE TABLE real_category (
	id bigint NOT NULL,
	name varchar(32),
	country_id bigint,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS season;
CREATE TABLE season (
	id bigint NOT NULL,
	unique_tournament_id bigint,
	name varchar(32),
	abbr varchar(8),
	start_date bigint,
	end_date bigint,
	neutral_ground boolean,
	friendly boolean,
	year varchar(8),
	coverage_lineups boolean,
	real_category_id bigint,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS season__ise_odds;
CREATE TABLE season__ise_odds (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS season__matches;
CREATE TABLE season__matches (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS season__odds;
CREATE TABLE season__odds (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS season__tables;
CREATE TABLE season__tables (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS season__tournaments;
CREATE TABLE season__tournaments (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS season_over_under;
CREATE TABLE season_over_under (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
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
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS season_pos;
CREATE TABLE season_pos (
	id bigint NOT NULL,
	unique_team_id bigint,
	season_id bigint,
	match_id bigint,
	round int,
	position int,
	moved varchar(2),
	PRIMARY KEY (id));
DROP TABLE IF EXISTS sport;
CREATE TABLE sport (
	id bigint NOT NULL,
	name varchar(16),
	PRIMARY KEY (id));
DROP TABLE IF EXISTS stadium;
CREATE TABLE stadium (
	id bigint NOT NULL,
	country_id bigint,
	name varchar(32),
	city varchar(32),
	country varchar(32),
	capacity varchar(8),
	constr_year varchar(4),
	address varchar(32),
	googlecoords varchar(32),
	pitchsize_x int,
	pitchsize_y int,
	url varchar(32),
	phone varchar(16),
	PRIMARY KEY (id));
DROP TABLE IF EXISTS stadium__team_homes;
CREATE TABLE stadium__team_homes (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS statistics_table;
CREATE TABLE statistics_table (
	id bigint NOT NULL,
	name varchar(16),
	abbr varchar(16),
	max_rounds varchar(2),
	tournament_id bigint,
	season_id varchar(8),
	season_type varchar(2),
	season_type_name varchar(32),
	season_type_unique varchar(2),
	season_start bigint,
	season_end bigint,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS statistics_table__matches;
CREATE TABLE statistics_table__matches (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS stats_form_table;
CREATE TABLE stats_form_table (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	win_points int,
	loss_points int,
	current_round int,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_form_table__match_types;
CREATE TABLE stats_form_table__match_types (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_form_table__table_types;
CREATE TABLE stats_form_table__table_types (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_form_table__team_form_tables;
CREATE TABLE stats_form_table__team_form_tables (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS stats_match_get;
CREATE TABLE stats_match_get (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	match_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_match_situation;
CREATE TABLE stats_match_situation (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	match_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_match_situation__entries;
CREATE TABLE stats_match_situation__entries (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_fixtures;
CREATE TABLE stats_season_fixtures (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_goals;
CREATE TABLE stats_season_goals (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	matches int,
	scored_sum int,
	scored0015 int,
	scored1630 int,
	scored3145 int,
	scored4660 int,
	scored6175 int,
	scored7690 int,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_goals__tables;
CREATE TABLE stats_season_goals__tables (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_goals__team_goal_stats;
CREATE TABLE stats_season_goals__team_goal_stats (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS stats_season_injuries;
CREATE TABLE stats_season_injuries (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_injuries__player_statuses;
CREATE TABLE stats_season_injuries__player_statuses (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_last_x;
CREATE TABLE stats_season_last_x (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_last_x__matches;
CREATE TABLE stats_season_last_x__matches (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_last_x__tournaments;
CREATE TABLE stats_season_last_x__tournaments (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_league_summary;
CREATE TABLE stats_season_league_summary (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
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
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_meta;
CREATE TABLE stats_season_meta (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
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
	stats_coverage_transfer_history boolean,
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
	season_id bigint,
	real_category_id bigint,
	unique_tournament_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_meta__tables;
CREATE TABLE stats_season_meta__tables (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_meta__tournaments;
CREATE TABLE stats_season_meta__tournaments (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_next_x;
CREATE TABLE stats_season_next_x (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_next_x__matches;
CREATE TABLE stats_season_next_x__matches (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_next_x__tournaments;
CREATE TABLE stats_season_next_x__tournaments (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_odds;
CREATE TABLE stats_season_odds (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_over_under;
CREATE TABLE stats_season_over_under (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	league_totals_id bigint,
	league_totals_time_stamp bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_over_under__stats_team_over_unders;
CREATE TABLE stats_season_over_under__stats_team_over_unders (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS stats_season_tables;
CREATE TABLE stats_season_tables (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_team_position_history;
CREATE TABLE stats_season_team_position_history (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	team_count int,
	round_count int,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_team_position_history__promotions;
CREATE TABLE stats_season_team_position_history__promotions (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_team_position_history__season_positions;
CREATE TABLE stats_season_team_position_history__season_positions (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_team_position_history__tables;
CREATE TABLE stats_season_team_position_history__tables (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_team_position_history__teams;
CREATE TABLE stats_season_team_position_history__teams (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_teams2;
CREATE TABLE stats_season_teams2 (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_teams2__stats_tables;
CREATE TABLE stats_season_teams2__stats_tables (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_teams2__teams;
CREATE TABLE stats_season_teams2__teams (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_top_assists;
CREATE TABLE stats_season_top_assists (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_top_assists__players;
CREATE TABLE stats_season_top_assists__players (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS stats_season_top_assists__unique_teams;
CREATE TABLE stats_season_top_assists__unique_teams (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_top_cards;
CREATE TABLE stats_season_top_cards (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_top_cards__players;
CREATE TABLE stats_season_top_cards__players (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS stats_season_top_cards__unique_teams;
CREATE TABLE stats_season_top_cards__unique_teams (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_top_goals;
CREATE TABLE stats_season_top_goals (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_top_goals__players;
CREATE TABLE stats_season_top_goals__players (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS stats_season_top_goals__unique_teams;
CREATE TABLE stats_season_top_goals__unique_teams (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_season_unique_team_stats;
CREATE TABLE stats_season_unique_team_stats (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	season_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_season_unique_team_stats__unique_team_stats;
CREATE TABLE stats_season_unique_team_stats__unique_team_stats (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS stats_team_info;
CREATE TABLE stats_team_info (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	unique_team_id bigint,
	stadium_id bigint,
	manager_id bigint,
	manager_member_since bigint,
	twitter varchar(16),
	hashtag varchar(16),
	matchup varchar(8),
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_team_info__tournaments;
CREATE TABLE stats_team_info__tournaments (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_last_x;
CREATE TABLE stats_team_last_x (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	unique_team_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_team_last_x__matches;
CREATE TABLE stats_team_last_x__matches (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_last_x__real_categories;
CREATE TABLE stats_team_last_x__real_categories (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_last_x__tournaments;
CREATE TABLE stats_team_last_x__tournaments (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_last_x__unique_tournaments;
CREATE TABLE stats_team_last_x__unique_tournaments (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_next_x;
CREATE TABLE stats_team_next_x (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	unique_team_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_team_next_x__matches;
CREATE TABLE stats_team_next_x__matches (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_next_x__real_categories;
CREATE TABLE stats_team_next_x__real_categories (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_next_x__tournaments;
CREATE TABLE stats_team_next_x__tournaments (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_next_x__unique_tournaments;
CREATE TABLE stats_team_next_x__unique_tournaments (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_odds_client;
CREATE TABLE stats_team_odds_client (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	unique_team_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_team_odds_client__odds;
CREATE TABLE stats_team_odds_client__odds (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS stats_team_versus;
CREATE TABLE stats_team_versus (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	next_match_id bigint,
	live_match_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS stats_team_versus__matches;
CREATE TABLE stats_team_versus__matches (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_versus__real_categories;
CREATE TABLE stats_team_versus__real_categories (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_versus__tournaments;
CREATE TABLE stats_team_versus__tournaments (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS stats_team_versus__unique_teams;
CREATE TABLE stats_team_versus__unique_teams (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id));
DROP TABLE IF EXISTS table_round;
CREATE TABLE table_round (
	id bigint NOT NULL,
	name varchar(2),
	PRIMARY KEY (id));
DROP TABLE IF EXISTS table_row;
CREATE TABLE table_row (
	id bigint NOT NULL,
	promotion_id bigint,
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
	PRIMARY KEY (id));
DROP TABLE IF EXISTS table_type;
CREATE TABLE table_type (
	id bigint NOT NULL,
	name varchar(16),
	PRIMARY KEY (id));
DROP TABLE IF EXISTS team;
CREATE TABLE team (
	id bigint NOT NULL,
	uid bigint,
	name varchar(32),
	abbr varchar(4),
	medium_name varchar(32),
	is_country boolean,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS team_form_entry;
CREATE TABLE team_form_entry (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	_index int,
	group_name varchar(8),
	type_id varchar(1),
	value varchar(1),
	home_match boolean,
	neutral_ground boolean,
	match_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS team_form_table;
CREATE TABLE team_form_table (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
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
	next_opponent_match_difficulty_rating_home int,
	next_opponent_match_difficulty_rating_away int,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS team_form_table__form_entries;
CREATE TABLE team_form_table__form_entries (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS team_goal_stats;
CREATE TABLE team_goal_stats (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	team_id bigint,
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
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS team_over_under;
CREATE TABLE team_over_under (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
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
	team_id bigint,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS team_player_top_list_entry;
CREATE TABLE team_player_top_list_entry (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	unique_team_id bigint,
	player_id boolean,
	active boolean,
	last_event varchar(32),
	started int,
	assists int,
	matches int,
	shirt_number int,
	substituted_in int,
	minutes_played int,
	yellow_cards int,
	yellow_red_cards int,
	red_cards int,
	first_half_cards int,
	second_half_cards int,
	goals int,
	penalties int,
	goal_points int,
	first_goals int,
	last_goals int,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS tie_break_rule;
CREATE TABLE tie_break_rule (
	id bigint NOT NULL,
	name varchar(256),
	PRIMARY KEY (id));
DROP TABLE IF EXISTS top_list_entry;
CREATE TABLE top_list_entry (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
	player_id bigint,
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
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS top_list_entry__teams_entries;
CREATE TABLE top_list_entry__teams_entries (
	src_id bigint NOT NULL,
	src_time_stamp bigint NOT NULL,
	dst_id bigint NOT NULL,
	dst_time_stamp bigint NOT NULL,
	PRIMARY KEY (src_id, src_time_stamp, dst_id, dst_time_stamp));
DROP TABLE IF EXISTS tournament;
CREATE TABLE tournament (
	id bigint NOT NULL,
	real_category_id bigint,
	season_id bigint,
	current_season_id bigint,
	isk bigint,
	season_type varchar(2),
	season_type_name varchar(32),
	season_type_unique varchar(2),
	year varchar(8),
	name varchar(32),
	abbr varchar(4),
	friendly boolean,
	round_by_round boolean,
	outdated boolean,
	live_table bigint,
	tournament_level_order bigint,
	tournament_level_name varchar(16),
	current_round int,
	cup_roster_id varchar(8),
	group_name varchar(8),
	PRIMARY KEY (id));
DROP TABLE IF EXISTS tournament__matches;
CREATE TABLE tournament__matches (
	src_id bigint NOT NULL,
	dst_id bigint NOT NULL,
	PRIMARY KEY (src_id, dst_id));
DROP TABLE IF EXISTS unique_team;
CREATE TABLE unique_team (
	id bigint NOT NULL,
	name varchar(32),
	abbr varchar(4),
	medium_name varchar(32),
	is_country boolean,
	founded varchar(4),
	sex varchar(1),
	real_category_id bigint,
	team_type_id bigint,
	suffix varchar(4),
	country_code_id bigint,
	stadium_id bigint,
	home_real_category_id bigint,
	PRIMARY KEY (id));
DROP TABLE IF EXISTS unique_team_form;
CREATE TABLE unique_team_form (
	id bigint NOT NULL,
	unique_team_id bigint,
	match_id bigint,
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
	PRIMARY KEY (id));
DROP TABLE IF EXISTS unique_team_stats;
CREATE TABLE unique_team_stats (
	id bigint NOT NULL,
	time_stamp bigint NOT NULL,
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
	attendance int,
	yellow_cards int,
	red_cards int,
	goals_scored int,
	goals_conceded int,
	yellow_red_cards int,
	shooting_efficiency int,
	late_winning_goals int,
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
	attendance_avg real,
	yellow_cards_avg real,
	red_cards_avg real,
	goals_scored_avg real,
	goals_conceded_avg real,
	yellow_red_cards_avg real,
	shooting_efficiency_avg varchar(4),
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
	attendance_matches int,
	yellow_cards_matches int,
	red_cards_matches int,
	goals_scored_matches int,
	goals_conceded_matches int,
	yellow_red_cards_matches int,
	shooting_efficiency_matches int,
	PRIMARY KEY (id, time_stamp));
DROP TABLE IF EXISTS unique_tournament;
CREATE TABLE unique_tournament (
	id bigint NOT NULL,
	name varchar(32),
	real_category_id bigint,
	friendly boolean,
	PRIMARY KEY (id));
