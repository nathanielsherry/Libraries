package swidget.widgets.tabbedinterface;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.LayerUI;

import org.jdesktop.swingx.border.DropShadowBorder;

import javafx.embed.swing.SwingFXUtils;

public class TabbedInterfacePanel extends JLayeredPane {

	private Stack<Component> modalComponents = new Stack<>();
	private JPanel modalLayer = new JPanel();
	private JScrollPane modalScroller;
	private JPanel contentLayer = new JPanel();
	
	boolean modalShown = false;
	
	
	
	public TabbedInterfacePanel() {
		setLayout(new TabbedInterfaceLayerLayout());
		
		modalLayer.addMouseListener(new MouseAdapter() {});
		modalLayer.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				e.consume();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				e.consume();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				e.consume();
			}
		});

		
		final JLayer<JPanel> layer = new JLayer<JPanel>(contentLayer, new BlurLayerUI<JPanel>(this) {
			@Override
			public void eventDispatched(AWTEvent e, JLayer<? extends JPanel> l) {
				((InputEvent) e).consume();
			}
			

			
		});
				
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				updateModalContentDimensions();
			}
		});
		
		add(layer, new StackConstraints(JLayeredPane.DEFAULT_LAYER, "content"));
		
		
	}
	
	private static final class StackConstraints {
		public final int layer;
		public final Object layoutConstraints;

		public StackConstraints(int layer, Object layoutConstraints) {
			this.layer = layer;
			this.layoutConstraints = layoutConstraints;
		}
	}

	protected void addImpl(Component comp, Object constraints, int index) {
		int layer = 0;
		int pos = 0;
		Object constr;
		if (constraints instanceof StackConstraints) {
			layer = ((StackConstraints) constraints).layer;
			constr = ((StackConstraints) constraints).layoutConstraints;
		} else {
			layer = getLayer(comp);
			constr = constraints;
		}
		pos = insertIndexForLayer(layer, index);
		super.addImpl(comp, constr, pos);
		setLayer(comp, layer, pos);
		comp.validate();
		comp.repaint();
	}
	
	//Shows the modal layer
	private void showModal() {
		modalShown = true;
		modalLayer.setOpaque(false);
		modalLayer.setBackground(new Color(0, 0, 0, 0f));

		contentLayer.setEnabled(false);
		add(modalLayer, new StackConstraints(JLayeredPane.MODAL_LAYER, "modal"));
		modalLayer.requestFocus();	
		this.repaint();
	}
	
	//Hides the modal layer
	private void hideModal() {
		modalComponents.clear();
		modalShown = false;
		this.remove(modalLayer);
		contentLayer.setEnabled(true);
		contentLayer.requestFocus();
	}
	
	/**
	 * Adds a modal component to the top of the modal stack. This allows more 
	 * than one modal dialog at a time.
	 */
	public void pushModalComponent(Component panel) {
		modalComponents.push(panel);
		updateModalComponent();
	}
	
	/**
	 * Removes the topmost modal component from the modal stack
	 */
	public void popModalComponent() {
		if (modalComponents.empty()) {
			return;
		}
		modalComponents.pop();
		updateModalComponent();
	}
	
	
	
	// Updates the displayed component from the stack
	private void updateModalComponent() {
		if (modalComponents.empty()) {
			hideModal();
		} else {
			if (!modalShown) {
				showModal();
			}
			setModalComponent(modalComponents.peek());
		}
	}
	
	private void setModalComponent(Component panel) {
		
		JPanel wrap = new JPanel(new BorderLayout());
		wrap.setOpaque(false);
		modalScroller = new JScrollPane();
		modalScroller.setViewportView(panel);
		modalScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		modalScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		modalScroller.setBorder(new EmptyBorder(0, 0, 0, 0));

		
		wrap.add(modalScroller, BorderLayout.CENTER);
		DropShadowBorder border = new DropShadowBorder(Color.BLACK, 10, 0.4f, 20, true, true, true, true);
		wrap.setBorder(border);
		
		modalLayer.removeAll();
		modalLayer.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 3;
		c.gridwidth = 3;
		c.weightx = 0f;
		c.weighty = 0f;
		c.gridx = 1;
		c.gridy = 1;
		
		modalLayer.add(wrap, c);
		
		updateModalContentDimensions();
		
		this.repaint();
	}
	
	private void updateModalContentDimensions() {
		if (modalScroller == null) { 
			return; 
		}
		Component modal = modalScroller.getViewport().getView();
		if (modal == null) {
			return;
		}
		Dimension size = getSize();
		int newWidth = (int)Math.max(50, Math.min(size.getWidth()-40, modal.getPreferredSize().getWidth()));
		int newHeight = (int)Math.max(50, Math.min(size.getHeight()-40, modal.getPreferredSize().getHeight()));
		
		modalScroller.getViewport().setPreferredSize(new Dimension(newWidth, newHeight));
		modalScroller.revalidate();
		
	}


	
	
	public JPanel getContentLayer() {
		return contentLayer;
	}
	
	

}

class BlurLayerUI<T extends Component> extends LayerUI<T> {
	private BufferedImage mOffscreenImage;
	private BufferedImageOp mOperation;

	private TabbedInterfacePanel parent;

	public BlurLayerUI(TabbedInterfacePanel parent) {
		this.parent = parent;
		float ninth = 1.0f / 9.0f;
		float[] blurKernel = { ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth };
		mOperation = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, null);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		if (parent.modalShown) {
			int w = c.getWidth();
			int h = c.getHeight();
	
			if (w == 0 || h == 0) {
				return;
			}
	
			// Only create the offscreen image if the one we have
			// is the wrong size.
			if (mOffscreenImage == null || mOffscreenImage.getWidth() != w || mOffscreenImage.getHeight() != h) {
				mOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			}
	
			Graphics2D ig2 = mOffscreenImage.createGraphics();
			ig2.setClip(g.getClip());
			super.paint(ig2, c);
			ig2.dispose();
	
			Graphics2D g2 = (Graphics2D) g.create();
			g2.drawImage(mOffscreenImage, mOperation, 0, 0);
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, c.getWidth(), c.getHeight());
			g2.dispose();
			
		} else {
			super.paint(g, c);
		}
	}
}

