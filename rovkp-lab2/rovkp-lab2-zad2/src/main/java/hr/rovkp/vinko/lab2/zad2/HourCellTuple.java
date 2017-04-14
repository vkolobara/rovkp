/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

/**
 *
 * @author vkolobara
 */
public class HourCellTuple implements WritableComparable<HourCellTuple> {

    private IntWritable hour;
    private IntWritable cell;

    public HourCellTuple() {
        this(-1, -1);
    }

    
    
    public HourCellTuple(int hour, int cell) {
        this.hour = new IntWritable(hour);
        this.cell = new IntWritable(cell);
    }
    
    

    @Override
    public void write(DataOutput d) throws IOException {
        hour.write(d);
        cell.write(d);
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        hour.readFields(di);
        cell.readFields(di);
    }

    @Override
    public int compareTo(HourCellTuple o) {
        int comp = hour.compareTo(o.hour);

        return comp == 0 ? cell.compareTo(o.cell) : comp;
    }

    public IntWritable getHour() {
        return hour;
    }

    public IntWritable getCell() {
        return cell;
    }
    
    

}
