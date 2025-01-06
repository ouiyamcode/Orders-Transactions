package fr.isen.projet.ordertransaction.interfaces.models;

import fr.isen.projet.ordertransaction.interfaces.models.Enums.ItemType;
import fr.isen.projet.ordertransaction.interfaces.models.Enums.OrderStatus;

//begin of modifiable zone(Javadoc).......C/74653637-bf69-4bd5-bb47-c5be6c02b278

//end of modifiable zone(Javadoc).........E/74653637-bf69-4bd5-bb47-c5be6c02b278
public class Orders {
//begin of modifiable zone(Javadoc).......C/7b027092-29be-42e4-93d3-c02021461a89

//end of modifiable zone(Javadoc).........E/7b027092-29be-42e4-93d3-c02021461a89
    private int idOrder;

//begin of modifiable zone(Javadoc).......C/5f276c55-6903-4265-8316-f2ac18f0853a

//end of modifiable zone(Javadoc).........E/5f276c55-6903-4265-8316-f2ac18f0853a
    private String dateCreation;

//begin of modifiable zone(Javadoc).......C/3821f9b7-8614-4411-8e14-b9bd602feb1e

//end of modifiable zone(Javadoc).........E/3821f9b7-8614-4411-8e14-b9bd602feb1e
    private String dateUpdate;

//begin of modifiable zone(Javadoc).......C/a36ff9b7-3236-4279-8df6-80e34df4d36c

//end of modifiable zone(Javadoc).........E/a36ff9b7-3236-4279-8df6-80e34df4d36c
    private OrderStatus status;

//begin of modifiable zone(Javadoc).......C/f02c6692-a694-4505-be6c-f31a66782f7a

//end of modifiable zone(Javadoc).........E/f02c6692-a694-4505-be6c-f31a66782f7a
    private float totalAmount;

//begin of modifiable zone(Javadoc).......C/f29c8659-67ea-48e5-8d84-5040fb413634

//end of modifiable zone(Javadoc).........E/f29c8659-67ea-48e5-8d84-5040fb413634
    private int idItem;

//begin of modifiable zone(Javadoc).......C/aff87e04-739d-4344-b020-d3a71e82eeac

//end of modifiable zone(Javadoc).........E/aff87e04-739d-4344-b020-d3a71e82eeac
    private ItemType itemType;

//begin of modifiable zone(Javadoc).......C/421bee4c-7809-4368-b68f-4a88cc82985e

//end of modifiable zone(Javadoc).........E/421bee4c-7809-4368-b68f-4a88cc82985e
    private int idUser;

//begin of modifiable zone(Javadoc).......C/b574398a-e7af-4d59-abc9-0607168fadf3

//end of modifiable zone(Javadoc).........E/b574398a-e7af-4d59-abc9-0607168fadf3
    private String description;

}
