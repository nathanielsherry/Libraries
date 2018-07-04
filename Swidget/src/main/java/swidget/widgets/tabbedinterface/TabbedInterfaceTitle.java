/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package swidget.widgets.tabbedinterface;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Component to be used as tabComponent;
 * Contains a JLabel to show the text and 
 * a JButton to close the tab it belongs to 
 */
public class TabbedInterfaceTitle extends JPanel {
    
	private final TabbedInterface<?> owner;
	private JLabel label;
	
	public TabbedInterfaceTitle(String title, int width) {
		this(null, width, false);
		setTitle(title);
	}
	
	public TabbedInterfaceTitle(final TabbedInterface<?> owner, int width) {
		this(owner, width, true);
	}
	
    public TabbedInterfaceTitle(final TabbedInterface<?> owner, int width, boolean closeButton) {
        super(new BorderLayout());
        if (owner == null && closeButton == true) {
            throw new NullPointerException("Owner cannot be null if close buttons are to be used");
        }
        this.owner = owner;
        setOpaque(false);
         
        //make JLabel read titles from JTabbedPane
        label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);

        add(label, BorderLayout.CENTER);
        
        if (closeButton) {
	        //add more space between the label and the button
	        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
	        //tab button
	        JButton button = new TabButton();
	        add(button, BorderLayout.EAST);
        }
        
        setMinimumSize(new Dimension(width, (int)getMinimumSize().getHeight()));
        setPreferredSize(new Dimension(width, (int)getPreferredSize().getHeight()));
        
    }
    
    protected void setTitle(String title)
    {
    	label.setText(title);
    }
    
    protected String getTitle()
    {
    	return label.getText();
    }
 
    private class TabButton extends JButton implements ActionListener {
    	
    	int size = 16;
    	
        public TabButton() {
            
        	super();
        	
        	

            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            //setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
        	
            setPreferredSize(new Dimension(size, size));
        	setToolTipText("Close Tab");
        	

            //Making nice rollover effect
            //we use the same listener for all buttons
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
            
        }
 
        public void actionPerformed(ActionEvent e) {
        	
            int i = owner.getJTabbedPane().indexOfTabComponent(TabbedInterfaceTitle.this);
            owner.closeTab(i);
        }
 
        //we don't want to update UI for this button
        public void updateUI() {}
 
        //paint the cross
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
            	g2.setColor(new Color(0.64f, 0f, 0f));
            	g2.fillOval(1, 1, size - 2, size - 2);
            	g2.setColor(Color.WHITE);
                
            }
            
            int delta = 5;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
        
    }
 

}