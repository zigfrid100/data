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
package de.hda.fbi.ds.ks.configuration;

import java.util.List;

/**
 * A container class that contains all the
 * CLI parameters.
 *
 * @author Michael Bredel
 */
public class CliParameters {

    /** The one and only instance of CLI parameters. */
    private static CliParameters instance;

    /** The address of the broker. */
    private String brokerAddress = "iot.eclipse.org";
    /** The port of the broker. */
    private String brokerPort = "1883";
    /** The port of the protocol. */
    private String brokerProtocol = "tcp";
    /** The topic the MQTT client subscribes to. */
    private String topic = "hda/mbredel/ds";
    /** The message that is published. */
    private String message = "Maker1;";
    /** Product for offer */
    private static String[] prodacts = {"Tomaten","Gurken","Zwibeln","Wurst","Käse","Zuker","Wasser","Karotten","Milch", "Soja",
            "Kartoffeln","Kohl","Rettich","Hänchen","Rind","Salz","Paprika","Fisch","Butter","Schmand"};

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

    public String makeOffer(){
        String tmp = message;
        int min_price = 20;
        int max_price = 100;

        for(int i = 0 ; i < prodacts.length ; i ++){
            tmp = tmp + " Offer " + prodacts[i] + " 20 for " + getRandomInt(max_price,min_price) +" Euro ; ";
        }
        //message = tmp;
        return tmp;
    }

    public String makeSpecialOffer(){
        String tmp = message;
        int min_prod = 0;
        int max_prod = 19;
        int min_price = 30;
        int max_price = 80;

        tmp = tmp + " Special Offer " + prodacts[getRandomInt(max_prod,min_prod)] + " 30 for " + getRandomInt(max_price,min_price) +" Euro ; ";
        return  tmp;
    }

    public int getRandomInt(int max,int min){
        int random = 0;
        random = (int )(Math.random() * max + min);
        return random;
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
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        int min = 1;
        int max = 10;
        int result  = getRandomInt(max,min);

        if(result % 10 == 0){
            return makeSpecialOffer();
        }else {
            return makeOffer();//this.message;
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessage(List<String> args) {
        this.message = "";
        for (String arg: args) this.message += arg + " ";
        this.message = this.message.trim();
    }

    /**
     * A private constructor to avoid
     * instantiation.
     */
    private CliParameters() {}
}
