DROP TABLE IF EXISTS programs;

CREATE TABLE programs
(
    id          VARCHAR(250) AUTO_INCREMENT PRIMARY KEY,
    channel_id  VARCHAR(250)  NOT NULL,
    image_url   VARCHAR(250) NOT NULL,
    title       VARCHAR(250) NOT NULL,
    description VARCHAR(250) NOT NULL,
    start_time  DATETIME     NOT NULL,
    end_time    DATETIME     NOT NULL
)
