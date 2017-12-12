package swidget.widgets.listcontrols;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import swidget.icons.StockIcon;
import swidget.widgets.ImageButton;
import swidget.widgets.Spacing;



public abstract class SelectionListControls extends JPanel
{
	
	public SelectionListControls(String name){
		
		super();
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		ImageButton add = new ImageButton(StockIcon.CHOOSE_OK, "OK", "Add Selected " + name);
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				approve();
			}
		});


		ImageButton cancel = new ImageButton(StockIcon.CHOOSE_CANCEL, "Cancel", "Discard Selections");
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				cancel();
			}
		});


		add(add);
		add(cancel);

		setBorder(Spacing.bSmall());

	}
	
	protected abstract void approve();
	protected abstract void cancel();
	
}
