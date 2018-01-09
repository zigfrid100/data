package de.hda.fbi.ds.ks;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class Main {

    /** The port the server listens to. */
    public static final int PORT = 9090;
    /** ALL OFFERS FROM PUBLISHER save in this variable */
    public static OfferList offerList = new OfferList();
    public static OfferList specialOfferList = new OfferList();
    /**
     * Start a simple Thrift server.
     *
     * @param processor The handler that handles incoming messages.
     */
    public static void StartSimpleServer(ShopService.Processor<ServerHandler> processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(PORT);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         TServer server = new TThreadPoolServer(TThreadPoolServer.Args(serverTransport).processor(processor));
         server.serve();

         */
    }

    /**
     * The main method that actually starts the program.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {


        ServerHandler serverHandler = new ServerHandler();

        Thread p1 = new Thread(){
            public void run(){
                StartSimpleServer(new ShopService.Processor<>(serverHandler));
            }
        };

        Thread p2 = new Thread(){
            public void run(){
                serverHandler.run(args);
            }
        };

        p1.start();
        p2.start();

    }
}
