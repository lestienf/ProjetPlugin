package observable;

import java.io.File;
import java.io.FilenameFilter;

import observers.FileEvent;
import observers.FileListener;

public class MyFile implements FileListener{
	
	
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

	@Override
	public void fileAdded(FileEvent myEventObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fileRemoved(FileEvent myEventObject) {
		// TODO Auto-generated method stub
		
	}

}
