CREATE SCHEMA terraria_item_perf_comp;

CREATE TABLE `item_comp_votes` (
	`id`	integer AUTO_INCREMENT  PRIMARY KEY,
	`comp_count`	smallint	NOT NULL    DEFAULT 0,
	`created_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP,
	`updated_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP,
	`choose_reason`	varchar(127)	NULL,
	`situation_id`	integer	NOT NULL,
	`user_id`	integer	NOT NULL,
	`chosen_item_id`	integer	NOT NULL,
	`vote_count`    integer NOT NULL    DEFAULT 0
);

CREATE TABLE `comp_iteration_counts` (
    `id`	integer	AUTO_INCREMENT  PRIMARY KEY,
    `category_id`	integer	NOT NULL,
    `iteration_count`   integer NOT NULL    DEFAULT 0
);

CREATE TABLE `users` (
	`id`	integer	AUTO_INCREMENT  PRIMARY KEY,
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
	`deleted_at`	datetime	NULL,
	`role_id` integer NOT NULL
);

CREATE TABLE `user_roles` (
    `id`	integer	AUTO_INCREMENT  PRIMARY KEY,
    `role_name` varchar(255)
);

CREATE TABLE `items` (
	`id`	integer	AUTO_INCREMENT  PRIMARY KEY,
	`item_name`	varchar(255)	NOT NULL,
	`img_url`	text	NULL,
	`min_progression_id`	integer	NOT NULL,
	`category_id`	integer	NOT NULL,
	`title_id`	integer	NOT NULL
);

CREATE TABLE `titles` (
	`id`	integer	AUTO_INCREMENT  PRIMARY KEY,
	`title`	varchar(255)	NOT NULL,
	`img_url`   text    NULL
);

CREATE TABLE `item_category` (
	`id`	integer	AUTO_INCREMENT  PRIMARY KEY,
	`category_name`	varchar(255)	NOT NULL,
	`title_id`	integer	NOT NULL,
	`img_url`   text    NULL
);

CREATE TABLE `item_balance_votes` (
	`id`	integer	AUTO_INCREMENT  PRIMARY KEY,
	`choose_count`	integer	    NOT NULL    DEFAULT 0,
	`created_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP,
	`updated_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP,
	`situation_id`	integer	NOT NULL,
	`user_id`	integer	NOT NULL,
	`chosen_item_id`	integer	NOT NULL
);

CREATE TABLE `progressions` (
	`id`	integer	AUTO_INCREMENT  PRIMARY KEY,
	`progress_name`	varchar(255)	NOT NULL,
	`title_id`	integer	NOT NULL,
	`img_url`   text    NULL
);

CREATE TABLE `item_comp_situations` (
	`id`	integer	AUTO_INCREMENT  PRIMARY KEY,
	`title_id`	integer	NOT NULL,
	`progression_id`	integer	NOT NULL,
	`category_id`	integer	NOT NULL,
	`comp_item_1`	integer	NOT NULL,
	`comp_item_2`	integer	NOT NULL
);

CREATE TABLE `item_comp_stats` (
	`situation_id`	integer NOT NULL,
	`item_id`	integer	NOT NULL,
	`vote_count`	bigint	NOT NULL    DEFAULT 0,
	`updated_at`	datetime	NOT NULL    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `user_balance_stats` (
    `id` integer AUTO_INCREMENT  PRIMARY KEY,
    `user_id`   integer NOT NULL,
    `title_id`  integer NOT NULL,
    `correctness_rate`  DECIMAL(10, 3) NOT NULL DEFAULT 0
);

ALTER TABLE `item_comp_votes`
  ADD CONSTRAINT `fk_item_comp_votes_situation`
  FOREIGN KEY (`situation_id`) REFERENCES `item_comp_situations`(`id`) ON DELETE CASCADE;
ALTER TABLE `item_comp_votes`
  ADD CONSTRAINT `fk_item_comp_votes_user`
  FOREIGN KEY (`user_id`) REFERENCES `item_comp_situations`(`id`) ON DELETE CASCADE;

ALTER TABLE `comp_iteration_counts`
  ADD CONSTRAINT `fk_comp_iteration_counts_category`
  FOREIGN KEY (`category_id`) REFERENCES `item_category`(`id`) ON DELETE CASCADE;

ALTER TABLE `users`
  ADD CONSTRAINT `fk_users_role`
  FOREIGN KEY (`role_id`) REFERENCES `user_roles`(`id`) ON DELETE CASCADE;

ALTER TABLE `items`
  ADD CONSTRAINT `fk_items_min_progression`
  FOREIGN KEY (`min_progression_id`) REFERENCES `progressions`(`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_items_category`
  FOREIGN KEY (`category_id`) REFERENCES `item_category`(`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_items_title`
  FOREIGN KEY (`title_id`) REFERENCES `titles`(`id`) ON DELETE CASCADE;

ALTER TABLE `item_category`
  ADD CONSTRAINT `fk_item_category_title`
  FOREIGN KEY (`title_id`) REFERENCES `titles`(`id`) ON DELETE CASCADE;

ALTER TABLE `item_balance_votes`
  ADD CONSTRAINT `fk_item_balance_votes_situation`
  FOREIGN KEY (`situation_id`) REFERENCES `item_comp_situations`(`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_item_balance_votes_user`
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_item_balance_votes_chosen_item`
  FOREIGN KEY (`chosen_item_id`) REFERENCES `items`(`id`) ON DELETE CASCADE;

ALTER TABLE `progressions`
  ADD CONSTRAINT `fk_progressions_title`
  FOREIGN KEY (`title_id`) REFERENCES `titles`(`id`) ON DELETE CASCADE;

ALTER TABLE `item_comp_situations`
  ADD CONSTRAINT `fk_item_comp_situations_title`
  FOREIGN KEY (`title_id`) REFERENCES `titles`(`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_item_comp_situations_progression`
  FOREIGN KEY (`progression_id`) REFERENCES `progressions`(`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_item_comp_situations_category`
  FOREIGN KEY (`category_id`) REFERENCES `item_category`(`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_item_comp_situations_item1`
  FOREIGN KEY (`comp_item_1`) REFERENCES `items`(`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_item_comp_situations_item2`
  FOREIGN KEY (`comp_item_2`) REFERENCES `items`(`id`) ON DELETE CASCADE;

ALTER TABLE `item_comp_stats`
  ADD CONSTRAINT `fk_item_comp_stats_situation`
  FOREIGN KEY (`situation_id`) REFERENCES `item_comp_situations`(`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_item_comp_stats_item`
  FOREIGN KEY (`item_id`) REFERENCES `items`(`id`) ON DELETE CASCADE;

ALTER TABLE `user_balance_stats`
  ADD CONSTRAINT `fk_user_balance_stats_user`
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_user_balance_stats_title`
  FOREIGN KEY (`title_id`) REFERENCES `titles`(`id`) ON DELETE CASCADE;

ALTER TABLE `item_comp_stats` ADD CONSTRAINT `PK_ITEM_COMP_STATS` PRIMARY KEY (
	`situation_id`,
	`item_id`
);

ALTER TABLE `users` ADD CONSTRAINT `unique_user` UNIQUE (`email`, `password`, `session_key`);

ALTER TABLE item_comp_situations ADD COLUMN comp_item_max INT GENERATED ALWAYS AS (GREATEST(`comp_item_1`, `comp_item_2`));
ALTER TABLE item_comp_situations ADD COLUMN comp_item_min INT GENERATED ALWAYS AS (LEAST(`comp_item_1`, `comp_item_2`));

ALTER TABLE `item_comp_situations` ADD CONSTRAINT `unique_situation` UNIQUE (
    `progression_id`, `category_id`, `comp_item_max`, `comp_item_min`
);

ALTER TABLE `item_comp_situations` ADD CONSTRAINT `items_not_equal` CHECK (comp_item_1 <> comp_item_2);