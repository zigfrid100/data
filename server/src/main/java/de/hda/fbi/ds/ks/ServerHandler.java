package de.hda.fbi.ds.ks;


import org.apache.thrift.TException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The server handler that implements the
 * Thrift interface methods and handles
 * the incoming RPC messages.
 */
public class ServerHandler implements ShopService.Iface {

    List<String> history = new ArrayList<String>();
    /** ALL OFFERS FROM PUBLISHER save in this variable */
    private List<Offer> offerList = new ArrayList<Offer>();
    private Offer specialOfferList = new Offer();
    private Offer offer = new Offer();
    String[] tmpResult;

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

        String[] result;

        File[]fList;
        File F = new File("../java/de/hda/fbi/ds/ks/files");

        fList = F.listFiles();

        for(int i=0; i<fList.length; i++)
        {
            if(fList[i].isFile()){
                System.out.println(String.valueOf(i) + " - " + fList[i].getName());
                try{

                    FileReader fr = new FileReader("../java/de/hda/fbi/ds/ks/files/"+fList[i].getName());
                    Scanner scan = new Scanner(fr);

                    System.out.println("fList length " +  fList.length);

                    if(fList[i].getName().equals("offerSpecial.txt") ){
                        specialOfferList.addOffer(scan.nextLine());
                    }
                    //if(fList[i].getName().equals("offer"+i+".txt")){
                    if(fList[i].getName().equals("offer1.txt")){
                        offerList.add(new Offer(scan.nextLine()));
                    }
                    if(fList[i].getName().equals("offer2.txt")){
                        offerList.add(new Offer(scan.nextLine()));
                    }


                    fr.close();
                }
                catch(IOException ex){

                    System.out.println(ex.getMessage());
                }
            }

        }

        //System.out.println("Before compare and name is " + name);

        if(specialOfferList.findProduct(name)){
            result = specialOfferList.getPriceAndValueSpecial(specialOfferList.getOffer(),name);
        }else{
            result = offer.getPriceAndValue(offer.getBetterOffer(offerList,name),name);
        }

        productBuyFromMaker("Client buy: " + result[0] +" "+ result[1]  +  "  and pay " + result[2] + " euro.");
        //System.out.println("After compare");

        history.add("Client buy: " + result[0] +" "+ result[1]  +  "  and pay " + result[2] + " euro.");
        String temp = "Client buy: " + result[0] +" "+ result[1]  +  "  and pay " + result[2] + " euro.";
        //history.add("Client buy: " + value +" "+ name  +  "  and pay " + price + " euro.");
        //String temp = "Client buy: " + value +" "+ name  +  "  and pay " + price + " euro.";
        return temp;
    }


    public List<String> getInvoices(){

        return history;
    }

    public void productBuyFromMaker(String offer){

        String topic = "hda/ks/ds/Maker1";
        String content = offer;
        int qos = 2;
        String broker = "tcp://iot.eclipse.org:1883";
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }



    }


}
