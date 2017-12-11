package autodialog;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.JSlider;

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
import autodialog.view.layouts.ADLayoutFactory;
import autodialog.view.layouts.FramesADLayout;
import autodialog.view.layouts.IADLayout;
import autodialog.view.layouts.SimpleADLayout;
import autodialog.view.layouts.TabbedADLayout;
import swidget.Swidget;
import swidget.dialogues.fileio.SimpleFileFilter;

public class Test {

	public static void main(String[] args)
	{
		
		Swidget.initialize();
		
		final List<Parameter<?>> params = new ArrayList<>();
		

		final String g1 = "First Set";
		final String g2 = "Second Set";
		final String s1 = "First Subset";
		final String s2 = "Second Subset";
		
		params.add(new Parameter<>("Boolean", new BooleanEditor(), Boolean.TRUE, g1, s1));
		params.add(new Parameter<>("Boolean #2", new BooleanEditor(), Boolean.TRUE, g1, s1));
		params.add(new Parameter<>("Integer", new IntegerEditor(), 0, g1, s1));
		params.add(new Parameter<>("Integer #2", new IntegerEditor(), 0, g1, s2));
		params.add(new Parameter<>("Real", new DoubleEditor(), 0d, g1, s2));
		params.add(new Parameter<>("Dummy Separator", new DummyEditor(new JSeparator()), null, g2));
		params.add(new Parameter<>("List", new ListEditor<>(LabelStyle.values()), LabelStyle.LABEL_HIDDEN, g2));
		
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(new SimpleFileFilter(new String[]{"txt", "dat"}, "Some File Extensions"));
		params.add(new Parameter<>("Filename", new FilenameEditor(chooser), null, g2));
		
		params.add(new Parameter<>("Slider", new SliderEditor(new JSlider(1, 10)), 1, g2));
		params.add(new Parameter<String>("TextArea", new TextAreaEditor(), "", g2));
		
		AutoDialog d;
		d = new AutoDialog(new SimpleADController(params), AutoDialogButtons.OK_CANCEL);
		d.setModal(true);
		d.initialize(new SimpleADLayout());
		
		d = new AutoDialog(new SimpleADController(params), AutoDialogButtons.OK_CANCEL);
		d.setModal(true);
		d.initialize(new FramesADLayout(new ADLayoutFactory() {
			
			@Override
			public IADLayout getLayout(List<Parameter<?>> params, int level, String group) {
				return new FramesADLayout(this);
			}
		}));
		
		d = new AutoDialog(new SimpleADController(params), AutoDialogButtons.OK_CANCEL);
		d.setModal(true);
		d.initialize(new TabbedADLayout(new ADLayoutFactory() {
			
			@Override
			public IADLayout getLayout(List<Parameter<?>> params, int level, String group) {
				System.out.println(group);
				System.out.println(level);
				System.out.println("---");
				//if (g1.equals(group)) return new TabbedADLayout();
				return new FramesADLayout(this);
			}
		}));
		
		for (Parameter<?> param : params) System.out.println(param.getValue());
	}
	
	
	
	
}
