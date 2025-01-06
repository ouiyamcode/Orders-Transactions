package fr.isen.projet.ordertransaction.interfaces.models;

import java.util.List;

//begin of modifiable zone(Javadoc).......C/d6a3c9a7-848b-4c3e-a27e-17a7f9d6b9f3

//end of modifiable zone(Javadoc).........E/d6a3c9a7-848b-4c3e-a27e-17a7f9d6b9f3
public class Services {
//begin of modifiable zone(Javadoc).......C/000910e1-ad0b-4de4-b723-79d412404e0c

//end of modifiable zone(Javadoc).........E/000910e1-ad0b-4de4-b723-79d412404e0c
    public interface ServiceOrders {
//begin of modifiable zone(Javadoc).......C/0edff492-5b08-44db-902c-9b8c8d488e7e

//end of modifiable zone(Javadoc).........E/0edff492-5b08-44db-902c-9b8c8d488e7e
        int createOrder(final Orders Orders);

//begin of modifiable zone(Javadoc).......C/ed76db15-8dff-433b-acab-d1597da270eb

//end of modifiable zone(Javadoc).........E/ed76db15-8dff-433b-acab-d1597da270eb
        Orders getOrderById(final int idOrder);

//begin of modifiable zone(Javadoc).......C/50d883ed-fbbe-45f1-ab6f-80ef59eb32a6

//end of modifiable zone(Javadoc).........E/50d883ed-fbbe-45f1-ab6f-80ef59eb32a6
        List<Orders> getAllOrders(final OrderSearchCriteria criteria, final int limit);

//begin of modifiable zone(Javadoc).......C/96dbdce8-c5d2-4fea-8dac-f7544847d517

//end of modifiable zone(Javadoc).........E/96dbdce8-c5d2-4fea-8dac-f7544847d517
        int updateOrder(final Orders Order);

//begin of modifiable zone(Javadoc).......C/29015d4f-5cda-47e9-b93d-42b606d5e791

//end of modifiable zone(Javadoc).........E/29015d4f-5cda-47e9-b93d-42b606d5e791
        void deleteOrder(final int idOrder);

    }

//begin of modifiable zone(Javadoc).......C/c4e1cdb5-8b81-4cea-8517-ef49fe51ea1f

//end of modifiable zone(Javadoc).........E/c4e1cdb5-8b81-4cea-8517-ef49fe51ea1f
    public interface ServiceTransactions {
//begin of modifiable zone(Javadoc).......C/45ed55e8-5554-4202-9fe3-3b65df793f08

//end of modifiable zone(Javadoc).........E/45ed55e8-5554-4202-9fe3-3b65df793f08
        int createTransaction(final Transactions Transaction);

//begin of modifiable zone(Javadoc).......C/e8d9828e-94b2-42c1-b9d5-d53d64d11f49

//end of modifiable zone(Javadoc).........E/e8d9828e-94b2-42c1-b9d5-d53d64d11f49
        Transactions getTransactionById(final int idTransaction);

//begin of modifiable zone(Javadoc).......C/55049feb-1b4f-492f-8366-fc6054dac31b

//end of modifiable zone(Javadoc).........E/55049feb-1b4f-492f-8366-fc6054dac31b
        List<Transactions> getAllTransactions(final TransactionSearchCriteria Criteria, final int limit);

//begin of modifiable zone(Javadoc).......C/357f58b5-56f0-4aa8-954a-6e0f4a361f98

//end of modifiable zone(Javadoc).........E/357f58b5-56f0-4aa8-954a-6e0f4a361f98
        int updateTransaction(final Transactions Transaction);

//begin of modifiable zone(Javadoc).......C/68ece4e5-c3ed-4fb3-903a-a54a040e489e

//end of modifiable zone(Javadoc).........E/68ece4e5-c3ed-4fb3-903a-a54a040e489e
        void deleteTransaction(final int idTransaction);

    }

}
