package Graphique;

import javax.swing.JMenuItem;

import plugin.Plugin;

public class MyJMenuItem extends JMenuItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Plugin plugin;
	
	public MyJMenuItem(String name, Plugin plugin){
		super(name);
		this.plugin = plugin;
	}
	
	public Plugin getPlugin(){
		return plugin;
	}
	
	

}
