/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad3;

import hr.rovkp.vinko.lab2.zad1.FilterJob;
import hr.rovkp.vinko.lab2.zad2.HourlyJob;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;

/**
 *
 * @author vkolobara
 */
public class ChainJob {

    private final static String TEMP_PATH = "tmp";

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        if (args.length != 2) {
            System.err.println("Input and output paths must be provided.");
            return;
        }

        Configuration conf = new Configuration();

        Job job = FilterJob.getJob(new Path(args[0]), new Path(TEMP_PATH), conf);
        job.setJarByClass(ChainJob.class);
        job.setMapperClass(ChainFilterMapper.class);

        int code = job.waitForCompletion(true) ? 0 : 1;

        if (code == 0) {
            job = HourlyJob.getJob(new Path(TEMP_PATH), new Path(args[1]), conf);
            job.setMapperClass(ChainMapper.class);
            job.setJarByClass(ChainJob.class);

            code = job.waitForCompletion(true) ? 0 : 1;
        }

        FileSystem.get(conf).delete(new Path(TEMP_PATH), true);
        System.exit(code);

    }

}
