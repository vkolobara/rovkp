package hr.rovkp.vinko.dz2.zad1;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;

/**
 * Created by vkolobara on 4.4.2017..
 */
public class TripDuration {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        if (args.length != 2) {
            System.err.println("Input and output paths must be provided.");
            return;
        }

        Job job = getJob(new Path(args[0]), new Path(args[1]), new Configuration());

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static Job getJob(Path input, Path output, Configuration conf) throws IOException {
        Job job = Job.getInstance(conf);
        job.setJarByClass(TripDuration.class);
        job.setJobName("Total trip duration");

        FileInputFormat.addInputPath(job, input);

        FileOutputFormat.setOutputPath(job, output);

        job.setMapperClass(TripDurationMapper.class);
        job.setCombinerClass(TripDurationReducer.class);
        job.setReducerClass(TripDurationReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DurationTriple.class);
        return job;
    }
}
