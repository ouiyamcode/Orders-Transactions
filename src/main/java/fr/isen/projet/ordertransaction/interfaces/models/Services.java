package fr.isen.projet.ordertransaction.interfaces.models;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("d6a3c9a7-848b-4c3e-a27e-17a7f9d6b9f3")
public class Services {
    @objid ("000910e1-ad0b-4de4-b723-79d412404e0c")
    public interface ServiceOrders {
        @objid ("0edff492-5b08-44db-902c-9b8c8d488e7e")
        int createOrder(final Orders Orders);

        @objid ("ed76db15-8dff-433b-acab-d1597da270eb")
        Orders getOrderById(final int idOrder);

        @objid ("50d883ed-fbbe-45f1-ab6f-80ef59eb32a6")
        List<Orders> getAllOrders(final OrderSearchCriteria criteria, final int limit);

        @objid ("96dbdce8-c5d2-4fea-8dac-f7544847d517")
        int updateOrder(final Orders Order);

        @objid ("29015d4f-5cda-47e9-b93d-42b606d5e791")
        void deleteOrder(final int idOrder);

    }

    @objid ("c4e1cdb5-8b81-4cea-8517-ef49fe51ea1f")
    public interface ServiceTransactions {
        @objid ("45ed55e8-5554-4202-9fe3-3b65df793f08")
        int createTransaction(final Transactions Transaction);

        @objid ("e8d9828e-94b2-42c1-b9d5-d53d64d11f49")
        Transactions getTransactionById(final int idTransaction);

        @objid ("55049feb-1b4f-492f-8366-fc6054dac31b")
        List<Transactions> getAllTransactions(final TransactionSearchCriteria Criteria, final int limit);

        @objid ("357f58b5-56f0-4aa8-954a-6e0f4a361f98")
        int updateTransaction(final Transactions Transaction);

        @objid ("68ece4e5-c3ed-4fb3-903a-a54a040e489e")
        void deleteTransaction(final int idTransaction);

    }

}
