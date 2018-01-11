package swidget.stratus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.Painter;
import javax.swing.UIDefaults;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import swidget.icons.IconSize;
import swidget.icons.StockIcon;

public class StratusLookAndFeel extends NimbusLookAndFeel {


	@Override
	public UIDefaults getDefaults() {
		UIDefaults ret = super.getDefaults();

		Color highlight = new Color(0x498ed8);
		Color control = new Color(0xe6e6e6);

		// UI/Theme Overrides
		ret.put("nimbusSelection", highlight);
		ret.put("nimbusSelectionBackground", highlight);
		ret.put("nimbusFocus", new Color(0x72a0d1));
		ret.put("nimbusBlueGrey", new Color(0xbebebe));
		ret.put("nimbusInfoBlue", new Color(0x2e6fb4));
		ret.put("nimbusBase", new Color(0x3D6A99));
		ret.put("nimbusOrange", new Color(0x2F76BF));

		ret.put("textInactiveText", new Color(0x919191));
		ret.put("textHighlight", highlight);
		ret.put("textBackground", highlight);

		ret.put("menu", new Color(0xFFFFFF));
		ret.put("control", control);

		ret.put("MenuBar[Enabled].backgroundPainter", new FillPainter(control));
		ret.put("MenuBar[Enabled].borderPainter", new FillPainter(control));
		ret.put("MenuBar:Menu[Selected].backgroundPainter", new FillPainter(highlight));
		ret.put("MenuBar.contentMargins", new Insets(0, 0, 0, 0));
		ret.put("MenuBar:Menu.contentMargins", new Insets(4, 8, 5, 8));
		
		ret.put("MenuItem.contentMargins", new Insets(4, 12, 4, 13));
		ret.put("CheckBoxMenuItem.contentMargins", new Insets(4, 12, 4, 13));
		ret.put("RadioButtonMenuItem.contentMargins", new Insets(4, 12, 4, 13));

		
		ret.put("Menu.contentMargins", new Insets(4, 12, 4, 5));
		
		
		

		// ICONS
		ret.put("Tree.closedIcon", StockIcon.PLACE_FOLDER.toImageIcon(IconSize.BUTTON));
		ret.put("Tree.openIcon", StockIcon.PLACE_FOLDER_OPEN.toImageIcon(IconSize.BUTTON));

		ret.put("FileView.directoryIcon", StockIcon.PLACE_FOLDER.toImageIcon(IconSize.BUTTON));
		
		ret.put("FileChooser.directoryIcon", StockIcon.PLACE_FOLDER.toImageIcon(IconSize.BUTTON));
		ret.put("FileChooser.fileIcon", StockIcon.MIME_TEXT.toImageIcon(IconSize.BUTTON));
		ret.put("FileChooser.homeFolderIcon", StockIcon.PLACE_HOME.toImageIcon(IconSize.BUTTON));
		ret.put("FileChooser.newFolderIcon", StockIcon.PLACE_FOLDER_NEW.toImageIcon(IconSize.BUTTON));
		ret.put("FileChooser.upFolderIcon", StockIcon.GO_UP.toImageIcon(IconSize.BUTTON));
		
		

		ret.put("OptionPane.errorIcon", StockIcon.BADGE_ERROR.toImageIcon(IconSize.ICON));
		ret.put("OptionPane.informationIcon", StockIcon.BADGE_INFO.toImageIcon(IconSize.ICON));
		ret.put("OptionPane.questionIcon", StockIcon.BADGE_HELP.toImageIcon(IconSize.ICON));
		ret.put("OptionPane.warningIcon", StockIcon.BADGE_WARNING.toImageIcon(IconSize.ICON));

		return ret;
	}
	
	@Override
	public String getID() {
		return "Stratus";
	}
	
	@Override
	public String getName() {
		return "Stratus";
	}
	
	@Override
	public String getDescription() {
		return "Stratus Look and Feel";
	}
	
	
}



class FillPainter implements Painter<JComponent> {

    private final Color color;

    public FillPainter(Color c) { color = c; }

    @Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
        g.setColor(color);
        g.fillRect(0, 0, width-1, height-1);
    }

}