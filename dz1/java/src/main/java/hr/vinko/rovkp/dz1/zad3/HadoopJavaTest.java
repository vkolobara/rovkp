package hr.vinko.rovkp.dz1.zad3;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;

public class HadoopJavaTest {

	public static void main(String[] args) throws IOException, URISyntaxException {
		Configuration conf = new Configuration();
		LocalFileSystem lfs = LocalFileSystem.getLocal(conf);
		FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:9000"), conf);
		Path localPath = new Path("/home/rovkp/ROVKP_DZ1/");
		Path hdfsPath = new Path("/user/rovkp/gutenberg.zip");
		
		System.out.println("Na putanji \"" + hdfsPath + "\" se nalazi datoteka: " + hdfs.isFile(hdfsPath));
		System.out.println("Na putanji \"" + localPath + "\" se nalazi direktorij: " + lfs.isDirectory(localPath));

		lfs.close();
		hdfs.close();
	}
	
}
