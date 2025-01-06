package fr.isen.projet.ordertransaction.interfaces.models;

import com.modeliosoft.modelio.javadesigner.annotations.mdl;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.ItemType;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.OrderStatus;

@objid ("f5f8de3b-e676-4380-9ce2-c6c4fb14bc9e")
public class OrderSearchCriteria {
    @mdl.prop
    @objid ("17a60e5f-b606-41c4-b3c2-e154231a8fbb")
    private String dateCreation;

    @mdl.prop
    @objid ("cdf85545-2c39-4c36-aa18-da54e46676eb")
    private float totalAmount;

    @mdl.prop
    @objid ("1264f2c7-61cc-4f20-93c1-35fa39886b83")
    private OrderStatus status;

    @mdl.prop
    @objid ("91163f2c-c342-44dc-9ddf-6e987fa6e867")
    private ItemType itemType;

}
