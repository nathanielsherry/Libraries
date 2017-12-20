package autodialog.view.javafx;

import java.io.IOException;
import java.util.Arrays;

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
import autodialog.view.editors.Editor.LabelStyle;
import eventful.EventfulConfig;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class FXAutoDialogTest extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {
		
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
		
		
		
		FXAutoDialog d;
		//d = new FXAutoDialog(top, AutoDialogButtons.OK_CANCEL);
		d = new FXAutoDialog(top);
		//d.setModal(true);
		d.initialize();
		
		
		top.visit(p -> System.out.println(p));
		
		

        new Thread(() -> {

        	try {
        		Thread.sleep(5000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
	        Platform.runLater(() -> {
	        	sel.setValue(LabelStyle.LABEL_ON_SIDE);
	        });
        }).start();
        
		
	}

	
	public static void main(String[] args) {
		EventfulConfig.runThread = Platform::runLater;
		launch(args);	
	}
	
}
