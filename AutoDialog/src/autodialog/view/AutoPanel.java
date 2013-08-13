package autodialog.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import swidget.widgets.Spacing;
import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import autodialog.view.editors.IEditor;
import autodialog.view.editors.IEditor.LabelStyle;

public class AutoPanel extends JPanel {

	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	boolean needsVerticalGlue = true;
	
	private List<Parameter<?>> params = new ArrayList<>();
	
	public AutoPanel() {
		
		super();
		setOpaque(false);
		setLayout(layout);
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1f;
		add(Box.createHorizontalGlue(), c);
		c.insets = Spacing.iSmall();
		
	}

	public void addGrouping(AutoPanel control, String title)
	{
		
		needsVerticalGlue &= (!control.expandVertical());
		c.weighty = control.expandVertical() ? 1f : 0f;
		c.weightx = control.expandHorizontal() ? 1f : 0f;
		
		c.gridy += 1;
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		
		control.setBorder(new TitledBorder(title));
		
		c.gridwidth = 2;				
		add(control, c);
		c.gridwidth = 1;
	}
	
	//adds a parameter directly to the panel
	public void addParam(Parameter<?> param, IAutoDialogController controller)
	{
		//fetch the editor from the Parameter
		IEditor<?> editor = param.getEditor();		
		
		//add a param listener, and add this param to the list of displayed params
		editor.addListener(new ParamListener<>(param, controller));
		params.add(param);
		
		
		JLabel paramLabel = new JLabel(param.name);
		paramLabel.setFont(paramLabel.getFont().deriveFont(Font.PLAIN));
		paramLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		needsVerticalGlue &= (!editor.expandVertical());
		
		c.weighty = editor.expandVertical() ? 1f : 0f;
		c.weightx = editor.expandHorizontal() ? 1f : 0f;
		c.gridy += 1;
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		
		c.anchor = GridBagConstraints.LINE_START;

		if (editor.getLabelStyle() == LabelStyle.LABEL_ON_SIDE)
		{
			c.weightx = 0;
			add(paramLabel, c);
			
			c.weightx = editor.expandHorizontal() ? 1f : 0f;
			c.gridx++;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.LINE_END;
			
			add(editor.getComponent(), c);
			
		}
		else if (editor.getLabelStyle() == LabelStyle.LABEL_ON_TOP)
		{
			c.gridwidth = 2;
			
			c.weighty = 0f;
			add(paramLabel, c);

			c.gridy++;
			
			c.weighty = editor.expandVertical() ? 1f : 0f;
			add(editor.getComponent(), c);
			
			c.gridwidth = 1;
		}
		else if(editor.getLabelStyle() == LabelStyle.LABEL_HIDDEN)
		{
			c.gridwidth = 2;				
			add(editor.getComponent(), c);
			c.gridwidth = 1;
		}
	}

	
	public void finishPanel(boolean bigBorder)
	{
		if (needsVerticalGlue)
		{
			c.gridy++;
			c.weighty = 1f;
			add(Box.createVerticalGlue(), c);
		}
		
		doLayout();

		if (bigBorder) {
			setBorder(Spacing.bHuge());
		} else {
			setBorder(Spacing.bNone());
		}
		
	}
	
	//creates a subpanel for adding to a larger group
	public static AutoPanel subpanel(List<Parameter<?>> params, IAutoDialogController controller)
	{
		AutoPanel panel = new AutoPanel();
		for (Parameter<?> param : params) {
			panel.addParam(param, controller);
		}
		panel.needsVerticalGlue = false;
		panel.finishPanel(false);
		return panel;
	}
	

	public static AutoPanel panel(IAutoDialogController controller)
	{
		AutoPanel view = new AutoPanel();
		List<String> groups = new ArrayList<>();	//list of groups already handled
				
		//add parameters to panel
		for (Parameter<?> param : controller.getParameters())
		{	
			if (param.getGroup() == null)
			{	
				view.addParam(param, controller);
			} 
			else if (!groups.contains(param.getGroup()))
			{
				String groupLabel = param.getGroup();
				
				//create the subpanel
				AutoPanel subpanel = AutoPanel.subpanel(getGroupParams(controller.getParameters(), groupLabel), controller);
				view.addGrouping(subpanel, groupLabel);
				
				//put this in the list of groups already handled
				groups.add(groupLabel);
			}
		}
		view.finishPanel(true);
		return view;
	}
	
	private static List<Parameter<?>> getGroupParams(List<Parameter<?>> params, String group)
	{
		List<Parameter<?>> selected = new ArrayList<>();
		for (Parameter<?> param : params)
		{
			if (param.getGroup() == null) continue;
			if (param.getGroup().equals(group)) selected.add(param);
		}
		return selected;
	}
	
	
	
	public boolean expandVertical() {
		for (Parameter<?> param : params) {
			if (param.getEditor().expandVertical()) return true;
		}
		return false;
	}

	public boolean expandHorizontal() {
		for (Parameter<?> param : params) {
			if (param.getEditor().expandHorizontal()) return true;
		}
		return false;
	}

	
}
