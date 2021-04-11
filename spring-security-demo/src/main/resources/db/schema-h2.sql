DROP TABLE IF EXISTS USER;

CREATE TABLE USER
(
    id       BIGINT(20) auto_increment primary key COMMENT '主键ID',
    account  VARCHAR(30) NULL DEFAULT NULL COMMENT '用户名',
    password VARCHAR(50) NULL DEFAULT NULL COMMENT '密码'
);