package autodialog;


import javax.swing.JSeparator;
import javax.swing.JSlider;

import swidget.Swidget;
import fava.functionable.FList;
import autodialog.controller.SimpleAutoDialogController;
import autodialog.model.Parameter;
import autodialog.view.AutoDialog;
import autodialog.view.AutoDialog.AutoDialogButtons;
import autodialog.view.editors.BooleanEditor;
import autodialog.view.editors.DummyEditor;
import autodialog.view.editors.FilenameEditor;
import autodialog.view.editors.IEditor.LabelStyle;
import autodialog.view.editors.IntegerEditor;
import autodialog.view.editors.ListEditor;
import autodialog.view.editors.DoubleEditor;
import autodialog.view.editors.SliderEditor;

public class Test {

	public static void main(String[] args)
	{
		
		Swidget.initialize();
		
		final FList<Parameter<?>> params = new FList<>();
		
		params.add(new Parameter<>("Boolean", new BooleanEditor(), Boolean.TRUE));
		params.add(new Parameter<>("Integer", new IntegerEditor(), 0));
		params.add(new Parameter<>("Real", new DoubleEditor(), 0d));
		params.add(new Parameter<>("Dummy Separator", new DummyEditor(new JSeparator()), null));
		params.add(new Parameter<>("List", new ListEditor<>(LabelStyle.values()), LabelStyle.LABEL_HIDDEN));
		params.add(new Parameter<>("Filename", new FilenameEditor(), null));
		params.add(new Parameter<>("Slider", new SliderEditor(new JSlider(1, 10)), 1));
		
		AutoDialog d = new AutoDialog(new SimpleAutoDialogController(params), AutoDialogButtons.OK_CANCEL);
		
		d.setModal(true);
		d.initialize();
		
		for (Parameter<?> param : params) System.out.println(param.getValue());
	}
	
}
