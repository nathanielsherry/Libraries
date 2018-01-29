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
		super(filename, text, text, defaultLayout, false, defaultSize, defaultInsets, defaultBorder );
	}
	
	public ToolbarImageButton(String filename, String text, String tooltip)
	{
		super(filename, text, tooltip, defaultLayout, false, defaultSize, defaultInsets, defaultBorder );
	}
	
	public ToolbarImageButton(String filename, String text, String tooltip, boolean isSignificant)
	{
		super(filename, text, tooltip, (isSignificant ? significantLayout : defaultLayout), false, defaultSize, defaultInsets, defaultBorder );
	}
	
	
	
	public ToolbarImageButton(StockIcon stock, String text)
	{
		super(stock.toIconName(), text, text, defaultLayout, false, defaultSize, defaultInsets, defaultBorder );
	}
	
	public ToolbarImageButton(StockIcon stock, String text, String tooltip)
	{
		super(stock.toIconName(), text, tooltip, defaultLayout, false, defaultSize, defaultInsets, defaultBorder );
	}
	
	public ToolbarImageButton(StockIcon stock, String text, String tooltip, boolean isSignificant)
	{
		super(stock.toIconName(), text, tooltip, (isSignificant ? significantLayout : defaultLayout), false, defaultSize, defaultInsets, defaultBorder );
	}
	
}
