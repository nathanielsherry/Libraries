package plural.streams.swing;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import plural.streams.StreamExecutor;
import swidget.icons.StockIcon;
import swidget.widgets.ImageButton;
import swidget.widgets.Spacing;

public class StreamExecutorPanel extends JPanel {

	public StreamExecutorPanel(String title, StreamExecutorView... observerViews) {
		this(title, Arrays.asList(observerViews));
	}

	public StreamExecutorPanel(String t, List<StreamExecutorView> observerViews) {

		LayoutManager layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		JLabel title = new JLabel(t);
		title.setFont(title.getFont().deriveFont(Font.BOLD).deriveFont(title.getFont().getSize() + 2f));
		title.setBorder(new EmptyBorder(Spacing.medium, 0, Spacing.medium, Spacing.medium));
		add(title, c);

		for (StreamExecutorView obsv : observerViews) {
			c.gridy += 1;
			add(obsv, c);
		}

		c.gridy += 1;
		c.weighty = 1.0;
		c.weightx = 1.0;

		JProgressBar progress = new JProgressBar();
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
		cancel.addActionListener(e -> {
			List<StreamExecutorView> reversed = new ArrayList<>(observerViews);
			Collections.reverse(reversed);
			for (StreamExecutorView v : reversed) {
				v.getExecutor().abort();
			}
		});
		add(cancel, c);

		setBorder(Spacing.bHuge());

		for (StreamExecutorView v : observerViews) {
			v.getExecutor().addListener(event -> {
				StreamExecutor<?> exec = v.getExecutor();
				if (exec.getSize() <= 0) {
					progress.setIndeterminate(true);
				} else {
					progress.setIndeterminate(false);
					progress.setValue(exec.getCount() * 100 / exec.getSize());
				}
			});
		}

	}

}
