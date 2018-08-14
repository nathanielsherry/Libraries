package swidget.widgets.layerpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;

import swidget.widgets.ClearPanel;
import swidget.widgets.TextWrapping;

public class ToastLayer implements Layer {

	private JPanel toast = new ClearPanel();
	private final JLayer<JPanel> toastJLayer;
	
	private LayerPanel parent;
	
	public ToastLayer(LayerPanel parent, String message) {
		this(parent, message, () -> {});
	}
	
	public ToastLayer(LayerPanel parent, String message, Runnable onClick) {
		this.parent = parent;
		
		toast.setLayout(new FlowLayout());
		

		
		String html = "<html><div style='text-align: center; padding: 10px; border-radius: 5px; color: #ffffff; width: 300px'>" + 
				message + 
				"</div></html>";
		
		JLabel label = new JLabel(html) {
			
			@Override
			protected void paintComponent(Graphics g) {
				
				((Graphics2D)g).setRenderingHint(
					    RenderingHints.KEY_ANTIALIASING,
					    RenderingHints.VALUE_ANTIALIAS_ON);
				
				g.setColor(new Color(0xa0000000, true));
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
				super.paintComponent(g);
			}
		};
		
		
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClick.run();
			}
		});
		
		//label.setBackground(new Color(0x40000000, true));
//		label.setBackground(Color.BLACK);
//		label.setForeground(Color.WHITE);
		//label.setOpaque(true);
		
		toast.add(label, BorderLayout.CENTER);
		
		toastJLayer = new JLayer<JPanel>(toast);
		
	}
	
	@Override
	public JLayer<JPanel> getJLayer() {
		
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				parent.removeLayer(ToastLayer.this);
			}
		}, 5000);
		
		return toastJLayer;
	}

	@Override
	public JPanel getComponent() {
		return toast;
	}

	@Override
	public void discard() {
		
	}
	
	@Override
	public boolean modal() {
		return false;
	}


}
