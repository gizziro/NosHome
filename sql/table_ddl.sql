CREATE TABLE tb_user(
    user_id                     VARCHAR(50) NOT NULL COMMENT '사용자 ID',
    password                    VARCHAR(250) NOT NULL COMMENT '사용자 패스워드',
    password_change_date        DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '패스워드 변경 날짜',
    
    name                        VARCHAR(50) NOT NULL COMMENT '사용자 이름',
    phone                       VARCHAR(50) DEFAULT NULL COMMENT '사용자 전화번호',
    description                 TEXT DEFAULT NULL COMMENT '사용자 설명',
    email                       VARCHAR(50) NOT NULL COMMENT '사용자 이메일',
    email_verified              BIT DEFAULT 0 COMMENT '이메일 인증 여부',
    email_token                 VARCHAR(80) COMMENT '이메일 토큰',
    
    use_mfa                     BIT DEFAULT 0 COMMENT 'MFA 사용 여부',
    
    enabled                     BIT DEFAULT 1 COMMENT '계정 활성화 여부',
    
    failed_login_attempts       INT DEFAULT 0 COMMENT '로그인 실패 횟수',
    locked                      BIT DEFAULT 0 COMMENT '계정 잠금 여부',
    locked_date                 DATETIME DEFAULT NULL COMMENT '계정 잠금 날짜',
    
    provider                    ENUM('LOCAL', 'FACEBOOK', 'GOOGLE', 'APPLE') DEFAULT 'LOCAL' COMMENT '가입 방법',
    registration_date           DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '가입 날짜',
	primary key (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE tb_group (
    group_id         VARCHAR(100)    NOT NULL  COMMENT '그룹 아이디',
    name             VARCHAR(100)    NOT NULL  COMMENT '그룹 이름',
    description      VARCHAR(500)              COMMENT '그룹 설명',
    system_group     BOOLEAN         DEFAULT FALSE COMMENT '시스템 그룹 여부',
    enable           BOOLEAN         NOT NULL DEFAULT TRUE COMMENT '그룹 활성화 여부',
    create_time      DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '그룹 생성 날짜',
	primary key (group_id)    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE tb_user_group_mapping (
    seq            BIGINT           NOT NULL AUTO_INCREMENT,
    user_id        VARCHAR(50)      NOT NULL,
    group_id       VARCHAR(100)     NOT NULL,
    join_time      DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,    
    joined_by      VARCHAR(100)     NOT NULL, -- 권한 부여한 관리자 ID
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_user(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES tb_group(group_id) ON DELETE CASCADE,
    primary key (seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




CREATE TABLE tb_modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE tb_module_permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_code VARCHAR(100) NOT NULL,
    code VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    CONSTRAINT uq_module_perm_group UNIQUE (module_code, code),
    CONSTRAINT fk_module_code
        FOREIGN KEY (module_code) REFERENCES tb_modules(code)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-- 인덱스 추가
CREATE INDEX idx_module_code ON tb_module_permissions(module_code);

-- 모듈 테이블
CREATE TABLE tb_modules (
    module_id VARCHAR(100) NOT NULL COMMENT '모듈 고유 id',
    module_type VARCHAR(50) NOT NULL COMMENT '모듈 타입(SYSTEM, SERVICE)',
    name VARCHAR(100) NOT NULL COMMENT '모듈 이름',
    description VARCHAR(250) COMMENT '모듈 설명',
    PRIMARY KEY (module_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='모듈 정보';

/* 모듈 인스턴스 테이블 */
CREATE TABLE tb_module_instance (
    instance_id        BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '인스턴스 ID',
    module_id          VARCHAR(255) NOT NULL COMMENT '모듈 고유 id',
    instance_name      VARCHAR(100) NOT NULL COMMENT '인스턴스 이름',
    instance_code      VARCHAR(100) NOT NULL COMMENT '인스턴스 고유 코드',
    config_data        JSON                 COMMENT '인스턴스별 설정 데이터',
    enabled            BOOLEAN DEFAULT TRUE COMMENT '인스턴스 활성화 여부',
    created_by         VARCHAR(100) NOT NULL COMMENT '생성자 ID',
    created_at         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_by         VARCHAR(100) COMMENT '수정자 ID',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    UNIQUE KEY uk_instance_code (instance_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='모듈 인스턴스 정보';

/* 모듈 권한 테이블 */
CREATE TABLE tb_module_permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_id VARCHAR(100) NOT NULL,
    permission_code VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    CONSTRAINT uq_module_perm_group UNIQUE (module_id, permission_code),
    CONSTRAINT fk_module_code
        FOREIGN KEY (module_id) REFERENCES tb_modules(module_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



/* 사용자 그룹 테이블 */
CREATE TABLE tb_group (
    group_seq BIGINT AUTO_INCREMENT PRIMARY KEY  COMMENT '그룹 식별자',
    group_id VARCHAR(100) NOT NULL  COMMENT '그룹 아이디',
    name VARCHAR(100) NOT NULL  COMMENT '그룹 이름',
    description VARCHAR(500) COMMENT '그룹 설명',
    system_group BOOLEAN DEFAULT FALSE COMMENT '시스템 그룹 여부',
    enable BOOLEAN NOT NULL DEFAULT TRUE COMMENT '그룹 활성화 여부',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '그룹 생성 날짜'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




CREATE TABLE tb_user_group_map (
    seq            BIGINT           NOT NULL AUTO_INCREMENT PRIMARY KEY  COMMENT '매핑 식별자',
    user_id        VARCHAR(50)      NOT NULL,
    group_id       VARCHAR(100)     NOT NULL,
    join_time      DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,    
    joined_by      VARCHAR(50)      NOT NULL, -- 권한 부여한 관리자 ID
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_user(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES tb_group(group_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SHOW INDEX FROM tb_user WHERE Column_name = 'user_id';
SHOW INDEX FROM tb_group WHERE Column_name = 'group_id';
ALTER TABLE tb_user ADD UNIQUE INDEX idx_user_id (user_id);
ALTER TABLE tb_group ADD UNIQUE INDEX idx_group_id (group_id);


/* 최상위 메뉴 */
CREATE TABLE tb_menu_root (
    root_id VARCHAR(50) PRIMARY KEY COMMENT '메뉴 루트 ID',
    name VARCHAR(100) NOT NULL COMMENT '메뉴 루트 이름',
    description VARCHAR(500) COMMENT '메뉴 루트 설명',
    created_by VARCHAR(100) NOT NULL COMMENT '생성자 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_by VARCHAR(100) COMMENT '수정자 ID',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '메뉴 루트 정보';


CREATE TABLE tb_module_instance (
    seq                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '인스턴스 ID',
    module_id          VARCHAR(100) NOT NULL COMMENT '모듈 고유 ID',
    instance_id        VARCHAR(100) NOT NULL COMMENT '인스턴스 고유 ID',
    instance_name      VARCHAR(100) NOT NULL COMMENT '인스턴스 이름',
    config_data        JSON COMMENT '인스턴스별 설정 데이터(JSON 형식)',
    enabled            BOOLEAN DEFAULT TRUE COMMENT '인스턴스 활성화 여부',
    created_by         VARCHAR(100) NOT NULL COMMENT '생성자 ID',
    created_at         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_by         VARCHAR(100) COMMENT '수정자 ID',
    updated_at         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    FOREIGN KEY (module_id) REFERENCES tb_modules(module_id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY uk_instance_id (instance_id) COMMENT '인스턴스 고유 ID는 유일해야 함'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='모듈 인스턴스 정보 테이블';


CREATE TABLE tb_group_permission_mapping (
    seq              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '고유 식별자',
    group_id         VARCHAR(50)     NOT NULL COMMENT '그룹 ID',
    module_id        VARCHAR(255)    NOT NULL COMMENT '모듈 고유 ID',
    instance_id      VARCHAR(100)    DEFAULT NULL COMMENT '모듈 인스턴스 ID (NULL이면 모든 인스턴스)',
    permission_code  VARCHAR(100)    NOT NULL COMMENT '권한 코드',
    created_by       VARCHAR(100)    NOT NULL COMMENT '권한 부여자 ID',
    created_at       DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_by       VARCHAR(100)    COMMENT '수정자 ID',
    updated_at       DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (seq),
    UNIQUE KEY uk_group_perm (group_id, module_id, instance_id, permission_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='그룹 권한 매핑 테이블';


CREATE TABLE tb_user_permission_mapping (
    seq              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '고유 식별자',
    user_id          VARCHAR(50)     NOT NULL COMMENT '사용자 ID',
    module_id        VARCHAR(255)    NOT NULL COMMENT '모듈 고유 ID',
    instance_id      VARCHAR(100)    DEFAULT NULL COMMENT '모듈 인스턴스 ID (NULL이면 모든 인스턴스)',
    permission_code  VARCHAR(100)    NOT NULL COMMENT '권한 코드',
    created_by       VARCHAR(100)    NOT NULL COMMENT '권한 부여자 ID',
    created_at       DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_by       VARCHAR(100)    COMMENT '수정자 ID',
    updated_at       DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (seq),
    UNIQUE KEY uk_user_perm (user_id, module_id, instance_id, permission_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 권한 매핑 테이블';


