package swidget.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager2;
import java.awt.LinearGradientPaint;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.widgets.ImageButton.Layout;
import swidget.widgets.gradientpanel.PaintedPanel;

public class HeaderBox extends PaintedPanel {

	
	public HeaderBox(Component left, String title, Component right) {
		super(true);
		JLabel label = new JLabel(title);
		Font font = label.getFont();
		font = font.deriveFont(Font.BOLD);
		font = font.deriveFont(font.getSize() + 1f);
		label.setFont(font);
		label.setHorizontalAlignment(JLabel.CENTER);
		
		init(left, label, right);
		
	}
	
	public HeaderBox(Component left, Component centre, Component right) {
		super(true);
		init(left, centre, right);
	}
	
	private void init(Component left, Component centre, Component right) {
		

		
		setBorder(Spacing.bMedium());
		setLayout(new BorderLayout());
		JPanel inner = new ClearPanel();
		add(inner, BorderLayout.CENTER);
				
		HeaderLayout layout = new HeaderLayout(left, centre, right);
		inner.setLayout(layout);
		if (left != null) inner.add(left);
		if (centre != null) inner.add(centre);
		if (right != null) inner.add(right);
		
		setBackgroundPaint(new LinearGradientPaint(0, 0, 0, this.getPreferredSize().height, new float[] {0, 1f}, new Color[] {
			new Color(0x88ffffff, true),
			new Color(0x00ffffff, true)
		}));
		
		Border b = Spacing.bMedium();
		b = new CompoundBorder(new MatteBorder(0, 0, 1, 0, new Color(0x20000000, true)), b);
		b = new CompoundBorder(new MatteBorder(0, 0, 1, 0, new Color(0x60000000, true)), b);
		setBorder(b);
		
	}

	
	private class HeaderLayout implements LayoutManager2 {

		Component left, centre, right;

		public HeaderLayout(Component left, Component centre, Component right) {
			this.left = left;
			this.centre = centre;
			this.right = right;
		}

		@Override
		public void addLayoutComponent(String name, Component comp) {
			// TODO Auto-generated method stub
		}

		@Override
		public void removeLayoutComponent(Component comp) {
			// TODO Auto-generated method stub
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			Dimension d = new Dimension();
			
			d.width += sideSize();
			d.width += centre.getPreferredSize().width;
			d.width += sideSize();
			
			d.height = left == null ? 0 : left.getPreferredSize().height;
			d.height = Math.max(d.height, centre == null ? 0 : centre.getPreferredSize().height);
			d.height = Math.max(d.height, right == null ? 0 : right.getPreferredSize().height);
			
			return d;
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			Dimension d = new Dimension();
			
			d.width += sideSize();
			d.width += sideSize();
			
			d.height = left == null ? 0 : left.getPreferredSize().height;
			d.height = Math.max(d.height, centre == null ? 0 : centre.getPreferredSize().height);
			d.height = Math.max(d.height, right == null ? 0 : right.getPreferredSize().height);
			
			return d;
		}

		@Override
		public void layoutContainer(Container parent) {
			Dimension leftSize = left == null ? new Dimension(0, 0) : new Dimension(left.getPreferredSize());
			Dimension rightSize = right == null ? new Dimension(0, 0) : new Dimension(right.getPreferredSize());
						
			if (left != null)   left.setBounds(0, 0, leftSize.width, leftSize.height);
			if (centre != null) centre.setBounds(sideSize(), 0, parent.getWidth() - sideSize() - sideSize(), parent.getHeight());
			if (right != null)  right.setBounds(parent.getWidth() - rightSize.width, 0, rightSize.width, rightSize.height);

		}

		@Override
		public void addLayoutComponent(Component comp, Object constraints) {
			// TODO Auto-generated method stub
		}

		@Override
		public Dimension maximumLayoutSize(Container target) {
			return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		}

		@Override
		public float getLayoutAlignmentX(Container target) {
			return 0;
		}

		@Override
		public float getLayoutAlignmentY(Container target) {
			return 0;
		}

		@Override
		public void invalidateLayout(Container target) {

		}
		
				
		private int sideSize() {
			int leftWidth = left == null ? 0 : left.getPreferredSize().width;
			int rightWidth = right == null ? 0 : right.getPreferredSize().width;
			return Math.max(leftWidth, rightWidth);
		}

	}

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		
		JFrame frame = new JFrame("test");
		frame.setPreferredSize(new Dimension(640, 480));
		frame.pack();
		frame.getContentPane().setLayout(new BorderLayout());
		
		HeaderBox box = new HeaderBox(null, "Title Text", new HButton("Right Side of the Window"));
		frame.add(box, BorderLayout.NORTH);
		
		frame.setVisible(true);
		
	}
	
}
