package hr.vinko.rovkp.dz1.zad2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class GutenbergToTxtLocal {

	public static void main(String[] args) throws IOException {

		final AtomicInteger lineCounter = new AtomicInteger(0);

		long startTime = System.currentTimeMillis();
		try (BufferedWriter out = new BufferedWriter(new FileWriter("gutenberg_books.txt"))) {
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

					return FileVisitResult.CONTINUE;
				}
			});
		}

		System.out.println("DURATION: " + (System.currentTimeMillis() - startTime) + "ms.");
		System.out.println("LINE NUMBERS: " + lineCounter);

	}

}
