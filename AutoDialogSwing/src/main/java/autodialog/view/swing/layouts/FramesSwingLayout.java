package autodialog.view.swing.layouts;

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

import autodialog.view.swing.SwingView;
import autodialog.model.Group;
import autodialog.model.Parameter;
import autodialog.model.Value;
import autodialog.view.View;
import autodialog.view.editors.Editor;
import autodialog.view.editors.Editor.LabelStyle;
import autodialog.view.swing.editors.SwingEditor;
import autodialog.view.swing.editors.SwingEditorFactory;
import swidget.widgets.Spacing;

public class FramesSwingLayout extends SimpleSwingLayout {

	
	protected JComponent component(SwingView view) {
		if (view instanceof SwingLayout) {
			//this is a layout (of a group). Put it in a frame
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(view.getComponent(), BorderLayout.CENTER);
			panel.setBorder(new TitledBorder(view.getTitle()));
			return panel;			
		} else {
			return view.getComponent();
		}
	}

}
