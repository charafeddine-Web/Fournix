CREATE TABLE IF NOT EXISTS commande (
                                        id BIGSERIAL PRIMARY KEY,
                                        fournisseur_id BIGINT REFERENCES fournisseur(id),
                                        prix NUMERIC(10,2),
                                        statut_commande varchar(50) DEFAULT 'EN_ATTENTE' NOT NULL,
                                        date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
