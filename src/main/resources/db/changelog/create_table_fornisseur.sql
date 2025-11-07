

CREATE TABLE IF NOT EXISTS fournisseur (
                                           id BIGSERIAL PRIMARY KEY,
                                           email VARCHAR(50) UNIQUE,
                                           nom VARCHAR(50),
                                           societe VARCHAR(50),
                                           adresse VARCHAR(100),
                                           contact VARCHAR(100),
                                           telephone VARCHAR(50),
                                           ville VARCHAR(50),
                                           ice VARCHAR(50) UNIQUE,
                                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);