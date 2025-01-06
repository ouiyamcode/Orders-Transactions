package fr.isen.projet.ordertransaction.interfaces.models;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.PaymentMethod;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.TransactionStatus;

//begin of modifiable zone(Javadoc).......C/da5184a4-8322-4e94-9c45-07f3069817ab

//end of modifiable zone(Javadoc).........E/da5184a4-8322-4e94-9c45-07f3069817ab
public class Transactions {
//begin of modifiable zone(Javadoc).......C/3a148c23-5ff7-4e21-844f-e5508019c829

//end of modifiable zone(Javadoc).........E/3a148c23-5ff7-4e21-844f-e5508019c829
    private int idTransaction ;

//begin of modifiable zone(Javadoc).......C/75a1c065-3276-471a-a959-48d2bb882fbb

//end of modifiable zone(Javadoc).........E/75a1c065-3276-471a-a959-48d2bb882fbb
    private float amount;

//begin of modifiable zone(Javadoc).......C/f0a60046-1394-4f19-9eb6-6a3d3b765a5c

//end of modifiable zone(Javadoc).........E/f0a60046-1394-4f19-9eb6-6a3d3b765a5c
    private PaymentMethod paymentMethod;

//begin of modifiable zone(Javadoc).......C/1d9063f2-83bd-4b8e-90e6-bd6cb430fb8e

//end of modifiable zone(Javadoc).........E/1d9063f2-83bd-4b8e-90e6-bd6cb430fb8e
    private int idOrder;

//begin of modifiable zone(Javadoc).......C/d414a610-2d33-4d88-a375-fe07c80c28a2

//end of modifiable zone(Javadoc).........E/d414a610-2d33-4d88-a375-fe07c80c28a2
    private TransactionStatus transactionStatus;

//begin of modifiable zone(Javadoc).......C/b88afcf5-da0c-41f3-873f-d49c225356ba

//end of modifiable zone(Javadoc).........E/b88afcf5-da0c-41f3-873f-d49c225356ba
    private String dateCreation;

//begin of modifiable zone(Javadoc).......C/303c03bc-ff72-496d-8b8d-f2b6c9fa38b4

//end of modifiable zone(Javadoc).........E/303c03bc-ff72-496d-8b8d-f2b6c9fa38b4
    private String dateUpdate;

//begin of modifiable zone(Javadoc).......C/e0dd3422-4570-4873-8fab-ae36156d2c22

//end of modifiable zone(Javadoc).........E/e0dd3422-4570-4873-8fab-ae36156d2c22
    private String description;

}
