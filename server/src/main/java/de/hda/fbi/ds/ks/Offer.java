package de.hda.fbi.ds.ks;


/**
 * Created by zigfrid on 09.01.18.
 */
public class Offer {

    private String offer;;

    Offer(String offer ){
        this.offer = offer;
    }

    Offer(){
        this.offer = "No offer";
    }


    public String getOffer() {
        return this.offer;
    }

    public void addOffer(String message){
        this.offer = message;
    }


}
