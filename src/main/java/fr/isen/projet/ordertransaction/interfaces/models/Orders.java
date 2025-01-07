package fr.isen.projet.ordertransaction.interfaces.models;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.ItemType;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.OrderStatus;

public class Orders {
    private int idOrder;
    private String uuidOrder; // Nouveau
    private String dateCreation; // Conservé
    private String dateUpdate; // Conservé
    private OrderStatus status;
    private float totalAmount;
    private int idItem; // Conservé
    private String uuidItem; // Nouveau
    private ItemType itemType;
    private int idUser; // Conservé
    private String uuidUser; // Nouveau
    private String description;

    // Getters and Setters

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getUuidOrder() {
        return uuidOrder;
    }

    public void setUuidOrder(String uuidOrder) {
        this.uuidOrder = uuidOrder;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getUuidItem() {
        return uuidItem;
    }

    public void setUuidItem(String uuidItem) {
        this.uuidItem = uuidItem;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUuidUser() {
        return uuidUser;
    }

    public void setUuidUser(String uuidUser) {
        this.uuidUser = uuidUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

