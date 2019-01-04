package plugin;

import java.util.EventObject;

public class FileEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String fileName;
	
	public FileEvent(Object source){
		super(source);
		this.fileName = source.toString();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFile(String fileName) {
		this.fileName = fileName;
	}

}
