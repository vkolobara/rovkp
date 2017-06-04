package hr.vinko.rovkp.dz4.zad2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class Main implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String DEFAULT_FILE_IN = "data/StateNames.csv";

	public static void main(String[] args) throws IOException {
		SparkConf conf = new SparkConf().setAppName("ROVKP DZ4_ZAD2").setMaster("local[2]").set("spark.executor.memory",
				"4g");

		try (JavaSparkContext sc = new JavaSparkContext(conf)) {
			sc.setLogLevel("ERROR");
			JavaRDD<String> file = sc.textFile(DEFAULT_FILE_IN);

			JavaRDD<USBabyNameRecord> babyRDD = file.filter(line -> USBabyNameRecord.isParseable(line))
					.map(line -> new USBabyNameRecord(line)).cache();

			System.out.println("Least popular female name: " + mostUnpopularFName(babyRDD));
			System.out.println("Most popular male names: " + mostPopularMNames(babyRDD));
			System.out.println("State in which most children are born in 1946: " + mostChildren1946State(babyRDD));
			List<Tuple2<Integer, Long>> females = bornFemalesMovement(babyRDD);
			
			try (Writer writer = new BufferedWriter(new FileWriter("femalesByYears.csv"))) {
				females.forEach(record -> {
					try {
						writer.write(record._1 + "," + record._2 + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
			
			System.out.println("Born females over years: " + females);
			SortedSet<Tuple2<Integer, Double>> mary = maryPercentageOverYears(babyRDD, females);
			
			try (Writer writer = new BufferedWriter(new FileWriter("maryByYears.csv"))) {
				mary.forEach(record -> {
					try {
						writer.write(record._1 + "," + record._2 + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
			
			System.out.println("Maries over years percentage: " + mary);
			System.out.println("Total children born: " + totalChildren(babyRDD));
			System.out.println("Unique names: " + uniqueNames(babyRDD));
		}
	}

	private static String mostUnpopularFName(JavaRDD<USBabyNameRecord> records) {
		return records.filter(record -> record.getGender().equals("F")).cache()
				.mapToPair(record -> new Tuple2<>(record.getName(), record.getCount())).reduceByKey((k1, k2) -> k1 + k2)
				.mapToPair(Tuple2::swap).sortByKey().map(tuple -> tuple._2).first();

	}

	private static List<String> mostPopularMNames(JavaRDD<USBabyNameRecord> records) {
		return records.filter(record -> record.getGender().equals("M"))
				.mapToPair(record -> new Tuple2<>(record.getName(), record.getCount())).reduceByKey((k1, k2) -> k1 + k2)
				.mapToPair(Tuple2::swap).sortByKey(false).map(tuple -> tuple._2).take(10);
	}

	private static String mostChildren1946State(JavaRDD<USBabyNameRecord> records) {
		return records.filter(record -> record.getYear() == 1946)
				.mapToPair(record -> new Tuple2<>(record.getState(), record.getCount()))
				.reduceByKey((k1, k2) -> k1 + k2).mapToPair(Tuple2::swap).sortByKey(false).first()._2;
	}

	private static List<Tuple2<Integer, Long>> bornFemalesMovement(JavaRDD<USBabyNameRecord> records) {
		return records.filter(record -> record.getGender().equals("F"))
				.mapToPair(record -> new Tuple2<>(record.getYear(), record.getCount()))
				.reduceByKey((k1, k2 ) -> k1+k2).sortByKey().cache().collect();
	}

	private static SortedSet<Tuple2<Integer, Double>> maryPercentageOverYears(JavaRDD<USBabyNameRecord> records,
			List<Tuple2<Integer, Long>> females) {
		SortedSet<Tuple2<Integer, Double>> set = new TreeSet<>((o1, o2) -> o1._1.compareTo(o2._1));
		set.addAll(records.filter(record -> record.getGender().equals("F") && record.getName().equals("Mary"))
				.mapToPair(record -> new Tuple2<>(record.getYear(), record.getCount())).reduceByKey((k1, k2) -> k1 + k2)
				.map(pair -> new Tuple2<>(pair._1, (1.0*pair._2 / females.get(pair._1 - 1910)._2))).collect());
		return set;
	}
	
	private static long totalChildren(JavaRDD<USBabyNameRecord> records) {
		return records.map(USBabyNameRecord::getCount).fold(0L, (l1, l2) -> l1 + l2);
	}
	
	private static long uniqueNames(JavaRDD<USBabyNameRecord> records) {
		return records.map(USBabyNameRecord::getName).distinct().count();
	}

}
