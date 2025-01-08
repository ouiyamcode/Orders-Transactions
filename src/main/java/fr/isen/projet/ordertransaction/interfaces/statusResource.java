package fr.isen.projet.ordertransaction.interfaces;
//Mettez le nom et le chemin de votre dossier

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isen.projet.ordertransaction.impl.services.statusImpl;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/status")
public class statusResource {
    private final statusImpl statusService = new statusImpl();
    @GET
    public String getStatus() throws JsonProcessingException {
        String state = "OK"; // OK ou KO ou Dégradé
        // L'état dégradé veut dire que votre code interne fonctionne mais que vous attendez un code dont vous êtes dépendants, donc d'un autre groupe
        String version = "1.0";
        int count = statusService.countDatabaseElements(); // Remplacez par une requête réelle pour compter les éléments de votre BDD

        Map<String, Object> statusResponse = new HashMap<>();
        statusResponse.put("state", state);
        statusResponse.put("count", count);
        statusResponse.put("version", version);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(statusResponse);
    }

}
