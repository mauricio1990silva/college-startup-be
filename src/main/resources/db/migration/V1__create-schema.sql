CREATE TABLE `user` (
  `user_id`             BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `name`                VARCHAR(30)  NOT NULL,
  `password`            VARCHAR(100) NOT NULL,
  `username`            VARCHAR(30)  NOT NULL,
  `registration_status` VARCHAR(30)  NOT NULL,
  `created_at`          DATETIME     NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_username` (`username`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `user_profile` (
  `user_id`    BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `major`      VARCHAR(100) NOT NULL,
  `year`       VARCHAR(30)  NOT NULL,
  `created_at` DATETIME     NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_user_profile` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `tag` (
  `tag_id`  BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`tag_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `user_profile_tags` (
  `tag_id`  BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  KEY `FK_user_tag_id` (`tag_id`),
  KEY `FK_user_id` (`user_id`),
  CONSTRAINT `FK_user_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`),
  CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_profile` (`user_id`),
  UNIQUE KEY `UK_user_tag` (`user_id`, `tag_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `project` (
  `project_id`       BIGINT(20)       NOT NULL AUTO_INCREMENT,
  `type`             VARCHAR(255)     NOT NULL,
  `name`             VARCHAR(255)     NOT NULL,
  `content`          VARCHAR(255)     NOT NULL,
  `created_at`       DATETIME         NOT NULL,
  `user_id`          BIGINT(20)       NOT NULL,
  `members_required` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`project_id`),
  KEY `FK_user` (`user_id`),
  CONSTRAINT `FK_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# CREATE TABLE `project_group` (
#   `project_group_id` BIGINT(20)  NOT NULL AUTO_INCREMENT,
#   `user_id`          BIGINT(20)  NOT NULL,
#   `user_role`        VARCHAR(40) NOT NULL,
#   PRIMARY KEY (`project_group_id`),
#   CONSTRAINT `FK_group_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
#   CONSTRAINT `FK_group_project` FOREIGN KEY (`project_group_id`) REFERENCES `project` (`project_id`),
#   UNIQUE KEY `UK_follower_followed` (`user_id`, `project_group_id`)
# )
#   ENGINE = InnoDB
#   DEFAULT CHARSET = utf8;


CREATE TABLE `liked_project` (
  `user_id`    BIGINT(20) NOT NULL,
  `project_id` BIGINT(20) NOT NULL,
  KEY `FK_liked_by_user` (`user_id`),
  KEY `FK_liked_project` (`project_id`),
  CONSTRAINT `FK_liked_by_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_liked_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  UNIQUE KEY `UK_user_liked_project` (`user_id`, `project_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `project_comment` (
  `comment_id` BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `content`            VARCHAR(255) NOT NULL,
  `created_at`         DATETIME     NOT NULL,
  `user_id`            BIGINT(20)   NOT NULL,
  `project_id`         BIGINT(20)   NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK_comment_by_user` (`user_id`),
  KEY `FK_project_comment` (`project_id`),
  CONSTRAINT `FK_comment_by_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_project_comment` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `project_tags` (
  `tag_id`     BIGINT(20) NOT NULL,
  `project_id` BIGINT(20) NOT NULL,
  KEY `FK_tag` (`tag_id`),
  KEY `FK_project` (`project_id`),
  CONSTRAINT `FK_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`),
  CONSTRAINT `FK_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  UNIQUE KEY `UK_project_tag` (`project_id`, `tag_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `relationship` (
  `relationship_id`  BIGINT(20) NOT NULL AUTO_INCREMENT,
  `followed_user_id` BIGINT(20) NOT NULL,
  `follower_user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`relationship_id`),
  KEY `FK_followed` (`followed_user_id`),
  KEY `FK_follower` (`follower_user_id`),
  CONSTRAINT `FK_follower` FOREIGN KEY (`followed_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_followed` FOREIGN KEY (`follower_user_id`) REFERENCES `user` (`user_id`),
  UNIQUE KEY `UK_follower_followed` (`follower_user_id`, `followed_user_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;



# TEST DATA
#
# insert into user_profile values ('1', 'CS', '2014', now());
# insert into project values ('2', 'Music Band', 'Awesome app', 'bullshit', now(), '2', '5');
# insert into project values ('3', 'Research', 'Awesome app', 'bullshit', now(), '2', '5');
#
# insert into tag values ('1', 'hello');
# insert into tag values ('2', 'world');
#
# insert into project_tags values ('1', '1');
# insert into user_profile_tags values ('2', '1');
#
# insert into project_tags values ('2', '2');
#
# insert into user_profile values ('1', 'CS', '2014', now());
# insert into project values ('2', 'Music Band', 'Awesome app', 'bullshit', now(), '2', '5');
# insert into project values ('3', 'Research', 'Awesome app', 'bullshit', now(), '2', '5');
#
# insert into tag values ('1', 'hello');
# insert into tag values ('2', 'world');
#
# insert into project_tags values ('1', '1');
# insert into user_profile_tags values ('2', '1');
#
# insert into project_tags values ('2', '2');