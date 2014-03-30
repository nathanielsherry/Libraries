package autodialog.view.layouts;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import swidget.widgets.Spacing;
import autodialog.model.Parameter;
import autodialog.view.AutoPanel;
import autodialog.view.editors.IEditor;
import autodialog.view.editors.IEditor.LabelStyle;

public class FramesADLayout extends AbstractGroupingADLayout {

	private JPanel root;
	
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	private boolean needsVerticalGlue = true;
	private ADLayoutFactory factory;
	
	
	public FramesADLayout() {
		this(new ADLayoutFactory() {
			
			@Override
			public IADLayout getLayout(List<Parameter<?>> params, int level, String group) {
				return new SimpleADLayout();
			}
		});
	}
	
	public FramesADLayout(ADLayoutFactory factory) {
		this.factory = factory;
	}

	
	@Override
	public void setAutoPanel(AutoPanel root, int level) {
		
		System.out.println("SetAutoPanel " + level);
		
		this.root = root;
		this.level = level;
		if (level == 0) root.setBorder(Spacing.bHuge());
	}

	
	protected void startPanel()
	{
		root.setOpaque(false);
		root.setLayout(layout);
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1f;
		root.add(Box.createHorizontalGlue(), c);
		c.insets = Spacing.iSmall();
	}
		
	
	


	@Override
	protected void finishPanel() {
		
		if (needsVerticalGlue)
		{
			c.gridy++;
			c.weighty = 1f;
			root.add(Box.createVerticalGlue(), c);
		}
		
		root.doLayout();

	}
	
	
	@Override
	protected void addParamGroup(List<Parameter<?>> params, String title)
	{

		AutoPanel subpanel = new AutoPanel(params, factory.getLayout(params, level+1, title), level+1);
		subpanel.setBorder(new TitledBorder(title));
		
		needsVerticalGlue &= (!subpanel.expandVertical());
		
		c.weighty = subpanel.expandVertical() ? 1f : 0f;
		c.weightx = subpanel.expandHorizontal() ? 1f : 0f;
		c.gridy += 1;
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		
		c.anchor = GridBagConstraints.LINE_START;
		
		c.gridwidth = 2;				
		root.add(subpanel, c);
		c.gridwidth = 1;
		
	}
	
	@Override
	protected void addSingleParam(Parameter<?> param)
	{
		//fetch the editor from the Parameter
		IEditor<?> editor = param.getEditor();		

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
			root.add(paramLabel, c);
			
			c.weightx = editor.expandHorizontal() ? 1f : 0f;
			c.gridx++;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.LINE_END;
			
			root.add(editor.getComponent(), c);
			
		}
		else if (editor.getLabelStyle() == LabelStyle.LABEL_ON_TOP)
		{
			c.gridwidth = 2;
			
			c.weighty = 0f;
			root.add(paramLabel, c);

			c.gridy++;
			
			c.weighty = editor.expandVertical() ? 1f : 0f;
			root.add(editor.getComponent(), c);
			
			c.gridwidth = 1;
		}
		else if(editor.getLabelStyle() == LabelStyle.LABEL_HIDDEN)
		{
			c.gridwidth = 2;				
			root.add(editor.getComponent(), c);
			c.gridwidth = 1;
		}
	}

	@Override
	public Border topLevelBorder() {
		return Spacing.bHuge();
	}


	
	
	
}
