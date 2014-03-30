package plural.swing;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import plural.executor.ExecutorSet;
import plural.executor.ExecutorState;
import plural.executor.PluralExecutor;
import swidget.icons.StockIcon;
import swidget.widgets.ImageButton;
import swidget.widgets.Spacing;
import eventful.EventfulListener;

public class ExecutorSetView extends JDialog {

	ExecutorSet<?> executors;
	ExecutorSetViewPanel panel;
	
	public ExecutorSetView(Window owner, ExecutorSet<?> _tasks){
		
		super(owner, "Working...", ModalityType.DOCUMENT_MODAL);
		this.executors = _tasks;
		init(owner);
	}
	
	public ExecutorSetView(JDialog owner, ExecutorSet<?> _tasks){
		
		super(owner, "Working...", true);
		this.executors = _tasks;
		init(owner);
	}
		
	private void init(Window owner)
	{
	
		setResizable(false);
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		
		
		panel = new ExecutorSetViewPanel(executors);
		getContentPane().add(panel);

		
		executors.addListener(new EventfulListener() {
		
			public void change() {
			
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					
					public void run()
					{
						if (executors.isAborted()){
							executors.finished();
							setVisible(false);
							dispose();
						}
						else if (executors.getCompleted()){
							executors.finished();
							setVisible(false);
							dispose();
						} else {
							updateProgressBar();
						}
					}
				});
				

			}
		});
		
		pack();
		setLocationRelativeTo(owner);
		
		addWindowListener(new WindowListener() {
			
			public void windowOpened(WindowEvent e)
			{
				executors.startWorking();
			}
		
			public void windowIconified(WindowEvent e){}
		
			public void windowDeiconified(WindowEvent e){}
		
			public void windowDeactivated(WindowEvent e){}
		
			public void windowClosing(WindowEvent e){}
			
			public void windowClosed(WindowEvent e){}

			public void windowActivated(WindowEvent e){}
		});
		setVisible(true);
        
        
	}
	
	
	@Override
	public synchronized void setVisible(boolean b)
	{
		super.setVisible(b);
	}
	
	protected void updateProgressBar(){
		panel.updateProgressBar();
	}
	
	public static void main(String[] args)
	{
		
	}
		
}
