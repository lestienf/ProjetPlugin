package plugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

public class FileChecker {

	private Timer timer;
	private List<String> knownFileNames;
	private List<FileListener> observers = new ArrayList<>();
	private FilenameFilter filenameFilter;
	private File dir;

	public FileChecker(File dir, FilenameFilter fileName) {
		this.filenameFilter = fileName;
		this.dir = dir;
		knownFileNames = new ArrayList<>();
		String[] tmp = dir.list(fileName);
		knownFileNames = this.changeTabOfList(tmp);
	}

	public void addListener(FileListener listener) {
		if (!observers.contains(listener)) {
			observers.add(listener);
		}
	}

	public void removeListener(FileListener listener) {
		if (observers.contains(listener)) {
			observers.remove(listener);
		}
	}

	private void fireFileAdded(String fileName) {
		for (FileListener fl : observers) {
			fl.fileAdded(new FileEvent(fileName));
		}
	}

	private void fireFileRemoved(String fileName) {
		for (FileListener fl : observers) {
			fl.fileRemoved(new FileEvent(fileName));
		}
	}
	
	public void start(){
		timer = new Timer(2000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkFilesAdded();
				checkFilesRemoved();
			}});
		timer.start();
		while(true);
	}

	protected void checkFilesAdded() {
		Iterator<String> iter;
		List<String> file = changeTabOfList(dir.list(filenameFilter));
		
		iter = file.iterator();
		while(iter.hasNext()) {
			String s = iter.next();
			if (!this.knownFileNames.contains(s)) {
				knownFileNames.add(s);
				fireFileAdded(s);
			}
		}
	}
	
	protected void checkFilesRemoved(){
		List<String> tmp = new ArrayList<>();
		Iterator<String> iter = knownFileNames.iterator();
		List<String> file = changeTabOfList(dir.list(filenameFilter));
		while(iter.hasNext()){
			String s = iter.next();
			if(!file.contains(s)){
				tmp.add(s);
				fireFileRemoved(s);
			}
		}
		knownFileNames.removeAll(tmp);
	}
	
	protected List<String> changeTabOfList(String[] tab){
		List<String> result = new ArrayList<>();
		for(String s: tab){
			result.add(s);
		}
		return result;
	}

	public List<FileListener> getObservers() {
		return observers;
	}

	public void setObservers(List<FileListener> observers) {
		this.observers = observers;
	}

	public List<String> getKnownFileNames() {
		return knownFileNames;
	}

	public FilenameFilter getFilenameFilter() {
		return filenameFilter;
	}

	public File getDir() {
		return dir;
	}
	
	
}
