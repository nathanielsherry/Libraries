package autodialog.view.swing.editors;


import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import autodialog.model.Parameter;
import autodialog.view.editors.Editor.LabelStyle;

public class TextAreaEditor extends WrappingEditor<String, JTextArea> {

	private Parameter<String> param;
	
	public TextAreaEditor() {
		this(new JTextArea());
		component.setRows(5);
		component.setColumns(20);
	}
	
	public TextAreaEditor(JTextArea textarea) {
		super(textarea, true, true, LabelStyle.LABEL_ON_TOP);
	}

	@Override
	public void initialize(Parameter<String> param) {
		this.param = param;

		setFromParameter();
		param.getValueHook().addListener(v -> this.setFromParameter());
		
		component.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				getValueHook().updateListeners(getEditorValue());
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				getValueHook().updateListeners(getEditorValue());
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				getValueHook().updateListeners(getEditorValue());
			}
		});
	}

	@Override
	public void setFromParameter() {
		component.setText(param.getValue());
	}

	@Override
	public String getEditorValue() {
		return component.getText();
	}
	
	@Override
	public Parameter<String> getParameter() {
		return param;
	}



}
