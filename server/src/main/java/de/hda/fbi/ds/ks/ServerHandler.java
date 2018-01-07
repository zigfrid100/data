package de.hda.fbi.ds.ks;

import de.hda.fbi.ds.ks.configuration.CliProcessor;
import de.hda.fbi.ds.ks.mqtt.Subscriber;
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
        int min = 2;
        int max = 20;
        int random = (int )(Math.random() * max + min);
        return random;
    }

    @Override
    public String buyProduct(String name , int value , int price) throws TException {
        price = price * value;
        history.add("Client buy: " + value +" "+ name  +  "  and pay " + price + " euro.");
        String temp = "Client buy: " + value +" "+ name  +  "  and pay " + price + " euro.";
        return temp;
    }

    @Override
    public List<String> getInvoices(){
        return history;
    }

    public void run(String[] args){
        System.out.println("Hello from run");

        // Parse the command line.
        CliProcessor.getInstance().parseCliOptions(args);
        // Start the MQTT subscriber.
        Subscriber subscriber = new Subscriber();
        subscriber.run();
    }

    ServerHandler(){}
}
