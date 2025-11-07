CREATE TABLE IF NOT EXISTS produit_commande (
                                                id BIGSERIAL PRIMARY KEY,
                                                produit_id BIGINT REFERENCES produit(id),
    commande_id BIGINT REFERENCES commande(id),
    quantite INT,
    prix_unit NUMERIC(10,2)
    );