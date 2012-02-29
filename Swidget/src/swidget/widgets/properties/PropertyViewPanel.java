package swidget.widgets.properties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.widgets.Spacing;


public class PropertyViewPanel extends JPanel
{

	GridBagConstraints c;

	public PropertyViewPanel(Map<String, String> properties)
	{
		this(properties, null);
	}

	public PropertyViewPanel(Map<String, String> properties, String caption)
	{
		setLayout(new GridBagLayout());
		setProperties(properties, caption);
	}
	
	public void setProperties(Map<String, String> properties, String caption)
	{
		removeAll();
		
		c = new GridBagConstraints();
		c.ipadx = 8;
		c.ipady = 8;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.anchor = GridBagConstraints.NORTH;
		c.gridy = 0;
		c.gridx = 0;
		c.gridheight = properties.size() + 1;
		c.weightx = 0;
		
		JLabel icon = new JLabel(StockIcon.BADGE_INFO.toImageIcon(IconSize.ICON));
		icon.setBorder(new EmptyBorder(0, 0, 0, Spacing.huge));
		add(icon, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 1;
		c.gridheight = 1;
		c.gridy = 0;
		c.gridx = 1;
		
		if (caption != null) 
		{
			c.gridwidth = 2;
			add(new JLabel("<html><big><b>" + caption + "</b></big></html>"), c);
			c.gridwidth = 1;
			c.gridy++;
			
		}
		
		
		
		for (Entry<String, String> property : properties.entrySet())
		{
			addJLabelPair(property.getKey(), property.getValue());
		}
	}
	

	private void addJLabelPair(String one, String two)
	{
		
		JLabel label;
		
		c.gridx = 1;
		c.weightx = 0.0f;
		label = new JLabel(one, SwingConstants.RIGHT);
		label.setForeground(Color.gray);
		add(label, c);
		
		c.gridx = 2;
		c.weightx = 1f;
		label = new JLabel(two, SwingConstants.LEFT);
		label.setPreferredSize(new Dimension(225, label.getPreferredSize().height));
		label.setToolTipText(two);
		label.setBorder(new EmptyBorder(0, Spacing.large, 0, 0));
		add(label, c);
		c.gridy += 1;
		c.gridx = 0;
		
		
	}
}
