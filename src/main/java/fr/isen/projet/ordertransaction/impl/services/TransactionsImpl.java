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

import java.sql.*;
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
                System.out.println("Préparation de l'insertion ou mise à jour de l'ordre : " + order);
                PreparedStatement orderStmt = conn.prepareStatement(
                        "INSERT INTO `order` (id_order, uuid_order, date_created, date_update, status, total_amount, id_item, uuid_item, item_type, id_user, uuid_user, description) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE date_created = VALUES(date_created), date_update = VALUES(date_update), status = VALUES(status), " +
                                "total_amount = VALUES(total_amount), id_item = VALUES(id_item), uuid_item = VALUES(uuid_item), item_type = VALUES(item_type), " +
                                "id_user = VALUES(id_user), uuid_user = VALUES(uuid_user), description = VALUES(description)"
                );

                orderStmt.setInt(1, order.getIdOrder());
                orderStmt.setString(2, order.getUuidOrder());
                orderStmt.setTimestamp(3, order.getDateCreation() != null ? Timestamp.valueOf(order.getDateCreation()) : null);
                orderStmt.setTimestamp(4, order.getDateUpdate() != null ? Timestamp.valueOf(order.getDateUpdate()) : null);
                orderStmt.setString(5, order.getStatus().name());
                orderStmt.setFloat(6, order.getTotalAmount());
                orderStmt.setInt(7, order.getIdItem());
                orderStmt.setString(8, order.getUuidItem());
                orderStmt.setString(9, order.getItemType().name());
                orderStmt.setInt(10, order.getIdUser());
                orderStmt.setString(11, order.getUuidUser());
                orderStmt.setString(12, order.getDescription());

                System.out.println("Exécution de l'insertion ou mise à jour de l'ordre...");
                orderStmt.executeUpdate();
                orderStmt.close();
            } else {
                System.out.println("Aucun ordre associé à la transaction. Passage à la transaction.");
            }

            PreparedStatement transactionStmt = conn.prepareStatement(
                    "INSERT INTO transaction (id_transaction, uuid_transaction, amount, payment_method, id_order, transaction_status, date_created, date_update, description) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE amount = VALUES(amount), payment_method = VALUES(payment_method), id_order = VALUES(id_order), " +
                            "transaction_status = VALUES(transaction_status), date_created = VALUES(date_created), date_update = VALUES(date_update), description = VALUES(description)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            transactionStmt.setInt(1, transaction.getIdTransaction());
            transactionStmt.setString(2, transaction.getUuidTransaction());
            transactionStmt.setFloat(3, transaction.getAmount());
            transactionStmt.setString(4, transaction.getPaymentMethod().name());
            transactionStmt.setInt(5, transaction.getOrder() != null ? transaction.getOrder().getIdOrder() : 0);
            transactionStmt.setString(6, transaction.getTransactionStatus().name());
            transactionStmt.setTimestamp(7, transaction.getDateCreation() != null ? Timestamp.valueOf(transaction.getDateCreation()) : null);
            transactionStmt.setTimestamp(8, transaction.getDateUpdate() != null ? Timestamp.valueOf(transaction.getDateUpdate()) : null);
            transactionStmt.setString(9, transaction.getDescription());

            System.out.println("Exécution de l'insertion ou mise à jour de la transaction...");
            int affectedRows = transactionStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La création de la transaction a échoué, aucune ligne affectée.");
            }

            conn.commit();
            transactionStmt.close();
            conn.close();

            System.out.println("Transaction créée avec succès : ID " + transaction.getIdTransaction());
            return transaction.getIdTransaction();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Rollback effectué à cause de l'erreur : " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new RuntimeException("Erreur lors de la création de la transaction", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connexion fermée.");
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
                    "SELECT t.id_transaction, t.uuid_transaction, t.amount, t.payment_method, t.id_order, " +
                            "t.transaction_status, t.date_created, t.date_update, t.description, " +
                            "o.id_order, o.uuid_order, o.date_created AS order_date_created, o.date_update AS order_date_update, " +
                            "o.status, o.total_amount, o.id_item, o.uuid_item, o.item_type, o.id_user, o.uuid_user, o.description AS order_description " +
                            "FROM transaction t " +
                            "INNER JOIN `order` o ON t.id_order = o.id_order " +
                            "WHERE t.id_transaction = ?"
            );

            stmt.setInt(1, idTransaction);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Construire la transaction
                transaction = new Transactions();
                transaction.setIdTransaction(rs.getInt("id_transaction"));
                transaction.setUuidTransaction(rs.getString("uuid_transaction"));
                transaction.setAmount(rs.getFloat("amount"));
                transaction.setPaymentMethod(PaymentMethod.valueOf(rs.getString("payment_method")));
                transaction.setTransactionStatus(TransactionStatus.valueOf(rs.getString("transaction_status")));
                transaction.setDateCreation(rs.getString("date_created"));
                transaction.setDateUpdate(rs.getString("date_update"));
                transaction.setDescription(rs.getString("description"));

                // Construire la commande associée
                Orders order = new Orders();
                order.setIdOrder(rs.getInt("id_order"));
                order.setUuidOrder(rs.getString("uuid_order"));
                order.setDateCreation(rs.getString("order_date_created"));
                order.setDateUpdate(rs.getString("order_date_update"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setTotalAmount(rs.getFloat("total_amount"));
                order.setIdItem(rs.getInt("id_item"));
                order.setUuidItem(rs.getString("uuid_item"));
                order.setItemType(ItemType.valueOf(rs.getString("item_type")));
                order.setIdUser(rs.getInt("id_user"));
                order.setUuidUser(rs.getString("uuid_user"));
                order.setDescription(rs.getString("order_description"));

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
                    "SELECT t.id_transaction, t.uuid_transaction, t.amount, t.payment_method, t.id_order, " +
                            "t.transaction_status, t.date_created, t.date_update, t.description, " +
                            "o.id_order, o.uuid_order, o.date_created AS order_date_created, o.date_update AS order_date_update, " +
                            "o.status, o.total_amount, o.id_item, o.uuid_item, o.item_type, o.id_user, o.uuid_user, o.description AS order_description " +
                            "FROM transaction t " +
                            "INNER JOIN `order` o ON t.id_order = o.id_order WHERE 1=1"
            );

            // Ajouter les critères dynamiques
            if (criteria.getTransactionStatus() != null) {
                query.append(" AND t.transaction_status = ?");
            }
            if (criteria.getPaymentMethod() != null) {
                query.append(" AND t.payment_method = ?");
            }
            if (criteria.getDateCreation() != null) {
                query.append(" AND t.date_created = ?");
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
                // Construire la transaction
                Transactions transaction = new Transactions();
                transaction.setIdTransaction(rs.getInt("id_transaction"));
                transaction.setUuidTransaction(rs.getString("uuid_transaction"));
                transaction.setAmount(rs.getFloat("amount"));
                transaction.setPaymentMethod(PaymentMethod.valueOf(rs.getString("payment_method")));
                transaction.setTransactionStatus(TransactionStatus.valueOf(rs.getString("transaction_status")));
                transaction.setDateCreation(rs.getString("date_created"));
                transaction.setDateUpdate(rs.getString("date_update"));
                transaction.setDescription(rs.getString("description"));

                // Construire l'ordre associé
                Orders order = new Orders();
                order.setIdOrder(rs.getInt("id_order"));
                order.setUuidOrder(rs.getString("uuid_order"));
                order.setDateCreation(rs.getString("order_date_created"));
                order.setDateUpdate(rs.getString("order_date_update"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setTotalAmount(rs.getFloat("total_amount"));
                order.setIdItem(rs.getInt("id_item"));
                order.setUuidItem(rs.getString("uuid_item"));
                order.setItemType(ItemType.valueOf(rs.getString("item_type")));
                order.setIdUser(rs.getInt("id_user"));
                order.setUuidUser(rs.getString("uuid_user"));
                order.setDescription(rs.getString("order_description"));

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
                        "UPDATE `order` SET " +
                                "date_created = ?, " +
                                "date_update = ?, " +
                                "status = ?, " +
                                "total_amount = ?, " +
                                "id_item = ?, " +
                                "uuid_item = ?, " +
                                "item_type = ?, " +
                                "id_user = ?, " +
                                "uuid_user = ?, " +
                                "description = ? " +
                                "WHERE id_order = ?"
                );

                orderStmt.setString(1, order.getDateCreation());
                orderStmt.setString(2, order.getDateUpdate());
                orderStmt.setString(3, order.getStatus().name());
                orderStmt.setFloat(4, order.getTotalAmount());
                orderStmt.setInt(5, order.getIdItem());
                orderStmt.setString(6, order.getUuidItem());
                orderStmt.setString(7, order.getItemType().name());
                orderStmt.setInt(8, order.getIdUser());
                orderStmt.setString(9, order.getUuidUser());
                orderStmt.setString(10, order.getDescription());
                orderStmt.setInt(11, order.getIdOrder());

                int affectedOrderRows = orderStmt.executeUpdate();
                System.out.println("Lignes affectées dans `order` : " + affectedOrderRows);

                if (affectedOrderRows == 0) {
                    System.err.println("Erreur : Aucun ordre trouvé avec l'ID " + order.getIdOrder());
                    conn.rollback();
                    return 0;
                }

                orderStmt.close();
            }

            // Mise à jour de la transaction
            PreparedStatement transactionStmt = conn.prepareStatement(
                    "UPDATE transaction SET " +
                            "amount = ?, " +
                            "payment_method = ?, " +
                            "id_order = ?, " +
                            "transaction_status = ?, " +
                            "date_created = ?, " +
                            "date_update = ?, " +
                            "description = ? " +
                            "WHERE id_transaction = ?"
            );

            transactionStmt.setFloat(1, transaction.getAmount());
            transactionStmt.setString(2, transaction.getPaymentMethod().name());
            transactionStmt.setInt(3, order != null ? order.getIdOrder() : null);
            transactionStmt.setString(4, transaction.getTransactionStatus().name());
            transactionStmt.setString(5, transaction.getDateCreation());
            transactionStmt.setString(6, transaction.getDateUpdate());
            transactionStmt.setString(7, transaction.getDescription());
            transactionStmt.setInt(8, transaction.getIdTransaction());

            int affectedTransactionRows = transactionStmt.executeUpdate();
            System.out.println("Lignes affectées dans transaction : " + affectedTransactionRows);

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

            // Mettre à jour le statut de la transaction en FAILED
            PreparedStatement transactionStmt = conn.prepareStatement(
                    "UPDATE transaction SET transaction_status = ? WHERE id_transaction = ?"
            );
            transactionStmt.setString(1, "FAILED");
            transactionStmt.setInt(2, idTransaction);

            int affectedRows = transactionStmt.executeUpdate();
            System.out.println("Lignes affectées dans transaction : " + affectedRows);

            if (affectedRows == 0) {
                System.err.println("Erreur : Aucune transaction trouvée avec l'ID " + idTransaction);
                conn.rollback();
                return;
            }

            transactionStmt.close();

            // Valider la transaction
            conn.commit();
            System.out.println("Statut de la transaction mis à jour avec succès : ID " + idTransaction);

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Rollback effectué en raison d'une erreur.");
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new RuntimeException("Erreur lors de la mise à jour du statut de la transaction avec l'ID " + idTransaction, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connexion fermée.");
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

}

