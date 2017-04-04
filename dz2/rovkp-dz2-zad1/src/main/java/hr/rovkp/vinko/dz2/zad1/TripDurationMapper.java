package hr.rovkp.vinko.dz2.zad1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by vkolobara on 4.4.2017..
 */
public class TripDurationMapper extends Mapper<LongWritable, Text, Text, DurationTriple> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        DEBSParser parser = new DEBSParser();
            
        if (key.get() > 0) {
            parser.parse(value.toString());
            DurationTriple duration = new DurationTriple(parser.getTripTime());
            context.write(new Text(parser.getMedallion()), duration);
        }

    }
}
