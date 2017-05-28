package hr.vinko.rovkp.lab3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

public class FileUtil {

	private final static double EPS = 1e-6;

	public static void writeMatrixToFile(double[][] matrix, Map<Integer, Long> seqIdMap, String path) throws IOException {
		try (Writer writer = new BufferedWriter(new FileWriter(path))) {

			for (int i = 0; i < matrix.length; i++) {
				for (int j = i + 1; j < matrix[i].length; j++) {
					if (matrix[i][j] > EPS) {
						writer.write(String.format(Locale.US, "%d,%d,%f\n", seqIdMap.get(i), seqIdMap.get(j), matrix[i][j]));
					}
				}
			}
		}
	}

}
