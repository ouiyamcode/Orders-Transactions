package fr.isen.projet.ordertransaction.interfaces.models;

import com.modeliosoft.modelio.javadesigner.annotations.mdl;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.ItemType;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.OrderStatus;

@objid ("74653637-bf69-4bd5-bb47-c5be6c02b278")
public class Orders {
    @mdl.prop
    @objid ("7b027092-29be-42e4-93d3-c02021461a89")
    private int idOrder;

    @mdl.prop
    @objid ("5f276c55-6903-4265-8316-f2ac18f0853a")
    private String dateCreation;

    @mdl.prop
    @objid ("3821f9b7-8614-4411-8e14-b9bd602feb1e")
    private String dateUpdate;

    @mdl.prop
    @objid ("a36ff9b7-3236-4279-8df6-80e34df4d36c")
    private OrderStatus status;

    @mdl.prop
    @objid ("f02c6692-a694-4505-be6c-f31a66782f7a")
    private float totalAmount;

    @mdl.prop
    @objid ("f29c8659-67ea-48e5-8d84-5040fb413634")
    private int idItem;

    @mdl.prop
    @objid ("aff87e04-739d-4344-b020-d3a71e82eeac")
    private ItemType itemType;

    @mdl.prop
    @objid ("421bee4c-7809-4368-b68f-4a88cc82985e")
    private int idUser;

    @mdl.prop
    @objid ("b574398a-e7af-4d59-abc9-0607168fadf3")
    private String description;

}
