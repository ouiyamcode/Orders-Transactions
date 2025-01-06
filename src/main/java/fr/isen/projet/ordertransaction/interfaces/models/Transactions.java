package fr.isen.projet.ordertransaction.interfaces.models;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.PaymentMethod;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.TransactionStatus;

public class Transactions {
    private int idTransaction ;
    private float amount;
    private PaymentMethod paymentMethod;
    private int idOrder;
    private TransactionStatus transactionStatus;
    private String dateCreation;
    private String dateUpdate;
    private String description;

}
