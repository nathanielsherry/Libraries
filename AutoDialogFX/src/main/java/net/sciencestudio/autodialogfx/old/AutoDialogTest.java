package net.sciencestudio.autodialogfx.old;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.sciencestudio.autodialogfx.old.model.group.Group;
import net.sciencestudio.autodialogfx.old.model.group.IGroup;
import net.sciencestudio.autodialogfx.old.model.value.IValue;
import net.sciencestudio.autodialogfx.old.model.value.Value;
import net.sciencestudio.autodialogfx.old.view.editors.CheckboxEditor;
import net.sciencestudio.autodialogfx.old.view.editors.SpinnerIntegerEditor;
import net.sciencestudio.autodialogfx.old.view.layouts.TitledLayout;
import net.sciencestudio.autodialogfx.old.view.layouts.VerticalLayout;

public class AutoDialogTest extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {
		
		Group top = new IGroup(VerticalLayout.class, "Top");
		
		Group coords = new IGroup(TitledLayout.class, "Coords");
		Value<Integer> x = new IValue<>("X", 0, SpinnerIntegerEditor.class);
		Value<Integer> y = new IValue<>("Y", 0, SpinnerIntegerEditor.class);
		coords.addAll(x, y);
		top.add(coords);
		
		Value<Boolean> toggle = new IValue<Boolean>("Toggle", false, CheckboxEditor.class);
		top.add(toggle);
		
		
		AutoDialog dialog = new AutoDialog(top);
		dialog.present(primaryStage);
		
        primaryStage.sizeToScene();
        primaryStage.show();
        
        
        new Thread(() -> {

        	try {
        		Thread.sleep(5000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
	        Platform.runLater(() -> {
	        	x.setValue(5);
	        });
        }).start();
        
		
	}

	
	public static void main(String[] args) {
		launch(args);	
	}
	
}
