package hr.vinko.rovkp.dz3.zad2;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class Main {

	public static void main(String[] args) throws IOException, TasteException {
		
		DataModel model = new FileDataModel(new File("../data/jester_ratings.csv"));
		ItemSimilarity similarity = new FileItemSimilarity(new File("../item_similarity.csv"));
		
		ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
		
		List<RecommendedItem> recommendations = recommender.recommend(220, 10);
        for (RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
        }
		
	}
	
}
