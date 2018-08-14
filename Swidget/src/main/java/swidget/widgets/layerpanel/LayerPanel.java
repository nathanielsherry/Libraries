package swidget.widgets.layerpanel;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.InputEvent;
import java.util.Stack;

import javax.swing.JLayer;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class LayerPanel extends JLayeredPane {

	private Stack<Layer> layers = new Stack<>();
	private Layer contentLayer;
	
	
	
	
	public LayerPanel() {
		setLayout(new LayerLayout());	
		contentLayer = new ContentLayer(this);
		add(contentLayer.getJLayer(), new StackConstraints(JLayeredPane.DEFAULT_LAYER, "content"));
				
	}
	
	

	Layer layerForComponent(Component component) {
		for (Layer layer : layers) {
			if (layer.getComponent() == component) {
				return layer;
			}
		}
		return null;
	}
	
	boolean isLayerBlocked(Layer layer) {
		boolean blocked = false;
		if (layers.isEmpty()) {
			return blocked;
		}
		for (int i = layers.size()-1; i >= 0; i--) {
			Layer li = layers.get(i);
			if (layer == li) {
				return blocked;
			}
			if (li.modal()) {
				blocked = true;
			}
		}
		return blocked;
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
	public void pushLayer(Layer layer) {
		layers.push(layer);
		
		this.add(layer.getJLayer(), new StackConstraints(layers.size()+200, "modal-layer-" + layers.size()));
				
		layer.getJLayer().requestFocus();
		this.revalidate();
		this.repaint();
		
	}
	
	/**
	 * Removes the topmost modal component from the modal stack
	 */
	public void popLayer() {
		if (layers.empty()) {
			return;
		}
		removeLayer(layers.peek());
	}

	public void removeLayer(Layer layer) {
		if (!layers.contains(layer)) {
			return;
		}
		
		layers.remove(layer);
		this.remove(layer.getJLayer());
		layer.discard();
		this.repaint();

	}
	
	
	public JPanel getContentLayer() {
		return contentLayer.getComponent();
	}
	
	

}


