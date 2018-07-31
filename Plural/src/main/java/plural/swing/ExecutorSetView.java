package plural.swing;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import plural.executor.ExecutorSet;
import plural.executor.ExecutorState;
import plural.executor.PluralExecutor;
import swidget.icons.StockIcon;
import swidget.widgets.ImageButton;
import swidget.widgets.Spacing;

public class ExecutorSetView extends JPanel
{

	ExecutorSet<?> executors;
	private JProgressBar progress;
	
	public ExecutorSetView(ExecutorSet<?> _tasks){
		
		this.executors = _tasks;
		init();
	}
	
		
	private void init()
	{
		
        LayoutManager layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);
		
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        
        JLabel title = new JLabel(executors.getDescription());
        title.setFont(title.getFont().deriveFont(Font.BOLD).deriveFont(title.getFont().getSize() + 2f));
        title.setBorder(Spacing.bMedium());
        add(title, c);
        

		ExecutorView view;
		for (PluralExecutor pl : executors){
			
			c.gridy += 1;
			
			view = new ExecutorView(pl);
			add(view, c);
			
		}

        
		c.gridy += 1;
		c.weighty = 1.0;
		c.weightx = 1.0;
		
		progress = new JProgressBar();
		progress.setMaximum(100);
		progress.setMinimum(0);
		progress.setValue(0);
		JPanel progressPanel = new JPanel();
		progressPanel.add(progress);
		progressPanel.setBorder(Spacing.bLarge());
		add(progressPanel, c);
        
		c.weighty = 0.0;
		c.weightx = 0.0;
		c.gridy += 1;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		ImageButton cancel = new ImageButton("Cancel", StockIcon.CHOOSE_CANCEL);
		cancel.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				executors.requestAbortWorking();
			}
		});
		add(cancel, c);
		
		
		setBorder(Spacing.bHuge());     
        
		
		executors.addListener(() -> {
			javax.swing.SwingUtilities.invokeLater(() -> {

				if (executors.isAborted()){
					executors.finished();
				}
				else if (executors.getCompleted()){
					executors.finished();
				} else {
					updateProgressBar();
				}
				
			});
		});
		
		
	}
	
	
	@Override
	public synchronized void setVisible(boolean b)
	{
		super.setVisible(b);
	}
	
	protected void updateProgressBar(){
				
		for (PluralExecutor e : executors){
			if (e.getState() == ExecutorState.WORKING){
				progress.setValue((int)(e.getProgress() * 100));
				progress.setIndeterminate(false);
				break;
			} else if (e.getState() == ExecutorState.STALLED){
				progress.setIndeterminate(true);
			}
		}
		
	}
	

	
}
