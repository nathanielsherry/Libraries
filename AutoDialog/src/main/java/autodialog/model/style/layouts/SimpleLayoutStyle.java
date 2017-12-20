package autodialog.model.style.layouts;

import java.util.List;

import autodialog.model.Value;
import autodialog.model.style.CoreStyle;
import autodialog.model.style.Style;

public class SimpleLayoutStyle implements Style<List<Value<?>>> {

	private String style;
	
	public SimpleLayoutStyle(String style) {
		this.style = style;
	}
	
	@Override
	public String getStyle() {
		return style;
	}

	@Override
	public CoreStyle getFallbackStyle() {
		return null;
	}

}
