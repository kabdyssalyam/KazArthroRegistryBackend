CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       login VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       firstname VARCHAR(255) NOT NULL,
                       lastname VARCHAR(255) NOT NULL,
                       type VARCHAR(50) NOT NULL,
                       active TINYINT NOT NULL
);

INSERT INTO users (login, password, firstname, lastname, type, active)
VALUES ('admin', '$2a$10$8Xz1NQ4knE.Hqvr6HQB34up9jZMn1/9rP.LUKmnD8gynaIILSWaqG', 'Admin', 'User', 'ADMIN', 1);
