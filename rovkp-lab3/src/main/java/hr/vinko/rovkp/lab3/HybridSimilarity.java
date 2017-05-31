package hr.vinko.rovkp.lab3;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class HybridSimilarity extends UserItemSimilarity{

	
	public HybridSimilarity(DataModel model, ItemSimilarity other) throws TasteException {
		this(model, other, 1, 1);
	}
	
	public HybridSimilarity(DataModel model, ItemSimilarity other, double w1, double w2) throws TasteException {
		super(model);
		hybridSimilarityMatrix(SimilarityUtil.getIdsFromModel(model), other, w1, w2);
	}


	private void hybridSimilarityMatrix(List<Long> ids, ItemSimilarity other, double weight1, double weight2) throws TasteException {

		double[][] matrix2 = SimilarityUtil.similarityToMatrix(ids, other);
		
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				matrix[i][j] = weight1 * matrix[i][j] + weight2 * matrix2[i][j];
			}
		}
		
	}
	
	
}
