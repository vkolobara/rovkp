/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad1;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author vkolobara
 */
public class FilterMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private final static int MIN_CELL = 1;
    private final static int MAX_CELL = 150;

    private final static double BEGIN_LAT = 41.474937;
    private final static double BEGIN_LON = -74.913585;

    private final static Coordinates BEGIN = new Coordinates(BEGIN_LAT, BEGIN_LON);

    private final static double END_LAT = 40.1274702;
    private final static double END_LON = -73.117785;

    private final static Coordinates END = new Coordinates(BEGIN_LAT, BEGIN_LON);

    private final static double GRID_WIDTH = 0.008983112;
    private final static double GRID_LENGTH = 0.011972;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        DEBSParser parser = new DEBSParser();
        parser.parse(value.toString());

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
            context.write(value, NullWritable.get());
        }

    }

    private boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    private int[] getCellId(Coordinates coordinates) {
        return new int[]{
            (int) ((coordinates.getLongitude() - BEGIN_LON) / GRID_LENGTH) + 1,
            (int) ((BEGIN_LAT - coordinates.getLatitude()) / GRID_WIDTH) + 1
        };
    }

}
