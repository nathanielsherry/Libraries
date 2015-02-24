package net.sciencestudio.autodialogfx.view.decor;

import javafx.scene.Node;
import javafx.scene.control.Separator;

public class SeparatorDecor extends AbstractDecor {

	Separator separator = new Separator(); 
	
	@Override
	public Node getNode() {
		return separator;
	}

}
