package hr.vinko.rovkp.lab3;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class Test {

	private final static String DEFAULT_FILE_SIMILARITY_PATH = "./jester_dataset_2/item_similarity.csv";
	private final static String RATINGS_FILE_PATH = "./jester_dataset_2/jester_ratings.dat";
	private final static String SIMILARITY_OUT_PATH = "hybrid_similarity.csv";

	public static void main(String[] args) throws IOException, TasteException {

		DataModel model = new FileDataModel(new File(RATINGS_FILE_PATH), "\\s+");
		
		HybridRecommender recommender = new HybridRecommender(model);
		
		System.out.println(recommender.evaluate(model, 0.3, 0.5));
		
	}


}
