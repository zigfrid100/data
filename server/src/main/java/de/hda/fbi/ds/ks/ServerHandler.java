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

    @Override
    public  String hello(String name) throws TException{
        System.out.println("Received: " + name);
        return  "Answer from " + name;
    }

    @Override
    public int getPriceByName(String name) throws TException{
        //note a single Random object is reused here
        int min = 2;
        int max = 20;
        int random = (int )(Math.random() * max + min);
        /*
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);*/
        return random;
    }

    @Override
    public String buyProduct(String name , int value , int price) throws TException {

        int min = 10;
        int max = 20;
        int random = (int )(Math.random() * max + min);

        price = price * random;
        value = value + random;
        //System.out.println("Client buy: " + value +" "+ name  +  "  and pay " + price*value + " money.");
        history.add("Client buy: " + random +" "+ name  +  "  and pay " + price + " euro.");
        String temp = "Client buy: " + random +" "+ name  +  "  and pay " + price + " euro.";
        return temp;
    }





}
