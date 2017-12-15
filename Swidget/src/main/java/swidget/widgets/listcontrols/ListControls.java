package swidget.widgets.listcontrols;


import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPopupMenu;

import eventful.EventfulEnumListener;
import swidget.icons.StockIcon;
import swidget.widgets.ClearPanel;
import swidget.widgets.DropdownImageButton.Actions;
import swidget.widgets.Spacing;



public abstract class ListControls extends ClearPanel
{

	private List<Component>	buttons;
	private ElementCount lastcount = ElementCount.NONE;
	

	public enum ElementCount
	{
		NONE, ONE, MANY
	}

	public ListControls(JPopupMenu addMenu)
	{
		this(null, addMenu);
	}
	
	public ListControls(String[] tooltips)
	{
		this(tooltips, null);
	}
	
	public ListControls()
	{
		this(null, null);
	}

	public ListControls(String[] tooltips, JPopupMenu addMenu)
	{
		
		buttons = new ArrayList<Component>();


		final ListControlButton remove, clear, up, down;
		final ListControlWidget add;
		String tooltip;


		int tooltipCount = 0;
		tooltip = (tooltips != null && tooltips.length > tooltipCount) ? tooltips[tooltipCount] : "";
		
		if (addMenu == null)
		{
			ListControlButton addButton;
			addButton = new ListControlButton(StockIcon.EDIT_ADD, "Add", tooltip) {

				@Override
				public void setEnableState(ElementCount ec)
				{
					this.setEnabled(true);
				}
			};
			addButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e)
				{
					add();
				}
			});
			
			add = addButton;
		}
		else 
		{
			ListControlDropdownButton addButton;
			
			addButton = new ListControlDropdownButton(StockIcon.EDIT_ADD, "Add", tooltip, addMenu) {

				@Override
				public void setEnableState(ElementCount ec)
				{
					this.setEnabled(true);
				}
			};
			addButton.addListener(new EventfulEnumListener<Actions>() {
				
				public void change(Actions message)
				{
					switch (message)
					{
						case MAIN:
							add();
							break;
						case MENU:
							//handled internally
							break;
					}
				}
			});

			add = addButton;
			
		}



		tooltipCount++;
		tooltip = (tooltips != null && tooltips.length > tooltipCount) ? tooltips[tooltipCount] : "";
		remove = new ListControlButton(StockIcon.EDIT_DELETE, "Remove", tooltip) {

			@Override
			public void setEnableState(ElementCount ec)
			{
				this.setEnabled(ec != ElementCount.NONE);
			}
		};
		remove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				remove();
			}
		});


		tooltipCount++;
		tooltip = (tooltips != null && tooltips.length > tooltipCount) ? tooltips[tooltipCount] : "";
		clear = new ListControlButton(StockIcon.EDIT_CLEAR, "Clear", tooltip) {

			@Override
			public void setEnableState(ElementCount ec)
			{
				this.setEnabled(ec != ElementCount.NONE);
			}
		};
		clear.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				clear();
			}
		});


		tooltipCount++;
		tooltip = (tooltips != null && tooltips.length > tooltipCount) ? tooltips[tooltipCount] : "";
		up = new ListControlButton(StockIcon.GO_UP, "Up", tooltip) {

			@Override
			public void setEnableState(ElementCount ec)
			{
				this.setEnabled(ec == ElementCount.MANY);
			}
		};
		up.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				up();
			}
		});


		tooltipCount++;
		tooltip = (tooltips != null && tooltips.length > tooltipCount) ? tooltips[tooltipCount] : "";
		down = new ListControlButton(StockIcon.GO_DOWN, "Down", tooltip) {

			@Override
			public void setEnableState(ElementCount ec)
			{
				this.setEnabled(ec == ElementCount.MANY);
			}
		};
		down.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				down();
			}
		});



		buttons.add((Component)add);
		buttons.add(remove);
		buttons.add(clear);

		buttons.add(new ClearPanel());

		buttons.add(up);
		buttons.add(down);

		layoutButtons();

		setBorder(Spacing.bSmall());
		setElementCount(ElementCount.NONE);

	}


	private void layoutButtons()
	{

		this.removeAll();
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints(
			0,
			0,
			1,
			1,
			0.0,
			0.0,
			GridBagConstraints.CENTER,
			GridBagConstraints.NONE,
			new Insets(0, Spacing.tiny, 0, Spacing.tiny),
			0,
			0);

		for (Component b : buttons)
		{
			
			if (! (b instanceof ListControlWidget))
			{
				c.weightx = 1.0;
				c.fill = GridBagConstraints.HORIZONTAL;
			} else {
				c.weightx = 0.0;
				c.fill = GridBagConstraints.NONE;
			}
			
			add(b, c);
			c.gridx += 1;
		}

	}


	public void setElementCount(ElementCount ec)
	{
/*
		if (ec == ElementCount.NONE)
		{

			add.setEnabled(true);
			remove.setEnabled(false);
			clear.setEnabled(false);
			up.setEnabled(false);
			down.setEnabled(false);

		}
		else if (ec == ElementCount.ONE)
		{

			add.setEnabled(true);
			remove.setEnabled(true);
			clear.setEnabled(true);
			up.setEnabled(false);
			down.setEnabled(false);

		}
		else
		{

			add.setEnabled(true);
			remove.setEnabled(true);
			clear.setEnabled(true);
			up.setEnabled(true);
			down.setEnabled(true);

		}
*/
		for (Component button : buttons)
		{
			if (button instanceof ListControlWidget) ((ListControlWidget)button).setEnableState(ec);
		}
		
		lastcount = ec;

	}


	protected abstract void add();


	protected abstract void remove();


	protected abstract void clear();


	protected abstract void up();


	protected abstract void down();


	public void addButton(ListControlButton button, int index)
	{
		button.setEnableState(lastcount);
		buttons.add(index, button);
		layoutButtons();
	}

}