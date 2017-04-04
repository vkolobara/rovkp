package hr.rovkp.vinko.dz2.zad1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vkolobara on 4.4.2017..
 */
public class DEBSParser {

    private final static int MEDALLION = 0;
    private final static int TRIP_TIME = 8;


    private String medallion;
    private Double tripTime;

    public void parse(String record) {
        String[] splitted = record.split(",");
        this.medallion = splitted[MEDALLION];
        this.tripTime = Double.parseDouble(splitted[TRIP_TIME]);
    }

    public String getMedallion() {
        return medallion;
    }

    public Double getTripTime() {
        return tripTime;
    }
}
