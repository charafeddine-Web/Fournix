DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'status_commande') THEN
CREATE TYPE status_commande AS ENUM ('EN_ATTENTE', 'VALIDEE', 'LIVREE', 'ANNULEE');
END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'type_mouvement') THEN
CREATE TYPE type_mouvement AS ENUM ('ENTREE', 'SORTIE', 'AJUSTEMENT');
END IF;
END $$;

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