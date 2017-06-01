package manel.com.manel.comms.udp;

import java.util.ArrayList;
import java.util.HashMap;

import static manel.com.manel.utils.Constants.KEYS.TO_SEND.getSendingKeys;

/**
 * This is a class for practical issues.
 * It only has two methods:
 *  1: For changing from HashMap to String (useful for sending)
 *  2: For changing from String to HashMap (useful for receiving)
 *
 * @author  Ignasi Ballara, Joaquim Porte, Arnau Tienda
 * @version 2.0
 */
public class UdpDatagramHelper {

    /**
     * Method used for deserializing data to a HashMap.
     *
     * @param receivedMessage String
     * @return HashMap<String, String>
     */
    public static HashMap<String, String> parseReceivedMessage(String receivedMessage) {

        HashMap<String, String> map = new HashMap();
        String[] key_value = receivedMessage.split(",");

        for (String string : key_value) {
            String[] uniqueData = string.split(":");
            map.put(uniqueData[0], uniqueData[1]);
        }
        return map;
    }

    /**
     * Method used for Serializing data to a String.
     *
     * @param data HashMap<String, String>
     * @return String
     */
    public static String formatMessageToSend(HashMap<String, String> data) {

        String datagram = "";
        ArrayList<String> keys = getSendingKeys();
        for (String key : keys) {
            datagram = datagram.concat(key + ":" + data.get(key) + ",");
        }
        return removeLastComma(datagram);
    }

    /* *********************
    **** PRIVATE METHODS
    ************************/

    /**
     * This is a method that deletes the last comma "," of the string we will send.
     *
     * @param string String
     * @return String
     */
    private static String removeLastComma(String string) {
        if (string != null)
            return string.substring(0, string.length()-1);
        else
            return null;
    }
}
