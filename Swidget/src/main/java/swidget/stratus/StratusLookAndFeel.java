package swidget.stratus;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.Painter;
import javax.swing.UIDefaults;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import swidget.icons.IconSize;
import swidget.icons.StockIcon;
import swidget.stratus.Stratus.ButtonState;
import swidget.stratus.painters.BorderPainter;
import swidget.stratus.painters.ButtonPainter;
import swidget.stratus.painters.CheckPainter;
import swidget.stratus.painters.CompositePainter;
import swidget.stratus.painters.FadePainter;
import swidget.stratus.painters.FillPainter;
import swidget.stratus.painters.RadioButtonPainter;
import swidget.stratus.painters.TitledBorderBorder;
import swidget.stratus.painters.ToolTipPainter;
import swidget.stratus.painters.scrollbar.ScrollBarThumbPainter;
import swidget.stratus.painters.scrollbar.ScrollBarTrackPainter;
import swidget.stratus.painters.spinner.NextButtonPainter;
import swidget.stratus.painters.spinner.PreviousButtonPainter;
import swidget.stratus.painters.textfield.TextFieldBackgroundPainter;
import swidget.stratus.painters.textfield.TextFieldBorderPainter;

public class StratusLookAndFeel extends NimbusLookAndFeel {


	@Override
	public UIDefaults getDefaults() {
		UIDefaults ret = super.getDefaults();



		// UI/Theme Overrides
		ret.put("nimbusSelection", Stratus.highlight);
		ret.put("nimbusSelectionBackground", Stratus.highlight);
		ret.put("nimbusFocus", new Color(0x72a0d1));
		ret.put("nimbusBlueGrey", new Color(0xbebebe));
		ret.put("nimbusInfoBlue", new Color(0x2e6fb4));
		ret.put("nimbusBase", new Color(0x3D6A99));
		ret.put("nimbusOrange", new Color(0x2F76BF));

		ret.put("textInactiveText", new Color(0x919191));
		ret.put("textHighlight", Stratus.highlight);
		ret.put("textBackground", Stratus.highlight);

		ret.put("menu", new Color(0xFFFFFF));
		ret.put("control", Stratus.control);

		ret.put("MenuBar[Enabled].backgroundPainter", new FillPainter(Stratus.control));
		ret.put("MenuBar[Enabled].borderPainter", new FillPainter(Stratus.control));
		ret.put("MenuBar:Menu[Selected].backgroundPainter", new FillPainter(Stratus.highlight));
		ret.put("MenuBar.contentMargins", new Insets(0, 0, 0, 0));
		ret.put("MenuBar:Menu.contentMargins", new Insets(4, 8, 5, 8));
		
		ret.put("MenuItem.contentMargins", new Insets(4, 12, 4, 13));
		ret.put("MenuItem:MenuItemAccelerator[Enabled].textForeground", Stratus.border);
		ret.put("CheckBoxMenuItem.contentMargins", new Insets(4, 12, 4, 13));
		ret.put("RadioButtonMenuItem.contentMargins", new Insets(4, 12, 4, 13));

		ret.put("Menu.contentMargins", new Insets(4, 12, 4, 5));
					
		ret.put("ToolBar.backgroundPainter", new FadePainter(Stratus.control));
		
		
		
		//BUTTON
		ret.put("Button.foreground", 						Stratus.controlText);
		ret.put("Button.disabledText", 						Stratus.border);
		ret.put("Button[Disabled].textForeground", 			Stratus.border);
		ret.put("Button[Default+Pressed].textForeground", 	Stratus.controlText);

		ret.put("Button[Default+Focused+MouseOver].backgroundPainter", 	new ButtonPainter(ButtonState.DEFAULT, ButtonState.FOCUSED, ButtonState.MOUSEOVER));
		ret.put("Button[Default+Focused+Pressed].backgroundPainter", 	new ButtonPainter(ButtonState.DEFAULT, ButtonState.FOCUSED, ButtonState.PRESSED));
		ret.put("Button[Default+Focused].backgroundPainter", 			new ButtonPainter(ButtonState.DEFAULT, ButtonState.FOCUSED));
		ret.put("Button[Default+MouseOver].backgroundPainter", 			new ButtonPainter(ButtonState.DEFAULT, ButtonState.MOUSEOVER));
		ret.put("Button[Default+Pressed].backgroundPainter", 			new ButtonPainter(ButtonState.DEFAULT, ButtonState.PRESSED));
		ret.put("Button[Default].backgroundPainter", 					new ButtonPainter(ButtonState.DEFAULT));
		ret.put("Button[Disabled].backgroundPainter", 					new ButtonPainter(ButtonState.DISABLED));
		ret.put("Button[Enabled].backgroundPainter", 					new ButtonPainter(ButtonState.ENABLED));
		ret.put("Button[Focused+MouseOver].backgroundPainter", 			new ButtonPainter(ButtonState.FOCUSED, ButtonState.MOUSEOVER));
		ret.put("Button[Focused+Pressed].backgroundPainter", 			new ButtonPainter(ButtonState.FOCUSED, ButtonState.PRESSED));
		ret.put("Button[Focused].backgroundPainter", 					new ButtonPainter(ButtonState.FOCUSED));
		ret.put("Button[MouseOver].backgroundPainter", 					new ButtonPainter(ButtonState.MOUSEOVER));
		ret.put("Button[Pressed].backgroundPainter", 					new ButtonPainter(ButtonState.PRESSED));
				

		
		//TOGGLE BUTTON
		ret.put("ToggleButton.foreground", 							Stratus.controlText);
		ret.put("ToggleButton.disabledText", 						Stratus.border);
		ret.put("ToggleButton[Disabled].textForeground", 			Stratus.border);
		ret.put("ToggleButton[Default+Pressed].textForeground", 	Stratus.controlText);

		ret.put("ToggleButton[Default+Focused+MouseOver].backgroundPainter", 	new ButtonPainter(ButtonState.DEFAULT, ButtonState.FOCUSED, ButtonState.MOUSEOVER));
		ret.put("ToggleButton[Default+Focused+Pressed].backgroundPainter", 		new ButtonPainter(ButtonState.DEFAULT, ButtonState.FOCUSED, ButtonState.PRESSED));
		ret.put("ToggleButton[Default+Focused].backgroundPainter", 				new ButtonPainter(ButtonState.DEFAULT, ButtonState.FOCUSED));
		ret.put("ToggleButton[Default+MouseOver].backgroundPainter", 			new ButtonPainter(ButtonState.DEFAULT, ButtonState.MOUSEOVER));
		ret.put("ToggleButton[Default+Pressed].backgroundPainter", 				new ButtonPainter(ButtonState.DEFAULT, ButtonState.PRESSED));
		ret.put("ToggleButton[Default].backgroundPainter", 						new ButtonPainter(ButtonState.DEFAULT));
		ret.put("ToggleButton[Disabled].backgroundPainter", 					new ButtonPainter(ButtonState.DISABLED));
		ret.put("ToggleButton[Disabled+Selected].backgroundPainter", 			new ButtonPainter(ButtonState.DISABLED, ButtonState.SELECTED));
		ret.put("ToggleButton[Enabled].backgroundPainter", 						new ButtonPainter(ButtonState.ENABLED));
		ret.put("ToggleButton[Focused+MouseOver].backgroundPainter", 			new ButtonPainter(ButtonState.FOCUSED, ButtonState.MOUSEOVER));
		ret.put("ToggleButton[Focused+MouseOver+Selected].backgroundPainter", 	new ButtonPainter(ButtonState.FOCUSED, ButtonState.MOUSEOVER, ButtonState.SELECTED));
		ret.put("ToggleButton[Focused+Pressed].backgroundPainter", 				new ButtonPainter(ButtonState.FOCUSED, ButtonState.PRESSED));
		ret.put("ToggleButton[Focused+Pressed+Selected].backgroundPainter", 	new ButtonPainter(ButtonState.FOCUSED, ButtonState.PRESSED, ButtonState.SELECTED));
		ret.put("ToggleButton[Focused].backgroundPainter", 						new ButtonPainter(ButtonState.FOCUSED));
		ret.put("ToggleButton[Focused+Selected].backgroundPainter", 			new ButtonPainter(ButtonState.FOCUSED, ButtonState.SELECTED));
		ret.put("ToggleButton[MouseOver].backgroundPainter", 					new ButtonPainter(ButtonState.MOUSEOVER));
		ret.put("ToggleButton[MouseOver+Selected].backgroundPainter", 			new ButtonPainter(ButtonState.MOUSEOVER, ButtonState.SELECTED));
		ret.put("ToggleButton[Pressed].backgroundPainter", 						new ButtonPainter(ButtonState.PRESSED));
		ret.put("ToggleButton[Pressed+Selected].backgroundPainter", 			new ButtonPainter(ButtonState.PRESSED, ButtonState.SELECTED));
		ret.put("ToggleButton[Selected].backgroundPainter", 					new ButtonPainter(ButtonState.SELECTED));
		
		
		
		
		
		
		//SCROLL BARS
		ret.put("ScrollBar:ScrollBarTrack[Enabled].backgroundPainter", new ScrollBarTrackPainter());
		ret.put("ScrollBar:ScrollBarTrack[Enabled].backgroundPainter", new ScrollBarTrackPainter());
		
		ret.put("ScrollBar.incrementButtonGap", 0);
		ret.put("ScrollBar.decrementButtonGap", 0);
		ret.put("ScrollBar:\"ScrollBar.button\".size", 0);

		ret.put("ScrollBar:ScrollBarThumb[Enabled].backgroundPainter", new ScrollBarThumbPainter(ButtonState.ENABLED));
		ret.put("ScrollBar:ScrollBarThumb[Pressed].backgroundPainter", new ScrollBarThumbPainter(ButtonState.PRESSED));
		ret.put("ScrollBar:ScrollBarThumb[MouseOver].backgroundPainter", new ScrollBarThumbPainter(ButtonState.MOUSEOVER));
		
		
		//COMBOBOX
		ret.put("ComboBox[Enabled].backgroundPainter", new ButtonPainter(ButtonState.ENABLED));
		ret.put("ComboBox[Enabled+Selected].backgroundPainter", new ButtonPainter(ButtonState.ENABLED));
		ret.put("ComboBox[Disabled].backgroundPainter", new ButtonPainter(ButtonState.DISABLED));
		ret.put("ComboBox[Disabled+Pressed].backgroundPainter", new ButtonPainter(ButtonState.DISABLED, ButtonState.PRESSED));
		ret.put("ComboBox[Disabled+Selected].backgroundPainter", new ButtonPainter(ButtonState.DISABLED));
		ret.put("ComboBox[MouseOver].backgroundPainter", new ButtonPainter(ButtonState.MOUSEOVER));
		ret.put("ComboBox[Pressed].backgroundPainter", new ButtonPainter(ButtonState.PRESSED));
		ret.put("ComboBox[Focused].backgroundPainter", new ButtonPainter(ButtonState.FOCUSED));
		ret.put("ComboBox[Focused+Pressed].backgroundPainter", new ButtonPainter(ButtonState.PRESSED, ButtonState.FOCUSED));
		ret.put("ComboBox[Focused+MouseOver].backgroundPainter", new ButtonPainter(ButtonState.MOUSEOVER, ButtonState.FOCUSED));
		
//		ret.put("ComboBox[Disabled+Editable].backgroundPainter", new ButtonPainter(ButtonState.DISABLED));
//		ret.put("ComboBox[Editable+Enabled].backgroundPainter", new ButtonPainter(ButtonState.ENABLED));
//		ret.put("ComboBox[Editable+Focused].backgroundPainter", new ButtonPainter(ButtonState.FOCUSED));
//		ret.put("ComboBox[Editable+MouseOver].backgroundPainter", new ButtonPainter(ButtonState.MOUSEOVER));
//		ret.put("ComboBox[Editable+Pressed].backgroundPainter", new ButtonPainter(ButtonState.PRESSED));

		ret.put("ComboBox:\"ComboBox.arrowButton\"[Disabled+Editable].backgroundPainter", new ButtonPainter(ButtonState.DISABLED));
		ret.put("ComboBox:\"ComboBox.arrowButton\"[Editable+Enabled].backgroundPainter", new ButtonPainter(ButtonState.ENABLED));
		ret.put("ComboBox:\"ComboBox.arrowButton\"[Editable+MouseOver].backgroundPainter", new ButtonPainter(ButtonState.MOUSEOVER));
		ret.put("ComboBox:\"ComboBox.arrowButton\"[Editable+Pressed].backgroundPainter", new ButtonPainter(ButtonState.PRESSED));
		ret.put("ComboBox:\"ComboBox.arrowButton\"[Editable+Selected].backgroundPainter", new ButtonPainter(ButtonState.ENABLED));
		ret.put("ComboBox:\"ComboBox.arrowButton\"[Editable+Selected].backgroundPainter", new ButtonPainter(ButtonState.ENABLED));
		
		//TODO: arrow painters
//		Object arrow = ret.get("ComboBox:\"ComboBox.arrowButton\"[Enabled].foregroundPainter");
//		ret.put("ComboBox:\"ComboBox.arrowButton\"[Selected].backgroundPainter", arrow);
//		ret.put("ComboBox:\"ComboBox.arrowButton\"[Pressed].backgroundPainter", arrow);
		
		
		//CHECKBOX
		ret.put("CheckBox[Disabled].iconPainter", new ButtonPainter(ButtonState.DISABLED));
		ret.put("CheckBox[Disabled+Selected].iconPainter", new CompositePainter(new ButtonPainter(ButtonState.DISABLED), new CheckPainter(5, -1, false)));
		ret.put("CheckBox[Enabled].iconPainter", new ButtonPainter(ButtonState.ENABLED));
		ret.put("CheckBox[Enabled+Selected].iconPainter", new CompositePainter(new ButtonPainter(ButtonState.ENABLED), new CheckPainter(5, -1)));
		ret.put("CheckBox[Focused+MouseOver].iconPainter", new ButtonPainter(ButtonState.FOCUSED, ButtonState.MOUSEOVER));
		ret.put("CheckBox[Focused+MouseOver+Selected].iconPainter", new CompositePainter(new ButtonPainter(ButtonState.FOCUSED, ButtonState.MOUSEOVER), new CheckPainter(5, -1)));
		ret.put("CheckBox[Focused+Pressed].iconPainter", new ButtonPainter(ButtonState.FOCUSED, ButtonState.PRESSED));
		ret.put("CheckBox[Focused+Pressed+Selected].iconPainter", new CompositePainter(new ButtonPainter(ButtonState.FOCUSED, ButtonState.PRESSED), new CheckPainter(5, -1)));
		ret.put("CheckBox[Focused].iconPainter", new ButtonPainter(ButtonState.FOCUSED));
		ret.put("CheckBox[Focused+Selected].iconPainter", new CompositePainter(new ButtonPainter(ButtonState.FOCUSED), new CheckPainter(5, -1)));
		ret.put("CheckBox[MouseOver].iconPainter", new ButtonPainter(ButtonState.MOUSEOVER));
		ret.put("CheckBox[MouseOver+Selected].iconPainter", new CompositePainter(new ButtonPainter(ButtonState.MOUSEOVER), new CheckPainter(5, -1)));
		ret.put("CheckBox[Pressed].iconPainter", new ButtonPainter(ButtonState.PRESSED));
		ret.put("CheckBox[Pressed+Selected].iconPainter", new CompositePainter(new ButtonPainter(ButtonState.PRESSED), new CheckPainter(5, -1)));
		

		
		
		//RADIO BUTTONS
		ret.put("RadioButton[Disabled+Selected].iconPainter", new RadioButtonPainter(true, ButtonState.DISABLED));
		ret.put("RadioButton[Disabled].iconPainter", new RadioButtonPainter(false, ButtonState.DISABLED));
		ret.put("RadioButton[Enabled].iconPainter", new RadioButtonPainter(false, ButtonState.ENABLED));
		ret.put("RadioButton[Focused+MouseOver+Selected].iconPainter", new RadioButtonPainter(true, ButtonState.FOCUSED, ButtonState.MOUSEOVER));
		ret.put("RadioButton[Focused+MouseOver].iconPainter", new RadioButtonPainter(false, ButtonState.FOCUSED, ButtonState.MOUSEOVER));
		ret.put("RadioButton[Focused+Pressed+Selected].iconPainter", new RadioButtonPainter(true, ButtonState.FOCUSED, ButtonState.PRESSED));
		ret.put("RadioButton[Focused+Pressed].iconPainter", new RadioButtonPainter(false, ButtonState.FOCUSED, ButtonState.PRESSED));
		ret.put("RadioButton[Focused+Selected].iconPainter", new RadioButtonPainter(true, ButtonState.FOCUSED));
		ret.put("RadioButton[Focused].iconPainter", new RadioButtonPainter(false, ButtonState.FOCUSED));
		ret.put("RadioButton[MouseOver+Selected].iconPainter", new RadioButtonPainter(true, ButtonState.MOUSEOVER));
		ret.put("RadioButton[MouseOver].iconPainter", new RadioButtonPainter(false, ButtonState.MOUSEOVER));
		ret.put("RadioButton[Pressed+Selected].iconPainter", new RadioButtonPainter(true, ButtonState.PRESSED));
		ret.put("RadioButton[Pressed].iconPainter", new RadioButtonPainter(false, ButtonState.PRESSED));
		ret.put("RadioButton[Selected].iconPainter", new RadioButtonPainter(true, ButtonState.ENABLED));
		
		
		
		//SPINNERS TODO: Merged buttons? 
		ret.put("Spinner:\"Spinner.nextButton\"[Disabled].backgroundPainter", new NextButtonPainter(ButtonState.DISABLED));
		ret.put("Spinner:\"Spinner.nextButton\"[Enabled].backgroundPainter", new NextButtonPainter(ButtonState.ENABLED));
		ret.put("Spinner:\"Spinner.nextButton\"[Focused+MouseOver].backgroundPainter", new NextButtonPainter(ButtonState.FOCUSED, ButtonState.MOUSEOVER));
		ret.put("Spinner:\"Spinner.nextButton\"[Focused+Pressed].backgroundPainter", new NextButtonPainter(ButtonState.FOCUSED, ButtonState.PRESSED));
		ret.put("Spinner:\"Spinner.nextButton\"[Focused].backgroundPainter", new NextButtonPainter(ButtonState.FOCUSED));
		ret.put("Spinner:\"Spinner.nextButton\"[MouseOver].backgroundPainter", new NextButtonPainter(ButtonState.MOUSEOVER));
		ret.put("Spinner:\"Spinner.nextButton\"[Pressed].backgroundPainter", new NextButtonPainter(ButtonState.PRESSED));
		ret.put("Spinner:\"Spinner.previousButton\"[Disabled].backgroundPainter", new PreviousButtonPainter(ButtonState.DISABLED));
		ret.put("Spinner:\"Spinner.previousButton\"[Enabled].backgroundPainter", new PreviousButtonPainter(ButtonState.ENABLED));
		ret.put("Spinner:\"Spinner.previousButton\"[Focused+MouseOver].backgroundPainter", new PreviousButtonPainter(ButtonState.FOCUSED, ButtonState.MOUSEOVER));
		ret.put("Spinner:\"Spinner.previousButton\"[Focused+Pressed].backgroundPainter", new PreviousButtonPainter(ButtonState.FOCUSED, ButtonState.PRESSED));
		ret.put("Spinner:\"Spinner.previousButton\"[Focused].backgroundPainter", new PreviousButtonPainter(ButtonState.FOCUSED));
		ret.put("Spinner:\"Spinner.previousButton\"[MouseOver].backgroundPainter", new PreviousButtonPainter(ButtonState.MOUSEOVER));
		ret.put("Spinner:\"Spinner.previousButton\"[Pressed].backgroundPainter", new PreviousButtonPainter(ButtonState.PRESSED));
		
		ret.put("Spinner:Panel:\"Spinner.formattedTextField\"[Disabled].backgroundPainter", new TextFieldBackgroundPainter(ButtonState.DISABLED));
		ret.put("Spinner:Panel:\"Spinner.formattedTextField\"[Enabled].backgroundPainter", new TextFieldBackgroundPainter(ButtonState.ENABLED));
		ret.put("Spinner:Panel:\"Spinner.formattedTextField\"[Focused+Selected].backgroundPainter", new TextFieldBackgroundPainter(ButtonState.FOCUSED, ButtonState.SELECTED));
		ret.put("Spinner:Panel:\"Spinner.formattedTextField\"[Focused].backgroundPainter", new TextFieldBackgroundPainter(ButtonState.FOCUSED));
		ret.put("Spinner:Panel:\"Spinner.formattedTextField\"[Selected].backgroundPainter", new TextFieldBackgroundPainter(ButtonState.SELECTED));
		
		

		//TEXTFIELD
		ret.put("TextField[Disabled].borderPainter", new TextFieldBorderPainter(ButtonState.DISABLED));
		ret.put("TextField[Enabled].borderPainter", new TextFieldBorderPainter(ButtonState.ENABLED));
		ret.put("TextField[Focused].borderPainter", new TextFieldBorderPainter(ButtonState.ENABLED, ButtonState.SELECTED));

		ret.put("TextField[Disabled].backgroundPainter", new TextFieldBackgroundPainter(ButtonState.DISABLED));
		ret.put("TextField[Enabled].backgroundPainter", new TextFieldBackgroundPainter(ButtonState.ENABLED));
		ret.put("TextField[Focused].backgroundPainter", new TextFieldBackgroundPainter(ButtonState.ENABLED, ButtonState.SELECTED));

		
		//TITLEDBORDER
		ret.put("TitledBorder.border", new CompoundBorder(new EmptyBorder(2, 4, 4, 4), new CompoundBorder(new TitledBorderBorder(), new EmptyBorder(6, 6, 6, 6))));
		
		//TOOLTIP
//		ret.put("ToolTip[Enabled].backgroundPainter", new ToolTipPainter());
//		ret.put("ToolTip.foreground", Color.white);
		
		
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



