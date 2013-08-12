package autodialog.view.editors;

import javax.swing.JComponent;

import eventful.Eventful;


public abstract class WrappingEditor<T, S extends JComponent> extends Eventful implements IEditor<T> {

	protected S component;
	
	public WrappingEditor(S component) {
		this.component = component;
	}
	

	@Override
	public boolean expandVertical() {
		return false;
	}

	@Override
	public boolean expandHorizontal() {
		return false;
	}

	@Override
	public LabelStyle getLabelStyle() {
		return LabelStyle.LABEL_ON_SIDE;
	}


	@Override
	public void validateFailed() {
		setFromParameter();
	}


	@Override
	public S getComponent() {
		return component;
	}

}
