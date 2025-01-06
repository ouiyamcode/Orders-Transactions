package fr.isen.projet.ordertransaction.interfaces.models;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.ItemType;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.OrderStatus;

public class Orders {
    private int idOrder;
    private String dateCreation;
    private String dateUpdate;
    private OrderStatus status;
    private float totalAmount;
    private int idItem;
    private ItemType itemType;
    private int idUser;
    private String description;
}
