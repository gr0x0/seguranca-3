package backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Archive {
	
	public static boolean WriteArchive(byte[] content, String name, String type, String path){
		Path newpath = Paths.get(path+name+type);
		
		try {
			Files.write(newpath, content);
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
}
