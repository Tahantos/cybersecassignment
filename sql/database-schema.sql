/* Do not change this schema */
CREATE TABLE User (
    id varchar(6) PRIMARY KEY,
    name varchar(200), secret varchar(200), password varchar(200)
);

CREATE TABLE Messages ( id varchar(6) PRIMARY KEY, message varchar(140), user_id varchar(6) references User(id));
