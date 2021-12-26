## 分库分表

## MySQL 手动创建库
create schema demo_ds_0;
create schema demo_ds_1;

## Proxy
CREATE TABLE t_order (order_id BIGINT NOT NULL AUTO_INCREMENT, user_id INT NOT NULL, status VARCHAR(50), PRIMARY KEY (order_id));
use sharding_db;
insert into t_order(user_id, status) values(1,'OK'),(1,'FAIL');
insert into t_order(user_id, status) values(2,'OK'),(2,'FAIL');
