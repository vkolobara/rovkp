/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad3;

import hr.rovkp.vinko.lab2.zad2.CellTimeAmountTriple;
import hr.rovkp.vinko.lab2.zad2.Coordinates;
import hr.rovkp.vinko.lab2.zad2.DEBSParser;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author vkolobara
 */
public class ChainFilterMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private final static int MIN_CELL = 0;
    private final static int MAX_CELL = 149;

    private final static double BEGIN_LAT = 41.474937;
    private final static double BEGIN_LON = -74.913585;

    private final static double GRID_WIDTH = 0.008983112;
    private final static double GRID_LENGTH = 0.011972;
    
    private final static int GRID_SIZE = 150;
    
    @Override
    protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {
        DEBSParser parser = new DEBSParser();
        try {
            parser.parse(value.toString());
        } catch (Exception ex) {
            Logger.getLogger(ChainFilterMapper.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        double totalAmount = parser.getTotalAmount();
        Coordinates pickup = parser.getPickup();
        Coordinates dropoff = parser.getDropoff();

        int[] cellPickup = getCellId(pickup);
        int[] cellDropoff = getCellId(dropoff);

        if (totalAmount > 0
                && inRange(cellPickup[0], MIN_CELL, MAX_CELL)
                && inRange(cellPickup[1], MIN_CELL, MAX_CELL)
                && inRange(cellDropoff[0], MIN_CELL, MAX_CELL)
                && inRange(cellDropoff[1], MIN_CELL, MAX_CELL)) {
            
            context.write(new Text(DEBSParser.DATE_FORMAT.format(parser.getPickupDateTime()) + "," + pickup.getLatitude() + "," + pickup.getLongitude() + "," + totalAmount), NullWritable.get());

        }

    }

    private boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    private int[] getCellId(Coordinates coordinates) {
        return new int[]{
            (int) ((coordinates.getLongitude() - BEGIN_LON) / GRID_LENGTH),
            (int) ((BEGIN_LAT - coordinates.getLatitude()) / GRID_WIDTH)
        };
    }
}
