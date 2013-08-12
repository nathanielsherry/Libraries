package autodialog.view;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.widgets.ButtonBox;
import swidget.widgets.ImageButton;
import swidget.widgets.ImageButton.Layout;
import swidget.widgets.Spacing;
import swidget.widgets.TextWrapping;
import autodialog.controller.IAutoDialogController;


public class AutoDialog extends JDialog
{

	public enum AutoDialogButtons {
		CLOSE, OK_CANCEL
	}
	
	private IAutoDialogController	controller;
	private Container owner;
	
	private String helpTitle;
	private String helpMessage;
	
	private AutoDialogButtons buttons;

	
	private ImageButton info;
	

	public AutoDialog(IAutoDialogController _controller, AutoDialogButtons buttons, Window owner)
	{
		super(owner);
		this.controller = _controller;
		this.owner = owner;
		this.buttons = buttons;
	}

	public AutoDialog(IAutoDialogController _controller, AutoDialogButtons buttons)
	{
		super();
		this.controller = _controller;
		this.buttons = buttons;
	}
	
	

	public void initialize(){
		
		
		
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		AutoPanel view = new AutoPanel(controller);
		
		JScrollPane scroller = new JScrollPane(view);
		scroller.setBorder(Spacing.bNone());
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		c.add(scroller, BorderLayout.CENTER);


		c.add(createButtonBox(), BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(owner);
		setVisible(true);
	}
	
	
	private JPanel createButtonBox()
	{
		
		ButtonBox bbox = new ButtonBox();
		
		if (buttons == AutoDialogButtons.OK_CANCEL) {
			
			ImageButton ok = new ImageButton(StockIcon.CHOOSE_OK, "OK", true);
			ok.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e)
				{
					AutoDialog.this.setVisible(false);
					controller.submit();
				}
			});
			
			ImageButton cancel = new ImageButton(StockIcon.CHOOSE_CANCEL, "Cancel", true);
			cancel.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e)
				{
					AutoDialog.this.setVisible(false);
					controller.cancel();
				}
			});
			
			bbox.addRight(0, cancel);
			bbox.addRight(0, ok);
			
		} else if (buttons == AutoDialogButtons.CLOSE) {
			
			ImageButton close = new ImageButton(StockIcon.WINDOW_CLOSE, "Close", true);
			close.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e)
				{
					AutoDialog.this.setVisible(false);
					controller.close();
				}
			});
			
			bbox.addRight(0, close);
			
		}
		
		
		info = new ImageButton(StockIcon.BADGE_HELP, "Filter Information", Layout.IMAGE, true);
		info.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{	
				JOptionPane.showMessageDialog(
						AutoDialog.this, 
						TextWrapping.wrapTextForMultiline(helpMessage),
						helpTitle, 
						JOptionPane.INFORMATION_MESSAGE, 
						StockIcon.BADGE_HELP.toImageIcon(IconSize.ICON)
					);

			}
		});
		if (helpMessage == null) info.setVisible(false);
		
		
		bbox.addLeft(0, info);

		
				
		return bbox;
		
	}

	
	
	public String getHelpTitle() {
		return helpTitle;
	}

	public void setHelpTitle(String helpTitle) {
		this.helpTitle = helpTitle;
		
	}

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
		if (info != null) info.setVisible((helpMessage != null && helpMessage.length() > 0));
	}
	
	
	
	

}
