package fr.isen.projet.ordertransaction.impl.services;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.OrderStatus;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.ItemType;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.TransactionStatus;
import fr.isen.projet.ordertransaction.interfaces.models.OrderSearchCriteria;
import fr.isen.projet.ordertransaction.interfaces.models.Orders;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.inject.spi.CDI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersImpl {
    AgroalDataSource dataSource = CDI.current().select(AgroalDataSource.class).get();

    public int createOrder(Orders order) {
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO `order` (uuid_order, date_created, date_update, status, total_amount, id_item, uuid_item, item_type, id_user, uuid_user, description) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, order.getUuidOrder());
            stmt.setTimestamp(2, Timestamp.valueOf(order.getDateCreation()));
            stmt.setTimestamp(3, Timestamp.valueOf(order.getDateUpdate()));
            stmt.setString(4, order.getStatus().name());
            stmt.setFloat(5, order.getTotalAmount());
            stmt.setInt(6, order.getIdItem());
            stmt.setString(7, order.getUuidItem());
            stmt.setString(8, order.getItemType().name());
            stmt.setInt(9, order.getIdUser());
            stmt.setString(10, order.getUuidUser());
            stmt.setString(11, order.getDescription());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La création de la commande a échoué.");
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                // Appeler les APIs externes selon le type d'élément
                /*
                try {
                    if (order.getItemType() == ItemType.PRODUCT) {
                        String productApiUrl = "http://localhost:8089/products/" + order.getIdItem() + "/stock/1";
                        callExternalAPI(productApiUrl);
                    } else if (order.getItemType() == ItemType.APPARTMENT) {
                        String apartmentApiUrl = "http://localhost:8084/apartment/" + order.getIdItem() + "/ordered";
                        callExternalAPI(apartmentApiUrl);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors de l'appel aux APIs externes", e);
                }
                */

                return orderId;
            }
            return -1;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création de la commande", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Orders getOrderById(int idOrder) {
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM `order` WHERE id_order = ?"
            );

            stmt.setInt(1, idOrder);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapOrder(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la commande", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Orders> getOrdersByCriteria(OrderSearchCriteria criteria, Integer limit) {
        List<Orders> orders = new ArrayList<>();
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            StringBuilder query = new StringBuilder("SELECT * FROM `order` WHERE 1=1");

            // Ajout des critères dynamiques
            if (criteria.getDateCreation() != null) {
                query.append(" AND date_created = ?");
            }
            if (criteria.getTotalAmount() != null) {
                query.append(" AND ABS(total_amount - ?) < 0.01"); // Tolérance pour FLOAT
            }
            if (criteria.getStatus() != null) {
                query.append(" AND status = ?");
            }
            if (criteria.getItemType() != null) {
                query.append(" AND item_type = ?");
            }

            // Ajout de la limite si fournie
            if (limit != null && limit > 0) {
                query.append(" LIMIT ?");
            }

            PreparedStatement stmt = conn.prepareStatement(query.toString());

            // Ajout des paramètres dynamiques
            int index = 1;
            if (criteria.getDateCreation() != null) {
                stmt.setTimestamp(index++, Timestamp.valueOf(criteria.getDateCreation()));
            }
            if (criteria.getTotalAmount() != null) {
                stmt.setFloat(index++, criteria.getTotalAmount());
            }
            if (criteria.getStatus() != null) {
                stmt.setString(index++, criteria.getStatus().name());
            }
            if (criteria.getItemType() != null) {
                stmt.setString(index++, criteria.getItemType().name());
            }
            if (limit != null && limit > 0) {
                stmt.setInt(index++, limit);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(mapOrder(rs));
            }

            return orders;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commandes avec critères.", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public int updateOrder(Orders order) {
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE `order` SET date_update = ?, status = ?, total_amount = ?, description = ? WHERE id_order = ?"
            );

            stmt.setTimestamp(1, Timestamp.valueOf(order.getDateUpdate()));
            stmt.setString(2, order.getStatus().name());
            stmt.setFloat(3, order.getTotalAmount());
            stmt.setString(4, order.getDescription());
            stmt.setInt(5, order.getIdOrder());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la commande", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void markOrderAsCancelled(int idOrder) {
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            // Mise à jour du statut de la commande
            PreparedStatement updateOrderStmt = conn.prepareStatement(
                    "UPDATE `order` SET status = ? WHERE id_order = ?"
            );
            updateOrderStmt.setString(1, OrderStatus.CANCELLED.name());
            updateOrderStmt.setInt(2, idOrder);
            updateOrderStmt.executeUpdate();

            // Mise à jour des transactions associées à la commande
            PreparedStatement updateTransactionsStmt = conn.prepareStatement(
                    "UPDATE `transaction` SET transaction_status = ? WHERE id_order = ?"
            );
            updateTransactionsStmt.setString(1, TransactionStatus.FAILED.name());
            updateTransactionsStmt.setInt(2, idOrder);
            updateTransactionsStmt.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("Erreur lors de l'annulation de la commande et de ses transactions associées", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    private void callExternalAPI(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode != 200 && responseCode != 201) {
            throw new RuntimeException("Échec de l'appel à l'API externe : " + apiUrl + " (Code : " + responseCode + ")");
        }

        connection.disconnect();
    }
    */

    private Orders mapOrder(ResultSet rs) throws SQLException {
        Orders order = new Orders();
        order.setIdOrder(rs.getInt("id_order"));
        order.setUuidOrder(rs.getString("uuid_order"));
        order.setDateCreation(rs.getString("date_created"));
        order.setDateUpdate(rs.getString("date_update"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setTotalAmount(rs.getFloat("total_amount"));
        order.setIdItem(rs.getInt("id_item"));
        order.setUuidItem(rs.getString("uuid_item"));
        order.setItemType(ItemType.valueOf(rs.getString("item_type")));
        order.setIdUser(rs.getInt("id_user"));
        order.setUuidUser(rs.getString("uuid_user"));
        order.setDescription(rs.getString("description"));
        return order;
    }
}
