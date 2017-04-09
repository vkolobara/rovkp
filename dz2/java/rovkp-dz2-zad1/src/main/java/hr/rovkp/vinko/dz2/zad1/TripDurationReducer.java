package hr.rovkp.vinko.dz2.zad1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by vkolobara on 4.4.2017..
 */
public class TripDurationReducer extends Reducer<Text, DurationTriple, Text, DurationTriple> {

    @Override
    protected void reduce(Text key, Iterable<DurationTriple> values, Context context) throws IOException, InterruptedException {

        DurationTriple durationTriple = new DurationTriple();
        for (DurationTriple value : values) {
            durationTriple = durationTriple.calculate(value);
        }

        context.write(key, durationTriple);

    }
}
