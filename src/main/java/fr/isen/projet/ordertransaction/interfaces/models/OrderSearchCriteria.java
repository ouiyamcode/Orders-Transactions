package fr.isen.projet.ordertransaction.interfaces.models;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.ItemType;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.OrderStatus;

public class OrderSearchCriteria {
    private String dateCreation;
    private float totalAmount;
    private OrderStatus status;
    private ItemType itemType;
}
