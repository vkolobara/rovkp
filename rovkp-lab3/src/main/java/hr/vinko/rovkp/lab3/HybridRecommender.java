package hr.vinko.rovkp.lab3;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class HybridRecommender extends RovkpRecommender {

	private final static String DEFAULT_FILE_SIMILARITY_PATH = "./jester_dataset_2/item_similarity.csv";
	
	public HybridRecommender(DataModel model) throws IOException, TasteException {
		this(model, DEFAULT_FILE_SIMILARITY_PATH);
	}
	
	private HybridRecommender(DataModel model, String filePath) throws IOException, TasteException {
		this(model, new FileItemSimilarity(new File(filePath)));
	}
	
	public HybridRecommender(DataModel model, ItemSimilarity similarity) throws IOException, TasteException {
		recommender = new GenericItemBasedRecommender(model, new HybridSimilarity(model, similarity));
	}
	
	@Override
	public RecommenderBuilder getBuilder() {
		return new RecommenderBuilder() {

			@Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				try {
					return new HybridRecommender(dataModel).recommender;
				} catch (IOException e) {
					return null;
				}
			}
		};
	}

}
