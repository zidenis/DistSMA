package br.unb.sma.utils;

import jade.util.Logger;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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

    // adapted from org.apache.commons.lang3
    public static String join(final Iterator<?> iterator, final String separator) {
        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            @SuppressWarnings("deprecation") // ObjectUtils.toString(Object) has been deprecated in 3.2
            final String result = first.toString();
            return result;
        }

        // two or more elements
        final StringBuilder buf = new StringBuilder(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            final Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static Point guiLocation(String guiType) {
        int x = 0;
        int y = 0;
        int maxY = 0;
        int maxX = 0;
        for (Frame otherFrame : Frame.getFrames()) {
            if (otherFrame.isShowing() && otherFrame.getTitle().startsWith(guiType)) {
                maxX = Math.max(maxX, otherFrame.getX());
                maxY = Math.max(maxY, otherFrame.getY());
            }
        }
        if (maxX == 0) {
            if (guiType.equals("AP")) {
                x = 1;
            }
            if (guiType.equals("AD")) {
                x = 1;
                y = 300;
            }
            if (guiType.equals("AM")) {
                x = 400;
            }
        } else {
            x = maxX + 21;
            y = maxY + 21;
        }
        return new Point(x, y);
    }
}
