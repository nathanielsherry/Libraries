package swidget.widgets;

import java.awt.Dimension;
import java.awt.Font;
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


public class PropertyPanel extends JPanel
{

	GridBagConstraints c;

	public PropertyPanel(Map<String, String> properties)
	{
		setLayout(new GridBagLayout());
		setProperties(properties);
	}

	
	public void setProperties(Map<String, String> properties)
	{
		removeAll();
		
		c = new GridBagConstraints();
		c.ipadx = 8;
		c.ipady = 8;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.anchor = GridBagConstraints.NORTH;
		c.gridy = 0;
		c.gridx = 0;
		c.gridheight = 11;
		c.weightx = 0;
		
		JLabel icon = new JLabel(StockIcon.BADGE_INFO.toImageIcon(IconSize.ICON));
		icon.setBorder(new EmptyBorder(0, 0, 0, Spacing.huge));
		add(icon, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 1;
		c.gridheight = 1;
		c.gridy = 0;
		c.gridx = 1;
		
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
		label = new JLabel(one, SwingConstants.LEFT);
		add(label, c);
		
		c.gridx = 2;
		c.weightx = 1f;
		label = new JLabel(two, SwingConstants.RIGHT);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		label.setPreferredSize(new Dimension(225, label.getPreferredSize().height));
		label.setToolTipText(two);
		add(label, c);
		c.gridy += 1;
		c.gridx = 0;
		
		
	}
}
