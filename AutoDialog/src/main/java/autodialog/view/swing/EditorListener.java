package autodialog.view.swing;


import autodialog.controller.IADController;
import autodialog.model.Parameter;
import autodialog.view.editors.IEditor;
import eventful.EventfulListener;

public class EditorListener<T> implements EventfulListener
{

	private IEditor<T>		editor;
	private IADController	controller;


	public EditorListener(IEditor<T> editor, IADController controller)
	{
		this.editor = editor;
		this.controller = controller;
	}

	@Override
	public void change()
	{
		controller.editorUpdated(editor);
	}


}