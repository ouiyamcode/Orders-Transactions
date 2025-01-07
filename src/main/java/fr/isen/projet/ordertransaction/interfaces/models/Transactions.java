package fr.isen.projet.ordertransaction.interfaces.models;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.PaymentMethod;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.TransactionStatus;

public class Transactions {
    private int idTransaction;
    private String uuidTransaction;
    private float amount;
    private PaymentMethod paymentMethod;
    private Orders order;
    private TransactionStatus transactionStatus;
    private String dateCreation;
    private String dateUpdate;
    private String description;

    // Getters and Setters

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getUuidTransaction() {
        return uuidTransaction;
    }

    public void setUuidTransaction(String uuidTransaction) {
        this.uuidTransaction = uuidTransaction;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

