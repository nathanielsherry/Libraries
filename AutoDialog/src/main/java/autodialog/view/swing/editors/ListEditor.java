package autodialog.view.swing.editors;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import autodialog.model.Parameter;
import autodialog.model.SelectionParameter;
import eventful.Eventful;


public class ListEditor<T> extends Eventful implements ISwingEditor<T>
{

	private SelectionParameter<T> param;
	private JComboBox<T> control;
	

	public ListEditor() {
		control = new JComboBox<>();
	}
	
	
	@Override
	public void initialize(Parameter<T> p)
	{
		this.param = (SelectionParameter<T>) p;
		
		
		
		for (T t : param.getPossibleValues()) control.addItem(t);
		
		control.setSelectedItem(param.getValue());
		control.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		control.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateListeners();
			}
		});
	}

	@Override
	public boolean expandVertical()
	{
		return false;
	}

	@Override
	public boolean expandHorizontal()
	{
		return false;
	}

	@Override
	public LabelStyle getLabelStyle()
	{
		return LabelStyle.LABEL_ON_SIDE;
	}

	@Override
	public JComponent getComponent()
	{
		return control;
	}

	@Override
	public void setFromParameter()
	{
		control.setSelectedItem(param.getValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getEditorValue()
	{
		return (T)control.getSelectedItem();
	}
	

	@Override
	public void validateFailed() {
		setFromParameter();
	}
	
	@Override
	public Parameter<T> getParameter() {
		return param;
	}
	
}
