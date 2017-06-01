package hr.vinko.rovkp.dz3.zad1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

public class Main {

	public static void main(String[] args) throws UnsupportedEncodingException, IOException, ParseException {
		List<Joke> jokes = JesterParser.parseFile("../data/jester_items.dat");
		
		JesterIndexer indexer = new JesterIndexer(jokes);
		
		float[][] matrix = indexer.similarityMatrix();
		
		FileUtil.writeMatrixToFile(matrix, "../data/item_similarity.csv");
		
	}
}
