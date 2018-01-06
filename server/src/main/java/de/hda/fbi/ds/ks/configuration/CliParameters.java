package de.hda.fbi.ds.ks.configuration;

import java.util.List;
/**
 * Created by zigfrid on 06.01.18.
 */
public class CliParameters {
    /**
     * The one and only instance of CLI parameters.
     */
    private static CliParameters instance;

    /**
     * The address of the broker.
     */
    private String brokerAddress = "iot.eclipse.org";
    /**
     * The port of the broker.
     */
    private String brokerPort = "1883";
    /**
     * The port of the protocol.
     */
    private String brokerProtocol = "tcp";
    /**
     * The topic the MQTT client subscribes to.
     */
    private String topic = "hda/mbredel/ds";

    /**
     * The static getter for the CLI parameters instance.
     *
     * @return The CLI parameters instance.
     */
    public static CliParameters getInstance() {
        if (instance == null)
            instance = new CliParameters();
        return instance;
    }

    //
    // Getter and Setter
    //

    public String getBrokerAddress() {
        return this.brokerAddress;
    }

    public void setBrokerAddress(String brokerAddress) {
        this.brokerAddress = brokerAddress;
    }

    public String getBrokerPort() {
        return this.brokerPort;
    }

    public void setBrokerPort(String brokerPort) {
        this.brokerPort = brokerPort;
    }

    public String getBrokerProtocol() {
        return this.brokerProtocol;
    }

    public void setBrokerProtocol(String brokerProtocol) {
        this.brokerProtocol = brokerProtocol;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * A private constructor to avoid
     * instantiation.
     */
    private CliParameters() {
    }
}
