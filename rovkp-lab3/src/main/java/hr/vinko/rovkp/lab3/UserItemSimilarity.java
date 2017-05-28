package hr.vinko.rovkp.lab3;

import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;

public class UserItemSimilarity extends CollaborativeItemSimilarity {

	private final static double THRESHOLD = 1e-6;

	public UserItemSimilarity(DataModel model) throws TasteException {
		super(model);
	}

	@Override
	public double itemSimilarity(long itemID1, long itemID2) throws TasteException {
		return matrix[idSeqMap.get(itemID1)][idSeqMap.get(itemID2)];
	}
	
	@Override
	public double[] itemSimilarities(long itemID1, long[] itemID2s) throws TasteException {
		double[] similarities = new double[itemID2s.length];

		for (int i = 0; i < itemID2s.length; i++) {
			similarities[i] = itemSimilarity(itemID1, itemID2s[i]);
		}

		return similarities;
	}

	@Override
	public long[] allSimilarItemIDs(long itemID) throws TasteException {
		return idSeqMap.keySet().stream().mapToLong(key -> key).filter(key -> {
			double similarity = Double.NaN;
			try {
				similarity = itemSimilarity(itemID, key);
			} catch (TasteException e) {
			}
			return !Double.isNaN(similarity) && Double.compare(similarity, THRESHOLD) >= 0;
		}).toArray();
	}
	
	public void writeToFile(String path) throws IOException {
		FileUtil.writeMatrixToFile(matrix, seqIdMap, path);
	}

}
