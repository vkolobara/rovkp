package hr.vinko.rovkp.dz3.zad2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public class MainUserBased {

	private final static String RATINGS_FILE_PATH = "../data/jester_ratings.dat";

	private final static double TRAINING_PERCENTAGE = 0.3;
	private final static double EVALUATION_PERCENTAGE = 0.5;

	private final static Long USER_ID = 220L;
	private final static int NUM_RECOMMENDATIONS = 10;

	public static void main(String[] args) throws IOException, TasteException {

		DataModel model = new FileDataModel(new File(RATINGS_FILE_PATH), "\\s+");

		RovkpRecommender userBasedRecommender = new RovkpUserBasedRecommender(model);

		System.out.println("\nUSER BASED RECOMMENDER RECOMMENDATIONS");
		userBasedRecommender.recommend(USER_ID, NUM_RECOMMENDATIONS).forEach(System.out::println);

		System.out.println("\nWRITE TO FILE FOR FIRST 100 USERS TOP 10 RECOMMENDATIONS");
		recommendAndWriteToFile(model, userBasedRecommender, "out.txt");

		double userScore = userBasedRecommender.evaluate(model, TRAINING_PERCENTAGE, EVALUATION_PERCENTAGE);
		System.out.println("USER BASED RECOMMENDER SCORE");
		System.out.println(userScore);
	}

	private static void recommendAndWriteToFile(DataModel model, RovkpRecommender recommender, String outPath)
			throws IOException, TasteException {
		int counter = 0;
		try (Writer writer = new BufferedWriter(new FileWriter(outPath))) {
			LongPrimitiveIterator it = model.getUserIDs();
			while(counter++ < 100) {
				System.out.println(it.peek());
				writer.write((int) it.peek() + "\t"); 
				List<RecommendedItem> recommendations = recommender.recommend(it.nextLong(), 10);
				recommendations.forEach(rec -> {
					try {
						writer.write(String.valueOf(rec.getItemID()) + " ");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				writer.write("\n");
			}
		}

	}

}
