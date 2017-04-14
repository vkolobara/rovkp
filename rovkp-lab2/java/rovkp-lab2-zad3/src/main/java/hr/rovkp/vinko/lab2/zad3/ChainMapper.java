/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad3;

import hr.rovkp.vinko.lab2.zad2.CellTimeAmountTriple;
import hr.rovkp.vinko.lab2.zad2.Coordinates;
import hr.rovkp.vinko.lab2.zad2.DEBSParser;
import hr.rovkp.vinko.lab2.zad2.HourlyMapper;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author vkolobara
 */
public class ChainMapper extends Mapper<LongWritable, Text, IntWritable, CellTimeAmountTriple> {
    private final static int MIN_CELL = 1;
    private final static int MAX_CELL = 150;

    private final static double BEGIN_LAT = 41.474937;
    private final static double BEGIN_LON = -74.913585;

    private final static double GRID_WIDTH = 0.008983112;
    private final static double GRID_LENGTH = 0.011972;
    
    private final static int GRID_SIZE = 150;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        DEBSMiniParser parser = new DEBSMiniParser();
        try {
            parser.parse(value.toString());
        } catch (Exception ex) {
            Logger.getLogger(HourlyMapper.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(parser.getDateTime());
        
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int[] cellId = getCellId(parser.getPickup());
        
        int cellNum = cellId[0] + cellId[1] * GRID_SIZE;
        
        CellTimeAmountTriple timeAmount = new CellTimeAmountTriple(cellNum, parser.getTotalAmount(), 1L);
        
        context.write(new IntWritable(hour), timeAmount);
        
    }

    private int[] getCellId(Coordinates coordinates) {
        return new int[]{
            (int) ((coordinates.getLongitude() - BEGIN_LON) / GRID_LENGTH),
            (int) ((BEGIN_LAT - coordinates.getLatitude()) / GRID_WIDTH)
        };
    }

}
