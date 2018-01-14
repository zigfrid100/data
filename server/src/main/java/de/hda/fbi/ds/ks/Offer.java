package de.hda.fbi.ds.ks;


import java.util.List;

/**
 * Created by zigfrid on 09.01.18.
 */
public class Offer {

    private String offer;

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

    public void printOffer(){
        System.out.println(this.offer);
    }

    public boolean findProduct(String nameOfProduct){
        if(offer.contains(nameOfProduct)){
            return true;
        }else{
            return false;
        }
    }

    public String[] getPriceAndValue(String offer, String searchProduct){

        String[] tmpResult;
        String[] parts;
        String[] result={"0","0","0"};
        tmpResult = offer.split(";");

        for(int i = 0 ; i < tmpResult.length; i++){
            if(tmpResult[i].contains(searchProduct)){

                parts = tmpResult[i].split(" ");
                result = new String[] {parts[3],parts[2],parts[5]};
            }
        }
        return result;
    }

    public String[] getPriceAndValueSpecial (String offer, String searchProduct){

        String[] tmpResult;
        String[] parts;
        String[] result={"0","0","0"};
        tmpResult = offer.split(";");

        for(int i = 0 ; i < tmpResult.length; i++){
            if(tmpResult[i].contains(searchProduct)){

                parts = tmpResult[i].split(" ");
                result = new String[] {parts[4],parts[3],parts[6]};
            }
        }
        return result;
    }

    public String getBetterOffer(List<Offer> offerList, String searchProduct){
        String result="";
        int offerListcounter = offerList.size();
        int[] prices = new int[offerListcounter];

        for(int i = 0 ; i < offerListcounter;i++){
            prices[i] = Integer.parseInt(getPriceAndValue(offerList.get(i).getOffer(),searchProduct)[2]);
        }

        if(prices.length > 1){
            if(prices[0] > prices[prices.length-1]){
                System.out.println("prices[0] " + prices[0] + " is bigger als " + prices[prices.length-1]);
                result = offerList.get(prices.length-1).getOffer();
            }else{
                System.out.println("prices[prices.length] " + prices[prices.length-1] + " is bigger als " + prices[0]);
                result = offerList.get(0).getOffer();
            }
        }else{
            result = offerList.get(prices.length-1).getOffer();
        }

        return result;
    }


}

