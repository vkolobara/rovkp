package hr.vinko.rovkp.lab1.zad2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class GutenbergToTxt {
	public static void main(String[] args) throws IOException, URISyntaxException {

		Configuration conf = new Configuration();
		
		final AtomicInteger lineCounter = new AtomicInteger(0);
		final AtomicInteger fileCounter = new AtomicInteger(0);
		
		org.apache.hadoop.fs.Path path = new org.apache.hadoop.fs.Path("/user/rovkp/vkolobara/gutenberg_books.txt");

		FileSystem hdfs = FileSystem.get(new URI("hdfs://cloudera2:8020"), conf);
		
		long startTime = System.currentTimeMillis();
		
		try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(hdfs.create(path)))) {
			Files.walkFileTree(Paths.get("gutenberg"), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

					try (BufferedReader in = new BufferedReader(new FileReader(file.toFile()))) {
						String line;

						while ((line = in.readLine()) != null) {
							out.write(line + "\n");
							lineCounter.getAndIncrement();
						}

					}

					fileCounter.getAndIncrement();
					return FileVisitResult.CONTINUE;
				}
			});
		}

		System.out.println("DURATION: " + (System.currentTimeMillis() - startTime) + "ms.");
		System.out.println("LINE NUMBERS: " + lineCounter);
		System.out.println("FILES READ: " + fileCounter);

		hdfs.close();
		
	}
}
