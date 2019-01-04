package plugin;

import java.io.File;
import java.io.FilenameFilter;

public class MyFile {
	
	public static void main(String[] args){
		File myFile = new File("src/main/java/plugin");
		String[] files = myFile.list(new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.endsWith(".java") && name.startsWith("My");
			}
		});
		for(String s: files){
			System.out.println(s);
		}
	}

}
