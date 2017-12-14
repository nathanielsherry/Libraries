package autodialog;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.JSlider;

import autodialog.controller.SimpleADController;
import autodialog.model.Parameter;
import autodialog.model.SelectionParameter;
import autodialog.model.style.styles.CheckBoxStyle;
import autodialog.model.style.styles.FileNameStyle;
import autodialog.model.style.styles.IntegerSliderStyle;
import autodialog.model.style.styles.IntegerSpinnerStyle;
import autodialog.model.style.styles.ListStyle;
import autodialog.model.style.styles.RealSliderStyle;
import autodialog.model.style.styles.SeparatorStyle;
import autodialog.model.style.styles.TextAreaStyle;
import autodialog.view.editors.IEditor;
import autodialog.view.editors.IEditor.LabelStyle;
import autodialog.view.swing.AutoDialog;
import autodialog.view.swing.AutoDialog.AutoDialogButtons;
import autodialog.view.swing.editors.BooleanEditor;
import autodialog.view.swing.editors.FloatEditor;
import autodialog.view.swing.editors.DummyEditor;
import autodialog.view.swing.editors.FilenameEditor;
import autodialog.view.swing.editors.IntegerEditor;
import autodialog.view.swing.editors.ListEditor;
import autodialog.view.swing.editors.SliderEditor;
import autodialog.view.swing.editors.TextAreaEditor;
import autodialog.view.swing.layouts.ADLayoutFactory;
import autodialog.view.swing.layouts.FramesADLayout;
import autodialog.view.swing.layouts.IADLayout;
import autodialog.view.swing.layouts.SimpleADLayout;
import autodialog.view.swing.layouts.TabbedADLayout;
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
		
		params.add(new Parameter<>("Boolean", new CheckBoxStyle(), Boolean.TRUE, g1, s1));
		params.add(new Parameter<>("Boolean #2", new CheckBoxStyle(), Boolean.TRUE, g1, s1));
		params.add(new Parameter<>("Integer", new IntegerSliderStyle(), 0, g1, s1));
		params.add(new Parameter<>("Integer #2", new IntegerSpinnerStyle(), 0, g1, s2));
		params.add(new Parameter<>("Real", new RealSliderStyle(), 0f, g1, s2));
		params.add(new Parameter<>("Dummy Separator", new SeparatorStyle(), null, g2));
//		params.add(new SelectionParameter<>("List", new ListEditor<>(LabelStyle.values()), LabelStyle.LABEL_HIDDEN, g2));
		SelectionParameter<LabelStyle> sel = new SelectionParameter<>("List", new ListStyle<>(), LabelStyle.LABEL_HIDDEN, g2);
		sel.setPossibleValues(Arrays.asList(LabelStyle.values()));
		params.add(sel);
		
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(new SimpleFileFilter(new String[]{"txt", "dat"}, "Some File Extensions"));
		params.add(new Parameter<>("Filename", new FileNameStyle(), null, g2));
		
		params.add(new Parameter<>("Slider", new IntegerSliderStyle(), 1, g2));
		params.add(new Parameter<String>("TextArea", new TextAreaStyle(), "", g2));
		
		
		
		AutoDialog d;
		d = AutoDialog.fromParameters(params, AutoDialogButtons.OK_CANCEL);
		d.setModal(true);
		d.initialize(new SimpleADLayout());
		
		d = AutoDialog.fromParameters(params, AutoDialogButtons.OK_CANCEL);
		d.setModal(true);
		d.initialize(new FramesADLayout(new ADLayoutFactory() {
			
			@Override
			public IADLayout getLayout(List<IEditor<?>> editors, int level, String group) {
				return new FramesADLayout(this);
			}
		}));
		
		d = AutoDialog.fromParameters(params, AutoDialogButtons.OK_CANCEL);
		d.setModal(true);
		d.initialize(new TabbedADLayout(new ADLayoutFactory() {
			
			@Override
			public IADLayout getLayout(List<IEditor<?>> editors, int level, String group) {
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
