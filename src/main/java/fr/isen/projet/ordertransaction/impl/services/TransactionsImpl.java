package fr.isen.projet.ordertransaction.impl.services;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.ItemType;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.OrderStatus;
import fr.isen.projet.ordertransaction.interfaces.models.Services;
import fr.isen.projet.ordertransaction.interfaces.models.TransactionSearchCriteria;
import fr.isen.projet.ordertransaction.interfaces.models.Transactions;
import fr.isen.projet.ordertransaction.interfaces.models.Orders;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.PaymentMethod;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.TransactionStatus;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.inject.spi.CDI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionsImpl implements Services.ServiceTransactions {

    AgroalDataSource dataSource = CDI.current().select(AgroalDataSource.class).get();

    @Override
    public int createTransaction(Transactions transaction) {
        if (transaction == null) {
            System.err.println("Erreur : La transaction reçue est null.");
            return -1;
        }

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            Orders order = transaction.getOrder();
            if (order != null) {
                PreparedStatement orderStmt = conn.prepareStatement(
                        "INSERT INTO Orders (idOrder, dateCreation, dateUpdate, status, totalAmount, idItem, itemType, idUser, description) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE dateCreation = VALUES(dateCreation), dateUpdate = VALUES(dateUpdate), status = VALUES(status), " +
                                "totalAmount = VALUES(totalAmount), idItem = VALUES(idItem), itemType = VALUES(itemType), idUser = VALUES(idUser), description = VALUES(description)"
                );

                orderStmt.setInt(1, order.getIdOrder());
                orderStmt.setString(2, order.getDateCreation());
                orderStmt.setString(3, order.getDateUpdate());
                orderStmt.setString(4, order.getStatus().name());
                orderStmt.setFloat(5, order.getTotalAmount());
                orderStmt.setInt(6, order.getIdItem());
                orderStmt.setString(7, order.getItemType().name());
                orderStmt.setInt(8, order.getIdUser());
                orderStmt.setString(9, order.getDescription());

                orderStmt.executeUpdate();
                orderStmt.close();
            }

            // Insertion ou mise à jour de la transaction
            PreparedStatement transactionStmt = conn.prepareStatement(
                    "INSERT INTO Transactions (idTransaction, amount, paymentMethod, idOrder, transactionStatus, dateCreation, dateUpdate, description) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE amount = VALUES(amount), paymentMethod = VALUES(paymentMethod), idOrder = VALUES(idOrder), " +
                            "transactionStatus = VALUES(transactionStatus), dateCreation = VALUES(dateCreation), dateUpdate = VALUES(dateUpdate), description = VALUES(description)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            transactionStmt.setInt(1, transaction.getIdTransaction());
            transactionStmt.setFloat(2, transaction.getAmount());
            transactionStmt.setString(3, transaction.getPaymentMethod().name());
            transactionStmt.setInt(4, transaction.getOrder().getIdOrder());
            transactionStmt.setString(5, transaction.getTransactionStatus().name());
            transactionStmt.setString(6, transaction.getDateCreation());
            transactionStmt.setString(7, transaction.getDateUpdate());
            transactionStmt.setString(8, transaction.getDescription());

            int affectedRows = transactionStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La création de la transaction a échoué, aucune ligne affectée.");
            }

            conn.commit();
            transactionStmt.close();
            conn.close();

            return transaction.getIdTransaction();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new RuntimeException("Erreur lors de la création de la transaction", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    @Override
    public Transactions getTransactionById(int idTransaction) {
        if (idTransaction <= 0) {
            System.err.println("Erreur : L'identifiant de la transaction est invalide.");
            return null;
        }

        Connection conn = null;
        Transactions transaction = null;

        try {
            conn = dataSource.getConnection();

            // Requête pour récupérer les informations de la transaction et de l'ordre associé
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT t.idTransaction, t.amount, t.paymentMethod, t.transactionStatus, t.dateCreation, t.dateUpdate, t.description, " +
                            "o.idOrder, o.dateCreation AS orderDateCreation, o.dateUpdate AS orderDateUpdate, o.status, o.totalAmount, " +
                            "o.idItem, o.itemType, o.idUser, o.description AS orderDescription " +
                            "FROM Transactions t " +
                            "INNER JOIN Orders o ON t.idOrder = o.idOrder " +
                            "WHERE t.idTransaction = ?"
            );

            stmt.setInt(1, idTransaction);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Construire la transaction
                transaction = new Transactions();
                transaction.setIdTransaction(rs.getInt("idTransaction"));
                transaction.setAmount(rs.getFloat("amount"));
                transaction.setPaymentMethod(PaymentMethod.valueOf(rs.getString("paymentMethod")));
                transaction.setTransactionStatus(TransactionStatus.valueOf(rs.getString("transactionStatus")));
                transaction.setDateCreation(rs.getString("dateCreation"));
                transaction.setDateUpdate(rs.getString("dateUpdate"));
                transaction.setDescription(rs.getString("description"));

                // Construire l'ordre associé
                Orders order = new Orders();
                order.setIdOrder(rs.getInt("idOrder"));
                order.setDateCreation(rs.getString("orderDateCreation"));
                order.setDateUpdate(rs.getString("orderDateUpdate"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setTotalAmount(rs.getFloat("totalAmount"));
                order.setIdItem(rs.getInt("idItem"));
                order.setItemType(ItemType.valueOf(rs.getString("itemType")));
                order.setIdUser(rs.getInt("idUser"));
                order.setDescription(rs.getString("orderDescription"));

                transaction.setOrder(order);
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la transaction avec l'ID " + idTransaction, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }

        return transaction;
    }


    @Override
    public List<Transactions> getAllTransactions(TransactionSearchCriteria criteria, int limit) {
        List<Transactions> transactions = new ArrayList<>();
        Connection conn = null;

        try {
            conn = dataSource.getConnection();

            // Construire la requête SQL dynamique en fonction des critères
            StringBuilder query = new StringBuilder(
                    "SELECT t.idTransaction, t.amount, t.paymentMethod, t.transactionStatus, t.dateCreation, t.dateUpdate, t.description, " +
                            "o.idOrder, o.dateCreation AS orderDateCreation, o.dateUpdate AS orderDateUpdate, o.status, o.totalAmount, " +
                            "o.idItem, o.itemType, o.idUser, o.description AS orderDescription " +
                            "FROM Transactions t INNER JOIN Orders o ON t.idOrder = o.idOrder WHERE 1=1"
            );

            if (criteria.getTransactionStatus() != null) {
                query.append(" AND t.transactionStatus = ?");
            }
            if (criteria.getPaymentMethod() != null) {
                query.append(" AND t.paymentMethod = ?");
            }
            if (criteria.getDateCreation() != null) {
                query.append(" AND t.dateCreation = ?");
            }

            query.append(" LIMIT ?");
            PreparedStatement stmt = conn.prepareStatement(query.toString());

            // Ajouter les paramètres dynamiques
            int index = 1;
            if (criteria.getTransactionStatus() != null) {
                stmt.setString(index++, criteria.getTransactionStatus().name());
            }
            if (criteria.getPaymentMethod() != null) {
                stmt.setString(index++, criteria.getPaymentMethod().name());
            }
            if (criteria.getDateCreation() != null) {
                stmt.setString(index++, criteria.getDateCreation());
            }
            stmt.setInt(index, limit);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transactions transaction = new Transactions();
                transaction.setIdTransaction(rs.getInt("idTransaction"));
                transaction.setAmount(rs.getFloat("amount"));
                transaction.setPaymentMethod(PaymentMethod.valueOf(rs.getString("paymentMethod")));
                transaction.setTransactionStatus(TransactionStatus.valueOf(rs.getString("transactionStatus")));
                transaction.setDateCreation(rs.getString("dateCreation"));
                transaction.setDateUpdate(rs.getString("dateUpdate"));
                transaction.setDescription(rs.getString("description"));

                Orders order = new Orders();
                order.setIdOrder(rs.getInt("idOrder"));
                order.setDateCreation(rs.getString("orderDateCreation"));
                order.setDateUpdate(rs.getString("orderDateUpdate"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setTotalAmount(rs.getFloat("totalAmount"));
                order.setIdItem(rs.getInt("idItem"));
                order.setItemType(ItemType.valueOf(rs.getString("itemType")));
                order.setIdUser(rs.getInt("idUser"));
                order.setDescription(rs.getString("orderDescription"));

                transaction.setOrder(order);
                transactions.add(transaction);
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des transactions", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }

        return transactions;
    }

    @Override
    public int updateTransaction(Transactions transaction) {
        if (transaction == null || transaction.getIdTransaction() <= 0) {
            System.err.println("Erreur : La transaction reçue est null ou l'ID est invalide.");
            return 0;
        }

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            // Mise à jour de l'ordre associé
            Orders order = transaction.getOrder();
            if (order != null) {
                PreparedStatement orderStmt = conn.prepareStatement(
                        "UPDATE Orders SET " +
                                "dateCreation = ?, " +
                                "dateUpdate = ?, " +
                                "status = ?, " +
                                "totalAmount = ?, " +
                                "idItem = ?, " +
                                "itemType = ?, " +
                                "idUser = ?, " +
                                "description = ? " +
                                "WHERE idOrder = ?"
                );

                orderStmt.setString(1, order.getDateCreation());
                orderStmt.setString(2, order.getDateUpdate());
                orderStmt.setString(3, order.getStatus().name());
                orderStmt.setFloat(4, order.getTotalAmount());
                orderStmt.setInt(5, order.getIdItem());
                orderStmt.setString(6, order.getItemType().name());
                orderStmt.setInt(7, order.getIdUser());
                orderStmt.setString(8, order.getDescription());
                orderStmt.setInt(9, order.getIdOrder());

                int affectedOrderRows = orderStmt.executeUpdate();
                System.out.println("Lignes affectées dans Orders : " + affectedOrderRows);

                if (affectedOrderRows == 0) {
                    System.err.println("Erreur : Aucun ordre trouvé avec l'ID " + order.getIdOrder());
                    conn.rollback();
                    return 0;
                }

                orderStmt.close();
            }

            // Mise à jour de la transaction
            PreparedStatement transactionStmt = conn.prepareStatement(
                    "UPDATE Transactions SET " +
                            "amount = ?, " +
                            "paymentMethod = ?, " +
                            "idOrder = ?, " +
                            "transactionStatus = ?, " +
                            "dateCreation = ?, " +
                            "dateUpdate = ?, " +
                            "description = ? " +
                            "WHERE idTransaction = ?"
            );

            transactionStmt.setFloat(1, transaction.getAmount());
            transactionStmt.setString(2, transaction.getPaymentMethod().name());
            transactionStmt.setInt(3, order.getIdOrder());
            transactionStmt.setString(4, transaction.getTransactionStatus().name());
            transactionStmt.setString(5, transaction.getDateCreation());
            transactionStmt.setString(6, transaction.getDateUpdate());
            transactionStmt.setString(7, transaction.getDescription());
            transactionStmt.setInt(8, transaction.getIdTransaction());

            int affectedTransactionRows = transactionStmt.executeUpdate();
            System.out.println("Lignes affectées dans Transactions : " + affectedTransactionRows);

            if (affectedTransactionRows == 0) {
                System.err.println("Erreur : Aucune transaction trouvée avec l'ID " + transaction.getIdTransaction());
                conn.rollback();
                return 0;
            }

            transactionStmt.close();

            conn.commit();
            conn.close();

            System.out.println("Mise à jour réussie pour la transaction et l'ordre associés.");
            return affectedTransactionRows;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new RuntimeException("Erreur lors de la mise à jour de la transaction et de l'ordre associés", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }


    @Override
    public void deleteTransaction(int idTransaction) {
        if (idTransaction <= 0) {
            System.err.println("Erreur : L'ID de la transaction est invalide.");
            return;
        }

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            // Supprimer la transaction
            PreparedStatement transactionStmt = conn.prepareStatement(
                    "DELETE FROM Transactions WHERE idTransaction = ?"
            );
            transactionStmt.setInt(1, idTransaction);

            int affectedRows = transactionStmt.executeUpdate();
            System.out.println("Lignes affectées dans Transactions : " + affectedRows);

            if (affectedRows == 0) {
                System.err.println("Erreur : Aucune transaction trouvée avec l'ID " + idTransaction);
                conn.rollback();
                return;
            }

            transactionStmt.close();

            // Valider la transaction
            conn.commit();
            System.out.println("Transaction supprimée avec succès : ID " + idTransaction);

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new RuntimeException("Erreur lors de la suppression de la transaction avec l'ID " + idTransaction, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

}

