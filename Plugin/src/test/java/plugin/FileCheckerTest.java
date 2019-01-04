package plugin;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import org.junit.*;
public class FileCheckerTest {

	private MockFileChecker mock;
	
	private class MockFileChecker extends FileChecker {
		
		int count;
		
		public MockFileChecker(File dir, FilenameFilter fileName) {
			super(dir, fileName);
			count=0;
		}
		
		public void fireFileAdded(String fileName) {
			List<FileListener> observers = getObservers();
			for (FileListener fl : observers) {
				count++;
			}
		}
		
		public void fireFileRemoved(String fileName) {
			List<FileListener> observers = getObservers();
			for (FileListener fl : observers) {
				count++;
			}
		}
		
	}
	
	private class MockFileListener implements FileListener{

		@Override
		public void fileAdded(FileEvent myEventObject) {
		}

		@Override
		public void fileRemoved(FileEvent myEventObject) {
		}
		
	}
	
	@Before
	public void init() {
		FilenameFilter filter = new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.endsWith(".class");
			}
		};
		mock = new MockFileChecker(new File("plugins"), filter);
	}
	
   @Test
   public void fireFileAddedShouldWarnEveryListeners() {
	   mock.addListener(new MockFileListener());
	   mock.addListener(new MockFileListener());
	   mock.fireFileAdded(null);
	   assertEquals(2, mock.count);
   }
   
   @Test
   public void fireFileRemovedShouldWarnEveryListeners() {
	   mock.addListener(new MockFileListener());
	   mock.addListener(new MockFileListener());
	   mock.fireFileAdded(null);
	   assertEquals(2, mock.count);
   }
   
   @Test
   public void addListenerShouldAddAListenerInTheList() {
	   mock.addListener(new MockFileListener());
	   assertEquals(1, mock.getObservers().size());
   }
   
   @Test
   public void addListenerShouldNotAddAListenerThatAlreadyExistsInTheList() {
	   MockFileListener listener = new MockFileListener();
	   mock.addListener(listener);
	   mock.addListener(listener);
	   assertEquals(1, mock.getObservers().size());
   }
   
   @Test
   public void removeListenerShouldRemoveAListenerInTheList() {
	   MockFileListener listener = new MockFileListener();
	   mock.addListener(listener);
	   mock.addListener(new MockFileListener());
	   mock.removeListener(listener);
	   assertEquals(1, mock.getObservers().size());
   }
   
   @Test
   public void RemoveListenerShouldNotRemoveAListenerThatDoNotExistsInTheList() {
	   MockFileListener listener = new MockFileListener();
	   mock.addListener(new MockFileListener());
	   mock.removeListener(listener);
	   assertEquals(1, mock.getObservers().size());
   }
   
   @Test
   public void  changeTabOfListShouldReturnAListWithTheNumberOfFilesInTheDirectory() {
	   int nbFiles = mock.getDir().list().length;
	   List<String> list = mock.changeTabOfList(mock.getDir().list());
	   assertEquals(nbFiles, list.size());
   }
}
