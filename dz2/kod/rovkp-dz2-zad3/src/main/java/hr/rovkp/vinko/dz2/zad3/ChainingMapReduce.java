/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.dz2.zad3;

import hr.rovkp.vinko.dz2.zad1.TripDuration;
import hr.rovkp.vinko.dz2.zad1.TripDurationMapper;
import hr.rovkp.vinko.dz2.zad2.LocationPassengersPartitioning;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
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

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);


        Job partitioningJob = LocationPassengersPartitioning.getJob(new Path(args[0]), new Path(TEMP_FOLDER), conf);
        int exitCode = partitioningJob.waitForCompletion(true) ? 0 : 1;

        if (exitCode == 0) {
            FileStatus[] statusList = fs.listStatus(new Path(TEMP_FOLDER));
            List<Job> jobs = new ArrayList<>();

            for (FileStatus status : statusList) {
                if (!status.getPath().getName().startsWith("_")) {
                    Job tripDurationJob = TripDuration.getJob(status.getPath(), new Path(args[1] + "_" + status.getPath().getName()), conf);
                    tripDurationJob.submit();
                    jobs.add(tripDurationJob);
                }
            }

            boolean flag = true;
            do {

                Thread.sleep(5000);
                for (Job job : jobs) {
                    flag = false;
                    if (!job.isComplete()) {
                        flag = true;
                        break;
                    }
                }
            } while (flag);
        }

        fs.delete(new Path(TEMP_FOLDER), true);

        System.exit(exitCode);

    }

}
