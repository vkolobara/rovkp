/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad1;

/**
 *
 * @author vkolobara
 */
public class DEBSParser {
    private final static int MEDALLION = 0;


    private final static int PICKUP_LONGITUDE = 6;
    private final static int PICKUP_LATITUDE = 7;
    
    private final static int DROPOFF_LONGITUDE = 8;
    private final static int DROPOFF_LATITUDE = 9;
    
    private final static int TOTAL_AMOUNT = 16;


    private String input;
    private String medallion;
    private Double totalAmount;
    private Coordinates pickup;
    private Coordinates dropoff;

    public void parse(String record) {
        String[] splitted = record.split(",");
        this.input = record;
        this.medallion = splitted[MEDALLION];
        this.totalAmount = Double.parseDouble(splitted[TOTAL_AMOUNT]);

        this.pickup = new Coordinates(Double.parseDouble(splitted[PICKUP_LONGITUDE]), Double.parseDouble(splitted[PICKUP_LATITUDE]));
        this.dropoff = new Coordinates(Double.parseDouble(splitted[DROPOFF_LONGITUDE]), Double.parseDouble(splitted[DROPOFF_LATITUDE]));

    }

    public String getInput() {
        return input;
    }

    public String getMedallion() {
        return medallion;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Coordinates getPickup() {
        return pickup;
    }

    public Coordinates getDropoff() {
        return dropoff;
    }

    
}


