package autodialog.view.editors;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import autodialog.model.Parameter;
import eventful.Eventful;
import fava.functionable.FList;


public class ListEditor<T> extends Eventful implements IEditor<T>
{

	private Parameter<T> param;
	private List<T> possibleValues;
	private JComboBox<T> control;
	


	public ListEditor(T[] possibleValues) {
		this(new FList<>(possibleValues));
	}
	
	public ListEditor(List<T> possibleValues) {
		this.possibleValues = possibleValues;
		control = new JComboBox<>();
	}
	
	
	@Override
	public void initialize(Parameter<T> param)
	{
		this.param = param;
		
		
		for (T t : possibleValues) control.addItem(t);
		
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
	
}
