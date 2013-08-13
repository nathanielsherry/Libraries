package swidget.widgets.properties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIDefaults;

import org.jdesktop.swingx.JXTaskPane;

import swidget.icons.StockIcon;
import swidget.widgets.ClearPanel;
import swidget.widgets.ImageButton;
import swidget.widgets.ImageButton.Layout;
import swidget.widgets.Spacing;

public abstract class PropertyViewer extends ClearPanel
{

	private String title, text;
	private JXTaskPane textpanel;
	private JTextArea textarea;
	
	public PropertyViewer(boolean editable, final Window parent, final String title, final String text)
	{
		this(editable, parent, title, text, false);
	}
	
	public PropertyViewer(boolean editable, final Window parent, final String title, final String text, boolean expanded)
	{
		super();
		
		this.title = title;
		this.text = text;
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.weightx = 1d;
		c.weighty = 0d;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
	
		
		
		textpanel = new JXTaskPane(title);
		
		textpanel.setLayout(new BorderLayout());
		textpanel.setAnimated(false);
		textpanel.setCollapsed(!expanded);		
		textpanel.add(textComponent(text), BorderLayout.CENTER);
		

		add(textpanel, c);
		
		if (editable) {
			c.gridx += 1;
			c.weightx = 0;
			final ImageButton edit = new ImageButton(StockIcon.EDIT_EDIT, "Edit", Layout.IMAGE);
			add(edit, c);
			
			c.gridx += 1;
			final ImageButton delete = new ImageButton(StockIcon.EDIT_DELETE, "Delete", Layout.IMAGE);
			add(delete, c);
			
	
			delete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					delete();
	
				}
			});
			
			
			edit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					edit();
				}
			});
		}
		
		
	}
	
	
	private Component textComponent(String data)
	{
		textarea = new JTextArea(data);	
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		textarea.setEditable(false);
		textarea.setFocusable(false);
		textarea.setBorder(Spacing.bNone());
		
		UIDefaults defaults = new UIDefaults();
		Color bgcolour = new JPanel().getBackground();
		defaults.put("TextArea[Enabled].backgroundPainter", bgcolour);
		textarea.putClientProperty("Nimbus.Overrides", defaults);
		textarea.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		textarea.setBackground(bgcolour);
		
		JScrollPane sp = new JScrollPane(textarea);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setBorder(Spacing.bNone());
		return sp;
	}
	
	protected abstract void edit();
	
	protected abstract void delete();


	public void setValues(String title, String text)
	{
		this.title = title;
		this.text = text;
			
		textpanel.setTitle(title);
		textarea.setText(text);
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
