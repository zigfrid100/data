package de.hda.fbi.ds.ks;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import de.hda.fbi.ds.ks.configuration.CliProcessor;
import de.hda.fbi.ds.ks.mqtt.Subscriber;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The server handler that implements the
 * Thrift interface methods and handles
 * the incoming RPC messages.
 */
public class ServerHandler implements ShopService.Iface, MqttCallback {

    List<String> history = new ArrayList<String>();
    public List<String> offer = new ArrayList<String>();

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


    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.error("Connection to MQTT broker lost!");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        LOGGER.info("Message received: "+ new String(mqttMessage.getPayload()) );
        // serverHandler.offer.add(new String(mqttMessage.getPayload())) ;
        offer.add(new String(mqttMessage.getPayload()));
        System.out.println("Size offer " + offer.size());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken mqttDeliveryToken) {
        try {
            LOGGER.info("Delivery completed: "+ mqttDeliveryToken.getMessage() );
        } catch (MqttException e) {
            LOGGER.error("Failed to get delivery token message: " + e.getMessage());
        }
    }

    ServerHandler(){}
}
