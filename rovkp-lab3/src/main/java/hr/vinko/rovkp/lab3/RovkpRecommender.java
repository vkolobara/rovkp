package hr.vinko.rovkp.lab3;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

public abstract class RovkpRecommender {

	Recommender recommender;
	
	public List<RecommendedItem> recommend(Long userId, int numRecommendations) throws TasteException {
		return recommender.recommend(userId, numRecommendations);
	}
	
	public abstract RecommenderBuilder getBuilder();
	
	
	public double evaluate(DataModel dataModel, double trainingPercentage, double evaluationPercentage) throws TasteException {
		RecommenderEvaluator eval = new RMSRecommenderEvaluator();
		return eval.evaluate(getBuilder(), null, dataModel, trainingPercentage, evaluationPercentage);
	}
	
	
		
}
