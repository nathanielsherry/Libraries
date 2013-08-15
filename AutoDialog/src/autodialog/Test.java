package autodialog;


import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.JSlider;

import swidget.Swidget;
import swidget.dialogues.fileio.SimpleFileFilter;
import autodialog.controller.SimpleADController;
import autodialog.model.Parameter;
import autodialog.view.AutoDialog;
import autodialog.view.AutoDialog.AutoDialogButtons;
import autodialog.view.editors.BooleanEditor;
import autodialog.view.editors.DoubleEditor;
import autodialog.view.editors.DummyEditor;
import autodialog.view.editors.FilenameEditor;
import autodialog.view.editors.IEditor.LabelStyle;
import autodialog.view.editors.IntegerEditor;
import autodialog.view.editors.ListEditor;
import autodialog.view.editors.SliderEditor;
import autodialog.view.editors.TextAreaEditor;
import autodialog.view.layouts.FramesADLayout;
import autodialog.view.layouts.SimpleADLayout;
import autodialog.view.layouts.TabbedADLayout;
import fava.functionable.FList;

public class Test {

	public static void main(String[] args)
	{
		
		Swidget.initialize();
		
		final FList<Parameter<?>> params = new FList<>();
		
		String g1 = "First Set";
		String g2 = "Second Set";
		
		params.add(new Parameter<>("Boolean", new BooleanEditor(), Boolean.TRUE, g1));
		params.add(new Parameter<>("Integer", new IntegerEditor(), 0));
		params.add(new Parameter<>("Real", new DoubleEditor(), 0d, g1));
		params.add(new Parameter<>("Dummy Separator", new DummyEditor(new JSeparator()), null));
		params.add(new Parameter<>("List", new ListEditor<>(LabelStyle.values()), LabelStyle.LABEL_HIDDEN, g2));
		
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(new SimpleFileFilter(new String[]{"txt", "dat"}, "Some File Extensions"));
		params.add(new Parameter<>("Filename", new FilenameEditor(chooser), null));
		
		params.add(new Parameter<>("Slider", new SliderEditor(new JSlider(1, 10)), 1, g2));
		params.add(new Parameter<String>("TextArea", new TextAreaEditor(), "", g2));
		
		AutoDialog d;
		d = new AutoDialog(new SimpleADController(params), AutoDialogButtons.OK_CANCEL);
		d.setModal(true);
		d.initialize(new SimpleADLayout());
		
		d = new AutoDialog(new SimpleADController(params), AutoDialogButtons.OK_CANCEL);
		d.setModal(true);
		d.initialize(new FramesADLayout());
		
		d = new AutoDialog(new SimpleADController(params), AutoDialogButtons.OK_CANCEL);
		d.setModal(true);
		d.initialize(new TabbedADLayout());
		
		for (Parameter<?> param : params) System.out.println(param.getValue());
	}
	
}
