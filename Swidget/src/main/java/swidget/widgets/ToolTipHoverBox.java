package swidget.widgets;

import javax.swing.JLabel;

import swidget.icons.IconFactory;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;

public class ToolTipHoverBox extends JLabel {

	public ToolTipHoverBox(String tooltip) {
		super(IconFactory.getImageIcon("hint-symbolic", IconSize.BUTTON));
		this.setToolTipText(tooltip);
		
	}
	
}
