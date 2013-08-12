package swidget.dialogues;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import commonenvironment.Apps;

import swidget.icons.IconFactory;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.widgets.ButtonBox;
import swidget.widgets.ImageButton;
import swidget.widgets.Spacing;


public class AboutDialogue extends JDialog
{
	
	Window owner;
	

	public AboutDialogue(Window owner, final String name, String description, String website, String copyright, final String licence, final String credits, String logo, String version, String longVersion, String releaseDescription, String date, boolean isRelease)
	{
		super(owner, "About " + name, ModalityType.DOCUMENT_MODAL);
		init(owner, name, description, website, copyright, licence, credits, logo, version, longVersion, releaseDescription, date, isRelease);
	}
		
	private void init(Window owner, final String name, String description, final String website, String copyright, final String licence, final String credits, String logo, String version, String longVersion, String releaseDescription, String date, boolean isRelease)
	{
		this.owner = owner;
		
		setResizable(false);
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new BorderLayout());
		c.add(panel);
		
		JPanel infopanel = new JPanel(new GridBagLayout());
		infopanel.setBorder(new EmptyBorder(0, 50, Spacing.large, 50));		
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.VERTICAL;
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		
		infopanel.add(new JLabel(IconFactory.getImageIcon( logo )), gc);

		
		gc.gridy += 1;
		gc.weighty = 0.0;
		gc.weightx = 1.0;
		
		JLabel text = new JLabel();
		text.setFont(text.getFont().deriveFont(Font.PLAIN));
		text.setText(
			"<html><center>" +
				"<b><big><big>" + name + " " + version + "</big></big></b>" +
				(("".equals(releaseDescription)) ? "" : "<br><b><font size=\"+1\" color=\"#c00000\">" + releaseDescription + "</font></b>") +  
				"<br>" +
				"<br>" +
				description +
				"<br>" +
				"<font size=\"-2\">" +
					"<font color=\"#777777\">Version " + longVersion +
					"<br>" + 
					"Build Date: " + date + 
					"</font>" +
					"<br>" +
					"<br>" +
					"Copyright &copy; " + copyright +
				"</font>" +
				"<br>" +
				"<br>" +
			"</center></html>");
		
		infopanel.add(text, gc);
		
		
		if (website != null) {
			JLabel weblabel = new JLabel("<html><u>" + website + "</u></html>");
			weblabel.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e){}
			
				public void mousePressed(MouseEvent e){}
			
				public void mouseExited(MouseEvent e){}
				
				public void mouseEntered(MouseEvent e){}
				
				public void mouseClicked(MouseEvent e){
					Apps.browser(website);
				}
			});
			weblabel.setForeground(Color.blue);
			gc.gridy += 1;
			infopanel.add(weblabel, gc);
		}
		
		ButtonBox bbox = new ButtonBox();
		
		ImageButton btnCredit = new ImageButton(StockIcon.MISC_ABOUT, "Credits", "View Credits", ImageButton.defaultLayout, true, IconSize.BUTTON);
		btnCredit.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(AboutDialogue.this, credits, "Credits", JOptionPane.INFORMATION_MESSAGE, StockIcon.MISC_ABOUT.toImageIcon(IconSize.ICON));
			}
		});
		bbox.addLeft(btnCredit);
		
		
		ImageButton btnLicence = new ImageButton(StockIcon.MIME_TEXT, "Licence", "View Licence", ImageButton.defaultLayout, true, IconSize.BUTTON);
		btnLicence.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(AboutDialogue.this, textForJOptionPane(licence), name + " Licence", JOptionPane.INFORMATION_MESSAGE, StockIcon.MIME_TEXT.toImageIcon(IconSize.ICON));
			}
		});
		bbox.addLeft(btnLicence);	


		ImageButton btnClose = new ImageButton(StockIcon.WINDOW_CLOSE, "Close", "Close this window", ImageButton.defaultLayout, true, IconSize.BUTTON);
		btnClose.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
			}
		});
		bbox.addRight(btnClose);
		
		
		
		
		panel.add(infopanel, BorderLayout.CENTER);
		panel.add(bbox, BorderLayout.SOUTH);
		
		
		pack();

		
		setLocationRelativeTo(owner);
		setVisible(true);
		
	}
	
	
    public JScrollPane textForJOptionPane(String text) {

		JTextArea ta = new JTextArea();
		ta.setText(text);
		ta.setWrapStyleWord(true);
		ta.setLineWrap(true);
		
		ta.setOpaque(false);
		
		
		
		JScrollPane s = new JScrollPane(ta);
		s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		
		s.setOpaque(false);
		
		s.setPreferredSize(new Dimension(500, 300));
		s.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		return s;
    	
    }
    

	
}
