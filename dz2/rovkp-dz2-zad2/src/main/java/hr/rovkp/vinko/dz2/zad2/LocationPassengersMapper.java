/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.dz2.zad2;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author vkolobara
 */
public class LocationPassengersMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

    private final static Coordinates START = new Coordinates(-74, 40.8);
    private final static Coordinates END = new Coordinates(-73.95, 40.75);
    
    private final static int IN_CENTER = 0;
    private final static int OUTSIDE_CENTER = 1;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
        
        if (key.get() > 0) {
            DEBSParser parser = new DEBSParser();
            
            parser.parse(value.toString());
             
            int passengerGroup = Math.min(2, parser.getPassengerCount()/2) + 1;            

            IntWritable location = new IntWritable(
                    (parser.getPickup().isInBoundingBox(START, END) && parser.getDropoff().isInBoundingBox(START, END) ? 
                            IN_CENTER : OUTSIDE_CENTER) * 3 + passengerGroup);
            
            context.write(location, new Text(parser.getInput()));
        }
    }
    
    
    
    
}
