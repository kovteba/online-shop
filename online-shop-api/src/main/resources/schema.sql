create table products
(
    id    bigserial    not null
        constraint products_pkey
            primary key,
    brand varchar(255) not null,
    ean   varchar(255) not null,
    model varchar(255) not null,
    price bigint       not null,
    type  integer      not null
);

alter table products
    owner to root;

create table product_info_list
(
    product_entity_id bigint       not null
        constraint fkd1aml695rrqj5rdcjbwlrkqjj
            references products,
    product_info      varchar(255),
    product_info_key  varchar(255) not null,
    constraint product_info_list_pkey
        primary key (product_entity_id, product_info_key)
);

alter table product_info_list
    owner to root;

create table product_pic_list
(
    product_entity_id bigint not null
        constraint fkjdekru6egor1drp98y88h8t6h
            references products,
    list_pic          varchar(255)
);

alter table product_pic_list
    owner to root;

create table secret_tokens
(
    id           bigserial not null
        constraint secret_tokens_pkey
            primary key,
    user_email   varchar(255),
    secret_token varchar(255)
);

alter table secret_tokens
    owner to root;

create table users
(
    id           bigserial    not null
        constraint users_pkey
            primary key,
    email        varchar(255) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    password     varchar(255) not null,
    phone_number varchar(255) not null
        constraint uk_9q63snka3mdh91as4io72espi
            unique,
    user_role    integer      not null
);

alter table users
    owner to root;

create table user_basket
(
    user_entity_id bigint not null
        constraint fkchoy4c22gsct14vvr5vklswdu
            references users,
    basket         varchar(255),
    basket_key     bigint not null
        constraint fk4672vdj251bftfuww20omk7td
            references products,
    constraint user_basket_pkey
        primary key (user_entity_id, basket_key)
);

alter table user_basket
    owner to root;

