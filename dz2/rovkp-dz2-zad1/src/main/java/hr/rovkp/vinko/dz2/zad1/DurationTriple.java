/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.dz2.zad1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.WritableComparable;

/**
 *
 * @author vkolobara
 */
public class DurationTriple implements WritableComparable<DurationTriple> {

    private DoubleWritable totalTripDuration;
    private DoubleWritable minTripDuration;
    private DoubleWritable maxTripDuration;

    
    public DurationTriple() {
        this(0, Double.MAX_VALUE, 0);
    }
    
    public DurationTriple(double duration) {
        this(duration, duration, duration);
    }
    
    public DurationTriple(double totalTripDuration, double minTripDuration, double maxTripDuration) {
        this.totalTripDuration = new DoubleWritable(totalTripDuration);
        this.minTripDuration = new DoubleWritable(minTripDuration);
        this.maxTripDuration = new DoubleWritable(maxTripDuration);
    }

    public DurationTriple calculate(DurationTriple other) {
        return new DurationTriple(totalTripDuration.get() + other.totalTripDuration.get(),
                                Math.min(minTripDuration.get(), other.minTripDuration.get()),
                                Math.max(maxTripDuration.get(), other.maxTripDuration.get()));
    }
       
    @Override
    public void write(DataOutput d) throws IOException {
        totalTripDuration.write(d);
        minTripDuration.write(d);
        maxTripDuration.write(d);
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        totalTripDuration.readFields(di);
        minTripDuration.readFields(di);
        maxTripDuration.readFields(di);
    }

    @Override
    public int compareTo(DurationTriple o) {
        return totalTripDuration.compareTo(o.totalTripDuration);
    }

    @Override
    public String toString() {
        return totalTripDuration.toString() + "\t" + minTripDuration + "\t" + maxTripDuration;
    }
    
    
    
}
