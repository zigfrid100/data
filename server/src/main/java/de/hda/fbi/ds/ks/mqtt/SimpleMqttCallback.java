package de.hda.fbi.ds.ks.mqtt;

import de.hda.fbi.ds.ks.Main;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

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
        LOGGER.info("GetID is " + mqttMessage.getId());
        /** SAVE ALL OFFERS ON file.txt*/
        String tmpl = new String(mqttMessage.getPayload());

        if(tmpl.contains("Special")){
            //Main.specialOfferList.addOffer("Message received: "+ new String(mqttMessage.getPayload()),mqttMessage.getId());
            try {
                FileWriter writer = new FileWriter("../java/de/hda/fbi/ks/files/offerSpecial.txt");
                writer.write(new String(mqttMessage.getPayload()));
                writer.flush();
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }else{
            //Main.offerList.addOffer("Message received: "+ new String(mqttMessage.getPayload()),mqttMessage.getId());
            try {
                FileWriter writer = new FileWriter("../java/de/hda/fbi/ks/files/offer1.txt");
                writer.write(new String(mqttMessage.getPayload()));
                writer.flush();
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }

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
