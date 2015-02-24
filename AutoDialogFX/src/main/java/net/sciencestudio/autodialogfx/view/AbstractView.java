package net.sciencestudio.autodialogfx.view;


public abstract class AbstractView implements View{

	private ViewProperties properties = new ViewProperties();
	
	@Override
	public ViewProperties getProperties() {
		return properties;
	}

	
}
