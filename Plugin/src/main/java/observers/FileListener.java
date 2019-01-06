package observers;

import java.util.EventListener;

public interface FileListener extends EventListener {
	
	/**
	 * Event launched when a file was added
	 * @param myEventObject the event
	 */
	public void fileAdded(FileEvent myEventObject);
	/**
	 * Event lauchend when a file was removed
	 * @param myEventObject the event
	 */
	public void fileRemoved(FileEvent myEventObject);
}
