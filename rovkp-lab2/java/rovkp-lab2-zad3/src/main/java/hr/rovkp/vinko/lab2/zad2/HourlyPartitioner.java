/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 *
 * @author vkolobara
 */
public class HourlyPartitioner extends Partitioner<IntWritable, CellTimeAmountTriple>{

    @Override
    public int getPartition(IntWritable key, CellTimeAmountTriple value, int i) {
        return key.get();
    }
    
}
