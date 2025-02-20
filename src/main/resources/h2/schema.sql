DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS summoner;
DROP TABLE IF EXISTS match_history;

CREATE TABLE `user` (
    seq_no BIGINT NOT NULL AUTO_INCREMENT,
    kakao_id VARCHAR(256),
    nickname VARCHAR(50) NOT NULL,
    is_school_verified VARCHAR(2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (seq_no),
    CONSTRAINT chk_is_school_verified CHECK (is_school_verified IN ('Y', 'N')),
    CONSTRAINT uq_kakao_id UNIQUE (kakao_id)
);

CREATE TABLE summoner (
    seq_no BIGINT NOT NULL AUTO_INCREMENT,
    user_seq_no BIGINT NOT NULL,
    is_main_summonner VARCHAR(1) NOT NULL,
    puuid varchar(256) NOT NULL,
    smnr_id VARCHAR(50) NOT NULL,
    smnr_tag VARCHAR(50) NOT NULL,
    smnr_icon VARCHAR(255) DEFAULT 'default_icon_url',
    smnr_level VARCHAR(10) DEFAULT NULL,
    smnr_tier VARCHAR(20) DEFAULT 'UNRANK',
    smnr_lp VARCHAR(10) DEFAULT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    school_seq bigint(20) DEFAULT NULL,
    smnr_rank VARCHAR(20) DEFAULT NULL,
    PRIMARY KEY (seq_no),
    CONSTRAINT fk_user_seq_no
        FOREIGN KEY (user_seq_no)
            REFERENCES `user` (seq_no)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT chk_is_main_summonner
        CHECK (is_main_summonner IN ('Y', 'N')),
    CONSTRAINT uq_puuid UNIQUE (puuid)
);

CREATE TABLE match_history (
    seq_no BIGINT AUTO_INCREMENT,
    smnr_seq_no BIGINT NOT NULL,
    match_id VARCHAR(255) NOT NULL,
    is_updated CHAR(1) NOT NULL DEFAULT 'N',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_match_history PRIMARY KEY (seq_no),
    CONSTRAINT chk_is_updated CHECK (is_updated IN ('Y', 'N')),
    CONSTRAINT fk_match_history_summoner FOREIGN KEY (smnr_seq_no)
        REFERENCES summoner (seq_no)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT uk_match_history_smnr_match UNIQUE (smnr_seq_no, match_id)
);