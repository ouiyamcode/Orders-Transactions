package fr.isen.projet.ordertransaction.impl.services;

import fr.isen.projet.ordertransaction.interfaces.models.Services;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.inject.spi.CDI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class statusImpl implements Services.ServiceStatus {
    AgroalDataSource dataSource = CDI.current().select(AgroalDataSource.class).get();

    @Override
    public int countDatabaseElements() {
        int totalCount = 0;
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            System.out.println("Connexion à la base de données réussie.");


            // Compte le nombre d'éléments dans la table `order`
            PreparedStatement ordersCountStmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM `order`");
            ResultSet ordersResultSet = ordersCountStmt.executeQuery();
            if (ordersResultSet.next()) {
                totalCount = totalCount + ordersResultSet.getInt("count");
            }
            ordersResultSet.close();
            ordersCountStmt.close();

            // Compte le nombre d'éléments dans la table `transaction`
            PreparedStatement transactionsCountStmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM `transaction`");
            ResultSet transactionsResultSet = transactionsCountStmt.executeQuery();
            if (transactionsResultSet.next()) {
                totalCount = totalCount + transactionsResultSet.getInt("count");
            }
            transactionsResultSet.close();
            transactionsCountStmt.close();

            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du comptage des éléments dans les tables order et transaction", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Erreur lors de la fermeture de la connexion", e);
                }
            }
        }

        return totalCount;
    }
}

