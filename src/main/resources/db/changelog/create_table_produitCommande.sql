CREATE TABLE IF NOT EXISTS produit_commande (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produit_id BIGINT,
    commande_id BIGINT,
    quantite INT,
    prix_unit DOUBLE,
    FOREIGN KEY (produit_id) REFERENCES produit(id),
    FOREIGN KEY (commande_id) REFERENCES commande(id)
)