package hr.vinko.rovkp.lab3;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;

import hr.vinko.rovkp.dz3.zad2.RovkpItemBasedRecommender;
import hr.vinko.rovkp.dz3.zad2.RovkpUserBasedRecommender;

public class Main {

	private final static String RATINGS_FILE_PATH = "./jester_dataset_2/jester_ratings.dat";
	private final static String SIMILARITY_OUT_PATH = "hybrid_similarity.csv";
	private final static String DEFAULT_FILE_SIMILARITY_PATH = "./jester_dataset_2/item_similarity.csv";

	public static void main(String[] args) throws IOException, TasteException {

		DataModel model = new FileDataModel(new File(RATINGS_FILE_PATH), "\\s+");
		HybridSimilarity similarity = new HybridSimilarity(model, new FileItemSimilarity(new File(DEFAULT_FILE_SIMILARITY_PATH)));
		
		HybridRecommender hybridRecommender = new HybridRecommender(model);
		System.out.println(hybridRecommender.evaluate(model, 0.3, 0.5));
		FileUtil.writeMatrixToFile(SimilarityUtil.similarityToMatrix(SimilarityUtil.getIdsFromModel(model), similarity), similarity.seqIdMap, SIMILARITY_OUT_PATH);
		
		RovkpItemBasedRecommender itemRecommender = new RovkpItemBasedRecommender(model, DEFAULT_FILE_SIMILARITY_PATH);
		System.out.println(itemRecommender.evaluate(model, 0.3, 0.5));
		
		RovkpUserBasedRecommender userRecommender = new RovkpUserBasedRecommender(model);
		System.out.println(userRecommender.evaluate(model, 0.3, 0.5));
	}


}
