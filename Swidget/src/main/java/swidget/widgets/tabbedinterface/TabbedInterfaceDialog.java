package swidget.widgets.tabbedinterface;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import swidget.widgets.ImageButton;

public class TabbedInterfaceDialog {

	private JOptionPane pane;
	private Consumer<Object> onResult;
	
	private String title;
	
	/**
	 * Creates a new TabbedInterface Dialog
	 * @param title the title of the dialog
	 * @param body the body text of the dialog
	 * @param messageType the {@link JOptionPane} message type
	 */
	public TabbedInterfaceDialog(String title, String body, int messageType, int optionType, Consumer<Object> onResult) {
		this.title = title;
		pane = new JOptionPane();
		pane.setMessage("<html><h2>" + title + "</h2><br/>" + body.replace("\n",	"<br/>") + "</html>");
		pane.setOptionType(optionType);
		pane.setMessageType(messageType);
		this.onResult = onResult;
	}
	
	public TabbedInterfaceDialog(String title, String body, int messageType) {
		this(title, body, messageType, "OK");
	}
	
	public TabbedInterfaceDialog(String title, String body, int messageType, String button) {
		this(title, body, messageType, new ImageButton(button));
	}
	
	public TabbedInterfaceDialog(String title, String body, int messageType, JButton... buttons) {
		this.title = title;
		pane = new JOptionPane();
		pane.setMessage("<html><h2>" + title + "</h2><br/>" + body.replace("\n",	"<br/>") + "</html>");
		pane.setOptionType(0);
		pane.setMessageType(messageType);
		pane.setOptions(buttons);
		for (JButton button : buttons) {
			button.addActionListener(e -> pane.setValue(button));
		}
		this.onResult = r -> {};
	}

	
	public void showIn(TabbedInterfacePanel owner) {
		if (owner == null) {
			JDialog dialog = pane.createDialog(title);
			dialog.setModal(true);
			dialog.setVisible(true);
			onResult.accept(pane.getValue());
		} else {
			owner.pushModalComponent(pane);
			pane.addPropertyChangeListener(p -> {
				if (p.getPropertyName().equals(JOptionPane.VALUE_PROPERTY) && 
						p.getNewValue() != null &&
						p.getNewValue() != JOptionPane.UNINITIALIZED_VALUE) {
					owner.popModalComponent();
					onResult.accept(p.getNewValue());
				}
			});
		}
	}
	
	public JOptionPane getComponent() {
		return pane;
	}
		
}
