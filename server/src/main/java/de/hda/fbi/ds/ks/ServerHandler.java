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
        //TODO зпись в файл инфу с мктт и считывать ее срифт сервером

        Offer offer = Main.offerList.getActualOffer();
        Offer specialOffer = Main.specialOfferList.getActualOffer();


        price = price * value;
        history.add("Client buy: " + value +" "+ name  +  "  and pay " + price + " euro.");
        String temp = "Client buy: " + value +" "+ name  +  "  and pay " + price + " euro.";
        return temp;
    }


    public List<String> getInvoices(){

        File[] fList;
        File F = new File("../java/de/hda/fbi/ds/ks/files/");

        fList = F.listFiles();

        for(int i=0; i<fList.length; i++)
        {
            if(fList[i].isFile()){
                System.out.println(String.valueOf(i) + " - " + fList[i].getName());
                /*try{

                    FileReader fr = new FileReader("../java/de/hda/fbi/ks/files/"+fList[i].getName());
                    Scanner scan = new Scanner(fr);

                    int ii = 1;

                    while (scan.hasNextLine()) {
                        System.out.println(ii + " : " + scan.nextLine());
                        ii++;
                    }

                    fr.close();
                }
                catch(IOException ex){

                    System.out.println(ex.getMessage());
                }
            */}

        }


        return history;
    }


}
