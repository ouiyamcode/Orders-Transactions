package fr.isen.projet.ordertransaction.interfaces.models;

import com.modeliosoft.modelio.javadesigner.annotations.mdl;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.PaymentMethod;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.TransactionStatus;

@objid ("da5184a4-8322-4e94-9c45-07f3069817ab")
public class Transactions {
    @mdl.prop
    @objid ("3a148c23-5ff7-4e21-844f-e5508019c829")
    private int idTransaction ;

    @mdl.prop
    @objid ("75a1c065-3276-471a-a959-48d2bb882fbb")
    private float amount;

    @mdl.prop
    @objid ("f0a60046-1394-4f19-9eb6-6a3d3b765a5c")
    private PaymentMethod paymentMethod;

    @mdl.prop
    @objid ("1d9063f2-83bd-4b8e-90e6-bd6cb430fb8e")
    private int idOrder;

    @mdl.prop
    @objid ("d414a610-2d33-4d88-a375-fe07c80c28a2")
    private TransactionStatus transactionStatus;

    @mdl.prop
    @objid ("b88afcf5-da0c-41f3-873f-d49c225356ba")
    private String dateCreation;

    @mdl.prop
    @objid ("303c03bc-ff72-496d-8b8d-f2b6c9fa38b4")
    private String dateUpdate;

    @mdl.prop
    @objid ("e0dd3422-4570-4873-8fab-ae36156d2c22")
    private String description;

}
