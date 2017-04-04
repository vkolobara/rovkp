/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.dz2.zad3;

import hr.rovkp.vinko.dz2.zad1.TripDuration;
import hr.rovkp.vinko.dz2.zad2.LocationPassengersPartitioning;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;

/**
 *
 * @author vkolobara
 */
public class ChainingMapReduce {

    private static final String TEMP_FOLDER = "tmp";

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        if (args.length != 2) {
            System.err.println("Input and output paths must be provided.");
            return;
        }
        
        Job partitioningJob = LocationPassengersPartitioning.getJob(args[0], TEMP_FOLDER);
        int exitCode = partitioningJob.waitForCompletion(true) ? 0 : 1;
        if (exitCode == 0) {
            Job tripDurationJob = TripDuration.getJob(TEMP_FOLDER, args[1]);
            exitCode = tripDurationJob.waitForCompletion(true) ? 0 : 1;
        }
        
        Configuration conf = new Configuration();
        FileSystem.get(conf).delete(new Path(TEMP_FOLDER), true);
        
        System.exit(exitCode);

    }

}
