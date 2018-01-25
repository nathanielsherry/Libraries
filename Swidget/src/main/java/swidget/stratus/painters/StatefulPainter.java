package swidget.stratus.painters;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus.ButtonState;

public abstract class StatefulPainter implements Painter<JComponent> {


    protected ArrayList<ButtonState> states;

    public StatefulPainter(ButtonState... buttonStates) {
    	this.states = new ArrayList<>(Arrays.asList(buttonStates));
    }
	
    protected boolean isSelected() {
    	return states.contains(ButtonState.SELECTED);
    }
    
    protected boolean isPressed() {
    	return states.contains(ButtonState.PRESSED);
    }
    
    protected boolean isDisabled() {
    	return states.contains(ButtonState.DISABLED);
    }
    
    protected boolean isEnabled() {
    	return states.contains(ButtonState.ENABLED);
    }
    
    protected boolean isMouseOver() {
    	return states.contains(ButtonState.MOUSEOVER);
    }

    protected boolean isFocused() {
    	return states.contains(ButtonState.FOCUSED);
    }
    
    protected boolean isDefault() {
    	return states.contains(ButtonState.DEFAULT);
    }

	
}
