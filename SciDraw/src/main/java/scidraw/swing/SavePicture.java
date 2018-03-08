package scidraw.swing;


import java.awt.BorderLayout;
import java.awt.Cursor;
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

import javax.swing.JDialog;
import javax.swing.JPanel;

import scitypes.log.SciLog;
import swidget.dialogues.fileio.SwidgetIO;
import swidget.icons.StockIcon;
import swidget.widgets.ButtonBox;
import swidget.widgets.ClearPanel;
import swidget.widgets.ImageButton;
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


	public JPanel createOptionsPane()
	{

		JPanel panel = new ClearPanel();		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
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
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.weighty = 0;
		c.weightx = 0;
		panel.add(png, c);
		
		c.gridy++;
		panel.add(svg, c);
		
		c.gridy++;
		panel.add(pdf, c);
		

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
			

			File tempfile = tempfile();
			
			OutputStream os = new FileOutputStream(tempfile);				
			controller.writePNG(os);
			os.close();
			
			InputStream is = new FileInputStream(tempfile);
			File result = SwidgetIO.saveFile(this, "Save Picture As...", "png", "Portable Network Graphic", startingFolder, is);
			is.close();
			
			tempfile.delete();
			

			

			if (result != null) 
			{
				startingFolder = result;
				setVisible(false);
			}
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
			

			File tempfile = tempfile();
			
			OutputStream os = new FileOutputStream(tempfile);				
			controller.writeSVG(os);
			os.close();
			
			InputStream is = new FileInputStream(tempfile);
			File result = SwidgetIO.saveFile(this, "Save Picture As...", "svg", "Scalable Vector Graphic", startingFolder, is);
			is.close();
			
			tempfile.delete();
			
			

			if (result != null) 
			{
				startingFolder = result;
				setVisible(false);
			}
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
			
			File tempfile = tempfile();
			
			OutputStream os = new FileOutputStream(tempfile);				
			controller.writePDF(os);
			os.close();
			
			InputStream is = new FileInputStream(tempfile);
			File result = SwidgetIO.saveFile(this, "Save Picture As...", "pdf", "Portable Document Format", startingFolder, is);
			is.close();
			
			tempfile.delete();

			if (result != null) 
			{
				startingFolder = result;
				setVisible(false);
			}
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
	

}
