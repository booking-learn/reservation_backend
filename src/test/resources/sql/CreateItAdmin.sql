INSERT INTO accounts (id, email, password, role)
VALUES (
           UUID_TO_BIN(UUID()),
           'it.admin@vetclinic.test',
           '$2b$10$/wawKPhuDakV1QClrtJL9eKhVr/wWzC2B685UYqOSL2L3rlaNoaRC', -- clair : ItAdmin123!
           'IT_ADMIN'
       );