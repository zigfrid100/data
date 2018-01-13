package de.hda.fbi.ds.ks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zigfrid on 09.01.18.
 */
public class OfferList {

    private List<Offer> offers = new ArrayList<Offer>();

    OfferList(){}

    public Offer getActualOffer () {
        if(offers.size() <= 0){
            return new Offer();
        }else{
            return offers.get(this.offers.size());
        }
    }

    public void addOffer(String offer ) {
        this.offers.add(new Offer(offer));
    }

    public int getSizeOfferList(){
        return this.offers.size();
    }

}
