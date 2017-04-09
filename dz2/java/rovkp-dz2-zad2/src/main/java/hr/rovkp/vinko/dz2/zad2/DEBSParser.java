package hr.rovkp.vinko.dz2.zad2;

/**
 * Created by vkolobara on 4.4.2017..
 */
public class DEBSParser {

    private final static int MEDALLION = 0;

    private final static int PASSENGER_COUNT = 7;

    private final static int PICKUP_LONGITUDE = 10;
    private final static int PICKUP_LATITUDE = 11;
    
    private final static int DROPOFF_LONGITUDE = 12;
    private final static int DROPOFF_LATITUDE = 13;

    private String input;
    private String medallion;
    private Integer passengerCount;
    private Coordinates pickup;
    private Coordinates dropoff;

    public void parse(String record) {
        String[] splitted = record.split(",");
        this.input = record;
        this.medallion = splitted[MEDALLION];
        this.passengerCount = Integer.parseInt(splitted[PASSENGER_COUNT]);

        this.pickup = new Coordinates(Double.parseDouble(splitted[PICKUP_LONGITUDE]), Double.parseDouble(splitted[PICKUP_LATITUDE]));
        this.dropoff = new Coordinates(Double.parseDouble(splitted[DROPOFF_LONGITUDE]), Double.parseDouble(splitted[DROPOFF_LATITUDE]));

    }

    public String getInput() {
        return input;
    }

    public String getMedallion() {
        return medallion;
    }

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public Coordinates getPickup() {
        return pickup;
    }

    public Coordinates getDropoff() {
        return dropoff;
    }

    
}
