CREATE SCHEMA terraria_item_perf_comp;

CREATE TABLE `item_comp_votes` (
	`id`	integer	NOT NULL,
	`comp_count`	smallint	NOT NULL    DEFAULT 0,
	`created_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP,
	`updated_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP,
	`choose_reason`	varchar(127)	NULL,
	`situation_id`	integer	NOT NULL,
	`user_id`	integer	NOT NULL,
	`chosen_item_id`	integer	NOT NULL
);

CREATE TABLE `users` (
	`id`	integer	NOT NULL,
	`email`	varchar(255)	NULL,
	`nickname`	varchar(15)	NULL,
	`session_key`	varchar(255)	NULL,
	`profile_url`	text	NULL,
	`password`	varchar(255)	NULL,
	`auth_code`	varchar(7)	NULL,
	`auth_code_expires_at`	datetime	NULL,
	`refresh_token`	text	NULL,
	`oauth_provider`	varchar(255)	NULL,
	`oauth_auth_code`	varchar(255)	NULL,
	`created_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP,
	`updated_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP,
	`deleted_at`	datetime	NULL
);

CREATE TABLE `items` (
	`id`	integer	NOT NULL,
	`item_name`	varchar(255)	NOT NULL,
	`img_url`	text	NULL,
	`min_progression_id`	integer	NOT NULL,
	`category_id`	integer	NOT NULL,
	`title_id`	integer	NOT NULL
);

CREATE TABLE `titles` (
	`id`	integer	NOT NULL,
	`title`	varchar(255)	NOT NULL,
	`img_url`   text    NULL
);

CREATE TABLE `item_category` (
	`id`	integer	NOT NULL,
	`category_name`	varchar(255)	NOT NULL,
	`title_id`	integer	NOT NULL,
	`img_url`   text    NULL
);

CREATE TABLE `item_balance_votes` (
	`id`	integer	NOT NULL,
	`choose_count`	smallint	NOT NULL    DEFAULT 0,
	`created_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP,
	`updated_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP,
	`situation_id`	integer	NOT NULL,
	`user_id`	integer	NOT NULL,
	`chosen_item_id`	integer	NOT NULL
);

CREATE TABLE `progressions` (
	`id`	integer	NOT NULL,
	`progress_name`	varchar(255)	NOT NULL,
	`title_id`	integer	NOT NULL,
	`img_url`   text    NULL
);

CREATE TABLE `item_comp_situations` (
	`id`	integer	NOT NULL,
	`title_id`	integer	NOT NULL,
	`progression_id`	integer	NOT NULL,
	`category_id`	integer	NOT NULL,
	`comp_item_1`	integer	NOT NULL,
	`comp_item_2`	integer	NOT NULL
);

CREATE TABLE `item_comp_stats` (
	`situation_id`	integer	NOT NULL,
	`item_id`	integer	NOT NULL,
	`vote_count`	bigint	NOT NULL    DEFAULT 0,
	`updated_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `item_comp_votes` ADD CONSTRAINT `PK_ITEM_COMP_VOTES` PRIMARY KEY (
	`id`
);

ALTER TABLE `users` ADD CONSTRAINT `PK_USERS` PRIMARY KEY (
	`id`
);

ALTER TABLE `items` ADD CONSTRAINT `PK_ITEMS` PRIMARY KEY (
	`id`
);

ALTER TABLE `titles` ADD CONSTRAINT `PK_TITLES` PRIMARY KEY (
	`id`
);

ALTER TABLE `item_category` ADD CONSTRAINT `PK_ITEM_CATEGORY` PRIMARY KEY (
	`id`
);

ALTER TABLE `item_balance_votes` ADD CONSTRAINT `PK_ITEM_BALANCE_VOTES` PRIMARY KEY (
	`id`
);

ALTER TABLE `progressions` ADD CONSTRAINT `PK_PROGRESSIONS` PRIMARY KEY (
	`id`
);

ALTER TABLE `item_comp_situations` ADD CONSTRAINT `PK_ITEM_COMP_SITUATIONS` PRIMARY KEY (
	`id`
);

ALTER TABLE `item_comp_stats` ADD CONSTRAINT `PK_ITEM_COMP_STATS` PRIMARY KEY (
	`situation_id`,
	`item_id`
);

ALTER TABLE `item_comp_stats` ADD CONSTRAINT `FK_item_comp_situations_TO_item_comp_stats_1` FOREIGN KEY (
	`situation_id`
)
REFERENCES `item_comp_situations` (
	`id`
);

ALTER TABLE `item_comp_stats` ADD CONSTRAINT `FK_items_TO_item_comp_stats_1` FOREIGN KEY (
	`item_id`
)
REFERENCES `items` (
	`id`
);

