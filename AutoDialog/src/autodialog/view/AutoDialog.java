package autodialog.view;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import autodialog.controller.IAutoDialogController;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.widgets.ButtonBox;
import swidget.widgets.ImageButton;
import swidget.widgets.Spacing;
import swidget.widgets.TextWrapping;
import swidget.widgets.ImageButton.Layout;


public class AutoDialog extends JDialog
{

	private IAutoDialogController	controller;
	private Container owner;
	
	private String helpTitle;
	private String helpMessage;

	
	private ImageButton info;
	
	public AutoDialog(IAutoDialogController _controller, Frame owner)
	{
		super(owner);
		this.controller = _controller;
		this.owner = owner;
	}
	
	public AutoDialog(IAutoDialogController _controller, Dialog owner)
	{
		super(owner);
		this.controller = _controller;
		this.owner = owner;
	}
	
	public AutoDialog(IAutoDialogController _controller, Window owner)
	{
		super(owner);
		this.controller = _controller;
		this.owner = owner;
	}

	public AutoDialog(IAutoDialogController _controller)
	{
		super();
		this.controller = _controller;
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
				
		ImageButton ok = new ImageButton(StockIcon.CHOOSE_OK, "OK", true);
		ok.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				AutoDialog.this.setVisible(false);
				AutoDialog.this.dispose();
				controller.submit();
			}
		});
		
		ImageButton cancel = new ImageButton(StockIcon.CHOOSE_CANCEL, "Cancel", true);
		cancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				AutoDialog.this.setVisible(false);
				AutoDialog.this.dispose();
				controller.cancel();
			}
		});
		
		
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
		
		ButtonBox bbox = new ButtonBox();
		bbox.addLeft(0, info);
		bbox.addRight(0, cancel);
		bbox.addRight(0, ok);
		
				
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
