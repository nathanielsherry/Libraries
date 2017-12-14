package autodialog.view.swing.editors;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import autodialog.model.Parameter;

public class SliderEditor extends WrappingEditor<Integer, JSlider> {

	public SliderEditor() {
		this(new JSlider());
	}
	
	public SliderEditor(JSlider component) {
		super(component);
	}

	private Parameter<Integer> param;
	
	@Override
	public void setFromParameter() {
		getComponent().setValue(param.getValue());
	}
	
	@Override
	public void initialize(Parameter<Integer> param) {
		
		this.param = param;

		setFromParameter();
		param.getValueHook().addListener(v -> this.setFromParameter());
		
		getComponent().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				getValueHook().updateListeners(getEditorValue());
			}
		});				
	}
	
	@Override
	public Integer getEditorValue() {
		return getComponent().getValue();
	}
	
	@Override
	public Parameter<Integer> getParameter() {
		return param;
	}
}
