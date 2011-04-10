package swidget.dialogues;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import swidget.icons.StockIcon;
import swidget.widgets.ButtonBox;
import swidget.widgets.ClearPanel;
import swidget.widgets.ImageButton;
import swidget.widgets.PropertyPanel;
import swidget.widgets.Spacing;


public class PropertyDialogue extends JDialog
{

	public PropertyDialogue(String title, JFrame owner, Map<String, String> properties)
	{
		super(owner, title);
		init(owner, properties);
	}
	public PropertyDialogue(String title, JDialog owner, Map<String, String> properties)
	{
		super(owner, title);
		init(owner, properties);
	}
	
	private void init(Window owner, Map<String, String> properties){
		
		Container container = getContentPane();
		JPanel containerPanel = new ClearPanel();
		containerPanel.setLayout(new BorderLayout());
		container.add(containerPanel);

			
		

		ButtonBox bbox = new ButtonBox(Spacing.bHuge());
		ImageButton close = new ImageButton(StockIcon.WINDOW_CLOSE, "Close", true);
		close.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e)
			{
				PropertyDialogue.this.setVisible(false);
			}
		});
		bbox.addRight(0, close);
		
		PropertyPanel propPanel = new PropertyPanel(properties);
		propPanel.setBorder(Spacing.bHuge());
		containerPanel.add(propPanel, BorderLayout.NORTH);
		containerPanel.add(bbox, BorderLayout.SOUTH);
		
		pack();
		
		setMinimumSize(getPreferredSize());
		
		setModal(true);
		setLocationRelativeTo(owner);
		setVisible(true);
		
	}
	
	
}
