package hr.vinko.rovkp.dz3.zad2;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class RovkpItemBasedRecommender extends RovkpRecommender{
	
	private final static String DEFAULT_FILE_SIMILARITY_PATH = "../data/item_similarity.csv";
	
	public RovkpItemBasedRecommender(DataModel model) throws IOException, TasteException {
		this(model, DEFAULT_FILE_SIMILARITY_PATH);
	}
	
	private RovkpItemBasedRecommender(DataModel model, String filePath) throws IOException, TasteException {
		this(model, new FileItemSimilarity(new File(filePath)));
	}
	
	public RovkpItemBasedRecommender(DataModel model, ItemSimilarity similarity) throws IOException, TasteException {
		recommender = new GenericItemBasedRecommender(model, similarity);
	}
	
	@Override
	public RecommenderBuilder getBuilder() {
		return new RecommenderBuilder() {

			@Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				try {
					return new RovkpItemBasedRecommender(dataModel).recommender;
				} catch (IOException e) {
					return null;
				}
			}
		};
	}
	

}
