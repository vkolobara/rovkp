package hr.vinko.rovkp.dz3.zad2;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

public class MainItemBased {

	private final static String RATINGS_FILE_PATH = "../data/jester_ratings.dat";

	private final static double TRAINING_PERCENTAGE = 0.3;
	private final static double EVALUATION_PERCENTAGE = 0.5;

	private final static Long USER_ID = 220L;
	private final static int NUM_RECOMMENDATIONS = 10;

	public static void main(String[] args) throws IOException, TasteException {

		DataModel model = new FileDataModel(new File(RATINGS_FILE_PATH), "\\s+");

		RovkpRecommender itemBasedRecommender = new RovkpItemBasedRecommender(model);

		System.out.println("ITEM BASED RECOMMENDER RECOMMENDATIONS");
		itemBasedRecommender.recommend(USER_ID, NUM_RECOMMENDATIONS).forEach(System.out::println);

		double itemScore = itemBasedRecommender.evaluate(model, TRAINING_PERCENTAGE, EVALUATION_PERCENTAGE);
		System.out.println("ITEM BASED RECOMMENDER SCORE");
		System.out.println(itemScore);
	}

}
