package fr.isen.projet.ordertransaction.interfaces.models;

import com.modeliosoft.modelio.javadesigner.annotations.mdl;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.PaymentMethod;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.TransactionStatus;

@objid ("a2f667a6-5135-4b71-a50c-3172ed8e9288")
public class TransactionSearchCriteria {
    @mdl.prop
    @objid ("d8721d17-0276-4e6e-9979-39490349c8ab")
    private String dateCreation;

    @mdl.prop
    @objid ("ca04f00c-821d-46af-b5af-d56ca22d8ebe")
    private TransactionStatus transactionStatus;

    @mdl.prop
    @objid ("615bebc4-c568-41fe-80c0-91382de0027e")
    private PaymentMethod paymentMethod;

}
