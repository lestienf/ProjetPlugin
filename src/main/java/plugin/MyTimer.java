package plugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.Timer;

public class MyTimer {
	
	
	public static void main(String[] args){
		Timer timer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println(Calendar.getInstance().getTime());
			}
		});
		timer.start();
		while(true);
	}

}
