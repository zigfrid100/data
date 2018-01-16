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

import java.io.IOException;



//1000 offers
//message check

public class Main extends Thread {

    /** The port the server listens to. */
    public static final int PORT = 9090;

    public static ShopService.Processor processorService;
    public static ServerHandler serverHandler;
    /**
     * Start a multiple Thrift server.
     *
     * @param processor The handler that handles incoming messages.
     */
    public static void StartMultipleServerThrift() {
        try {

            serverHandler = new ServerHandler();
            processorService = new ShopService.Processor(serverHandler);

            TServerTransport serverTransport = new TServerSocket(PORT);
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

            TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport)
                            .processor(processorService)
                            .protocolFactory(protocolFactory);
            TServer server = new TThreadPoolServer(args);
            System.out.println("Starting the multiple server...");
            server.serve();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start Simple mqtt server
     *
     * */
    public static void StartSimpleMqttServer(String[] args){
        try {
            // Parse the command line.
            CliProcessor.getInstance().parseCliOptions(args);
            // Start the MQTT subscriber.
            Subscriber subscriber = new Subscriber();
            subscriber.run();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * The main method that actually starts the program.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {


        int choice;

        if(args.length == 0){
            // Display menu graphics
            System.out.println("============================");
            System.out.println("|     MENU SMART FRIDGE    |");
            System.out.println("============================");
            System.out.println("| Options:                 |");
            System.out.println("|        1. Start Thrift   |");
            System.out.println("|        2. Start MQTT     |");
            System.out.println("|        3. Exit           |");
            System.out.println("============================");

            /** Normally use choice input*/
            choice = Keyin.inInt(" Select option: ");
        }else{
            /**choice for test, with use the bash scripts*/
            choice = Integer.parseInt(args[0]);
        }


        // Switch construct
        switch (choice) {
            case 1:
                StartMultipleServerThrift();
                break;
            case 2:
                StartSimpleMqttServer(args);
                break;
            case 3:
                System.out.println("Exit selected");
                break;
            default:
                System.out.println("Invalid selection");
                break; // This break is not really necessary
        }


    }
}
