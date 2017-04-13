package hr.rovkp.vinko.dz2.zad2;

import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by vkolobara on 4.4.2017..
 */
public class LocationPassengersPartitioning {
    
    private final static int NUM_REDUCE_TASKS = 6;

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
        job.setJarByClass(LocationPassengersPartitioning.class);
        job.setJobName("Partitioning by passenger count and location");
        
        job.setNumReduceTasks(NUM_REDUCE_TASKS);

        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);

        job.setMapperClass(LocationPassengersMapper.class);
        job.setPartitionerClass(LocationPassengersPartitioner.class);
        job.setReducerClass(LocationPassengersReducer.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        return job;
    }
}
