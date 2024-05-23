drop table if exists rewards;


create table rewards
(
    id                      int NOT NULL AUTO_INCREMENT,
    reward_points           integer,
    transaction_amount      float(53),
    transaction_entity_name varchar(255),
    `customer_id` int NOT NULL,
    primary key (id)
)


