INSERT INTO bank.public.USERS (USERNAME, PASSWORD, EMAIL, STATUS) VALUES ('admin','$2a$10$stY59P7EmX7HNbUKiIVBXueYq6Gcc7/rVbUM3wChykyLcBudoUkdm', 'admin@admin.com', true);

INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('All');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('Dashboard');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('Create User');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('User Detail');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('Home');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('Create Customer');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('Search Customer');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('Customer Upload');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('Balance');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('Deposit');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('Withdraw');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('History');
INSERT INTO bank.public.MENUS (USER_INTERFACE) VALUES ('Change Password');

INSERT INTO bank.public.ROLES (ROLE) VALUES ('ROLE_ADMIN');
INSERT INTO bank.public.ROLES (ROLE) VALUES ('ROLE_USER');

INSERT INTO bank.public.USERS_ROLES (USER_ID, ROLE_ID) VALUES (1, 1);

INSERT INTO bank.public.ROLES_MENUS (ROLE_ID, MENU_ID) VALUES (1, 1);

INSERT INTO bank.public.NATURES (TYPE) VALUES ('Business');
INSERT INTO bank.public.NATURES (TYPE) VALUES ('Savings');
INSERT INTO bank.public.NATURES (TYPE) VALUES ('Fixed');
INSERT INTO bank.public.NATURES (TYPE) VALUES ('Investment');

INSERT INTO bank.public.PRODUCTS (NATURE_ID, CODE, NAME) VALUES (2, '101/151', 'Savings Bank Deposit Account');
INSERT INTO bank.public.PRODUCTS (NATURE_ID, CODE, NAME) VALUES (2,'102', 'Savings Bank Deposit Account, Staff');
INSERT INTO bank.public.PRODUCTS (NATURE_ID, CODE, NAME) VALUES (2, '103', 'Savings Bank Deposit, Power Account');
INSERT INTO bank.public.PRODUCTS (NATURE_ID, CODE, NAME) VALUES (2, '104', 'ATM Account Credit Balance');
INSERT INTO bank.public.PRODUCTS (NATURE_ID, CODE, NAME) VALUES (2, '105', 'Excel Savings Account');


