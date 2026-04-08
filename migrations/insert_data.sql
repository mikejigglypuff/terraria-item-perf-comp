INSERT INTO `titles`(`title`) VALUES ('Terraria'), ('Calamity Mod');

INSERT INTO `item_category`(`category_name`, `title_id`)
VALUES ('Melee', '1'), ('Ranged', '1'), ('Magic', '1'), ('Summon', '1');

INSERT INTO `progressions`(`progress_name`, `title_id`)
VALUES
    ('Pre-evil biome boss', '1'), ('Pre-skeletron', '1'), ('Pre-hardmode', '1'),
    ('Pre-mechanical bosses', '1'), ('Post-one mechanical boss', '1'), ('Pre-plantera', '1'),
    ('Pre-golem', '1'), ('Pre-moon lord', '1'), ('Post-moon lord', '1');

INSERT INTO `user_roles`(`role_name`)
VALUES ('USER'), ('ADMIN'), ('GUEST');

INSERT INTO `users`(`id`, `role_id`, `nickname`)
VALUES (0, 1, 'Test User');

ㅍ