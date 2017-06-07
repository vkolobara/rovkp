package hr.vinko.rovkp.lab4.zad2;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class Main {

	private final static String DEFAULT_FILE_IN = "../data/DeathRecords.csv";

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("ROVKP LAB4_ZAD2").setMaster("local[2]")
				.set("spark.executor.memory", "4g");

		try (JavaSparkContext sc = new JavaSparkContext(conf)) {
			sc.setLogLevel("ERROR");
			JavaRDD<String> file = sc.textFile(DEFAULT_FILE_IN);

			JavaRDD<USDeathRecord> deathRDD = file.filter(line -> USDeathRecord.isParsable(line))
					.map(line -> new USDeathRecord(line)).cache();

			System.out.println("Females died in june: ");
			System.out.println(juneFemaleDeaths(deathRDD));

			System.out.println("Day of week when most males over 50 died: ");
			System.out.println(mostMaleOver50DeathsDay(deathRDD));

			System.out.println("Autopsies after death: ");
			System.out.println(autopsyAfterDeath(deathRDD));

			System.out.println("Dead males between 45 and 60 movement: ");
			List<Tuple2<Integer, Long>> deadMales = deadMalesBetween45_65ByMonths(deathRDD);
			System.out.println(deadMales);

			System.out.println("Married dead males between 45 and 60 percentage movement: ");
			System.out.println(deadMalesBetween45_65ByMonthsMarriedPercentage(deathRDD, deadMales));
			
			System.out.println("Died in accidents");
			System.out.println(diedInAccidents(deathRDD));
			
			System.out.println("Distinct ages");
			System.out.println(distinctAges(deathRDD));

		}
	}

	/*
	 * Koliko je ženskih osoba umrlo u lipnju kroz čitav period?
	 */
	private static long juneFemaleDeaths(JavaRDD<USDeathRecord> deathRDD) {
		return deathRDD.filter(record -> "F".equals(record.getGender())).cache()
				.filter(record -> record.getMonthOfDeath() == 6).count();
	}

	/*
	 * Koji dan u tjednu je umrlo najviše muških osoba starijih od 50 godina?
	 */
	private static int mostMaleOver50DeathsDay(JavaRDD<USDeathRecord> deathRDD) {
		return deathRDD.filter(record -> "M".equals(record.getGender())).cache().filter(record -> record.getAge() > 50)
				.mapToPair(record -> new Tuple2<>(record.getDayOfWeekOfDeath(), 1)).reduceByKey((k1, k2) -> k1 + k2)
				.mapToPair(Tuple2::swap).sortByKey(false).first()._2;
	}

	/*
	 * Koliko osoba je bilo podvrgnuto obdukciji nakon smrti?
	 */
	private static long autopsyAfterDeath(JavaRDD<USDeathRecord> deathRDD) {
		return deathRDD.filter(record -> "Y".equals(record.getAutopsy())).count();
	}

	/*
	 * Kakvo je kretanje broja umrlih muškaraca u dobi između 45 i 65 godina po
	 * mjesecima ? Rezultat je (sortirana) lista tipa Pair2 (ključ je redni broj
	 * mjeseca, a vrijednost je broj umrlih muškaraca)
	 * 
	 */
	private static List<Tuple2<Integer, Long>> deadMalesBetween45_65ByMonths(JavaRDD<USDeathRecord> deathRDD) {
		return deathRDD.filter(record -> "M".equals(record.getGender()))
				.filter(record -> record.getAge() > 45 && record.getAge() < 65).cache()
				.mapToPair(record -> new Tuple2<>(record.getMonthOfDeath(), 1L)).reduceByKey((k1, k2) -> k1 + k2)
				.sortByKey().collect();
	}

	/*
	 * Kakvo je kretanje postotka umrlih oženjenih muškaraca u dobi između 45 i
	 * 65 godina po mjesecima? Rezultat je (sortiran) skup tipa Pair2 (ključ je
	 * redni broj mjeseca, a vrijednost je postotak).
	 * 
	 */

	private static SortedSet<Tuple2<Integer, Double>> deadMalesBetween45_65ByMonthsMarriedPercentage(
			JavaRDD<USDeathRecord> deathRDD, List<Tuple2<Integer, Long>> dead) {
		SortedSet<Tuple2<Integer, Double>> set = new TreeSet<>((o1, o2) -> o1._1.compareTo(o2._1));

		set.addAll(deathRDD.filter(record -> "M".equals(record.getGender()))
				.filter(record -> record.getAge() > 45 && record.getAge() < 65)
				.filter(record -> "M".equals(record.getMaritialStatus()))
				.mapToPair(record -> new Tuple2<>(record.getMonthOfDeath(), 1L)).reduceByKey((k1, k2) -> k1 + k2)
				.map(pair -> new Tuple2<>(pair._1, (1.0 * pair._2 / dead.get(pair._1 - 1)._2))).collect());

		return set;

	}

	/*
	 * Koji je ukupni broj umrlih u nesreći (kod 1) u cjelokupnom periodu?
	 * 
	 */
	
	private static long diedInAccidents(JavaRDD<USDeathRecord> deathRDD) {
		return deathRDD.filter(record -> record.getMannerOfDeath() == 1).count();
	}

	/*
	 * Koliki je broj različitih godina starosti umrlih osoba koji se pojavljuju
	 * u zapisima?
	 * 
	 */
	
	private static long distinctAges(JavaRDD<USDeathRecord> deathRDD) {
		return deathRDD.map(record -> record.getAge()).distinct().count();
	}

}
