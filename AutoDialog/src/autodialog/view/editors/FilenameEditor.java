package autodialog.view.editors;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import autodialog.model.Parameter;
import eventful.Eventful;
import swidget.icons.StockIcon;
import swidget.widgets.ImageButton;
import swidget.widgets.ImageButton.Layout;

public class FilenameEditor extends Eventful implements IEditor<String> {

	private Parameter<String> param;
	private FileSelector control = new FileSelector(this);;
	

	public FilenameEditor() {
		
	}
	
	public FilenameEditor(JFileChooser chooser) {
		setFileChooser(chooser);
	}
	
	@Override
	public void initialize(Parameter<String> param) {
		this.param = param;
	}
	
	public void setFileChooser(JFileChooser chooser) {
		control.chooser = chooser;
	}
	
	public JFileChooser getFileChooser() {
		return control.chooser;
	}
	

	@Override
	public boolean expandVertical() {
		return false;
	}

	@Override
	public boolean expandHorizontal() {
		return true;
	}

	@Override
	public LabelStyle getLabelStyle() {
		return LabelStyle.LABEL_ON_SIDE;
	}

	@Override
	public void setFromParameter() {
		control.setFilename((String)param.getValue());
	}

	@Override
	public void validateFailed() {
		setFromParameter();
	}

	@Override
	public String getEditorValue() {
		return control.getFilename();
	}

	@Override
	public JComponent getComponent() {
		return control;
	}

}


class FileSelector extends JPanel
{
	JTextField filenameField;
	JButton open;
	String filename;
	JFileChooser chooser;
	
	public FileSelector(final FilenameEditor parent) {
		super(new BorderLayout());
		open = new ImageButton(StockIcon.DOCUMENT_OPEN, "Browse for Files", Layout.IMAGE);
		
		filenameField = new JTextField(10);
		filenameField.setEditable(false);
		
		chooser = new JFileChooser();
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//display dialog
				chooser.showOpenDialog(FileSelector.this);
				
				//return if no selection
				if (chooser.getSelectedFile() == null) return;
				
				//update with selection
				setFilename(chooser.getSelectedFile().toString());
				parent.updateListeners();
				
			}
		});
		
		add(open, BorderLayout.EAST);
		add(filenameField);
		
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public void setFilename(String filename)
	{
		this.filename = filename;
		setFilenameField(filename);
	}
	
	private void setFilenameField(String filename)
	{
		filenameField.setText(new File(filename).getName());
	}
	

}
