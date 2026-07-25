INSERT INTO `care_offerings` (`id`, `name`, `description`, `price`, `duration`, `care_service`)
VALUES
    (UUID_TO_BIN(UUID()), 'General Consultation', 'Complete health examination including auscultation, temperature check, and a general assessment of the animal.', 55.00, 30, 'CONSULTATION'),

    (UUID_TO_BIN(UUID()), 'Cat Spay', 'Surgical sterilization procedure for female cats, including anesthesia and post-operative follow-up.', 200.00, 60, 'STERILIZATION'),

    (UUID_TO_BIN(UUID()), 'Dog Neuter (Large Breed)', 'Surgical sterilization procedure for large-breed male dogs, including anesthesia and post-operative follow-up.', 280.00, 90, 'STERILIZATION'),

    (UUID_TO_BIN(UUID()), 'Dental Scaling', 'Complete dental cleaning under anesthesia with polishing, including an oral health check.', 180.00, 60, 'DENTISTRY'),

    (UUID_TO_BIN(UUID()), 'Annual Vaccination', 'Update of the vaccination protocol (rabies, parvovirus, etc.) based on the species and age of the animal.', 65.00, 20, 'VACCINATION'),

    (UUID_TO_BIN(UUID()), 'X-Ray Imaging', 'Diagnostic imaging exam used to assess bones, joints, and internal organs.', 120.00, 30, 'MEDICAL_IMAGING');