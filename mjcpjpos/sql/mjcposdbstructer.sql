CREATE DATABASE mjcpos;

-- ============================================================
--  카페 POS 스키마 (MariaDB 10.11에서 실행 확인 / MySQL 8.0.16 이상 호환 예상)
--  ERD 내보내기 SQL을 실행 가능한 문법으로 정리한 버전
--  ※ "⚠ 확인" 표시가 있는 컬럼 이름은 ERD 왼쪽 컬럼명을 확인하지 못해 추정한 값
-- ============================================================

CREATE DATABASE IF NOT EXISTS mjcpos DEFAULT CHARACTER SET utf8mb4;
USE mjcpos;

-- (재실행용) 기존 테이블 삭제: 자식 → 부모 순서. 필요할 때만 주석 해제
-- DROP TABLE IF EXISTS SUPPLY, ATTENDANCE, STAMP_LOG, SELL, STAFF, MENU, CUSTOMER;


-- ------------------------------------------------------------
-- 1. CUSTOMER (고객 테이블)
-- ------------------------------------------------------------
CREATE TABLE CUSTOMER (
    CUST_ID     INT          NOT NULL AUTO_INCREMENT COMMENT '고객 번호',
    STAMP       INT          NOT NULL DEFAULT 0      COMMENT '현재 보유 스탬프',
    NAME        VARCHAR(30)  NOT NULL                COMMENT '이름',
    PHONE_1     CHAR(3)      NOT NULL                COMMENT '전화번호 앞자리 (010)',
    PHONE_2     CHAR(4)      NOT NULL                COMMENT '전화번호 가운데',
    PHONE_3     CHAR(4)      NOT NULL                COMMENT '전화번호 뒤 4자리',
    CREATED_AT  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일시',
    PRIMARY KEY (CUST_ID),
    UNIQUE KEY UQ_CUSTOMER_PHONE (PHONE_1, PHONE_2, PHONE_3),   -- 같은 번호 중복 가입 방지
    KEY IDX_CUSTOMER_PHONE3 (PHONE_3)                           -- 뒤 4자리로 회원 조회
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='고객 테이블';


-- ------------------------------------------------------------
-- 2. STAFF (직원테이블)
-- ------------------------------------------------------------
CREATE TABLE STAFF (
    STAFF_ID     INT          NOT NULL AUTO_INCREMENT COMMENT '직원 번호',
    NAME         VARCHAR(30)  NOT NULL                COMMENT '이름',
    PHONE_1      CHAR(3)      NOT NULL                COMMENT '전화번호 앞자리',
    PHONE_2      CHAR(4)      NOT NULL                COMMENT '전화번호 가운데',
    PHONE_3      CHAR(4)      NOT NULL                COMMENT '전화번호 뒤 4자리',
    ROLE         VARCHAR(20)  NOT NULL                COMMENT '직책 (점장/직원/아르바이트)',
    STATUS       INT          NOT NULL DEFAULT 1      COMMENT '0 퇴사, 1 재직, 2 휴직',
    HOURLY_WAGE  INT          NOT NULL DEFAULT 0      COMMENT '시급(원)  ⚠ 확인: 원본 Field8 INT',
    PIN          VARCHAR(50)  NULL                    COMMENT '로그인 PIN  ⚠ 확인: 원본 Field7 VARCHAR(50)',
    PRIMARY KEY (STAFF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='직원테이블';


-- ------------------------------------------------------------
-- 3. MENU (메뉴 테이블)
-- ------------------------------------------------------------
CREATE TABLE MENU (
    MENU_ID      INT          NOT NULL AUTO_INCREMENT COMMENT '메뉴 번호',
    MENU         VARCHAR(50)  NOT NULL                COMMENT '메뉴 이름',
    PRICE        INT          NOT NULL                COMMENT '정가(원)',
    SEASON       VARCHAR(20)  NULL                    COMMENT '시즌 구분 (상시/여름 한정 등)',
    STOCK_QTY    INT          NOT NULL DEFAULT 0      COMMENT '현재 재고 수량',
    SALE         BOOLEAN      NOT NULL DEFAULT FALSE  COMMENT '할인 중 여부',
    SALE_PER     INT          NOT NULL DEFAULT 0      COMMENT '할인율(%)',
    CATEGORY     VARCHAR(20)  NOT NULL                COMMENT '분류 (커피/논커피/디저트)',
    MENU_OPTION  VARCHAR(100) NULL                    COMMENT '선택 가능한 옵션 목록',
    IS_SOLD_OUT  BOOLEAN      NOT NULL DEFAULT FALSE  COMMENT '품절 여부',
    PRIMARY KEY (MENU_ID),
    CONSTRAINT CK_MENU_PRICE    CHECK (PRICE >= 0),
    CONSTRAINT CK_MENU_STOCK    CHECK (STOCK_QTY >= 0),
    CONSTRAINT CK_MENU_SALE_PER CHECK (SALE_PER BETWEEN 0 AND 100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='메뉴 테이블';


-- ------------------------------------------------------------
-- 4. SELL (주문 테이블) : 메뉴 종류별 한 줄, ORDER_NO로 한 주문을 묶음
-- ------------------------------------------------------------
CREATE TABLE SELL (
    SELL_ID      INT          NOT NULL AUTO_INCREMENT COMMENT '줄 단위 고유 번호',
    MENU_ID      INT          NOT NULL                COMMENT '판매한 메뉴',
    CUST_ID      INT          NULL                    COMMENT '주문 회원 (NULL = 비회원)',
    STAFF_ID     INT          NULL                    COMMENT '계산한 직원 (NULL = 키오스크 주문)',
    TOTAL_PRICE  INT          NOT NULL                COMMENT '이 줄의 금액 (단가 x 수량)',
    SELL_TIME    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '주문 시각',
    ORDER_NO     INT          NOT NULL                COMMENT '주문 묶음 번호 (같은 주문은 같은 값)',
    PAY_TYPE     VARCHAR(15)  NULL                    COMMENT '결제 수단  ⚠ 확인: 원본 Field5 VARCHAR(15)',
    QUANTITY     INT          NOT NULL DEFAULT 1      COMMENT '구매 수량  ⚠ 확인: 원본 Field3 INT',
    STATUS       VARCHAR(10)  NOT NULL DEFAULT '완료' COMMENT '주문 상태 (완료/취소)  ⚠ 확인: 원본 Field6 VARCHAR(10)',
    PRIMARY KEY (SELL_ID),
    KEY IDX_SELL_ORDER_NO (ORDER_NO),
    KEY IDX_SELL_TIME (SELL_TIME),
    CONSTRAINT CK_SELL_QTY CHECK (QUANTITY > 0),
    CONSTRAINT FK_SELL_MENU     FOREIGN KEY (MENU_ID)  REFERENCES MENU (MENU_ID),
    CONSTRAINT FK_SELL_CUSTOMER FOREIGN KEY (CUST_ID)  REFERENCES CUSTOMER (CUST_ID),
    CONSTRAINT FK_SELL_STAFF    FOREIGN KEY (STAFF_ID) REFERENCES STAFF (STAFF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='주문 테이블';


-- ------------------------------------------------------------
-- 5. STAMP_LOG (스탬프 내역)
-- ------------------------------------------------------------
CREATE TABLE STAMP_LOG (
    STAMP_LOG_ID INT          NOT NULL AUTO_INCREMENT COMMENT '이력 번호',
    CUST_ID      INT          NOT NULL                COMMENT '대상 고객',
    TYPE         VARCHAR(10)  NOT NULL                COMMENT '적립/사용/소멸/환불',
    STAMP_TIME   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '적립/사용 시각',
    STAMP_COUNT  INT          NOT NULL                COMMENT '변동 개수 (적립 +, 사용 -)',
    PRIMARY KEY (STAMP_LOG_ID),
    KEY IDX_STAMP_LOG_CUST (CUST_ID),
    CONSTRAINT FK_STAMP_LOG_CUSTOMER FOREIGN KEY (CUST_ID) REFERENCES CUSTOMER (CUST_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='스탬프 내역';


-- ------------------------------------------------------------
-- 6. ATTENDANCE (근무 기록 테이블)
-- ------------------------------------------------------------
CREATE TABLE ATTENDANCE (
    ATTENDANCE_ID    INT      NOT NULL AUTO_INCREMENT COMMENT '근무 기록 번호',
    STAFF_ID         INT      NOT NULL                COMMENT '근무한 직원',
    WORK_DATE        DATE     NOT NULL                COMMENT '근무 날짜',
    CLOCK_IN         DATETIME NOT NULL                COMMENT '출근 시각',
    CLOCK_OUT        DATETIME NULL                    COMMENT '퇴근 시각 (근무 중이면 NULL)',
    TOTAL_WORK_TIME  INT      NULL                    COMMENT '총 근무시간 (분 단위 권장)',
    PRIMARY KEY (ATTENDANCE_ID),
    KEY IDX_ATTENDANCE_STAFF_DATE (STAFF_ID, WORK_DATE),
    CONSTRAINT FK_ATTENDANCE_STAFF FOREIGN KEY (STAFF_ID) REFERENCES STAFF (STAFF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='근무 기록 테이블';


-- ------------------------------------------------------------
-- 7. SUPPLY (소모품관리 테이블)  ※ 신규 테이블 — 컬럼 이름 전부 추정
-- ------------------------------------------------------------
CREATE TABLE SUPPLY (
    SUPPLY_ID    INT          NOT NULL AUTO_INCREMENT COMMENT '소모품 번호',
    MENU_ID      INT          NOT NULL                COMMENT '연결된 메뉴',
    SUPPLY_NAME  VARCHAR(50)  NOT NULL                COMMENT '소모품명  ⚠ 확인: 원본 Field CHAR',
    UNIT         VARCHAR(10)  NULL                    COMMENT '단위  ⚠ 확인: 원본 Field2 CHAR',
    STOCK_QTY    INT          NOT NULL DEFAULT 0      COMMENT '현재 재고  ⚠ 확인: 원본 Field3 INTd(오타)',
    MIN_QTY      INT          NOT NULL DEFAULT 0      COMMENT '최소 재고 기준  ⚠ 확인: 원본 Field4 INT',
    STATUS       VARCHAR(10)  NULL                    COMMENT '상태 (정상/부족)  ⚠ 확인: 원본 Field5 VARCHAR(10)',
    UPDATED_AT   DATETIME     NULL                    COMMENT '최근 갱신 일시  ⚠ 확인: 원본 Field6 DATETIME',
    PRIMARY KEY (SUPPLY_ID),
    KEY IDX_SUPPLY_MENU (MENU_ID),
    CONSTRAINT FK_SUPPLY_MENU FOREIGN KEY (MENU_ID) REFERENCES MENU (MENU_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='소모품관리 테이블';