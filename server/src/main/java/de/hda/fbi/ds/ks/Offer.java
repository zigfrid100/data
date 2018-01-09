package de.hda.fbi.ds.ks;


/**
 * Created by zigfrid on 09.01.18.
 */
public class Offer {

    private String offer;
    private int messageID ;

    Offer(String offer , int messageID){
        this.offer = offer;
        this.messageID = messageID;
    }

    Offer(){
        this.offer = "No offer";
        this.messageID = 0;
    }


    public String getOffer() {
        return this.offer;
    }

    public void addOffer(String message){
        this.offer = message;
    }

    public void setMessageID(int messageID){
        this.messageID = messageID;
    }

    public int getMessageID(){
        return this.messageID;
    }

}
