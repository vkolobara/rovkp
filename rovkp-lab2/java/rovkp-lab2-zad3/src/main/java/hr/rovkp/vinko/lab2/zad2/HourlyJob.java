/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad2;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author vkolobara
 */
public class HourlyJob {

    private final static int NUM_REDUCE_TASKS = 24;
    
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        if (args.length != 2) {
            System.err.println("Input and output paths must be provided.");
            return;
        }
        
        Configuration conf = new Configuration();
        
        Job job = getJob(new Path(args[0]), new Path(args[1]), conf);
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
    
    public static Job getJob(Path input, Path output, Configuration conf) throws IOException {
        Job job = Job.getInstance(conf);
        job.setJarByClass(HourlyJob.class);
        job.setJobName("Calculate hourly data");
        
        job.setNumReduceTasks(NUM_REDUCE_TASKS);
        
        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);
        
        job.setMapperClass(HourlyMapper.class);
        job.setPartitionerClass(HourlyPartitioner.class);
        job.setReducerClass(HourlyReducer.class);
        
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(CellTimeAmountTriple.class);
        
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        return job;
    }
}
