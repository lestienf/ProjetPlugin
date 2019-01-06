package Graphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import observable.FileChecker;
import observers.FileEvent;
import observers.FileListener;
import plugin.Plugin;
import plugin.PluginFilter;

public class InterfaceGraphique {

	private JFrame frame;
	private JTextArea textArea;
	private FileChecker checker;
	private JMenu pluginMenu;
	private JMenu helpMenu;

	public InterfaceGraphique(String dir) {
		PluginFilter filter = new PluginFilter();
		checker = new FileChecker(new File(dir), filter);
		checker.addListener(new PluginListener());
		frame = new JFrame("Plugin Project");
		JMenuBar bar = new JMenuBar();
		textArea = new JTextArea();
		frame.add(textArea);
		frame.setJMenuBar(bar);
		frame.setSize(1000, 500);
		frame.addWindowListener(new CloseWindowEvent());
		frame.setLocation(200, 200);
		pluginMenu = new JMenu("Tool");
		helpMenu = new JMenu("Help");
		JMenu file = new JMenu("File");
		JMenuItem nouveau = new JMenuItem("New...");
		nouveau.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			textArea.setText("");
		}});
		file.add(nouveau);
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new OpenEvent());
		file.add(open);
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			System.exit(0);
		}});
		file.add(exit);
		bar.add(file);
		initJMenuItem(dir);
		bar.add(pluginMenu);
		bar.add(helpMenu);
		frame.setVisible(true);
		checker.start();

	}

	private void initJMenuItem(String dir) {
		File file = new File(dir);
		String[] list = file.list(checker.getFilenameFilter());
		Class<?>[] parameters = new Class<?>[] {};
		for (int i = 0; i < list.length; i++) {
			Constructor<?> cons = null;
			Class<?> c = generateClass(list[i]);
			cons = generateConstructor(c, parameters);
			pluginMenu.add(generateJMenuItemPlugin(cons));
			helpMenu.add(generateJMenuItemHelp(cons));
		}
	}

	/**
	 * Generate the class associated with the name
	 * 
	 * @param name
	 *            the name of the class generated
	 * @return the class generated or null if doesn't exist
	 */
	private Class<?> generateClass(String name) {
		try {
			return Class.forName("plugins." + name.substring(0, name.length() - 6)).newInstance().getClass();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Generates the constructor of the class with parameters
	 * 
	 * @param cl
	 *            the class
	 * @param parameters
	 *            constructor parameters
	 * @return the constructor generated
	 */
	private Constructor<?> generateConstructor(Class<?> cl, Class<?>[] parameters) {
		try {
			return cl.getConstructor(parameters);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Generate the JMenuItem of the plugin
	 * 
	 * @param cons
	 *            the constructor of the plugin
	 * @return the JMenuItem associated
	 */
	private MyJMenuItem generateJMenuItemPlugin(Constructor<?> cons) {
		try {
			Plugin plugin = (Plugin) cons.newInstance();
			MyJMenuItem result = new MyJMenuItem(plugin.getLabel(), plugin);
			result.addActionListener(new PluginEffectEvent());
			return result;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private MyJMenuItem generateJMenuItemHelp(Constructor<?> cons) {
		try {
			Plugin plugin = (Plugin) cons.newInstance();
			MyJMenuItem result = new MyJMenuItem(plugin.getLabel(), plugin);
			result.addActionListener(new HelpEffectEvent());
			return result;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	private class PluginListener implements FileListener {

		@Override
		public void fileAdded(FileEvent myEventObject) {
			String nameClass = myEventObject.getFileName();
			Class<?> c = generateClass(nameClass);
			Constructor<?> cons = generateConstructor(c, new Class<?>[] {});
			pluginMenu.add(generateJMenuItemPlugin(cons));
			helpMenu.add(generateJMenuItemHelp(cons));
		}

		@Override
		public void fileRemoved(FileEvent myEventObject) {
			String label = "";
			String nameClass = myEventObject.getFileName();
			Class<?> c = generateClass(nameClass);
			Constructor<?> cons = generateConstructor(c, new Class<?>[] {});
			try {
				Plugin plug = (Plugin) cons.newInstance();
				label = plug.getLabel();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e ) {
				e.printStackTrace();
			}
			JMenuItem itemPlug = null;
			JMenuItem itemHelp = null;
			for(int i = 0; i<pluginMenu.getItemCount(); i++){
				if(pluginMenu.getItem(i).getText().equals(label)){
					itemPlug = pluginMenu.getItem(i);
				}
				if(helpMenu.getItem(i).getText().equals(label)){
					itemHelp = helpMenu.getItem(i);
				}
			}
			pluginMenu.remove(itemPlug);
			helpMenu.remove(itemHelp);
		}

	}
	
	private class OpenEvent implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filterText = new FileNameExtensionFilter(
		    		 "Fichier texte", "txt");
		    chooser.setFileFilter(filterText);
		    int returnVal = chooser.showOpenDialog(frame);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       InterfaceGraphique.this.textArea.setText(loadFile(chooser.getSelectedFile()));
		    }
			
		}
		
		private String loadFile(File f) {
		    try {
		       BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
		       StringWriter out = new StringWriter();
		       int b;
		       while ((b=in.read()) != -1)
		           out.write(b);
		       out.flush();
		       out.close();
		       in.close();
		       return out.toString();
		    }
		    catch (IOException ie)
		    {
		         ie.printStackTrace(); 
		         return "";
		    }
		}
		
	}

	private class PluginEffectEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MyJMenuItem item = (MyJMenuItem) e.getSource();
			Plugin plug = item.getPlugin();
			String text = InterfaceGraphique.this.textArea.getText();
			InterfaceGraphique.this.textArea.setText(plug.transform(text));
		}

	}
	
	private class HelpEffectEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MyJMenuItem item = (MyJMenuItem) e.getSource();
			Plugin plug = item.getPlugin();
			JOptionPane.showMessageDialog(null, plug.helpMessage());
		}

	}
	
	

	private class CloseWindowEvent extends WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent e) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		InterfaceGraphique i = new InterfaceGraphique("extensions/plugins");
	}

}
