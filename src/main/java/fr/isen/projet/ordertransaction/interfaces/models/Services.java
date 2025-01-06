package fr.isen.projet.ordertransaction.interfaces.models;

import java.util.List;

public class Services {
    public interface ServiceOrders {
        int createOrder(final Orders Orders);
        Orders getOrderById(final int idOrder);
        List<Orders> getAllOrders(final OrderSearchCriteria criteria, final int limit);
        int updateOrder(final Orders Order);
        void deleteOrder(final int idOrder);
    }

    public interface ServiceTransactions {
        int createTransaction(final Transactions Transaction);
        Transactions getTransactionById(final int idTransaction);
        List<Transactions> getAllTransactions(final TransactionSearchCriteria Criteria, final int limit);
        int updateTransaction(final Transactions Transaction);
        void deleteTransaction(final int idTransaction);
    }

    public interface ServiceStatus {
        int countDatabaseElements();
    }
}
