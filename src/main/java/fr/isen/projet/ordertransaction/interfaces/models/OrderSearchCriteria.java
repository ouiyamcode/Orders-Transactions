package fr.isen.projet.ordertransaction.interfaces.models;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.ItemType;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.OrderStatus;

public class OrderSearchCriteria {
    private String dateCreation;
    private Float totalAmount; // Utiliser Float pour permettre des valeurs nulles
    private OrderStatus status;
    private ItemType itemType;

    // Constructeur par défaut
    public OrderSearchCriteria() {
    }

    // Getters et Setters
    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    // Méthode toString pour le debug
    @Override
    public String toString() {
        return "OrderSearchCriteria{" +
                "dateCreation='" + dateCreation + '\'' +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", itemType=" + itemType +
                '}';
    }
}
