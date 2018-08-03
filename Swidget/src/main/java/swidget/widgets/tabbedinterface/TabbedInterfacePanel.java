package swidget.widgets.tabbedinterface;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.LayerUI;

import org.jdesktop.swingx.border.DropShadowBorder;

import swidget.widgets.ClearPanel;


public class TabbedInterfacePanel extends JLayeredPane {

	private Stack<ModalPane> modalComponents = new Stack<>();
	
	private JPanel contentLayerPanel = new JPanel();
	private final JLayer<JPanel> contentLayer;
	
	
	public TabbedInterfacePanel() {
		setLayout(new TabbedInterfaceLayerLayout());
		
		//set blur in case another layer is placed on top of this one
		BlurLayerUI<JPanel> blurUI = new BlurLayerUI<JPanel>(this, contentLayerPanel) {
			@Override
			public void eventDispatched(AWTEvent e, JLayer<? extends JPanel> l) {
				((InputEvent) e).consume();
			}
		};
		contentLayer = new JLayer<JPanel>(contentLayerPanel, blurUI);
		add(contentLayer, new StackConstraints(JLayeredPane.DEFAULT_LAYER, "content"));
				
	}
	
	

	
	boolean isComponentOnTop(Component component) {
		boolean result = false;
		if (component == contentLayerPanel) {
			result = modalComponents.size() == 0;
		} else if (modalComponents.size() == 0) {
			return false;
		} else {
			result = modalComponents.peek().getComponent().equals(component);
		}
		return result;
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

	/**
	 * This exists to work around a bug where nothing seems to be able to trigger 
	 * a paint when the window is minimized, to the point where even after 
	 * unminimizing it still won't redraw, only redrawing child components on 
	 * mouseover
	 */
	private void updateIfMinimized() {
		Component croot = SwingUtilities.getRoot(this);
		if (croot instanceof Frame) {
			int state = ((Frame) croot).getState();
			if (state == Frame.ICONIFIED) {
				this.update(this.getGraphics());
			}
		}
	}

	
	/**
	 * Adds a modal component to the top of the modal stack. This allows more 
	 * than one modal dialog at a time.
	 */
	public void pushModalComponent(Component panel) {
		ModalPane modalPane = new ModalPane(this, panel);
		modalComponents.push(modalPane);
		
		this.add(modalPane.getLayer(), new StackConstraints(modalComponents.size()+200, "modal-layer-" + modalComponents.size()));
				
		modalPane.getLayer().requestFocus();
		this.revalidate();
		this.repaint();
		
	}
	
	/**
	 * Removes the topmost modal component from the modal stack
	 */
	public void popModalComponent() {
		if (modalComponents.empty()) {
			return;
		}
		ModalPane modalPane = modalComponents.pop();
		this.remove(modalPane.getLayer());
		modalPane.discard();
		this.repaint();
		
	}

	
	public JPanel getContentLayer() {
		return contentLayerPanel;
	}
	
	

}

class BlurLayerUI<T extends Component> extends LayerUI<T> {
	private BufferedImage mOffscreenImage;
	private BufferedImageOp mOperation;

	private TabbedInterfacePanel parent;
	private Component component;
	
	public BlurLayerUI(TabbedInterfacePanel parent, Component component) {
		this.parent = parent;
		this.component = component;
		float ninth = 1.0f / 9.0f;
		float[] blurKernel = { ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth };
		mOperation = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, null);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		if (parent.isComponentOnTop(this.component)) {
			super.paint(g, c);
			
		} else {
			int w = c.getWidth();
			int h = c.getHeight();
	
			if (w == 0 || h == 0) {
				return;
			}
	
			// Only create the offscreen image if the one we have
			// is the wrong size.
			if (mOffscreenImage == null || mOffscreenImage.getWidth() != w || mOffscreenImage.getHeight() != h) {
				mOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
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
			
		}
	}
}

class ModalPane {
	private JLayer<JPanel> layer;
	private Component component;
	private TabbedInterfacePanel owner;
	
	private ComponentAdapter listener;
	
	public ModalPane(TabbedInterfacePanel owner, Component component) {
		this.owner = owner;
		this.component = component;
		this.layer = makeModalLayer();
	}
	

	public JLayer<JPanel> getLayer() {
		return layer;
	}


	public Component getComponent() {
		return component;
	}

	//clean up after we're done with this modal layer.
	public void discard() {
		if (listener == null) {
			return;
		}
		owner.removeComponentListener(listener);
	}

	private JLayer<JPanel> makeModalLayer() {
		
		JPanel modalPanel = new ClearPanel();
		
		modalPanel.addMouseListener(new MouseAdapter() {});
		modalPanel.addKeyListener(new KeyListener() {
			
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
		
		setModalPanelComponent(modalPanel, this.component);
		
		//set blur in case another layer is placed on top of this one
		BlurLayerUI<JPanel> blurUI = new BlurLayerUI<JPanel>(this.owner, this.component) {
			@Override
			public void eventDispatched(AWTEvent e, JLayer<? extends JPanel> l) {
				((InputEvent) e).consume();
			}
		};
		JLayer<JPanel> layer = new JLayer<JPanel>(modalPanel, blurUI);
		
		layer.setVisible(true);
		layer.setOpaque(false);
		layer.setBackground(new Color(0, 0, 0, 0f));
		
		return layer;
				
		
	}
	

	
	private void setModalPanelComponent(JPanel modalPanel, Component component) {
		
		JPanel wrap = new JPanel(new BorderLayout());
		wrap.setOpaque(false);
		JScrollPane modalScroller = new JScrollPane();
		modalScroller.setViewportView(component);
		modalScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		modalScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		modalScroller.setBorder(new EmptyBorder(0, 0, 0, 0));

		
		wrap.add(modalScroller, BorderLayout.CENTER);
		DropShadowBorder border = new DropShadowBorder(Color.BLACK, 12, 0.3f, 20, true, true, true, true);
		wrap.setBorder(border);
		
		modalPanel.removeAll();
		modalPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 3;
		c.gridwidth = 3;
		c.weightx = 0f;
		c.weighty = 0f;
		c.gridx = 1;
		c.gridy = 1;
		
		modalPanel.add(wrap, c);
		updateModalContentDimensions(modalScroller);
		listener = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				updateModalContentDimensions(modalScroller);
			}
		};
		owner.addComponentListener(listener);

		
	}
	
	private void updateModalContentDimensions(JScrollPane modalScroller) {
		if (modalScroller == null) { 
			return; 
		}
		Component modal = modalScroller.getViewport().getView();
		if (modal == null) {
			return;
		}
		Dimension ownerSize = owner.getSize();
		int newWidth = (int)Math.max(50, Math.min(ownerSize.getWidth()-40, modal.getPreferredSize().getWidth()));
		int newHeight = (int)Math.max(50, Math.min(ownerSize.getHeight()-40, modal.getPreferredSize().getHeight()));
		
		modalScroller.getViewport().setPreferredSize(new Dimension(newWidth, newHeight));
		modalScroller.revalidate();
		
	}

	
}

