package hr.vinko.rovkp.dz3.zad1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class JesterIndexer {
	
	private StandardAnalyzer analyzer;
	private Directory index;
	private IndexWriter indexWriter;
	
	private List<Joke> jokes;
	
	public JesterIndexer(List<Joke> jokes) throws IOException {
		
		analyzer = new StandardAnalyzer();
		index = new RAMDirectory();
		
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		
		indexWriter = new IndexWriter(index, config);
		
		jokes.forEach(joke -> {
			try {
				addToIndex(joke);
			} catch (IOException e) {
				//TODO neka greska uhvacena
				e.printStackTrace();
			}
		});
		
		this.jokes = new ArrayList<>(jokes);
		indexWriter.close();
	}
	
	private void addToIndex(Joke joke) throws IOException {
		Document doc = new Document();
		FieldType idFieldType = new FieldType();
		idFieldType.setStored(true);
		idFieldType.setTokenized(false);
		idFieldType.setIndexOptions(IndexOptions.NONE);
		
		doc.add(new TextField("text", joke.getText(), Field.Store.YES));
		doc.add(new Field("id", joke.getId(), idFieldType));
		indexWriter.addDocument(doc);
	}
	
	public float[][] similarityMatrix() throws ParseException, IOException {		
		float[][] matrix = calculateMatrix();
		
		return normalizeMatrix(matrix);
	}

	private float[][] normalizeMatrix(float[][] matrix) {
		
		float[][] newMatrix = new float[matrix.length][matrix.length];
		
		// Skaliranje na 1
		for (int i=0; i<matrix.length; i++) {			
			for (int j=0; j<matrix[i].length; j++) {
				newMatrix[i][j] = matrix[i][j] / matrix[i][i];
			}
		}
		
		// Simetricnost
		for (int i=0; i<newMatrix.length; i++) {
			for (int j=i; j<newMatrix[i].length; j++) {
				newMatrix[j][i] = newMatrix[i][j] = (newMatrix[i][j] + newMatrix[j][i]) / 2;
			}
		}
		
		return newMatrix;
	}

	private float[][] calculateMatrix() throws IOException, ParseException {
		int size = jokes.size();
		float[][] matrix = new float[size][size];
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		for (int i=0; i<jokes.size(); i++) {
			Joke joke = jokes.get(i);
			Query query = new QueryParser("text", analyzer).parse(QueryParser.escape(joke.getText()));
			TopDocs top = searcher.search(query, size);
			
			for (ScoreDoc scoreDoc : top.scoreDocs) {
				int j = Integer.parseInt(reader.document(scoreDoc.doc).getField("id").stringValue()) - 1;
				matrix[i][j] = scoreDoc.score;
			}
		}
		
		reader.close();
		
		return matrix;
	}
	
	public StandardAnalyzer getAnalyzer() {
		return analyzer;
	}

	public Directory getIndex() {
		return index;
	}

	public IndexWriter getIndexWriter() {
		return indexWriter;
	}
	
	

}
