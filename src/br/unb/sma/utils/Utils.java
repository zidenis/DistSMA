package br.unb.sma.utils;

import jade.util.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class Utils {

    public static String obfuscate(String str) {
        ArrayList<String> strList = new ArrayList<>();
        for (String substr : str.split(" ")) {
            strList.add(substr.replaceAll("(?!^.).", "*"));
        }
        return String.join(" ", strList);
    }

    public static String getCurrentDateTime() {
        Date now = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        return DATE_FORMAT.format(now);
    }

    public static void logInfo(String msg) {
        Logger.getJADELogger("DistSMA").log(Logger.INFO, msg);
    }

    public static void logError(String msg) {
        Logger.getJADELogger("DistSMA").log(Logger.INFO, msg);
    }
}
