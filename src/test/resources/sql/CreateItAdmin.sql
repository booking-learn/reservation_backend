INSERT INTO accounts (id, email, password, role)
VALUES (
           UUID_TO_BIN(UUID()),
           'it.admin@vetclinic.test',
           '$2b$10$oc1.f7n.Tl0aGC48rFGfvehC1BfmtBoBXTQZ08hMh1pAKd.Nu00/m',
           'IT_ADMIN'
       );