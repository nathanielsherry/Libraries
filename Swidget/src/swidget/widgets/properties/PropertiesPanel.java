package swidget.widgets.properties;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.widgets.Spacing;


public abstract class PropertiesPanel extends JPanel implements Scrollable
{

	protected GridBagConstraints c;
	protected JFrame parent;
	private boolean expanded;
	
	protected abstract void create();
	protected abstract boolean hasData();
	protected abstract void set(String key, String value);
	protected abstract String getValue(String key);
	protected abstract void delete(String key);
	protected abstract List<String> getKeys();
	protected abstract JPanel refreshFailedMessage();
	
	public PropertiesPanel(JFrame parent)
	{
		this(parent, false);
	}
	
	public PropertiesPanel(JFrame parent, boolean expandProperties) 
	{
		
		this.parent = parent;
		this.expanded = expandProperties;
		
		create();
		
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		
		c.insets = Spacing.iSmall();
		
		
	}
	


	public final void refresh()
	{

		removeAll();		
		
	
		if (hasData()) {

			setLayout(new GridBagLayout());
			c.gridx = 0;
			c.gridy = 0;
			
			for (String key : getKeys())
			{
				addLine(key, getValue(key));
			}
			
		}
	
		else
		{
			
			GridBagConstraints gbc = new GridBagConstraints();
			JPanel errorpanel = refreshFailedMessage();
			if (errorpanel == null)
			{
				JPanel msgpanel = new JPanel();
				msgpanel.add(new JLabel(StockIcon.BADGE_WARNING.toImageIcon(IconSize.ICON)));
				msgpanel.add(new JLabel("<html><b><font size='+2'>Failed to Display</font></b><br />The current item could not be accessed.</html>"));
				errorpanel = msgpanel;
			}
			add(errorpanel, gbc);

			
		}

	}
	
	
	
	protected void addLine(final String title, final String text)
	{

		c.gridx = 0;
		c.weightx = 1d;
		c.weighty = 0d;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		
		final PropertyViewer viewer;
		
		viewer = new PropertyViewer(true, parent, title, text, expanded) {
			
			@Override
			protected void edit()
			{
				PropertyEditor editor = new PropertyEditor(parent, title, text);	
				if (editor.isModified())
				{
					set(editor.getTitle(), editor.getText());
					setValues(editor.getTitle(), editor.getText());
					
					validate();
					getParent().validate();
				}
			}
			
			@Override
			protected void delete()
			{
				PropertiesPanel.this.delete(title);
				remove(this);
								
				validate();
				getParent().validate();
			}
		};
		
		add(viewer, c);
		
		
		c.gridy += 1;
		
		
	}
	
	
	
	
	
	
	
	
	@Override
	public Dimension getPreferredScrollableViewportSize()
	{
		return getPreferredSize();
	}
	



	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		return ((orientation == SwingConstants.VERTICAL) ? visibleRect.height : visibleRect.width) - 50;
	}
	



	@Override
	public boolean getScrollableTracksViewportHeight()
	{
		return false;
	}
	



	@Override
	public boolean getScrollableTracksViewportWidth()
	{
		return true;
	}
	



	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		return 50;
	}
	
	
}

