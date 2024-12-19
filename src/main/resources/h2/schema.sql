DROP TABLE IF EXISTS users;

CREATE TABLE users (
    seq_no BIGINT NOT NULL AUTO_INCREMENT,
    kakao_id BIGINT,
    nickname VARCHAR(50) NOT NULL,
    is_school_verified CHAR(1) NOT NULL,
    PRIMARY KEY (seq_no),
    CONSTRAINT chk_is_school_verified CHECK (is_school_verified IN ('Y', 'N')),
    CONSTRAINT uq_kakao_id UNIQUE (kakao_id)
);
