/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad3;

import hr.rovkp.vinko.lab2.zad2.Coordinates;
import hr.rovkp.vinko.lab2.zad2.DEBSParser;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author vkolobara
 */
public class DEBSMiniParser {

    private final static int DATE_TIME = 0;

    private final static int PICKUP_LATITUDE = 1;
    private final static int PICKUP_LONGITUDE = 2;
    
    private final static int TOTAL_AMOUNT = 3;

    private Date dateTime;

    private String input;
    private Coordinates pickup;
    private double totalAmount;

    public void parse(String record) throws ParseException {
        String[] splitted = record.split(",");
        this.input = record;
        this.totalAmount = Double.parseDouble(splitted[TOTAL_AMOUNT]);
        this.dateTime = DEBSParser.DATE_FORMAT.parse(splitted[DATE_TIME]);
        this.pickup = new Coordinates(Double.parseDouble(splitted[PICKUP_LONGITUDE]), Double.parseDouble(splitted[PICKUP_LATITUDE]));

    }

    public String getInput() {
        return input;
    }

    public Coordinates getPickup() {
        return pickup;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

}
