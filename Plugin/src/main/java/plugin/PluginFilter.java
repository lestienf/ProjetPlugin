package plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;

public class PluginFilter implements FilenameFilter {
	
	/**
	 * Check plugins conditions
	 * @param c the class tested
	 * @return true if the class is a plugin, false else
	 */
	private boolean checkPluginCondition(Class<?> c){
		Class<?>[] parameters = new Class<?>[]{};
		Class<?> plugin = plugin.Plugin.class;
		
		try{
			c.getConstructor(parameters);
		}catch(NoSuchMethodException e){
			return false;
		}
		
		return plugin.isAssignableFrom(c);
	}
	

	@Override
	public boolean accept(File dir, String name) {
		Class<?> c =  null;
		if(name.endsWith(".class")){
			name = name.substring(0,name.length()-6);
		}else if(!name.endsWith(".class") || !dir.getName().equals("plugins")){
			return false;
		}
		String realName = dir.getName().replaceAll("/", ".")+"."+name;
		try {
			c = Class.forName(realName).newInstance().getClass();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			return false;
		}
		
		return checkPluginCondition(c);
			
	}
	
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Class<?> obj = null;
		Class<?>[] parameters = new Class<?>[]{};
		Class<?> plugin = plugin.Plugin.class;
		try{
			obj = Class.forName("plugins.CesarCode").newInstance().getClass();
		}catch(Exception e){
			System.out.println("Erreur plugin");
		}
		System.out.println(plugin.isAssignableFrom(obj));
		Class<?>[] c = obj.getInterfaces();
		for(Class<?> cl : c){
			System.out.println(cl.toString());
		}
		Constructor<?> cons = obj.getConstructor(parameters);
		System.out.println(cons.toString());
	}

}
