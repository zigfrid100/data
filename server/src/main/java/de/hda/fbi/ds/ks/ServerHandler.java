package de.hda.fbi.ds.ks;

import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The server handler that implements the
 * Thrift interface methods and handles
 * the incoming RPC messages.
 */
public class ServerHandler implements ShopService.Iface {
  /*  @Override
    public void ping() throws TException {

    }

    @Override
    public int addTwo(int num1, int num2) throws TException {
        System.out.println("Received: " + num1 + ", " + num2);
        return num1 + num2;
    }

    @Override
    public Result addOne(Operands operands) throws TException {
        return null;
    }
  */
    List<String> history = new ArrayList<String>();

    @Override
    public  String hello(String name) throws TException{
        System.out.println("Received: " + name);
        return  "Answer from " + name;
    }

    @Override
    public int getPriceByName(String name) throws TException{
        //note a single Random object is reused here
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);
        return randomInt;
    }

    @Override
    public int buyProduct(String name , int value , int price) throws TException {
        System.out.println("Client buy: " + value +" "+ name  +  "  and pay " + price*value + " money.");
        history.add("Client buy: " + value +" "+ name  +  "  and pay " + price*value + " money.");
        return value;
    }





}
