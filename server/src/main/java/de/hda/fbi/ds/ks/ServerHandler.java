package de.hda.fbi.ds.ks;


import org.apache.thrift.TException;

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
                    System.out.println("Message from " +fList[i].getName()+" file : " + scan.nextLine());

                    /*
                    if(fList[i].getName().equals("offerSpecial.txt") ){
                        specialOfferList.addOffer(scan.nextLine());
                    }
                    //if(fList[i].getName().equals("offer"+i+".txt")){
                    if(fList[i].getName().equals("offer1.txt")){
                        offerList.add(new Offer(scan.nextLine()));
                    }
                    if(fList[i].getName().equals("offer2.txt")){
                        offerList.add(new Offer(scan.nextLine()));
                    }*/


                    fr.close();
                }
                catch(IOException ex){

                    System.out.println(ex.getMessage());
                }
            }

        }

        System.out.println("Before compare");

        if(specialOfferList.findProduct(name)){
            specialOfferList.printOffer();
            result = specialOfferList.getPriceAndValueSpecial(specialOfferList.getOffer(),name);
        }else{

            result = offer.getPriceAndValue(offer.getBetterOffer(offerList,name),name);
        }

        System.out.println("result length " + result.length + " end");

        //price = price * value;
        //history.add("Client buy: " + result[0] +" "+ result[1]  +  "  and pay " + result[2] + " euro.");
        //String temp = "Client buy: " + result[0] +" "+ result[1]  +  "  and pay " + result[2] + " euro.";
        history.add("Client buy: " + value +" "+ name  +  "  and pay " + price + " euro.");
        String temp = "Client buy: " + value +" "+ name  +  "  and pay " + price + " euro.";
        return temp;
    }


    public List<String> getInvoices(){

        return history;
    }


}
