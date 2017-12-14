package autodialog.view.swing.editors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import autodialog.model.Parameter;
import autodialog.model.style.CoreStyle;
import autodialog.model.style.Style;
import autodialog.view.editors.IEditor;

public class SwingEditorFactory {

	private static Map<String, Supplier<ISwingEditor>> styleProviders = new HashMap<>();
	
	static {
		registerStyleProvider("file-name", FilenameEditor::new);
		registerStyleProvider("integer-slider", SliderEditor::new);
	}
	
	
	
	
	
	public static void registerStyleProvider(String style, Supplier<ISwingEditor> provider) {
		styleProviders.put(style, provider);
	}
	
	public static List<IEditor<?>> forParameters(List<Parameter<?>> paramaters) {
		return paramaters.stream().map(SwingEditorFactory::forParameter).collect(Collectors.toList());
	}
	
	public static <T> IEditor<T> forParameter(Parameter<T> parameter) {

		ISwingEditor<T> editor = null;
		
		for (String key : styleProviders.keySet()) {
			System.out.println(key);
			System.out.println(parameter.getStyle().getStyle());
			System.out.println("---------------");
			if (key.equals(parameter.getStyle().getStyle())) {
				editor = (ISwingEditor<T>) styleProviders.get(key).get();
				break;
			}
		}
		
		if (editor == null) {
			//Fallback to CoreStyle
			editor = fallback(parameter.getStyle().getFallbackStyle());
		}
		
		editor.initialize(parameter);
		return editor;
		
		
	}
	
	private static <T> ISwingEditor<T> fallback(CoreStyle fallbackStyle) {
		switch (fallbackStyle) {
			case BOOLEAN: return (ISwingEditor<T>) new BooleanEditor();
			case TEXT_VALUE: return (ISwingEditor<T>) new TextAreaEditor();
			case TEXT_AREA: return (ISwingEditor<T>) new TextAreaEditor();
			case INTEGER: return (ISwingEditor<T>) new IntegerEditor();
			case FLOAT: return (ISwingEditor<T>) new FloatEditor();
			case LIST: return (ISwingEditor<T>) new ListEditor<T>();
			case SPACING: return (ISwingEditor<T>) new DummyEditor();
			default: return null;
		}
	}
	
}
