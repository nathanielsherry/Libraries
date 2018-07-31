package swidget.widgets;

import java.awt.Insets;

import javax.swing.border.Border;

import swidget.icons.IconSize;
import swidget.icons.StockIcon;

public class ToolbarImageButton extends ImageButton {

	public static final Layout defaultLayout = Layout.IMAGE;
	public static final Layout significantLayout = Layout.IMAGE_ON_SIDE;
	public static final IconSize defaultSize = IconSize.TOOLBAR_SMALL;
	public static final Insets defaultInsets = Spacing.iMedium();
	public static final Border defaultBorder = Spacing.bMedium();
	
	public ToolbarImageButton(String filename, String text)
	{
		this(filename, text, text);
	}
	
	public ToolbarImageButton(String filename, String text, String tooltip)
	{
		this(filename, text, tooltip, false);
	}
	
	public ToolbarImageButton(String filename, String text, String tooltip, boolean isSignificant)
	{
		super(text);
		super.withTooltip(tooltip)
			.withLayout(isSignificant ? significantLayout : defaultLayout)
			.withBordered(false)
			.withIcon(filename, defaultSize)
			.withBorder(defaultBorder);
	}
	
	
	
	public ToolbarImageButton(StockIcon stock, String text)
	{
		this(stock, text, text);
	}
	
	public ToolbarImageButton(StockIcon stock, String text, String tooltip)
	{
		this(stock, text, tooltip, false);
	}
	
	public ToolbarImageButton(StockIcon stock, String text, String tooltip, boolean isSignificant)
	{
		super(text);
		super.withTooltip(tooltip)
			.withLayout(isSignificant ? significantLayout : defaultLayout)
			.withBordered(false)
			.withIcon(stock, defaultSize)
			.withBorder(defaultBorder);
	}
	
}
