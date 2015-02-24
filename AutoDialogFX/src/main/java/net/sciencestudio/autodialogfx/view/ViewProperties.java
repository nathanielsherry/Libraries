package net.sciencestudio.autodialogfx.view;

public class ViewProperties {

	public enum LabelStyle {
		LABEL_ON_TOP,
		LABEL_ON_SIDE,
		LABEL_HIDDEN
	}
	
	public LabelStyle labelStyle = LabelStyle.LABEL_ON_SIDE;
	
	public boolean expandVertical = false;
	public boolean expandHorizontal = false;
	
}
