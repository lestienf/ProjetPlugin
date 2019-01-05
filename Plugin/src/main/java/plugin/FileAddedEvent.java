package plugin;

import java.io.File;
import java.io.FilenameFilter;

public class FileAddedEvent implements FileListener {

	@Override
	public void fileAdded(FileEvent myEventObject) {
		System.out.println("nouveau .class :"+myEventObject.getFileName()+" d�tect�");
		
	}

	@Override
	public void fileRemoved(FileEvent myEventObject) {
		System.out.println(".class "+myEventObject.getFileName()+" supprim� d�tect�");
	}
	
	
	public static void main(String[] args){
		FileAddedEvent listener = new FileAddedEvent();
		FilenameFilter filter = new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.endsWith(".class");
			}
		};
		FileChecker checker = new FileChecker(new File("target/classes/plugin"), filter);
		checker.addListener(listener);
		checker.start();
	}

	
	
}
