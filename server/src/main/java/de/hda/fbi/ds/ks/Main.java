package de.hda.fbi.ds.ks;

import de.hda.fbi.ds.ks.configuration.CliProcessor;
import de.hda.fbi.ds.ks.mqtt.Subscriber;
import org.apache.maven.settings.Server;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;


public class Main {

    /** The port the server listens to. */
    public static final int PORT = 9090;
    /** ALL OFFERS FROM PUBLISHER save in this variable */
    public static OfferList offerList = new OfferList();
    public static OfferList specialOfferList = new OfferList();

    public static ShopService.Processor processorService;
    public static ServerHandler serverHandler;
    /**
     * Start a simple Thrift server.
     *
     * @param processor The handler that handles incoming messages.
     */
    public static void StartSimpleServer() {
        //public static void StartSimpleServer(ShopService.Processor<ServerHandler> processor) {
        try {

            serverHandler = new ServerHandler();
            processorService = new ShopService.Processor(serverHandler);

            TServerTransport serverTransport = new TServerSocket(PORT);
            TServerSocket tServerSocket = new TServerSocket(PORT);
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

            TThreadPoolServer.Args args = new TThreadPoolServer.Args(tServerSocket)
                            .processor(processorService)
                            .protocolFactory(protocolFactory);
            TServer server = new TThreadPoolServer(args);
            System.out.println("Starting the simple server...");

            server.serve();

            //TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            //TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            //System.out.println("Starting the simple server...");
            //server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
         server.serve();

         */
    }

    /**
     * The main method that actually starts the program.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {


        //ServerHandler serverHandler = new ServerHandler();

        Thread p1 = new Thread(){
            public void run(){
                StartSimpleServer();
            }
        };

        Thread p2 = new Thread(){
            public void run(){

                //serverHandler.run(args);
                // Parse the command line.
                CliProcessor.getInstance().parseCliOptions(args);
                // Start the MQTT subscriber.
                Subscriber subscriber = new Subscriber();
                subscriber.run();
            }
        };

        p1.start();
        //p2.start();

    }
}
