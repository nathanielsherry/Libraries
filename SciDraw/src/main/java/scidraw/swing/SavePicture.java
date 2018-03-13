package scidraw.swing;


import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import scidraw.drawing.backends.Surface;
import scitypes.log.SciLog;
import scitypes.util.Mutable;
import swidget.Swidget;
import swidget.dialogues.fileio.SimpleFileExtension;
import swidget.dialogues.fileio.SwidgetFileDialogs;
import swidget.icons.StockIcon;
import swidget.widgets.ButtonBox;
import swidget.widgets.ClearPanel;
import swidget.widgets.ImageButton;
import swidget.widgets.SettingsPanel;
import swidget.widgets.SettingsPanel.LabelPosition;
import swidget.widgets.Spacing;
import swidget.widgets.toggle.ItemToggleButton;
import swidget.widgets.toggle.ToggleGroup;
import swidget.widgets.toggle.ItemToggleButton;


public class SavePicture extends JDialog
{

	private GraphicsPanel			controller;
	private File					startingFolder;
	private ToggleGroup				group;
	private JPanel					controlsPanel;
	
	private JSpinner spnWidth, spnHeight;
	
	
	public SavePicture(Window owner, GraphicsPanel controller, File startingFolder)
	{

		super(owner, "Save as Image");

		this.controller = controller;
		this.startingFolder = startingFolder;

		init(owner);
	}
	
	
	private void init(Window owner)
	{

		controlsPanel = new ClearPanel();

		controlsPanel.setLayout(new BorderLayout());
		controlsPanel.add(createOptionsPane(), BorderLayout.CENTER);
		controlsPanel.add(createControlPanel(), BorderLayout.SOUTH);
		
		add(controlsPanel);

		

		pack();
		setResizable(false);
		setLocationRelativeTo(owner);
		setModal(true);
		setVisible(true);
	}


	public JPanel createControlPanel()
	{

		ButtonBox buttonBox = new ButtonBox(Spacing.bHuge());


		ImageButton ok = new ImageButton(StockIcon.DOCUMENT_SAVE, "Save", true);
		ImageButton cancel = new ImageButton(StockIcon.CHOOSE_CANCEL, "Cancel", true);

		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{

				int selection = group.getToggledIndex();
				Cursor oldCursor = getCursor();
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				if (selection == 0) savePNG();
				if (selection == 1) saveSVG();
				if (selection == 2) savePDF();
				setCursor(oldCursor);

			}
		});

		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});

		buttonBox.addRight(cancel);
		buttonBox.addRight(ok);

		return buttonBox;

	}

	public JPanel createDimensionsPane() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		spnWidth = new JSpinner(new SpinnerNumberModel((int)Math.ceil(controller.getUsedWidth()), 100, 10000, 1));
		spnHeight = new JSpinner(new SpinnerNumberModel((int)Math.ceil(controller.getUsedHeight()), 100, 10000, 1));
		
		c.weightx = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		panel.add(Box.createHorizontalGlue(), c);
		c.gridx++;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		
		panel.add(new JLabel("Width"), c);
		c.gridx++;
		panel.add(spnWidth, c);
		c.gridx++;
		
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		panel.add(Box.createHorizontalGlue(), c);
		c.gridx++;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		
		
		panel.add(new JLabel("Height"), c);
		c.gridx++;
		panel.add(spnHeight, c);
		c.gridx++;
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		panel.add(Box.createHorizontalGlue(), c);
		c.gridx++;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		
		panel.setBorder(Spacing.bHuge());
		
		return panel;
		
	}

	public JPanel createOptionsPane()
	{

		JPanel panel = new ClearPanel();		
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.weighty = 0;
		c.weightx = 0;
		
		
		group = new ToggleGroup();

		ItemToggleButton png, svg, pdf;

		png = new ItemToggleButton(StockIcon.MIME_RASTER, "Pixel Image (PNG)",
				"Pixel based images are a grid of coloured dots. They have a fixed size and level of detail.");
		
		svg = new ItemToggleButton(StockIcon.MIME_SVG, "Vector Image (SVG)",
				"Vector images use points, lines, and curves to define an image. They can be scaled to any size.");

		pdf = new ItemToggleButton(StockIcon.MIME_PDF, "PDF File", "PDF files are a more print-oriented vector image format.");

		group.registerButton(png);
		group.registerButton(svg);
		group.registerButton(pdf);
		

		
		c.gridx = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.weighty = 0;
		c.weightx = 0;
		
		
		
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(createDimensionsPane(), c);
		c.fill = GridBagConstraints.HORIZONTAL;	
		c.gridy++;

		
		panel.add(png, c);
		c.gridy++;
		
		panel.add(svg, c);
		c.gridy++;
		
		panel.add(pdf, c);
		c.gridy++;


		
		group.setToggled(0);

		panel.setBorder(Spacing.bHuge());

		
		return panel;

	}


	private void savePNG()
	{

		try
		{
			
			controlsPanel.setEnabled(false);
			controlsPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			

			SimpleFileExtension png = new SimpleFileExtension("Portable Network Graphic", "png");
			File result = SwidgetFileDialogs.saveFile(this, "Save Picture As...", startingFolder, png);
			if (result == null) {
				setVisible(false);
				return;
			}
			
			OutputStream os = new FileOutputStream(result);
			int width = ((Number)spnWidth.getValue()).intValue();
			int height = ((Number)spnHeight.getValue()).intValue();
			controller.writePNG(os, new Dimension(width, height));
			os.close();

			startingFolder = result.getParentFile();
			setVisible(false);
			
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			setEnabled(true);
			
		}
		catch (IOException e)
		{
			SciLog.get().log(Level.SEVERE, "Failed to save PNG", e);
		}

	}


	private void saveSVG()
	{


		try
		{
			
			controlsPanel.setEnabled(false);
			controlsPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			
			SimpleFileExtension svg = new SimpleFileExtension("Scalable Vector Graphic", "svg");
			File result = SwidgetFileDialogs.saveFile(this, "Save Picture As...", startingFolder, svg);
			if (result == null) {
				setVisible(false);
				return;
			}
						
			OutputStream os = new FileOutputStream(result);				
			int width = ((Number)spnWidth.getValue()).intValue();
			int height = ((Number)spnHeight.getValue()).intValue();
			controller.writeSVG(os, new Dimension(width, height));
			os.close();

			startingFolder = result.getParentFile();
			setVisible(false);
			
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			setEnabled(true);
			
		}
		catch (IOException e)
		{
			SciLog.get().log(Level.SEVERE, "Failed to save SVG", e);
		}



	}


	private void savePDF()
	{

		try
		{
			
			controlsPanel.setEnabled(false);
			controlsPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			
			SimpleFileExtension pdf = new SimpleFileExtension("Portable Document Format", "pdf");
			File result = SwidgetFileDialogs.saveFile(this, "Save Picture As...", startingFolder, pdf);
			if (result == null) {
				setVisible(false);
				return;
			}
			
			OutputStream os = new FileOutputStream(result);				
			int width = ((Number)spnWidth.getValue()).intValue();
			int height = ((Number)spnHeight.getValue()).intValue();
			controller.writePDF(os, new Dimension(width, height));
			os.close();

			startingFolder = result.getParentFile();
			setVisible(false);
			
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			setEnabled(true);
			
		}
		catch (IOException e)
		{
			SciLog.get().log(Level.SEVERE, "Failed to save PDF", e);
		}

	}

	
	public File getStartingFolder()
	{
		return startingFolder;
	}

	
	private File tempfile() throws IOException
	{
		final File tempfile = File.createTempFile("Image File - ", " export");
		tempfile.deleteOnExit();
		return tempfile;
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		

		

		
		GraphicsPanel g = new GraphicsPanel() {
			
			@Override
			public float getUsedWidth(float zoom) {
				// TODO Auto-generated method stub
				return 1000;
			}
			
			@Override
			public float getUsedWidth() {
				// TODO Auto-generated method stub
				return 2000;
			}
			
			@Override
			public float getUsedHeight(float zoom) {
				// TODO Auto-generated method stub
				return 500;
			}
			
			@Override
			public float getUsedHeight() {
				// TODO Auto-generated method stub
				return 1000;
			}
			
			@Override
			protected void drawGraphics(Surface backend, boolean vector, Dimension size) {
				// TODO Auto-generated method stub
				
			}
		};
		
		Swidget.initializeAndWait();
		
		SavePicture s = new SavePicture(null, g, null);
		
		
		
		
		
	}

}
