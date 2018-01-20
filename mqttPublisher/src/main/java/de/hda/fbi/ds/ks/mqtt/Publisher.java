/*
 Copyright (c) 2017, Michael Bredel, H-DA
 ALL RIGHTS RESERVED.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 Neither the name of the H-DA and Michael Bredel
 nor the names of its contributors may be used to endorse or promote
 products derived from this software without specific prior written
 permission.
 */
package de.hda.fbi.ds.ks.mqtt;

import de.hda.fbi.ds.ks.configuration.CliParameters;
import de.hda.fbi.ds.ks.configuration.Constants;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The MQTT publisher that connects to a
 * broker and publishes messages on a
 * specific topic.
 *
 * @author Michael Bredel
 */
public class Publisher {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Publisher.class);

    /** The global CLI parameters that have been parsed in Main. */
    private CliParameters cliParameters;
    /** The broker URL. */
    private String broker;
    /** Test boolean variable */
    boolean doTimeTest = false;
    int valueOfMesseges = 100;

    /**
     * Default constructor that initializes
     * various class attributes.
     */
    public Publisher() {

        // Get the CLI parameters.
        cliParameters = CliParameters.getInstance();

        // Create the broker string from command line arguments.
        broker =
                cliParameters.getBrokerProtocol() + "://" +
                cliParameters.getBrokerAddress() + ":" +
                cliParameters.getBrokerPort();

    }

    /**
     * Runs the MQTT client and publishes a message.
     */
    public void run() throws IOException,InterruptedException{

        // Create some MQTT connection options.
        MqttConnectOptions mqttConnectOpts = new MqttConnectOptions();
        mqttConnectOpts.setCleanSession(true);


        try {
            if(doTimeTest){
                MqttClient client = new MqttClient(broker, MqttClient.generateClientId());

                // Connect to the MQTT broker using the connection options.
                client.connect(mqttConnectOpts);
                LOGGER.info("Connected to MQTT broker: " + client.getServerURI());

                //Start test
                Timer timer = new Timer();
                for(int i = 0 ; i < valueOfMesseges ; i++) {

                    String testMessage = "message"+i;
                    // Create the message and set a quality-of-service parameter.
                    MqttMessage message = new MqttMessage(testMessage.getBytes());
                    message.setQos(Constants.QOS_EXACTLY_ONCE);

                    // Publish the message.
                    client.publish(cliParameters.getTopic(), message);
                    LOGGER.info("Published message: " + message);
                }
                /** Time test TEST1*/
                System.out.println("Time for " + valueOfMesseges + " is: " +(timer.getEndTime() - timer.getStartTime())/1000);

                /** compare value send and value receive messages TEST3 */
                File[]fList;
                File F = new File("../../../../server/src/main/java/de/hda/fbi/ds/ks/files");
                fList = F.listFiles();

                if(fList.length == valueOfMesseges){
                    System.out.println("Send messages "+ valueOfMesseges+" is equal to receive messages " + fList.length);
                }else{
                    System.out.println("Send messages "+ valueOfMesseges+" is not equal to receive messages "+fList.length);
                }


            }else{

                MqttClient client = new MqttClient(broker, MqttClient.generateClientId());

                // Connect to the MQTT broker using the connection options.
                client.connect(mqttConnectOpts);
                LOGGER.info("Connected to MQTT broker: " + client.getServerURI());

                while(true) {
                    // Create the message and set a quality-of-service parameter.
                    String messageToSend = cliParameters.getMessage();


                    /** allowed chars */
                    String pattern = "^[a-zA-ZäüöÄÜÖ0-9 ];";
                    // Create a Pattern object
                    java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
                    // Now create matcher object.
                    java.util.regex.Matcher m = r.matcher(messageToSend);
                    // if chars authorized send UDP packet to Server
                    if (m.find()) {
                        System.out.println("is ok");
                    }else{
                        System.out.println("NOT ALLOWED SIGNS AVAILABLE");
                    }



                    MqttMessage message = new MqttMessage(messageToSend.getBytes());
                    message.setQos(Constants.QOS_EXACTLY_ONCE);

                    /** Publish the message.*/
                    client.publish(cliParameters.getTopic(), message);
                    LOGGER.info("Published message : " + message);

                    Thread.sleep(5000);


                    /** compare send and receive the Message TEST2 */
                    File[]fList;
                    File F = new File("../../../../server/src/main/java/de/hda/fbi/ds/ks/files");
                    fList = F.listFiles();
                    for(int i=0; i<fList.length; i++)
                    {
                        if(fList[i].isFile()){
                            try{
                                FileReader fr = new FileReader("../../../../server/src/main/java/de/hda/fbi/ds/ks/files/"+fList[i].getName());
                                Scanner scan = new Scanner(fr);

                                if(fList[i].getName().contains("Maker1.txt")){
                                    if(scan.nextLine().equals(messageToSend)){
                                        System.out.println("Message is equal");
                                    }else{
                                        System.out.println("Message is not equal");
                                    }
                                }
                                fr.close();
                            }
                            catch(IOException ex){

                                System.out.println(ex.getMessage());
                            }
                        }

                    }

                    Thread.sleep(60000);

            }


            }
            // Disconnect from the MQTT broker.
            //client.disconnect();
            //LOGGER.info("Disconnected from MQTT broker.");

            // Exit the app explicitly.
            //System.exit(Constants.EXIT_CODE_SUCCESS);

        } catch (MqttException e) {
            LOGGER.error("An error occurred: " + e.getMessage());
        }

    }

}
