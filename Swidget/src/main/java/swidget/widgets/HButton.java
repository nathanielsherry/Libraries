package swidget.widgets;

import java.awt.Dimension;


import swidget.icons.IconSize;
import swidget.icons.StockIcon;

public class HButton extends ImageButton {

	public HButton(StockIcon icon, String tooltip) {
		this(icon, tooltip, null);
	}
	
	public HButton(StockIcon icon, String tooltip, Runnable onPress) {
		super();
		super.withIcon(icon, IconSize.TOOLBAR_SMALL)
			.withTooltip(tooltip)
			.withBordered(false)
			.withLayout(Layout.IMAGE);
		
		this.setPreferredSize(new Dimension(32, 32));
		if (onPress != null) {
			this.addActionListener(e -> onPress.run());
		}
	}
	
	public HButton(String text) {
		this(text, null);
	}
	public HButton(String text, Runnable onPress) {
		super();
		this.setText(text);
		this.setPreferredSize(new Dimension((int) Math.max(this.getPreferredSize().getWidth(), 76), 32));
		
		if (onPress != null) {
			this.addActionListener(e -> onPress.run());
		}
	}
	
	

	
	
	
}
