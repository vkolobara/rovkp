package hr.vinko.rovkp.lab1.zad3;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;

public class SensorGenerator {

	private final static int NUM_READINGS = 100_000;
	
	private final static int MIN_SENSOR_ID = 1;
	private final static int MAX_SENSOR_ID = 100;
	
	private final static double MIN_SENSOR_VALUE = 0.00;
	private final static double MAX_SENSOR_VALUE = 99.99;
	
	private final static String OUT_FILE_PATH = "/user/rovkp/vkolobara/objects.bin";

	public static void main(String[] args) throws IOException {

		Random rand = new Random();
		
		Configuration conf = new Configuration();

		Path outputPath = new Path(OUT_FILE_PATH);

		SequenceFile.Writer writer = SequenceFile.createWriter(conf, SequenceFile.Writer.file(outputPath),
				SequenceFile.Writer.keyClass(IntWritable.class), SequenceFile.Writer.valueClass(DoubleWritable.class));

		for (int i = 0; i < NUM_READINGS; i++) {
			IntWritable key = new IntWritable(rand.nextInt(MAX_SENSOR_ID - MIN_SENSOR_ID + 1) + MIN_SENSOR_ID);
			DoubleWritable val = new DoubleWritable(rand.nextDouble() * (MAX_SENSOR_VALUE - MIN_SENSOR_VALUE) + MIN_SENSOR_VALUE);
			writer.append(key, val);
		}

		writer.close();
		
		
		int[] sensorCounts = new int[MAX_SENSOR_ID - MIN_SENSOR_ID + 1];
		double[] sensorSums = new double[sensorCounts.length];
		
		SequenceFile.Reader reader = new SequenceFile.Reader(conf, SequenceFile.Reader.file(outputPath));
		
		
		IntWritable key = new IntWritable();
		DoubleWritable value = new DoubleWritable();
		
		while(reader.next(key, value)) {
			sensorCounts[key.get() - MIN_SENSOR_ID]++;
			sensorSums[key.get() - MIN_SENSOR_ID] += value.get();
		}
		
		reader.close();
		
		for (int i=0; i<sensorCounts.length; i++) {
			if (sensorCounts[i] > 0) {
				System.out.printf("Senzor %d: %.6f\n", i + MIN_SENSOR_ID, sensorSums[i] / sensorCounts[i]);
			}
		}
	}

}
