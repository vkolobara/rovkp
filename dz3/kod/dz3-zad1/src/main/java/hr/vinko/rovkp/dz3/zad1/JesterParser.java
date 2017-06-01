package hr.vinko.rovkp.dz3.zad1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringEscapeUtils;

public class JesterParser {
	
	
	public static List<Joke> parseFile(String path) throws UnsupportedEncodingException, IOException {
		
		String fileString = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
		
		String[] jokes = fileString.split("\n\n");
		
		Stream<String> jokesStream = Arrays.stream(jokes);
		return jokesStream.map((jokeString) -> parseJoke(jokeString)).collect(Collectors.toList());
		
	}
	
	private static Joke parseJoke(String joke) {
		String[] jokeSplit = joke.split("\n", 2);
		
		String text = StringEscapeUtils.unescapeXml(jokeSplit[1].toLowerCase().replace("\\<.*?\\>", ""));
		String id = jokeSplit[0].substring(0, jokeSplit[0].length()-1);
		
		return new Joke(id, text);
	}
	
}
