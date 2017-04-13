/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.dz2.zad2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 *
 * @author vkolobara
 */
public class LocationPassengersPartitioner extends Partitioner<IntWritable, Text> {
 
    @Override
    public int getPartition(IntWritable key, Text value, int numPartitions) {
        DEBSParser parser = new DEBSParser();
        parser.parse(value.toString());
        
        int passengerGroup = Math.min(2, parser.getPassengerCount()/2) + 1;            

        return (key.get() * 3 + passengerGroup) % numPartitions;
    }
    
}
