package plugin;

import java.io.File;
import java.io.FilenameFilter;

public class PluginFilter implements FilenameFilter {
	

	@Override
	public boolean accept(File dir, String name) {
		if(name.endsWith(".class")){
			name.substring(0,name.length()-6);
		}else
			return false;
		
		try {
			Class<?> c = Class.forName(dir.getName()+"."+name).newInstance().getClass();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException{
		String name = "test.class";
		Object obj = null;
		try{
			obj = Class.forName("plugin.MyFile").newInstance();
		}catch(Exception e){
			
		}
		Class<?>[] c = obj.getClass().getInterfaces();
		for(Class<?> cl : c){
			System.out.println(cl.toString());
		}
	}

}
