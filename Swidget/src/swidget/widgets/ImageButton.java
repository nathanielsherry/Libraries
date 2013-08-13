package swidget.widgets;


import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import swidget.Swidget;
import swidget.icons.IconFactory;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import fava.Functions;
import fava.functionable.FList;


public class ImageButton extends JButton
{
	

	public final static Layout defaultLayout = Layout.IMAGE_ON_SIDE;
	public final static IconSize defaultSize = IconSize.BUTTON;
	public final static boolean defaultBorder = false;	
	
	private boolean isNimbus;
	
	public enum Layout
	{
		IMAGE, TEXT, IMAGE_ON_TOP, IMAGE_ON_SIDE
	}
	
	
	
	private boolean	showBorder;

	public ImageButton(String filename, String text)
	{
		super();
		initialize(filename, text, null, defaultLayout, defaultBorder, defaultSize, null, null);
	}

	public ImageButton(String filename, String text, boolean showBorder)
	{
		super();
		initialize(filename, text, null, defaultLayout, showBorder, defaultSize, null, null);
	}
	
	public ImageButton(String filename, String text, IconSize size)
	{
		super();
		initialize(filename, text, null, defaultLayout, defaultBorder, size, null, null);
	}
	
	
	public ImageButton(String filename, String text, Layout mode)
	{
		super();
		initialize(filename, text, null, mode, defaultBorder, defaultSize, null, null);
	}

	public ImageButton(String filename, String text, String tooltip)
	{
		super();
		initialize(filename, text, tooltip, defaultLayout, defaultBorder, defaultSize, null, null);
	}
	
	public ImageButton(String filename, String text, String tooltip, boolean showBorder)
	{
		super();
		initialize(filename, text, tooltip, defaultLayout, showBorder, defaultSize, null, null);
	}
	
	public ImageButton(String filename, String text, Layout mode, IconSize size)
	{
		super();
		initialize(filename, text, null, mode, defaultBorder, size, null, null);
	}
	
	public ImageButton(String filename, String text, String tooltip, Layout mode)
	{
		super();
		initialize(filename, text, tooltip, mode, defaultBorder, defaultSize, null, null);
	}

	public ImageButton(String filename, String text, String tooltip, Layout mode, IconSize size)
	{
		super();
		initialize(filename, text, tooltip, mode, defaultBorder, size, null, null);
	}

	public ImageButton(String filename, String text, String tooltip, IconSize size)
	{
		super();
		initialize(filename, text, tooltip, defaultLayout, defaultBorder, size, null, null);
	}

	public ImageButton(String filename, String text, Layout mode, boolean showBorder)
	{
		super();
		initialize(filename, text, null, mode, showBorder, defaultSize, null, null);
	}

	public ImageButton(String filename, String text, String tooltip, Layout mode, boolean showBorder)
	{
		super();
		initialize(filename, text, tooltip, mode, showBorder, defaultSize, null, null);
	}

	public ImageButton(String filename, String text, String tooltip, Layout mode, boolean showBorder, IconSize size)
	{
		super();
		initialize(filename, text, tooltip, mode, showBorder, size, null, null);
	}
	
	public ImageButton(String filename, String text, String tooltip, Layout mode, boolean showBorder, IconSize size, Insets insets, Border border)
	{
		super();
		initialize(filename, text, tooltip, mode, showBorder, size, insets, border);
	}

	

	

	public ImageButton(StockIcon stock, String text)
	{
		super();
		initialize(stock.toIconName(), text, null, defaultLayout, defaultBorder, defaultSize, null, null);
	}

	public ImageButton(StockIcon stock, String text, boolean showBorder)
	{
		super();
		initialize(stock.toIconName(), text, null, defaultLayout, showBorder, defaultSize, null, null);
	}
	
	public ImageButton(StockIcon stock, String text, IconSize size)
	{
		super();
		initialize(stock.toIconName(), text, null, defaultLayout, defaultBorder, size, null, null);
	}
	
	
	public ImageButton(StockIcon stock, String text, Layout mode)
	{
		super();
		initialize(stock.toIconName(), text, null, mode, defaultBorder, defaultSize, null, null);
	}

	public ImageButton(StockIcon stock, String text, String tooltip)
	{
		super();
		initialize(stock.toIconName(), text, tooltip, defaultLayout, defaultBorder, defaultSize, null, null);
	}
	
	public ImageButton(StockIcon stock, String text, String tooltip, boolean showBorder)
	{
		super();
		initialize(stock.toIconName(), text, tooltip, defaultLayout, showBorder, defaultSize, null, null);
	}
	
	public ImageButton(StockIcon stock, String text, Layout mode, IconSize size)
	{
		super();
		initialize(stock.toIconName(), text, null, mode, defaultBorder, size, null, null);
	}
	
	public ImageButton(StockIcon stock, String text, String tooltip, Layout mode)
	{
		super();
		initialize(stock.toIconName(), text, tooltip, mode, defaultBorder, defaultSize, null, null);
	}

	public ImageButton(StockIcon stock, String text, String tooltip, Layout mode, IconSize size)
	{
		super();
		initialize(stock.toIconName(), text, tooltip, mode, defaultBorder, size, null, null);
	}

	public ImageButton(StockIcon stock, String text, String tooltip, IconSize size)
	{
		super();
		initialize(stock.toIconName(), text, tooltip, defaultLayout, defaultBorder, size, null, null);
	}

	public ImageButton(StockIcon stock, String text, Layout mode, boolean showBorder)
	{
		super();
		initialize(stock.toIconName(), text, null, mode, showBorder, defaultSize, null, null);
	}

	public ImageButton(StockIcon stock, String text, String tooltip, Layout mode, boolean showBorder)
	{
		super();
		initialize(stock.toIconName(), text, tooltip, mode, showBorder, defaultSize, null, null);
	}

	public ImageButton(StockIcon stock, String text, String tooltip, Layout mode, boolean showBorder, IconSize size)
	{
		super();
		initialize(stock.toIconName(), text, tooltip, mode, showBorder, size, null, null);
	}
	
	public ImageButton(StockIcon stock, String text, String tooltip, Layout mode, boolean showBorder, IconSize size, Insets insets, Border border)
	{
		super();
		initialize(stock.toIconName(), text, tooltip, mode, showBorder, size, insets, border);
	}

	
	




	public void initialize(String filename, String text, String tooltip, Layout mode, boolean _showBorder, IconSize size, Insets insets, Border border)
	{
		
	
		isNimbus = Swidget.isNimbus();
		
		
		
		this.showBorder = _showBorder;		
		this.setContentAreaFilled(showBorder);
		this.setBorderPainted(showBorder);
		
		/* SET BUTTON CONTENT */
		ImageIcon image = IconFactory.getImageIcon(filename, size);

		if (image.getIconHeight() == -1) {
			mode = Layout.TEXT;
		} else if (text == null || "".equals(text)) {
			mode = Layout.IMAGE;
		}

	
		this.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e)
			{
				setButtonBorder();
			}


			public void mousePressed(MouseEvent e)
			{
				setButtonBorder();
			}


			public void mouseExited(MouseEvent e)
			{
				setButtonBorder();
			}


			public void mouseEntered(MouseEvent e)
			{
				setButtonBorder();
			}


			public void mouseClicked(MouseEvent e)
			{
				setButtonBorder();
			}
		});
		
		this.addFocusListener(new FocusListener() {
		
			public void focusLost(FocusEvent arg0)
			{
				setButtonBorder();
			}
		
		
			public void focusGained(FocusEvent arg0)
			{
				setButtonBorder();
			}
		});

		
		this.addKeyListener(new KeyListener() {
		
			public void keyTyped(KeyEvent arg0)
			{
				setButtonBorder();
			}
		
		
			public void keyReleased(KeyEvent arg0)
			{
				setButtonBorder();
			}
		
		
			public void keyPressed(KeyEvent arg0)
			{
				setButtonBorder();
			}
		});

		
		switch (mode) {

			case IMAGE:

				if (isNimbus) { 
					super.setBorder(Spacing.bMedium());
				} else {
					this.setMargin(Spacing.iSmall());
				}
				
				this.setIcon(image);
				if (tooltip == null || "".equals(tooltip)) {
					tooltip = text;
				}

				break;

			case TEXT:

				if (isNimbus) { 
					super.setBorder(Spacing.bLarge());
				} else {
					this.setMargin(Spacing.iSmall());
				}
				
				this.setText(text);
				break;

			case IMAGE_ON_TOP:

				if (isNimbus) { 
					super.setBorder(Spacing.bLarge());
				} else {
					super.setMargin(Spacing.iSmall());
				}
				
				this.setIcon(image);
				this.setText(text);

				this.setVerticalTextPosition(SwingConstants.BOTTOM);
				this.setHorizontalTextPosition(SwingConstants.CENTER);

				break;

			case IMAGE_ON_SIDE:

				if (isNimbus) { 
					super.setBorder(Spacing.bLarge());
				} else {
					super.setMargin(Spacing.iSmall());
				}
				
				this.setIcon(image);
				this.setText(text);

				break;

		}
		
		//if (! isNumbus && insets != null) this.setMargin(insets);
		if (isNimbus) if (border != null) super.setBorder(border);
		
		
		//setMargin(new Insets(-1, -1, -1, -1));

		//setBorder(new CompoundBorder(getBorder(), new javax.swing.plaf.basic.BasicBorders.MarginBorder()));
				
		if (tooltip != null) this.setToolTipText(tooltip);

		
	}
	
	
	
	protected void setButtonBorder()
	{
		setButtonBorder(false);
	}
	
	protected void setButtonBorder(boolean forceBorder)
	{
		ButtonModel m = this.getModel();
		boolean showBackground = isEnabled() && (m.isSelected() || m.isRollover() || m.isPressed() || m.isArmed() || forceBorder || showBorder);

		if (showBackground || (isEnabled() && hasFocus()) ) {
			setBorderPainted(true);
			this.setContentAreaFilled(true);
		} else {
			setBorderPainted(false);
			this.setContentAreaFilled(false);
		}

		
		
		repaint();
	}
	

	
	public static String getWrappingTooltipText(Component c, String text)
	{
		int width = 400;
		FList<String> lines = new FList<String>();
		
		Font font = c.getFont();
		FontMetrics metrics = c.getFontMetrics(font);
				
		String line = "";
		Graphics g = c.getGraphics();
		
		FList<String> chars = new FList<String>(text.split(" "));
		
		
		lines.clear();
		while (chars.size() > 0)
		{
		
			while ( metrics.getStringBounds(line, g).getWidth() < width )
			{
				if (chars.size() == 0) break;
				if (line != "") line += " ";
				line = line + chars.head();
				chars = chars.tail();
			}
			
			lines.add(line);
			line = "";
			
		}
		
		return "<html>" + lines.foldl(Functions.strcat("<br>")) + "</html>";
	}
	
	@Override
	public void setToolTipText(String text)
	{
		super.setToolTipText(ImageButton.getWrappingTooltipText(this, text));
	}


}
