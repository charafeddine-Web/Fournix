CREATE TABLE IF NOT EXISTS mouvements_stock (
    id BIGSERIAL PRIMARY KEY,
    date_mouvement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    quantite_mouvement INT,
    produit_id BIGINT REFERENCES produit(id),
    commande_id BIGINT REFERENCES commande(id),
    type_mouvement Varchar(50) NOT NULL
    );