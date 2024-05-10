create table author_book
(
    author_id bigint not null,
    book_id   bigint not null,
    primary key (author_id, book_id)
);
create table authors
(
    id         bigserial    not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    primary key (id)
);
create table book_comments
(
    book_id    bigint       not null,
    created_at timestamp(6) not null,
    id         bigserial    not null,
    text       varchar(255) not null,
    primary key (id)
);
create table books
(
    is_available boolean        not null,
    price        numeric(38, 2) not null,
    id           bigserial      not null,
    isbn         varchar(255)   not null,
    name         varchar(255)   not null,
    primary key (id)
);
create table order_book
(
    book_id  bigint not null,
    order_id bigint not null,
    primary key (book_id, order_id)
);
create table orders
(
    total_price numeric(38, 2) not null,
    created     timestamp(6),
    id          bigserial      not null,
    user_id     bigint,
    primary key (id)
);
create table shopping_cart_book
(
    book_id          bigint not null,
    shopping_cart_id bigint not null,
    primary key (book_id, shopping_cart_id)
);
create table shopping_carts
(
    user_id bigint not null,
    primary key (user_id)
);
create table users
(
    id       bigserial    not null,
    email    varchar(255) not null unique,
    password varchar(255) not null,
    role     varchar(255) check (role in ('USER', 'ADMIN')),
    username varchar(255) not null,
    primary key (id)
);
create table wish_list_book
(
    book_id      bigint not null,
    wish_list_id bigint not null,
    primary key (book_id, wish_list_id)
);
create table wish_lists
(
    user_id bigint not null,
    primary key (user_id)
);

alter table users
    add constraint user_uq_email unique (email);
alter table author_book
    add constraint author_book_fk_book foreign key (book_id) references books (id);
alter table author_book
    add constraint author_book_fk_author foreign key (author_id) references authors (id);
alter table book_comments
    add constraint book_comment_fk_book foreign key (book_id) references books (id);
alter table order_book
    add constraint order_book_fk_book foreign key (book_id) references books (id);
alter table order_book
    add constraint order_book_fk_order foreign key (order_id) references orders (id);
alter table orders
    add constraint order_fk_user foreign key (user_id) references users (id);
alter table shopping_carts
    add constraint shopping_cart_fk_user foreign key (user_id) references users (id);
alter table shopping_cart_book
    add constraint shopping_cart_book_fk_book foreign key (book_id) references books (id);
alter table shopping_cart_book
    add constraint shopping_cart_book_fk_shopping_cart foreign key (shopping_cart_id) references shopping_carts (user_id);
alter table wish_lists
    add constraint wish_list_fk_user foreign key (user_id) references users (id);
alter table wish_list_book
    add constraint wish_list_book_fk_book foreign key (book_id) references books (id);
alter table wish_list_book
    add constraint wish_list_book_fk_wish_list foreign key (wish_list_id) references wish_lists (user_id);
