package swidget.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import eventful.swing.EventfulPanel;
import swidget.icons.StockIcon;
import swidget.widgets.ImageButton.Layout;


public class ZoomSlider extends EventfulPanel
{

	private JSlider zoomSlider;
	private ImageButton in, out;
	private ChangeListener zoomSliderListener;

	
	public ZoomSlider(int start, int end, final int step)
	{

		setLayout(new BorderLayout());

		out = new ImageButton().withIcon(StockIcon.ZOOM_OUT).withTooltip("Zoom Out").withLayout(Layout.IMAGE).withBordered(false);
		in = new ImageButton().withIcon(StockIcon.ZOOM_IN).withTooltip("Zoom In").withLayout(Layout.IMAGE).withBordered(false);


		zoomSlider = new JSlider(start, end);
		zoomSlider.setPaintLabels(false);
		zoomSlider.setPaintTicks(false);
		zoomSlider.setValue(start);
		Dimension prefSize = zoomSlider.getPreferredSize();
		prefSize.width /= 2;
		zoomSlider.setPreferredSize(prefSize);

		zoomSliderListener = new ChangeListener() {

			public void stateChanged(ChangeEvent e)
			{
				updateListeners();
			}
		};
		
		zoomSlider.addChangeListener(zoomSliderListener);
		out.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				zoomSlider.setValue(zoomSlider.getValue() - step);
			}
		});
		in.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				zoomSlider.setValue(zoomSlider.getValue() + step);
			}
		});

		add(out, BorderLayout.WEST);
		add(zoomSlider, BorderLayout.CENTER);
		add(in, BorderLayout.EAST);

	}

	public void setValue(int value)
	{
		zoomSlider.setValue(value);
	}
	
	public void setValueEventless(int value)
	{
		zoomSlider.removeChangeListener(zoomSliderListener);
		zoomSlider.setValue(value);
		zoomSlider.addChangeListener(zoomSliderListener);
	}
	
	public int getValue()
	{
		return zoomSlider.getValue();
	}
	
	@Override
	public void setEnabled(boolean enabled)
	{
		zoomSlider.setEnabled(enabled);
		in.setEnabled(enabled);
		out.setEnabled(enabled);
	}
	
}
