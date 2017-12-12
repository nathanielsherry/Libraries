package swidget.widgets;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import commonenvironment.Env;
import eventful.EventfulEnum;
import eventful.swing.EventfulEnumPanel;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.widgets.ImageButton.Layout;



public class DropdownImageButton extends EventfulEnumPanel<DropdownImageButton.Actions>
{
	
	private JButton button, dropdown;
	
	public enum Actions
	{
		MAIN, MENU
	}
	
	EventfulEnum<Actions> eventful;
	
	
	public DropdownImageButton(String filename, String text, String tooltip, JPopupMenu menu)
	{
		init(filename, text, tooltip, IconSize.TOOLBAR_SMALL, ToolbarImageButton.defaultLayout, menu);
	}
	
	public DropdownImageButton(String filename, String text, String tooltip, IconSize size, JPopupMenu menu)
	{
		init(filename, text, tooltip, size, ToolbarImageButton.defaultLayout, menu);
	}

	public DropdownImageButton(String filename, String text, String tooltip, IconSize size,  Layout layout, JPopupMenu menu)
	{
		init(filename, text, tooltip, size, layout, menu);
	}
	
	public DropdownImageButton(StockIcon stock, String text, String tooltip, JPopupMenu menu)
	{
		init(stock.toIconName(), text, tooltip, IconSize.TOOLBAR_SMALL, ToolbarImageButton.defaultLayout, menu);
	}
	
	public DropdownImageButton(StockIcon stock, String text, String tooltip, IconSize size, JPopupMenu menu)
	{
		init(stock.toIconName(), text, tooltip, size, ToolbarImageButton.defaultLayout, menu);
	}

	public DropdownImageButton(StockIcon stock, String text, String tooltip, IconSize size,  Layout layout, JPopupMenu menu)
	{
		init(stock.toIconName(), text, tooltip, size, layout, menu);
	}
	
	private void init(String filename, String text, String tooltip, IconSize size, Layout layout, final JPopupMenu menu)
	{
		
		eventful = new EventfulEnum<Actions>(); 
		
		this.setFocusable(true);
		this.setOpaque(false);
		setLayout(new BorderLayout());
	
		button = new ImageButton(filename, text, tooltip, layout, size);
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				updateListeners(Actions.MAIN);
			}
		});
		button.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
		
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
		
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
		
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
		
			public void mouseClicked(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON3 && menu != null) {
					menu.show(
							DropdownImageButton.this, 
							button.getLocation().x, 
							button.getLocation().y + button.getHeight()
						);
				}
			}
		});
		
		
		add(button, BorderLayout.CENTER);

				
		String dropdownText = "▾";
		String imageName = "";
		
		boolean isTextArrow = button.getFont().canDisplay(dropdownText.charAt(0)) && !(Env.isMac());
		
		if (! isTextArrow)
		{
			dropdownText = "";
			imageName = "downarrow";
		}
		
		
		
		dropdown = new ImageButton(
			imageName,
			dropdownText.toString(),
			"Show additional options",
			(isTextArrow ? Layout.TEXT : Layout.IMAGE),
			false,
			null,
			Spacing.iNone(),
			Spacing.bMedium());

		dropdown.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				menu.show(DropdownImageButton.this, button.getLocation().x, button.getLocation().y + button.getHeight());
				updateListeners(Actions.MENU);
			}
		});
		
		
		if (menu != null) add(dropdown, BorderLayout.EAST);
		
		setBorder(Spacing.bNone());
		
	}
	
	
	@Override
	public void setEnabled(boolean enabled)
	{
		dropdown.setEnabled(enabled);
		button.setEnabled(enabled);
	}
	

}
