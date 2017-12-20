package autodialog.model.style.editors;

import autodialog.model.style.CoreStyle;
import autodialog.model.style.SimpleStyle;

public class FileNameStyle extends SimpleStyle<String>{

	public FileNameStyle() {
		super("file-name", CoreStyle.TEXT_VALUE);
	}

}
