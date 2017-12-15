package swidget.dialogues;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class SplashScreen extends JWindow {

	public SplashScreen(ImageIcon image) {
		super();
		JLabel l = new JLabel("", image, SwingConstants.CENTER);
		getContentPane().add(l, BorderLayout.CENTER);
		l.setVisible(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
