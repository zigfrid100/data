package de.hda.fbi.ds.ks;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * The main class that contains the
 * main method that starts the client.
 *
 * @author Michael Bredel
 */

public class Main {

    /** The port the client connects to. */
    public static final int PORT = 9090;
    /** The host the client connects to. */
    public static final String HOST = "localhost";

    /**
     * The main method that actually starts the program.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {

        try (TTransport transport = new TSocket(HOST, PORT)){
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            Calc.Client client = new Calc.Client(protocol);
            System.out.println("add result:" + client.addTwo(100, 200));
        } catch (TException x) {
            x.printStackTrace();
        }
        
    }

}
