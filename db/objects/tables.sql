-- dim_user
CREATE SEQUENCE bclient.seq_user_id;
CREATE TABLE bclient.dim_user (
    user_id int4 NOT NULL DEFAULT nextval('bclient.seq_user_id'::regclass),
    user_login varchar(100) NOT NULL,
    user_full_name varchar(300) NOT NULL,
	user_password varchar(100) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id),
	CONSTRAINT uk_user_login UNIQUE (user_login)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.dim_user IS 'Пользователи приложения';
ALTER SEQUENCE bclient.seq_user_id OWNED BY bclient.dim_user.user_id;

-- dim_legal_entity
CREATE SEQUENCE bclient.seq_legal_entity_id;
CREATE TABLE bclient.dim_legal_entity (
    legal_entity_id int4 NOT NULL DEFAULT nextval('bclient.seq_legal_entity_id'::regclass),
    legal_entity_short_name varchar(100) NOT NULL,
    legal_entity_full_name varchar(300) NOT NULL,
	legal_entity_inn varchar(20) NOT NULL,
	legal_entity_kpp varchar(20) NOT NULL,
	legal_entity_ogrn varchar(20),
	legal_address varchar(500),
    CONSTRAINT pk_legal_entity PRIMARY KEY (legal_entity_id)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.dim_legal_entity IS 'Юридические лица';
ALTER SEQUENCE bclient.seq_legal_entity_id OWNED BY bclient.dim_legal_entity.legal_entity_id;

-- dim_bank
CREATE SEQUENCE bclient.seq_bank_id;
CREATE TABLE bclient.dim_bank (
    bank_id int4 NOT NULL DEFAULT nextval('bclient.seq_bank_id'::regclass),
    bank_name varchar(300) NOT NULL,
	bank_inn varchar(50) NOT NULL,
	bank_kpp varchar(50) NOT NULL,
	bank_bic varchar(50) NOT NULL,
	bank_corr_acc varchar(50),
    CONSTRAINT pk_bank PRIMARY KEY (bank_id)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.dim_bank IS 'Банки';
ALTER SEQUENCE bclient.seq_bank_id OWNED BY bclient.dim_bank.bank_id;

-- dim_account_status
CREATE TABLE bclient.dim_account_status (
    account_status_id int4 NOT NULL,
    account_status_code varchar(100) NOT NULL,
    account_status_name varchar(300) NOT NULL,
    CONSTRAINT pk_account_status PRIMARY KEY (account_status_id),
	CONSTRAINT uk_account_status_code UNIQUE (account_status_code)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.dim_account_status IS 'Статусы счетов';

-- dim_account
CREATE SEQUENCE bclient.seq_account_id;
CREATE TABLE bclient.dim_account (
    account_id int4 NOT NULL DEFAULT nextval('bclient.seq_account_id'::regclass),
    account_name varchar(100) NOT NULL,
	account_num varchar(20) NOT NULL,
	legal_entity_id int4,
	bank_id int4 NOT NULL,
    currency_code varchar(10) NOT NULL,
	account_status_id int4 NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (account_id),
	CONSTRAINT uk_account_num UNIQUE (account_num),
	CONSTRAINT fk_account_to_legal_entity FOREIGN KEY (legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id),
	CONSTRAINT fk_account_to_bank FOREIGN KEY (bank_id) REFERENCES bclient.dim_bank (bank_id),
	CONSTRAINT fk_account_to_status FOREIGN KEY (account_status_id) REFERENCES bclient.dim_account_status (account_status_id)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.dim_account IS 'Счета';
ALTER SEQUENCE bclient.seq_account_id OWNED BY bclient.dim_account.account_id;
CREATE INDEX ix_account_legal_entity on bclient.dim_account (legal_entity_id);
CREATE INDEX ix_account_bank on bclient.dim_account (bank_id);
CREATE INDEX ix_account_status on bclient.dim_account (account_status_id);

-- dim_contract
CREATE SEQUENCE bclient.seq_contract_id;
CREATE TABLE bclient.dim_contract (
    contract_id int4 NOT NULL DEFAULT nextval('bclient.seq_contract_id'::regclass),
    contract_name varchar(100) NOT NULL,
	contract_num varchar(20) NOT NULL,
	contract_open_date date NOT NULL,
	contract_close_date date NOT NULL,
	issuer_legal_entity_id int4 NOT NULL,
	singer_legal_entity_id int4 NOT NULL,
    currency_code varchar(10) NOT NULL,
    CONSTRAINT pk_contract PRIMARY KEY (contract_id),
	CONSTRAINT fk_contract_to_issuer FOREIGN KEY (issuer_legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id),
	CONSTRAINT fk_contract_to_singer FOREIGN KEY (singer_legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.dim_contract IS 'Договора';
COMMENT ON COLUMN bclient.dim_contract.issuer_legal_entity_id IS 'Эмитент договора';
COMMENT ON COLUMN bclient.dim_contract.singer_legal_entity_id IS 'Подписант договора';
ALTER SEQUENCE bclient.seq_contract_id OWNED BY bclient.dim_contract.contract_id;
CREATE INDEX ix_contract_issuer on bclient.dim_contract (issuer_legal_entity_id);
CREATE INDEX ix_contract_singer on bclient.dim_contract (singer_legal_entity_id);

-- dim_payment_order_status
CREATE TABLE bclient.dim_payment_order_status (
    payment_order_status_id int4 NOT NULL,
    payment_order_status_code varchar(100) NOT NULL,
    payment_order_status_name varchar(300) NOT NULL,
    CONSTRAINT pk_payment_order_status PRIMARY KEY (payment_order_status_id),
	CONSTRAINT uk_payment_order_status_code UNIQUE (payment_order_status_code)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.dim_payment_order_status IS 'Статусы Платёжных поручений';

-- rel_user_legal_entity
CREATE TABLE bclient.rel_user_legal_entity (
    user_id int4 NOT NULL,
    legal_entity_id int4 NOT NULL,
    CONSTRAINT pk_user_legal_entity PRIMARY KEY (user_id, legal_entity_id),
	CONSTRAINT fk_user_legal_entity_user FOREIGN KEY (user_id) REFERENCES bclient.dim_user (user_id),
	CONSTRAINT fk_user_legal_entity_legal FOREIGN KEY (legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.rel_user_legal_entity IS 'Связь пользователя с Юр.лицом';
CREATE INDEX ix_user_legal_entity on bclient.rel_user_legal_entity (legal_entity_id);

-- fct_payment_order
CREATE SEQUENCE bclient.seq_payment_order_id;
CREATE TABLE bclient.fct_payment_order (
    payment_order_id int4 NOT NULL DEFAULT nextval('bclient.seq_payment_order_id'::regclass),
    payment_order_num int4 NOT NULL,
	payment_order_date date NOT NULL,
	sender_legal_entity_id int4 NOT NULL,
	sender_account_id int4 NOT NULL,
	recipient_legal_entity_id int4 NOT NULL,
	recipient_account_id int4 NOT NULL,
	contract_id int4,
    currency_code varchar(10) NOT NULL,
	payment_order_amt numeric(10,2) NOT NULL,
	payment_reason varchar(500),
	payment_priority_code varchar(2),
	payment_order_status_id int4 NOT NULL,
	reject_reason varchar(500),
    CONSTRAINT pk_payment_order PRIMARY KEY (payment_order_id),
	CONSTRAINT fk_payment_order_to_sender FOREIGN KEY (sender_legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id),
	CONSTRAINT fk_payment_order_to_recipient FOREIGN KEY (recipient_legal_entity_id) REFERENCES bclient.dim_legal_entity (legal_entity_id),
	CONSTRAINT fk_payment_order_to_sender_acc FOREIGN KEY (sender_account_id) REFERENCES bclient.dim_account (account_id),
	CONSTRAINT fk_payment_order_to_recipi_acc FOREIGN KEY (recipient_account_id) REFERENCES bclient.dim_account (account_id),
	CONSTRAINT fk_payment_order_to_contract FOREIGN KEY (contract_id) REFERENCES bclient.dim_contract (contract_id),
	CONSTRAINT fk_payment_order_to_status FOREIGN KEY (payment_order_status_id) REFERENCES bclient.dim_payment_order_status (payment_order_status_id)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.fct_payment_order IS 'Платёжные поручения';
ALTER SEQUENCE bclient.seq_payment_order_id OWNED BY bclient.fct_payment_order.payment_order_id;
CREATE INDEX ix_payment_order_sender on bclient.fct_payment_order (sender_legal_entity_id);
CREATE INDEX ix_payment_order_recipient on bclient.fct_payment_order (recipient_legal_entity_id);
CREATE INDEX ix_payment_order_sender_acc on bclient.fct_payment_order (sender_account_id);
CREATE INDEX ix_payment_order_recipi_acc on bclient.fct_payment_order (recipient_account_id);
CREATE INDEX ix_payment_order_contract on bclient.fct_payment_order (contract_id);
CREATE INDEX ix_payment_order_status on bclient.fct_payment_order (payment_order_status_id);

-- fct_operation
CREATE SEQUENCE bclient.seq_operation_id;
CREATE TABLE bclient.fct_operation (
    operation_id int4 NOT NULL DEFAULT nextval('bclient.seq_operation_id'::regclass),
	operation_date date NOT NULL,
	operation_amt numeric(10,2) NOT NULL,
	debet_account_id int4 NOT NULL,
	kredit_account_id int4 NOT NULL,
	operation_descr varchar(300),
	CONSTRAINT pk_operation PRIMARY KEY (operation_id),
	CONSTRAINT fk_operation_to_debet_acc FOREIGN KEY (debet_account_id) REFERENCES bclient.dim_account (account_id),
	CONSTRAINT fk_operation_to_kredit_acc FOREIGN KEY (kredit_account_id) REFERENCES bclient.dim_account (account_id)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.fct_operation IS 'Финансовые операции';
ALTER SEQUENCE bclient.seq_operation_id OWNED BY bclient.fct_operation.operation_id;
CREATE INDEX ix_operation_debet_acc on bclient.fct_operation (debet_account_id);
CREATE INDEX ix_operation_kredit_acc on bclient.fct_operation (kredit_account_id);

-- fct_account_balance
CREATE SEQUENCE bclient.seq_account_balance_id;
CREATE TABLE bclient.fct_account_balance (
    account_balance_id int4 NOT NULL DEFAULT nextval('bclient.seq_account_balance_id'::regclass),
	account_balance_date date NOT NULL,
	account_balance_amt numeric(10,2) NOT NULL,
	account_id int4 NOT NULL,
	CONSTRAINT pk_account_balance PRIMARY KEY (account_balance_id),
	CONSTRAINT fk_account_balance_to_account FOREIGN KEY (account_id) REFERENCES bclient.dim_account (account_id)
)
WITH (
    OIDS=FALSE
);
COMMENT ON TABLE bclient.fct_account_balance IS 'Остатки средств на счёте';
ALTER SEQUENCE bclient.seq_account_balance_id OWNED BY bclient.fct_account_balance.account_balance_id;
CREATE INDEX ix_account_balance_account on bclient.fct_account_balance (account_id);
