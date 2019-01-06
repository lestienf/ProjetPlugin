package observers;

import java.io.File;

import observable.FileChecker;
import plugin.PluginFilter;

public class SinglePluginObserver implements FileListener {

	@Override
	public void fileAdded(FileEvent myEventObject) {
		System.out.println("Un nouveau plugin "+myEventObject.getFileName()+ " vient d'être ajouté !");
		
	}

	@Override
	public void fileRemoved(FileEvent myEventObject) {
		System.out.println("Le plugin "+ myEventObject.getFileName() + " vient d'être supprimé");
	}
	
	
	public static void main(String[] args){
		SinglePluginObserver listener = new SinglePluginObserver();
		PluginFilter filter = new PluginFilter();
		FileChecker checker = new FileChecker(new File("target/extensions/plugins"), filter);
		checker.addListener(listener);
		checker.start();
	}
	
	

}
