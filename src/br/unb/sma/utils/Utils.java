package br.unb.sma.utils;

import jade.util.Logger;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.awt.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static br.unb.sma.database.Tables.T_INFO_DISTRIBUICAO;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class Utils {

    public static final boolean LOG_ENABLED = true;
    public static final boolean LOG_TO_DATABASE = true;
    public static DSLContext dbDSL;

    public static DSLContext getDbDSL() {
        if (dbDSL == null) {
            try {
                dbDSL = DSL.using(DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD), SQLDialect.POSTGRES);
            } catch (SQLException e) {
                Utils.logError(" erro ao conectar com banco de dados");
            }
        }
        return dbDSL;
    }

    public static String getCurrentDateTime() {
        Date now = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        return DATE_FORMAT.format(now);
    }

    public static void logInfo(String msg) {
        if (LOG_ENABLED) {
            Logger.getJADELogger("DistSMA").log(Logger.INFO, msg);
        }
    }

    public static void logError(String msg) {
        if (LOG_ENABLED) {
            Logger.getJADELogger("DistSMA").log(Logger.INFO, msg);
        }
    }

    public static void logDistributionInfo(String agent, String infoType, String distributionId, String info, String detail) {
        if (LOG_ENABLED) {
            if (LOG_TO_DATABASE) {
                if (distributionId.equals("")) distributionId = "0";
                Utils.getDbDSL()
                        .insertInto(T_INFO_DISTRIBUICAO)
                        .set(T_INFO_DISTRIBUICAO.COD_AGENTE, agent)
                        .set(T_INFO_DISTRIBUICAO.TIP_INFORMACAO, infoType)
                        .set(T_INFO_DISTRIBUICAO.SEQ_DISTRIBUICAO, Integer.valueOf(distributionId))
                        .set(T_INFO_DISTRIBUICAO.TXT_INFORMACAO, info)
                        .set(T_INFO_DISTRIBUICAO.TXT_DETALHES, detail)
                        .execute();
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(agent).append(" - ");
                sb.append(infoType).append(" : ");
                if (distributionId != null) sb.append(distributionId).append(" : ");
                sb.append(info);
                if (!detail.equals("")) sb.append(" : ").append(detail);
                logInfo(sb.toString());
            }
        }
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
            if (guiType.equals("AGP")) {
                x = 900;
                y = 0;
            }
        } else {
            x = maxX + 21;
            y = maxY + 21;
        }
        return new Point(x, y);
    }

    public static String obfuscate(String str) {
        ArrayList<String> strList = new ArrayList<>();
        for (String substr : str.split(" ")) {
            strList.add(substr.replaceAll("(?!^.).", "*"));
        }
        return String.join(" ", strList);
    }
}
