package hr.vinko.rovkp.lab3;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class SimilarityUtil {

	public static List<Long> getIdsFromModel(DataModel model) throws TasteException {

		LongPrimitiveIterator it = model.getItemIDs();
		List<Long> ids = new ArrayList<>();
		it.forEachRemaining(id -> ids.add(id));

		return ids;
	}

	public static double[][] similarityToMatrix(List<Long> ids, ItemSimilarity similarity) throws TasteException {

		int idNum = ids.size();

		double[][] matrix = new double[idNum][idNum];

		for (int i = 0; i < idNum; i++) {
			double rowSum = 0;

			for (int j = 0; j < idNum; j++) {
				rowSum += matrix[i][j] = similarity.itemSimilarity(ids.get(i), ids.get(j));
			}

			for (int j = 0; j < idNum; j++) {
				matrix[i][j] /= rowSum;
			}
		}

		return matrix;
	}

}
