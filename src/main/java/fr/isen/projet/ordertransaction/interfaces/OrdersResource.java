package fr.isen.projet.ordertransaction.interfaces;

import fr.isen.projet.ordertransaction.impl.services.OrdersImpl;
import fr.isen.projet.ordertransaction.interfaces.models.OrderSearchCriteria;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.OrderStatus;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.ItemType;
import fr.isen.projet.ordertransaction.interfaces.models.Orders;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/orders")
public class OrdersResource {
    private final OrdersImpl orderService = new OrdersImpl();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Orders order) {
        int createdOrderId = orderService.createOrder(order);
        if (createdOrderId > 0) {
            return Response.ok(createdOrderId).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erreur lors de la création de la commande. Vérifiez les données envoyées.")
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersByCriteria(
            @QueryParam("status") String status,
            @QueryParam("dateCreation") String dateCreation,
            @QueryParam("totalAmount") Float totalAmount,
            @QueryParam("itemType") String itemType,
            @QueryParam("limit") Integer limit
    ) {
        OrderSearchCriteria criteria = new OrderSearchCriteria();
        if (status != null) {
            criteria.setStatus(OrderStatus.valueOf(status));
        }
        if (dateCreation != null) {
            criteria.setDateCreation(dateCreation);
        }
        if (totalAmount != null) {
            criteria.setTotalAmount(totalAmount);
        }
        if (itemType != null) {
            criteria.setItemType(ItemType.valueOf(itemType));
        }

        List<Orders> orders = orderService.getOrdersByCriteria(criteria, limit);
        return Response.ok(orders).build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("id") int idOrder) {
        if (idOrder <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de commande invalide.")
                    .build();
        }

        Orders order = orderService.getOrderById(idOrder);
        if (order != null) {
            return Response.ok(order).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Aucune commande trouvée pour l'ID donné.")
                    .build();
        }
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrder(Orders order) {
        if (order == null || order.getIdOrder() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Commande invalide ou ID manquant.")
                    .build();
        }

        int updatedRows = orderService.updateOrder(order);
        if (updatedRows > 0) {
            return Response.ok("Commande mise à jour avec succès.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Échec de la mise à jour de la commande ou commande introuvable.")
                    .build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteOrder(@PathParam("id") int idOrder) {
        if (idOrder <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de commande invalide.")
                    .build();
        }

        try {
            orderService.markOrderAsCancelled(idOrder);
            return Response.ok("Commande marquée comme annulée avec succès.").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la mise à jour de la commande : " + e.getMessage())
                    .build();
        }
    }
}
