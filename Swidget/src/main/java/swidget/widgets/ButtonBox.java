package swidget.widgets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;


public class ButtonBox extends JPanel
{
	
	private JPanel left, right, centre;
	private List<Component> ll, rl, cl;
	
	private JPanel buttonPanel;
	
	private int spacing;
	
	public ButtonBox()
	{
		this(Spacing.bMedium(), Spacing.medium, true);
	}
	public ButtonBox(boolean divider)
	{
		this(Spacing.bMedium(), Spacing.medium, divider);
	}
	public ButtonBox(Border border)
	{
		this(border, Spacing.medium, true);
	}
	public ButtonBox(Border border, int spacing, boolean divider)
	{
		
		this.spacing = spacing;
		
		setLayout(new BorderLayout());
		
		buttonPanel = new ClearPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		if (divider) add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.CENTER);
		
		ll = new ArrayList<Component>();
		rl = new ArrayList<Component>();
		cl = new ArrayList<Component>();
		
		left = new ClearPanel();
		right = new ClearPanel();
		centre = new ClearPanel();
		
		left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));
		right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
		centre.setLayout(new BoxLayout(centre, BoxLayout.X_AXIS));
		
		left.setBorder(border);
		right.setBorder(border);
		centre.setBorder(border);
		
		buttonPanel.add(left);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(centre);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(right);
		
		
		
		
	}
	
	private void relayout()
	{
		
		centre.removeAll();
		for (int i = 0; i < cl.size(); i++)
		{
			Component c = cl.get(i);
			if (i > 0) centre.add(Box.createHorizontalStrut(spacing));
			centre.add(c);
		}
		
		left.removeAll();
		for (int i = 0; i < ll.size(); i++)
		{
			Component c = ll.get(i);
			if (i > 0) left.add(Box.createHorizontalStrut(spacing));
			left.add(c);
		}
		
		right.removeAll();
		for (int i = 0; i < rl.size(); i++)
		{
			Component c = rl.get(i);
			if (i > 0) right.add(Box.createHorizontalStrut(spacing));
			right.add(c);
		}
		
	}
	
	
	public void addLeft(int index, Component c)
	{
		ll.add(index, c);
		relayout();
	}
	public void addLeft(Component c)
	{
		ll.add(c);
		relayout();
	}
	
	public void addRight(int index, Component c)
	{
		rl.add(index, c);
		relayout();
	}
	public void addRight(Component c)
	{
		rl.add(c);
		relayout();
	}
	
	public void addCentre(int index, Component c)
	{
		cl.add(index, c);
		relayout();
	}
	public void addCentre(Component c)
	{
		cl.add(c);
		relayout();
	}

}
