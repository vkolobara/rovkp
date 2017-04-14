/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;

/**
 *
 * @author vkolobara
 */
public class CellTimeAmountTriple implements Writable {

    private IntWritable cell;
    private DoubleWritable totalAmount;
    private LongWritable numRides;

    public CellTimeAmountTriple() {
        this(0, 0, 0);
    }

    public CellTimeAmountTriple(int cell, double totalAmount, long numRides) {
        this.cell = new IntWritable(cell);
        this.totalAmount = new DoubleWritable(totalAmount);
        this.numRides = new LongWritable(numRides);
    }

    @Override
    public void write(DataOutput d) throws IOException {
        cell.write(d);
        numRides.write(d);
        totalAmount.write(d);

    }

    @Override
    public void readFields(DataInput di) throws IOException {
        cell.readFields(di);
        numRides.readFields(di);
        totalAmount.readFields(di);
    }

    public IntWritable getCell() {
        return cell;
    }
   
    public DoubleWritable getTotalAmount() {
        return totalAmount;
    }

    public LongWritable getNumRides() {
        return numRides;
    }
    
    

}
