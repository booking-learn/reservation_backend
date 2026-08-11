INSERT INTO accounts (id, email, password, role)
VALUES (
           UUID_TO_BIN('11111111-1111-1111-1111-111111111111'),
           'it.employee@vetclinic.test',
           '$2b$10$39mBsrNlNwu/sXcA9DSxhu2Ml1g1C4SC.rIClfwtgR2W.2KbeQlu.', -- clair : EmployeeTest123!
           'VETERINARIAN'
       );

INSERT INTO employees (id, account_id, first_name, last_name, phone_number)
VALUES (
           UUID_TO_BIN('22222222-2222-2222-2222-222222222222'),
           UUID_TO_BIN('11111111-1111-1111-1111-111111111111'),
           'Jean',
           'Testeur',
           '5145550000'
       );