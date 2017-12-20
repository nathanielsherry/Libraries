package autodialog;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.JSlider;

import autodialog.model.Group;
import autodialog.model.Parameter;
import autodialog.model.SelectionParameter;
import autodialog.model.style.editors.CheckBoxStyle;
import autodialog.model.style.editors.FileNameStyle;
import autodialog.model.style.editors.IntegerSliderStyle;
import autodialog.model.style.editors.IntegerSpinnerStyle;
import autodialog.model.style.editors.ListStyle;
import autodialog.model.style.editors.RealSliderStyle;
import autodialog.model.style.editors.SeparatorStyle;
import autodialog.model.style.editors.TextAreaStyle;
import autodialog.model.style.layouts.FramedLayoutStyle;
import autodialog.model.style.layouts.TabbedLayoutStyle;
import autodialog.view.editors.AutoDialogButtons;
import autodialog.view.editors.Editor;
import autodialog.view.editors.Editor.LabelStyle;
import autodialog.view.swing.SwingAutoDialog;
import autodialog.view.swing.SwingAutoDialog;
import autodialog.view.swing.editors.BooleanEditor;
import autodialog.view.swing.editors.FloatEditor;
import autodialog.view.swing.editors.DummyEditor;
import autodialog.view.swing.editors.FilenameEditor;
import autodialog.view.swing.editors.IntegerEditor;
import autodialog.view.swing.editors.ListEditor;
import autodialog.view.swing.editors.SliderEditor;
import autodialog.view.swing.editors.TextAreaEditor;
import autodialog.view.swing.layouts.SwingLayoutFactory;
import autodialog.view.swing.layouts.FramesSwingLayout;
import autodialog.view.swing.layouts.SimpleSwingLayout;
import autodialog.view.swing.layouts.TabbedSwingLayout;
import swidget.Swidget;
import swidget.dialogues.fileio.SimpleFileFilter;

public class Test {

	public static void main(String[] args)
	{
		
		Swidget.initialize();
			
		Group top = new Group("Demo", new TabbedLayoutStyle());
		Group g1 = new Group("First Set", new FramedLayoutStyle());
		Group g2 = new Group("Second Set");
		Group s1 = new Group("First Subset");
		Group s2 = new Group("Second Subset");
		g1.getValue().add(s1);
		g1.getValue().add(s2);
		top.getValue().add(g1);
		top.getValue().add(g2);
		
		
		s1.getValue().add(new Parameter<>("Boolean", new CheckBoxStyle(), Boolean.TRUE));
		s1.getValue().add(new Parameter<>("Boolean #2", new CheckBoxStyle(), Boolean.TRUE));
		s1.getValue().add(new Parameter<>("Integer", new IntegerSliderStyle(), 0, p -> p.getValue() < 10));
		
		s2.getValue().add(new Parameter<>("Integer #2", new IntegerSpinnerStyle(), 0));
		s2.getValue().add(new Parameter<>("Real", new RealSliderStyle(), 0f));
		
		g2.getValue().add(new Parameter<>("Dummy Separator", new SeparatorStyle(), null));
		SelectionParameter<LabelStyle> sel = new SelectionParameter<>("List", new ListStyle<>(), LabelStyle.LABEL_HIDDEN);
		sel.setPossibleValues(Arrays.asList(LabelStyle.values()));
		g2.getValue().add(sel);
		
		g2.getValue().add(new Parameter<>("Filename", new FileNameStyle(), null));
		
		g2.getValue().add(new Parameter<>("Slider", new IntegerSliderStyle(), 1));
		g2.getValue().add(new Parameter<String>("TextArea", new TextAreaStyle(), ""));
		
		
		
		SwingAutoDialog d;
		d = new SwingAutoDialog(top, AutoDialogButtons.OK_CANCEL);
		d.setModal(true);
		d.initialize();
		
		top.visit(p -> System.out.println(p));

	}
	
	
	
	
}
