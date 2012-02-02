package swidget.widgets.properties;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import swidget.icons.StockIcon;
import swidget.widgets.ButtonBox;
import swidget.widgets.ImageButton;
import swidget.widgets.Spacing;

public class PropertyEditor extends JDialog
{
	
	private boolean modified;
	private String title;
	private String text;
	
	public PropertyEditor(Window parent, String title, String text)
	{
		
		setModal(true);
		setTitle("Edit Property");
		
		
		this.title = title;
		this.text = text;
		
		getContentPane().setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500, 300));
		
		
		
		
		JPanel panel = new JPanel();
		
		panel.setBorder(Spacing.bLarge());
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.BOTH;
		
		
		JLabel lTitle = new JLabel("Property");
		JLabel lText = new JLabel("Text");
		final JTextField tTitle = new JTextField(title);
		final JTextArea tText = new JTextArea(text);
		tText.setLineWrap(true);
		tText.setWrapStyleWord(true);
		
		
		panel.add(lTitle, c); c.gridy++;
		panel.add(tTitle, c); c.gridy++;
		panel.add(lText, c); c.gridy++;
		
		c.weighty = 1;
		panel.add(tText, c); c.gridy++;
		c.weighty = 0;
		
		add(panel, BorderLayout.CENTER);
		
		
		
		
		
		
		ButtonBox controls = new ButtonBox(true);
		ImageButton bOK = new ImageButton(StockIcon.CHOOSE_OK, "OK");
		ImageButton bCancel = new ImageButton(StockIcon.CHOOSE_CANCEL, "Cancel");
		
		bOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				PropertyEditor.this.title = tTitle.getText();
				PropertyEditor.this.text = tText.getText();
				PropertyEditor.this.modified = true;
				PropertyEditor.this.setVisible(false);
			}
		});
		
		bCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PropertyEditor.this.setVisible(false);
			}
		});
		
		controls.addRight(bCancel);
		controls.addRight(bOK);
		
		add(controls, BorderLayout.SOUTH);
		
		
		pack();
		setLocationRelativeTo(parent);
		setVisible(true);
		
	}
	
	public boolean isModified()
	{
		return modified;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getText()
	{
		return text;
	}
	
}
