package plural.swing;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import eventful.EventfulListener;

import plural.executor.Plural;
import plural.executor.PluralSet;
import plural.executor.ExecutorState;
import swidget.icons.StockIcon;
import swidget.widgets.ImageButton;
import swidget.widgets.Spacing;

public class PluralSetView extends JDialog {

	PluralSet<?> tasks;
	private JProgressBar progress;
	
	public PluralSetView(JFrame owner, PluralSet<?> _tasks){
		
		super(owner, "Working...", true);
		this.tasks = _tasks;
		init(owner);
	}
	
	public PluralSetView(JDialog owner, PluralSet<?> _tasks){
		
		super(owner, "Working...", true);
		this.tasks = _tasks;
		init(owner);
	}
		
	private void init(Window owner)
	{
	
		setResizable(false);
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		
		
        JPanel panel = new JPanel();
        getContentPane().add(panel);

		
        LayoutManager layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panel.setLayout(layout);
		
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1.0;
        c.weighty = 0.0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        
        JLabel title = new JLabel(tasks.getDescription());
        title.setFont(title.getFont().deriveFont(Font.BOLD).deriveFont(title.getFont().getSize() + 2f));
        title.setBorder(Spacing.bMedium());
        panel.add(title, c);
        

		PluralView view;
		for (Plural pl : tasks){
			
			c.gridy += 1;
			
			view = new PluralView(pl);
			panel.add(view, c);
			
		}

        
		c.gridy += 1;
		c.weighty = 1.0;
		
		progress = new JProgressBar();
		progress.setMaximum(100);
		progress.setMinimum(0);
		progress.setValue(0);
		JPanel progressPanel = new JPanel();
		progressPanel.add(progress);
		progressPanel.setBorder(Spacing.bLarge());
		panel.add(progressPanel, c);
        
		c.weighty = 0.0;
		c.gridy += 1;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		ImageButton cancel = new ImageButton(StockIcon.CHOOSE_CANCEL, "Cancel", true);
		cancel.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tasks.requestAbortWorking();
			}
		});
		panel.add(cancel, c);
		
		
		panel.setBorder(Spacing.bHuge());
		
		tasks.addListener(new EventfulListener() {
		
			public void change() {
			
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					
					public void run()
					{
						if (tasks.isAborted()){
							tasks.finished();
							setVisible(false);
							dispose();
						}
						else if (tasks.getCompleted()){
							tasks.finished();
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
				tasks.startWorking();
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
				
		for (Plural t : tasks){
			if (t.getState() == ExecutorState.WORKING){
				progress.setValue((int)(t.getProgress() * 100));
				progress.setIndeterminate(false);
				break;
			} else if (t.getState() == ExecutorState.STALLED){
				progress.setIndeterminate(true);
			}
		}
		
	}
		
}
