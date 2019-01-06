package observable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import observers.FileEvent;
import observers.FileListener;

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

	/**
	 * Add a listener to the list
	 * @param listener the listener added
	 */
	public void addListener(FileListener listener) {
		if (!observers.contains(listener)) {
			observers.add(listener);
		}
	}

	/**
	 * Remove a listener to the list
	 * @param listener the listener removed
	 */
	public void removeListener(FileListener listener) {
		if (observers.contains(listener)) {
			observers.remove(listener);
		}
	}

	/**
	 * Send a fileAdded message at all listeners of the file Checker was added
	 * @param fileName the fileName who correspond to the event
	 */
	private void fireFileAdded(String fileName) {
		for (FileListener fl : observers) {
			fl.fileAdded(new FileEvent(fileName));
		}
	}
	
	/**
	 * Send a fileRemoved message at all listeners of the file Checker when a file was removed
	 * @param fileName the fileName who correspond to the event
	 */
	private void fireFileRemoved(String fileName) {
		for (FileListener fl : observers) {
			fl.fileRemoved(new FileEvent(fileName));
		}
	}
	
	/**
	 * Start the timer of the file checker
	 */
	public void start(){
		timer = new Timer(2000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkFilesAdded();
				checkFilesRemoved();
			}});
		timer.start();
		while(true);
	}

	/**
	 * Check the directory to find a new file
	 */
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
	
	/**
	 * Check the directory to find a removed file
	 */
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
	
	/**
	 * Transforms an array on a list
	 * @param tab the array transformed
	 * @return the list of the array
	 */
	public List<String> changeTabOfList(String[] tab){
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
