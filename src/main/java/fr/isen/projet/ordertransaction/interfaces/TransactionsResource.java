package fr.isen.projet.ordertransaction.interfaces;

import fr.isen.projet.ordertransaction.impl.services.TransactionsImpl;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.PaymentMethod;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.TransactionStatus;
import fr.isen.projet.ordertransaction.interfaces.models.TransactionSearchCriteria;
import fr.isen.projet.ordertransaction.interfaces.models.Transactions;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/transactions")
public class TransactionsResource {
    private final TransactionsImpl transactionService = new TransactionsImpl();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTransaction(Transactions transaction) {
        int createdTransactionId = transactionService.createTransaction(transaction);
        if (createdTransactionId > 0) {
            return Response.ok(createdTransactionId).build(); // Retourne l'int tel quel
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erreur lors de la création de la transaction. Vérifiez les données envoyées.")
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransactions(@QueryParam("limit") int limit,
                                       @QueryParam("status") String status,
                                       @QueryParam("paymentMethod") String paymentMethod,
                                       @QueryParam("dateCreation") String dateCreation) {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();

        if (status != null) {
            criteria.setTransactionStatus(status != null ? TransactionStatus.valueOf(status) : null);
        }
        if (paymentMethod != null) {
            criteria.setPaymentMethod(paymentMethod != null ? PaymentMethod.valueOf(paymentMethod) : null);
        }
        if (dateCreation != null) {
            criteria.setDateCreation(dateCreation);
        }

        List<Transactions> transactions = transactionService.getAllTransactions(criteria, limit);
        return Response.ok(transactions).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionById(@PathParam("id") int idTransaction) {
        if (idTransaction <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de transaction invalide.")
                    .build();
        }

        Transactions transaction = transactionService.getTransactionById(idTransaction);
        if (transaction != null) {
            return Response.ok(transaction).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Aucune transaction trouvée pour l'ID donné.")
                    .build();
        }
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTransaction(Transactions transaction) {
        if (transaction == null || transaction.getIdTransaction() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Transaction invalide ou ID manquant.")
                    .build();
        }

        int updatedRows = transactionService.updateTransaction(transaction);
        if (updatedRows > 0) {
            return Response.ok("Transaction mise à jour avec succès.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Échec de la mise à jour de la transaction ou transaction introuvable.")
                    .build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteTransaction(@PathParam("id") int idTransaction) {
        if (idTransaction <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de transaction invalide.")
                    .build();
        }

        try {
            transactionService.deleteTransaction(idTransaction);
            return Response.ok("Transaction supprimée avec succès.").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la suppression de la transaction : " + e.getMessage())
                    .build();
        }
    }
}


