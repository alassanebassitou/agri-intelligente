INSERT INTO type_culture (code, name) VALUES
    ('CEREALE', 'Céréale'),
    ('TUBERCULE', 'Tubercule'),
    ('LEGUMINEUSE', 'Légumineuse'),
    ('RENTE', 'Culture de rente');

-- Données de démo : cultures béninoises courantes, avec exigences agronomiques
INSERT INTO culture (
    name, duree_cycle_jours, temperature_min, temperature_max,
    pluviometrie_min_mm, pluviometrie_max_mm, type_sol_prefere,
    saison_semis_debut, saison_semis_fin, type_culture_code
) VALUES
    ('Maïs',    90, 20, 32, 400, 900, 'LIMONEUX',   4,  6, 'CEREALE'),
    ('Manioc', 270, 20, 35, 500, 1500, 'SABLONNEUX', 3,  7, 'TUBERCULE'),
    ('Igname', 240, 22, 33, 700, 1500, 'LIMONEUX',   3,  5, 'TUBERCULE'),
    ('Arachide', 100, 22, 32, 400, 800, 'SABLONNEUX', 5,  7, 'LEGUMINEUSE'),
    ('Niébé',   70, 20, 33, 300, 700, 'SABLONNEUX',  6,  8, 'LEGUMINEUSE');