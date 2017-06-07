package hr.vinko.rovkp.lab4.zad3;

import static org.apache.spark.streaming.Durations.seconds;

import java.io.Serializable;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import hr.vinko.rovkp.lab4.zad1.PollutionReading;
import scala.Tuple2;;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		SparkConf conf = new SparkConf().setAppName("ROVKP LAB4_ZAD3").setMaster("local[2]")
				.set("spark.executor.memory", "2g");

		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(3));
		jssc.sparkContext().setLogLevel("ERROR");

		JavaDStream<String> lines = jssc.socketTextStream("localhost", 10002);

		JavaPairDStream<GeoLocation, Integer> result = lines.map(line -> line.split(","))
				.filter(PollutionReading::isParseable).map(line -> new PollutionReading(line))
				.mapToPair(record -> new Tuple2<>(new GeoLocation(record.getLongitude(), record.getLatitude()),
						record.getOzone()))
				.reduceByKeyAndWindow((k1, k2) -> Math.min(k1, k2), seconds(45), seconds(15));

		result.dstream().saveAsTextFiles("result/result", "txt");

		jssc.start();
		jssc.awaitTermination();

		jssc.close();
	}

	private static class GeoLocation implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private double longitude;
		private double latitude;

		public GeoLocation(double longitude, double latitude) {
			this.longitude = longitude;
			this.latitude = latitude;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(latitude);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(longitude);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GeoLocation other = (GeoLocation) obj;
			if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
				return false;
			if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "(" + longitude + "," + latitude + ")";
		}

	}
}
