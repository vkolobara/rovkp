/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author vkolobara
 */
public class HourlyReducer extends Reducer<IntWritable, CellTimeAmountTriple, NullWritable, Text> {

    @Override
    protected void reduce(IntWritable key, Iterable<CellTimeAmountTriple> values, Context context) throws IOException, InterruptedException {

        Map<Integer, Long> ridesMap = new HashMap<>();
        Map<Integer, Double> amountMap = new HashMap<>();

        double totalAmount = 0;
        double totalRides = 0;

        for (CellTimeAmountTriple value : values) {
            int cellId = value.getCell().get();

            long rides = value.getNumRides().get();
            double amount = value.getTotalAmount().get();

            totalAmount += amount;
            totalRides += rides;

            ridesMap.put(cellId, ridesMap.getOrDefault(cellId, 0L) + rides);
            amountMap.put(cellId, amountMap.getOrDefault(cellId, 0.0) + amount);

        }

        Map.Entry<Integer, Double> maxAmount = amountMap.entrySet().stream().max(Map.Entry.comparingByValue()).get();
        Map.Entry<Integer, Long> maxRides = ridesMap.entrySet().stream().max(Map.Entry.comparingByValue()).get();

        int[] cellRides = new int[]{
            maxRides.getKey() % 150 + 1,
            maxRides.getKey() / 150 + 1
        };

        int[] cellAmount = new int[]{
            maxAmount.getKey() % 150 + 1,
            maxAmount.getKey() / 150 + 1
        };

        Text output = new Text(key + "\n"
                + cellRides[0] + "," + cellRides[1] + ":" + totalRides + "\n"
                + cellAmount[0] + "," + cellAmount[1] + ":" + totalAmount
        );

        context.write(NullWritable.get(), output);

    }

}
