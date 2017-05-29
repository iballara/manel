package manel.com.manel.comms.udp;

import java.util.ArrayList;
import java.util.HashMap;

import static manel.com.manel.utils.Constants.KEYS.TO_SEND.getSendingKeys;

public class UdpDatagramHelper {

    public static HashMap<String, String> parseReceivedMessage(String receivedMessage) {

        HashMap<String, String> map = new HashMap();
        String[] key_value = receivedMessage.split(",");

        for (String string : key_value) {
            String[] uniqueData = string.split(":");
            map.put(uniqueData[0], uniqueData[1]);
        }
        return map;
    }

    public static String formatMessageToSend(HashMap<String, String> data) {

        String datagram = "";
        ArrayList<String> keys = getSendingKeys();
        for (String key : keys) {
            datagram = datagram.concat(key + ":" + data.get(key) + ",");
        }
        return removeLastComma(datagram);
    }

    private static String removeLastComma(String string) {
        if (string != null)
            return string.substring(0, string.length()-1);
        else
            return null;
    }
}
