package plugin;

import java.util.EventListener;

public interface FileListener extends EventListener {
	
	public void fileAdded(FileEvent myEventObject);
	
	public void fileRemoved(FileEvent myEventObject);
}
