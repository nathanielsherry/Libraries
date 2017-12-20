package autodialog.view.javafx.layouts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import autodialog.model.Group;
import autodialog.model.Parameter;
import autodialog.model.Value;
import autodialog.view.View;
import autodialog.view.editors.Editor;
import autodialog.view.editors.Editor.LabelStyle;
import autodialog.view.javafx.FXView;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import swidget.widgets.Spacing;

public class FramesFXLayout extends SimpleFXLayout {

	
	protected Node component(FXView view) {
		if (view instanceof FXLayout) {
			//this is a layout (of a group). Put it in a frame
			TitledPane pane = new TitledPane(view.getTitle(), view.getComponent());
			return pane;			
		} else {
			return view.getComponent();
		}
	}

}
