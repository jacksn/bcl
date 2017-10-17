-- dim_user
CREATE SEQUENCE bclient.seq_user_id;

CREATE TABLE bclient.dim_user (
  user_id        INT4         NOT NULL DEFAULT nextval('bclient.seq_user_id' :: REGCLASS),
  user_login     VARCHAR(100) NOT NULL,
  user_full_name VARCHAR(300) NOT NULL,
  user_password  VARCHAR(100) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (user_id),
  CONSTRAINT uk_user_login UNIQUE (user_login)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.dim_user IS 'Пользователи приложения';

ALTER SEQUENCE bclient.seq_user_id
OWNED BY bclient.dim_user.user_id;

-- dim_legal_entity
CREATE SEQUENCE bclient.seq_legal_entity_id;

CREATE TABLE bclient.dim_legal_entity (
  legal_entity_id         INT4         NOT NULL DEFAULT nextval('bclient.seq_legal_entity_id' :: REGCLASS),
  legal_entity_short_name VARCHAR(100) NOT NULL,
  legal_entity_full_name  VARCHAR(300) NOT NULL,
  legal_entity_inn        VARCHAR(20)  NOT NULL,
  legal_entity_kpp        VARCHAR(20)  NOT NULL,
  legal_entity_ogrn       VARCHAR(20),
  legal_address           VARCHAR(500),
  CONSTRAINT pk_legal_entity PRIMARY KEY (legal_entity_id)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.dim_legal_entity IS 'Юридические лица';

ALTER SEQUENCE bclient.seq_legal_entity_id
OWNED BY bclient.dim_legal_entity.legal_entity_id;

-- dim_bank
CREATE SEQUENCE bclient.seq_bank_id;
CREATE TABLE bclient.dim_bank (
  bank_id       INT4         NOT NULL DEFAULT nextval('bclient.seq_bank_id' :: REGCLASS),
  bank_name     VARCHAR(300) NOT NULL,
  bank_inn      VARCHAR(50)  NOT NULL,
  bank_kpp      VARCHAR(50)  NOT NULL,
  bank_bic      VARCHAR(50)  NOT NULL,
  bank_corr_acc VARCHAR(50),
  CONSTRAINT pk_bank PRIMARY KEY (bank_id)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.dim_bank IS 'Банки';

ALTER SEQUENCE bclient.seq_bank_id
OWNED BY bclient.dim_bank.bank_id;

-- dim_account_status
CREATE TABLE bclient.dim_account_status (
  account_status_id   INT4         NOT NULL,
  account_status_code VARCHAR(100) NOT NULL,
  account_status_name VARCHAR(300) NOT NULL,
  CONSTRAINT pk_account_status PRIMARY KEY (account_status_id),
  CONSTRAINT uk_account_status_code UNIQUE (account_status_code)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.dim_account_status IS 'Статусы счетов';

-- dim_account
CREATE SEQUENCE bclient.seq_account_id;
CREATE TABLE bclient.dim_account (
  account_id        INT4         NOT NULL DEFAULT nextval('bclient.seq_account_id' :: REGCLASS),
  account_name      VARCHAR(100) NOT NULL,
  account_num       VARCHAR(20)  NOT NULL,
  legal_entity_id   INT4,
  bank_id           INT4         NOT NULL,
  currency_code     VARCHAR(10)  NOT NULL,
  account_status_id INT4         NOT NULL,
  CONSTRAINT pk_account PRIMARY KEY (account_id),
  CONSTRAINT uk_account_num UNIQUE (account_num),
  CONSTRAINT fk_account_to_legal_entity FOREIGN KEY (legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id),
  CONSTRAINT fk_account_to_bank FOREIGN KEY (bank_id) REFERENCES bclient.dim_bank (bank_id),
  CONSTRAINT fk_account_to_status FOREIGN KEY (account_status_id) REFERENCES bclient.dim_account_status (account_status_id)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.dim_account IS 'Счета';

ALTER SEQUENCE bclient.seq_account_id
OWNED BY bclient.dim_account.account_id;

CREATE INDEX ix_account_legal_entity
  ON bclient.dim_account (legal_entity_id);

CREATE INDEX ix_account_bank
  ON bclient.dim_account (bank_id);

CREATE INDEX ix_account_status
  ON bclient.dim_account (account_status_id);

-- dim_contract
CREATE SEQUENCE bclient.seq_contract_id;
CREATE TABLE bclient.dim_contract (
  contract_id            INT4         NOT NULL DEFAULT nextval('bclient.seq_contract_id' :: REGCLASS),
  contract_name          VARCHAR(100) NOT NULL,
  contract_num           VARCHAR(20)  NOT NULL,
  contract_open_date     DATE         NOT NULL,
  contract_close_date    DATE         NOT NULL,
  issuer_legal_entity_id INT4         NOT NULL,
  signer_legal_entity_id INT4         NOT NULL,
  currency_code          VARCHAR(10)  NOT NULL,
  CONSTRAINT pk_contract PRIMARY KEY (contract_id),
  CONSTRAINT fk_contract_to_issuer FOREIGN KEY (issuer_legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id),
  CONSTRAINT fk_contract_to_signer FOREIGN KEY (signer_legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.dim_contract IS 'Договора';

COMMENT ON COLUMN bclient.dim_contract.issuer_legal_entity_id IS 'Эмитент договора';

COMMENT ON COLUMN bclient.dim_contract.signer_legal_entity_id IS 'Подписант договора';

ALTER SEQUENCE bclient.seq_contract_id
OWNED BY bclient.dim_contract.contract_id;

CREATE INDEX ix_contract_issuer
  ON bclient.dim_contract (issuer_legal_entity_id);

CREATE INDEX ix_contract_signer
  ON bclient.dim_contract (signer_legal_entity_id);

-- dim_payment_order_status
CREATE TABLE bclient.dim_payment_order_status (
  payment_order_status_id   INT4         NOT NULL,
  payment_order_status_code VARCHAR(100) NOT NULL,
  payment_order_status_name VARCHAR(300) NOT NULL,
  CONSTRAINT pk_payment_order_status PRIMARY KEY (payment_order_status_id),
  CONSTRAINT uk_payment_order_status_code UNIQUE (payment_order_status_code)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.dim_payment_order_status IS 'Статусы Платёжных поручений';

-- rel_user_legal_entity
CREATE TABLE bclient.rel_user_legal_entity (
  user_id         INT4 NOT NULL,
  legal_entity_id INT4 NOT NULL,
  CONSTRAINT pk_user_legal_entity PRIMARY KEY (user_id, legal_entity_id),
  CONSTRAINT fk_user_legal_entity_user FOREIGN KEY (user_id) REFERENCES bclient.dim_user (user_id),
  CONSTRAINT fk_user_legal_entity_legal FOREIGN KEY (legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.rel_user_legal_entity IS 'Связь пользователя с Юр.лицом';

CREATE INDEX ix_user_legal_entity
  ON bclient.rel_user_legal_entity (legal_entity_id);

-- fct_payment_order
CREATE SEQUENCE bclient.seq_payment_order_id;

CREATE TABLE bclient.fct_payment_order (
  payment_order_id          INT4           NOT NULL DEFAULT nextval('bclient.seq_payment_order_id' :: REGCLASS),
  payment_order_num         INT4           NOT NULL,
  payment_order_date        DATE           NOT NULL,
  sender_legal_entity_id    INT4           NOT NULL,
  sender_account_id         INT4           NOT NULL,
  recipient_legal_entity_id INT4           NOT NULL,
  recipient_account_id      INT4           NOT NULL,
  contract_id               INT4,
  currency_code             VARCHAR(10)    NOT NULL,
  payment_order_amt         NUMERIC(10, 2) NOT NULL,
  payment_reason            VARCHAR(500),
  payment_priority_code     VARCHAR(2),
  payment_order_status_id   INT4           NOT NULL,
  reject_reason             VARCHAR(500),
  CONSTRAINT pk_payment_order PRIMARY KEY (payment_order_id),
  CONSTRAINT fk_payment_order_to_sender FOREIGN KEY (sender_legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id),
  CONSTRAINT fk_payment_order_to_recipient FOREIGN KEY (recipient_legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id),
  CONSTRAINT fk_payment_order_to_sender_acc FOREIGN KEY (sender_account_id) REFERENCES bclient.dim_account (account_id),
  CONSTRAINT fk_payment_order_to_recipi_acc FOREIGN KEY (recipient_account_id) REFERENCES bclient.dim_account (account_id),
  CONSTRAINT fk_payment_order_to_contract FOREIGN KEY (contract_id) REFERENCES bclient.dim_contract (contract_id),
  CONSTRAINT fk_payment_order_to_status FOREIGN KEY (payment_order_status_id) REFERENCES bclient.dim_payment_order_status (payment_order_status_id)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.fct_payment_order IS 'Платёжные поручения';

ALTER SEQUENCE bclient.seq_payment_order_id
OWNED BY bclient.fct_payment_order.payment_order_id;

CREATE INDEX ix_payment_order_sender
  ON bclient.fct_payment_order (sender_legal_entity_id);

CREATE INDEX ix_payment_order_recipient
  ON bclient.fct_payment_order (recipient_legal_entity_id);

CREATE INDEX ix_payment_order_sender_acc
  ON bclient.fct_payment_order (sender_account_id);

CREATE INDEX ix_payment_order_recipi_acc
  ON bclient.fct_payment_order (recipient_account_id);

CREATE INDEX ix_payment_order_contract
  ON bclient.fct_payment_order (contract_id);

CREATE INDEX ix_payment_order_status
  ON bclient.fct_payment_order (payment_order_status_id);

-- fct_operation
CREATE SEQUENCE bclient.seq_operation_id;

CREATE TABLE bclient.fct_operation (
  operation_id      INT4           NOT NULL DEFAULT nextval('bclient.seq_operation_id' :: REGCLASS),
  operation_date    DATE           NOT NULL,
  operation_amt     NUMERIC(10, 2) NOT NULL,
  debet_account_id  INT4           NOT NULL,
  credit_account_id INT4           NOT NULL,
  operation_descr   VARCHAR(300),
  CONSTRAINT pk_operation PRIMARY KEY (operation_id),
  CONSTRAINT fk_operation_to_debet_acc FOREIGN KEY (debet_account_id) REFERENCES bclient.dim_account (account_id),
  CONSTRAINT fk_operation_to_credit_acc FOREIGN KEY (credit_account_id) REFERENCES bclient.dim_account (account_id)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.fct_operation IS 'Финансовые операции';

ALTER SEQUENCE bclient.seq_operation_id
OWNED BY bclient.fct_operation.operation_id;

CREATE INDEX ix_operation_debet_acc
  ON bclient.fct_operation (debet_account_id);

CREATE INDEX ix_operation_credit_acc
  ON bclient.fct_operation (credit_account_id);

-- fct_account_balance
CREATE SEQUENCE bclient.seq_account_balance_id;

CREATE TABLE bclient.fct_account_balance (
  account_balance_id   INT4           NOT NULL DEFAULT nextval('bclient.seq_account_balance_id' :: REGCLASS),
  account_balance_date DATE           NOT NULL,
  account_balance_amt  NUMERIC(10, 2) NOT NULL,
  account_id           INT4           NOT NULL,
  CONSTRAINT pk_account_balance PRIMARY KEY (account_balance_id),
  CONSTRAINT fk_account_balance_to_account FOREIGN KEY (account_id) REFERENCES bclient.dim_account (account_id)
)
WITH (
OIDS = FALSE
);
COMMENT ON TABLE bclient.fct_account_balance IS 'Остатки средств на счёте';

ALTER SEQUENCE bclient.seq_account_balance_id
OWNED BY bclient.fct_account_balance.account_balance_id;

CREATE INDEX ix_account_balance_account
  ON bclient.fct_account_balance (account_id);
