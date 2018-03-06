package plural.streams.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import plural.streams.StreamExecutor;
import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.widgets.Spacing;

public class StreamExecutorView extends JPanel {

	
	private StreamExecutor<?> exec;
	
	public StreamExecutorView(StreamExecutor<?> exec, String title) {
		super();
		
		this.exec = exec;
		setLayout(new BorderLayout(8, 8));
		setBorder(Spacing.bSmall());
		
		JLabel icon = new JLabel();
		Dimension d = new Dimension(16, 16);
		icon.setMinimumSize(d);
		icon.setMaximumSize(d);
		icon.setPreferredSize(d);
		this.add(icon, BorderLayout.WEST);
		
		JLabel text = new JLabel(title);
		this.add(text, BorderLayout.CENTER);
		
		exec.addListener(() -> {
			if (exec.getState() == StreamExecutor.State.COMPLETED) {
				icon.setIcon(StockIcon.CHOOSE_OK.toImageIcon(IconSize.BUTTON));
			}
			if (exec.getState() == StreamExecutor.State.RUNNING && exec.getCount() > 0) {
				icon.setIcon(StockIcon.GO_NEXT.toImageIcon(IconSize.BUTTON));
			}
		});
		
		
	}
	
	public StreamExecutor<?> getExecutor() {
		return exec;
	}
	
}
