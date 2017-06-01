package hr.vinko.rovkp.dz3.zad2;

import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class RovkpUserBasedRecommender extends RovkpRecommender {

	public RovkpUserBasedRecommender(DataModel model) throws IOException, TasteException {
		this(model, new PearsonCorrelationSimilarity(model));
	}

	public RovkpUserBasedRecommender(DataModel model, UserSimilarity similarity) {
		this(model, similarity, new ThresholdUserNeighborhood(0.9, similarity, model));
	}

	public RovkpUserBasedRecommender(DataModel model, UserSimilarity similarity, UserNeighborhood neighborhood) {
		recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
	}

	@Override
	public RecommenderBuilder getBuilder() {
		return new RecommenderBuilder() {

			@Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				try {
					return new RovkpUserBasedRecommender(dataModel).recommender;
				} catch (IOException e) {
					return null;
				}
			}
		};
	}

}
