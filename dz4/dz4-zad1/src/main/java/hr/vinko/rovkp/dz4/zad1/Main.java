package hr.vinko.rovkp.dz4.zad1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

	private static final String DEF_DIR = "../data";
	private static final String DEF_OUT = "../data/sensorscope-monitor-all.csv";

	public static void main(String[] args) throws IOException {
		Stream<Path> paths = Files.list(Paths.get(DEF_DIR));

		Stream<String> allLines = createStreamFromPaths(paths);

		try (Writer writer = new BufferedWriter(new FileWriter(DEF_OUT))) {

			allLines.filter(line -> SensorscopeReading.isParseable(line)).map(line -> new SensorscopeReading(line))
					.sorted().forEach(reading -> {
						try {
							writer.write(reading.toString() + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					});

		}
	}

	private static Stream<String> createStreamFromPaths(Stream<Path> paths) {
		return paths.map(path -> {
			if (path.getFileName().toString().startsWith("sensorscope")) {
				try {
					return Files.lines(path);
				} catch (IOException e) {
					return Stream.of("");
				}
			} else {
				return Stream.of("");
			}
		}).reduce(Stream.empty(), Stream::concat);

	}

}
