package swidget.stratus.painters;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.Painter;

import swidget.stratus.Stratus.ButtonState;

public abstract class StatefulPainter implements Painter<JComponent> {


    protected ArrayList<ButtonState> states;
    private boolean selected, pressed, disabled, enabled, mouseover, focused, def;

    public StatefulPainter(ButtonState... buttonStates) {
    	this.states = new ArrayList<>(Arrays.asList(buttonStates));
    	
    	selected = states.contains(ButtonState.SELECTED);
    	pressed = states.contains(ButtonState.PRESSED);
    	disabled = states.contains(ButtonState.DISABLED);
    	enabled = states.contains(ButtonState.ENABLED);
    	mouseover = states.contains(ButtonState.MOUSEOVER);
    	focused = states.contains(ButtonState.FOCUSED);
    	def = states.contains(ButtonState.DEFAULT);
    }
	
    protected boolean isSelected() {
    	return selected;
    }
    
    protected boolean isPressed() {
    	return pressed;
    }
    
    protected boolean isDisabled() {
    	return disabled;
    }
    
    protected boolean isEnabled() {
    	return enabled;
    }
    
    protected boolean isMouseOver() {
    	return mouseover;
    }

    protected boolean isFocused() {
    	return focused;
    }
    
    protected boolean isDefault() {
    	return def;
    }

	
}
