package swidget.widgets.listcontrols;

import swidget.icons.StockIcon;
import swidget.widgets.ImageButton;
import swidget.widgets.listcontrols.ListControls.ElementCount;


public abstract class ListControlButton extends ImageButton implements ListControlWidget
{

	public ListControlButton(String filename, String text, String tooltip)
	{
		super(filename, text, tooltip, Layout.IMAGE);
	}
	
	public ListControlButton(StockIcon stock, String text, String tooltip)
	{
		super(stock.toIconName(), text, tooltip, Layout.IMAGE);
	}
	
	public abstract void setEnableState(ElementCount ec);

}
