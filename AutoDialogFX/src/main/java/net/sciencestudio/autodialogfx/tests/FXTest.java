package net.sciencestudio.autodialogfx.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sciencestudio.autodialogfx.model.Model;
import net.sciencestudio.autodialogfx.model.dummy.Dummy;
import net.sciencestudio.autodialogfx.model.group.Group;
import net.sciencestudio.autodialogfx.model.group.IGroup;
import net.sciencestudio.autodialogfx.model.group.Presenter;
import net.sciencestudio.autodialogfx.model.value.IValue;
import net.sciencestudio.autodialogfx.model.value.Value;
import net.sciencestudio.autodialogfx.model.value.bounded.BoundedValue;
import net.sciencestudio.autodialogfx.model.value.bounded.IBoundedValue;
import net.sciencestudio.autodialogfx.model.value.list.IListValue;
import net.sciencestudio.autodialogfx.model.value.list.ListValue;
import net.sciencestudio.autodialogfx.view.decor.SeparatorDecor;
import net.sciencestudio.autodialogfx.view.editors.CheckboxEditor;
import net.sciencestudio.autodialogfx.view.editors.ChoiceBoxEditor;
import net.sciencestudio.autodialogfx.view.editors.Editor;
import net.sciencestudio.autodialogfx.view.editors.FilenameEditor;
import net.sciencestudio.autodialogfx.view.editors.SliderIntegerEditor;
import net.sciencestudio.autodialogfx.view.editors.SpinnerIntegerEditor;
import net.sciencestudio.autodialogfx.view.editors.TextAreaEditor;
import net.sciencestudio.autodialogfx.view.editors.TextFieldEditor;
import net.sciencestudio.autodialogfx.view.layouts.Layout;
import net.sciencestudio.autodialogfx.view.layouts.LabeledLayout;
import net.sciencestudio.autodialogfx.view.layouts.TabbedLayout;
import net.sciencestudio.autodialogfx.view.layouts.TitledLayout;
import net.sciencestudio.autodialogfx.view.layouts.VerticalLayout;
import net.sciencestudio.chanje.ChangeBus;
import net.sciencestudio.chanje.IChangeBus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXTest extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(FXTest.class.getResource("Dialog.fxml"));
		BorderPane parent = loader.load();
		stage.setScene(new Scene(parent));
		VBox box = (VBox) parent.getCenter();
		
	
		
		Value<Boolean> check = new IValue<Boolean>("CheckBox Test", false, CheckboxEditor.class);
		Value<Boolean> check2 = new IValue<Boolean>("CheckBox Test #2", false, CheckboxEditor.class);
		ListValue<String> name = new IListValue<String>("Names", "Alice", ChoiceBoxEditor.class, Arrays.asList(new String[]{"Alice", "Bob", "Charlie"}));
		BoundedValue<Integer> count = new IBoundedValue<Integer>("Counter", 1, SliderIntegerEditor.class, 0, 10, 1);
		Value<String> text = new IValue<String>("Text Entry", "", TextFieldEditor.class);
		Value<File> file = new IValue<File>("File Location", new File("~"), FilenameEditor.class);
		Value<String> text2 = new IValue<String>("Paragraph", "", TextAreaEditor.class);
		
		
		Group group1 = new IGroup(LabeledLayout.class, "Group 1").addAll(check, count);
		Group group2 = new IGroup(LabeledLayout.class, "Group 2").addAll(name, text);
		Group group3 = new IGroup(VerticalLayout.class, "Group 3").addAll(file, text2);
		Group group4 = new IGroup(TitledLayout.class, "Group4").addAll(group1, group2);
		Group group5 = new IGroup(VerticalLayout.class, "Group 5").addAll(Group.wrap(TabbedLayout.class, group4), new Dummy(SeparatorDecor.class), group4);
		Group group6 = new IGroup(TabbedLayout.class, "Group 6").addAll(group3, group5);
		
		
		group1.addValidator(values -> {
			if (check.getProposedValue() && count.getProposedValue() > 5) { return false; }
			return true;
		});
		
		
		/*
		Layout layout = new SimpleLayout();
		
		Value<?>[] values = new Value<?>[]{check, name, count, text, file, text2};
		
		for (Value<?> value : values) {
			layout.addChild(EditorFactory.getEditor(value));
		}
		*/
		
		Presenter presenter = new Presenter(group6, new Insets(0));

		box.getChildren().add(presenter.getNode());
		
		stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
}
