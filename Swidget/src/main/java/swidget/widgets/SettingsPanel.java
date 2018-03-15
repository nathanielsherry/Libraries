package swidget.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel {

	
	public enum LabelPosition {
		BESIDE,
		ABOVE,
		NONE
	}
	
	private GridBagConstraints c = new GridBagConstraints();
	
	public SettingsPanel() {
		setLayout(new GridBagLayout());
		
		c.insets = Spacing.iTiny();
		c.ipadx = 0;
		c.ipady = 0;
		
	}
	
	public void addSetting(Component component) {
		addSetting(component, "", LabelPosition.NONE, false, false);
	}
	
	
	
	public void addSetting(Component component, String label, LabelPosition labelPosition) {
		addSetting(component, makeLabel(label), labelPosition);
	}
	
	public void addSetting(Component component, JLabel label, LabelPosition labelPosition) {
		addSetting(component, label, labelPosition, false, false);
	}
	
	
	
	public void addSetting(Component component, String label, LabelPosition labelPosition, boolean vFill, boolean hFill) {
		addSetting(component, makeLabel(label), labelPosition, vFill, hFill);
	}
	
	public void addSetting(Component component, JLabel label, LabelPosition labelPosition, boolean vFill, boolean hFill) {
		
		
		
		
		c.weighty = vFill ? 1f : 0f;
		c.weightx = hFill ? 1f : 0f;
		c.gridy += 1;
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		
		c.anchor = GridBagConstraints.LINE_START;

		if (labelPosition == LabelPosition.BESIDE)
		{
			c.weightx = 1;
			add(label, c);
			
			c.weightx = hFill ? 1f : 0f;
			c.gridx++;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.LINE_END;
			
			add(component, c);
			
		}
		else if (labelPosition == LabelPosition.ABOVE)
		{
			c.gridwidth = 2;
			
			c.weighty = 0f;
			add(label, c);

			c.gridy++;
			
			c.weighty = vFill ? 1f : 0f;
			add(component, c);
			
			c.gridwidth = 1;
		}
		else if(labelPosition == LabelPosition.NONE)
		{
			c.gridwidth = 2;				
			add(component, c);
			c.gridwidth = 1;
		}
		
	}
	
	
	
	private JLabel makeLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(label.getFont().deriveFont(Font.PLAIN));
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		return label;
	}
	
	
}
