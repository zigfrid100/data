package de.hda.fbi.ds.ks.mqtt;

import de.hda.fbi.ds.ks.Main;
import de.hda.fbi.ds.ks.ServerHandler;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by zigfrid on 06.01.18.
 */


public class SimpleMqttCallback implements MqttCallback {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMqttCallback.class);

    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.error("Connection to MQTT broker lost!");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        LOGGER.info("Message received: "+ new String(mqttMessage.getPayload()) );
       // serverHandler.offer.add(new String(mqttMessage.getPayload())) ;
        Main.offerMain.add("Message received: "+ new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken mqttDeliveryToken) {
        try {
            LOGGER.info("Delivery completed: "+ mqttDeliveryToken.getMessage() );
        } catch (MqttException e) {
            LOGGER.error("Failed to get delivery token message: " + e.getMessage());
        }
    }
}
