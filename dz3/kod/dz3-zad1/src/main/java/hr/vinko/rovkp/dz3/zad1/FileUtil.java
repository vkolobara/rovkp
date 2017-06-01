package hr.vinko.rovkp.dz3.zad1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

public class FileUtil {

	private final static float EPS = 1e-6f;

	public static void writeMatrixToFile(float[][] matrix, String path) throws IOException {
		try (Writer writer = new BufferedWriter(new FileWriter(path))) {

			for (int i = 0; i < matrix.length; i++) {
				for (int j = i + 1; j < matrix[i].length; j++) {
					if (matrix[i][j] > EPS) {
						writer.write(String.format(Locale.US, "%d,%d,%f\n", (i + 1), (j + 1), matrix[i][j]));
					}
				}
			}
		}
	}

}
