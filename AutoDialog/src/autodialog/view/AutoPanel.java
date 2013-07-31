package autodialog.view;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import autodialog.controller.IAutoDialogController;
import autodialog.model.Parameter;
import autodialog.view.editors.EditorFactory;
import autodialog.view.editors.IEditor;
import autodialog.view.editors.LabelStyle;
import fava.functionable.FList;
import swidget.widgets.Spacing;


public class AutoPanel extends JPanel
{

	private IAutoDialogController	controller;

	private List<IEditor>			editors;
	private List<Parameter> 		paramslist;

	public AutoPanel(IAutoDialogController controller)
	{
		this(controller, true);
	}
	
	public AutoPanel(IAutoDialogController controller, boolean bigBorder)
	{

		super(new BorderLayout());
		
		this.controller = controller;

		createSettingsPanel(bigBorder);
		setOpaque(false);
		
	}


	public void showSettings(boolean show)
	{
		setVisible(show);
	}
	
	public void updateWidgetsEnabled()
	{
		for (int i = 0; i < editors.size(); i++)
		{
			editors.get(i).getComponent().setEnabled(paramslist.get(i).enabled);
		}
	}

	public void updateFromParameters()
	{
		for (IEditor editor: editors)
		{
			editor.setFromParameter();
		}
	}

	private void createSettingsPanel(boolean bigBorder)
	{

		//get a list of parameters
		paramslist = new FList<Parameter>(controller.getParameters());
		editors = new ArrayList<IEditor>();
		
		Iterator<Parameter> params = paramslist.iterator();
		
	
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);

		



		JLabel paramLabel;
		IEditor editor;
		Parameter param;

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1f;
		add(Box.createHorizontalGlue(), c);
		c.insets = Spacing.iSmall();
		
		boolean needsVerticalGlue = true;
		
		while (params.hasNext()) {

			param = params.next();

			paramLabel = new JLabel(param.name);
			paramLabel.setFont(paramLabel.getFont().deriveFont(Font.PLAIN));
			paramLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			editor = EditorFactory.createEditor(param, controller, this);
			
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
	
	
	

}


