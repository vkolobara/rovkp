package hr.vinko.rovkp.dz4.zad3;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import static org.apache.spark.streaming.Durations.*;

import hr.vinko.rovkp.dz4.zad1.SensorscopeReading;
import scala.Tuple2;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		SparkConf conf = new SparkConf().setAppName("ROVKP DZ4_ZAD3").setMaster("local[2]").set("spark.executor.memory",
				"2g");
		
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5));
		jssc.sparkContext().setLogLevel("ERROR");

		JavaDStream<String> lines = jssc.socketTextStream("localhost", 10002);
		
		JavaPairDStream<Long, Double> result = lines.map(line -> line.split(",")).filter(SensorscopeReading::isParseable)
				.map(line -> new SensorscopeReading(line))
				.mapToPair(reading -> new Tuple2<Long, Double>(reading.getStationID(), reading.getSolarPanelCurrent()))
				.reduceByKeyAndWindow((k1, k2) -> Math.max(k1, k2), seconds(60), seconds(10));
		
		result.dstream().saveAsTextFiles("result/result", "txt");
		
		jssc.start();
		jssc.awaitTermination();
		
		jssc.close();
	}
}
