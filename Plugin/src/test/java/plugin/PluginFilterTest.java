package plugin;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class PluginFilterTest {
	
	File dir;
	String namePlugin;
	String nameConstructorTest;
	String nameImplementsPluginTest;
	PluginFilter filter;
	
	@Before
	public void setUp() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		dir = new File("mock");
		namePlugin = "PluginMock.class";
		nameConstructorTest = "PluginConstructorMock.class";
		nameImplementsPluginTest = "PluginImplementPluginTest.class";
		filter = new PluginFilter();	
	}
	
	@Test
	public void methodAcceptShouldReturnTrue(){
		assertTrue(filter.accept(dir, namePlugin));
	}
	
	@Test
	public void methodAcceptShouldReturnFalseWhenTheNameDoesntEndWithClass(){
		assertFalse(filter.accept(dir,"test"));
	}

	@Test
	public void methodAcceptShouldReturnFalseWhenDoestExistConstructorWithNoParameters(){
		assertFalse(filter.accept(dir, nameConstructorTest));
	}
	
	@Test
	public void methodAcceptShouldReturnFalseWhenTheClassDoesntImplementPluginClass(){
		assertFalse(filter.accept(dir, nameImplementsPluginTest));
	}
	
}
