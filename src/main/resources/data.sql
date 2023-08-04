INSERT IGNORE INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_MOD'),
       ('ROLE_USER');

INSERT IGNORE INTO permission (name)
VALUES ('USE_WRITE'),
       ('USE_READ'),
       ('USE_MOD'),
       ('USE_CREATE'),
       ('USE_CHANGESETTINGS'),
       ('USE_DELETE'),
       ('USE_CREATOR');