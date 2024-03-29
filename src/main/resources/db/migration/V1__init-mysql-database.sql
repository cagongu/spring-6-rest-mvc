
    drop table if exists account;

    drop table if exists beer;

    drop table if exists customer;

    create table account (
        years integer not null,
        id varchar(36) not null,
        name varchar(50),
        primary key (id)
    ) engine=InnoDB;

    create table beer (
        beer_style tinyint check (beer_style between 0 and 9),
        price decimal(38,2),
        quantity_on_hand integer,
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        beer_name varchar(50),
        upc varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        name varchar(50),
        primary key (id)
    ) engine=InnoDB;

    drop table if exists account;

    drop table if exists beer;

    drop table if exists customer;

    create table account (
        years integer not null,
        id varchar(36) not null,
        name varchar(50),
        primary key (id)
    ) engine=InnoDB;

    create table beer (
        beer_style tinyint check (beer_style between 0 and 9),
        price decimal(38,2),
        quantity_on_hand integer,
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        beer_name varchar(50),
        upc varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        name varchar(50),
        primary key (id)
    ) engine=InnoDB;

    drop table if exists account;

    drop table if exists beer;

    drop table if exists customer;

    create table account (
        years integer not null,
        id varchar(36) not null,
        name varchar(50),
        primary key (id)
    ) engine=InnoDB;

    create table beer (
        beer_style tinyint check (beer_style between 0 and 9),
        price decimal(38,2),
        quantity_on_hand integer,
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        beer_name varchar(50),
        upc varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table customer (
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        name varchar(50),
        primary key (id)
    ) engine=InnoDB;
