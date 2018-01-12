package de.hda.fbi.ds.ks;


import org.apache.thrift.TException;
import java.util.ArrayList;
import java.util.List;


/**
 * The server handler that implements the
 * Thrift interface methods and handles
 * the incoming RPC messages.
 */
public class ServerHandler implements ShopService.Iface {

    List<String> history = new ArrayList<String>();

    ServerHandler(){}

    public  String hello(String name) throws TException{
        System.out.println("Received: " + name);
        return  "Answer from " + name;
    }


    public int getPriceByName(String name) throws TException{
        int min = 2;
        int max = 20;
        int random = (int )(Math.random() * max + min);
        return random;
    }


    public String buyProduct(String name , int value , int price) throws TException {
        //TODO buy product from offer or special offer
        //TODO change adress by mqtt (from bredel to my)

        Offer offer = Main.offerList.getActualOffer();
        Offer specialOffer = Main.specialOfferList.getActualOffer();


        price = price * value;
        history.add("Client buy: " + value +" "+ name  +  "  and pay " + price + " euro.");
        String temp = "Client buy: " + value +" "+ name  +  "  and pay " + price + " euro.";
        return temp;
    }


    public List<String> getInvoices(){
        System.out.println("Offer Main size " + Main.offerList.getSizeOfferList());
        return history;
    }
/*
    public void run(String[] args){
        System.out.println("Hello from run");

        // Parse the command line.
        CliProcessor.getInstance().parseCliOptions(args);
        // Start the MQTT subscriber.
        Subscriber subscriber = new Subscriber();
        subscriber.run();
    }
*/

}
