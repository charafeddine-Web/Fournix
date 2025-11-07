CREATE TABLE IF NOT EXISTS produit (
                                       id BIGSERIAL PRIMARY KEY,
                                       nom VARCHAR(50),
                                       description TEXT,
                                       prix_unit NUMERIC(10,2),
                                       categorie VARCHAR(50),
                                       stock_actuel INT,
                                       cout_moyen NUMERIC(10,2)  -- si tu ajoutes ce champ pour CUMP
);