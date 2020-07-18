DROP TABLE IF EXISTS channels;

CREATE TABLE channels
(
    id       VARCHAR(250) AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(250) NOT NULL,
    position INTEGER      NOT NULL,
    category VARCHAR(250) NOT NULL
)
