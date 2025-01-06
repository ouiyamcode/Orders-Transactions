package fr.isen.projet.ordertransaction.interfaces.models;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.PaymentMethod;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.TransactionStatus;

public class TransactionSearchCriteria {
    private String dateCreation;
    private TransactionStatus transactionStatus;
    private PaymentMethod paymentMethod;

}
