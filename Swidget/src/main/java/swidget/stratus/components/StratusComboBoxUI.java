package swidget.stratus.components;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.synth.SynthComboBoxUI;

import swidget.stratus.Stratus;



public class StratusComboBoxUI extends SynthComboBoxUI {

    @Override
    protected ComboPopup createPopup() {
    	BasicComboPopup p = (BasicComboPopup) super.createPopup();
    	p.setBorder(new LineBorder(Stratus.border, 1));
    	p.setLightWeightPopupEnabled(false);
        return p;
    }
    
    public static ComponentUI createUI(JComponent c) {
        return new StratusComboBoxUI();
    }
	
}

