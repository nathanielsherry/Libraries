package swidget.widgets;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JViewport;

public class DraggingScrollPaneListener implements MouseMotionListener, MouseListener
{

	private Point			p0;
	private boolean			dragging;

	private JViewport		viewPort;
	private Point			scrollPosition;
	private JComponent		canvas;

	Cursor oldCursor;
	
	public boolean dragX = true, dragY = true;

	/**
	 * All you need to do is call this constructor.
	 * @param viewport
	 * @param canvas
	 */
	public DraggingScrollPaneListener(JViewport viewport, JComponent canvas) {
		viewPort = viewport;
		scrollPosition = viewPort.getViewPosition();
		this.canvas = canvas;
		
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
	}
	
	private Point getPoint(MouseEvent e)
	{
		Point p = e.getPoint();
		if (dragX) p.x += canvas.getLocationOnScreen().x;
		if (dragY) p.y += canvas.getLocationOnScreen().y;

		return p;
	}


	private void update(MouseEvent e)
	{

		if (p0 == null) return;
		
		Point p1 = getPoint(e);
		int dx = p1.x - p0.x;
		int dy = p1.y - p0.y;
		
		p0 = getPoint(e);
		
		
		
		if (dragX) {
			scrollPosition.x -= dx;
			//can't scroll to a position less than 0
			if (scrollPosition.x < 0) {
				scrollPosition.x = 0;
				
			//if the control isn't as large as the viewport, don't allow scrolling
			} else if (canvas.getWidth() <= viewPort.getWidth()) {
				scrollPosition.x = 0;
				
			//else if the scroll position is beyond the end of the control, cap it
			} else if (scrollPosition.x > canvas.getWidth() - viewPort.getWidth()) {
				scrollPosition.x = canvas.getWidth() - viewPort.getWidth();
			}
		}
		
		
		
		if (dragY) {
			scrollPosition.y -= dy;
			//can't scroll to a position less than 0
			if (scrollPosition.y < 0) {
				scrollPosition.y = 0;
				
			//if the control isn't as large as the viewport, don't allow scrolling
			} else if (canvas.getHeight() <= viewPort.getHeight()) {
				scrollPosition.y = 0;
				
			//if the scroll position is beyond the end of the control, cap it
			}else if (scrollPosition.y > canvas.getHeight() - viewPort.getHeight()) {
				scrollPosition.y = canvas.getHeight() - viewPort.getHeight();
			}
		}


		viewPort.setViewPosition(scrollPosition);

	}


	public void mouseDragged(MouseEvent e)
	{
		if (dragging)
		{
			update(e);
		}
	}


	public void mouseMoved(MouseEvent e)
	{
		if (dragging)
		{
			update(e);
		}
	}


	public void mouseClicked(MouseEvent e){}


	public void mouseEntered(MouseEvent e){}


	public void mouseExited(MouseEvent e){}


	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() != MouseEvent.BUTTON1) return;
		if (dragging) return;
		
		p0 = getPoint(e);
		scrollPosition = viewPort.getViewPosition();
		dragging = true;
		oldCursor = canvas.getCursor();
		canvas.setCursor(new Cursor(Cursor.MOVE_CURSOR));
	}


	public void mouseReleased(MouseEvent e)
	{
		
		if (e.getButton() != MouseEvent.BUTTON1) return;
		
		update(e);
		dragging = false;
		p0 = null;
		canvas.setCursor(oldCursor);
	}

}
