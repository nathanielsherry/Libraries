package autodialog.view.swing.layouts;

import java.util.ArrayList;
import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.editors.Editor;

public abstract class AbstractGroupingADLayout extends AbstractADLayout {

	List<String> groups = new ArrayList<>();	//list of groups already handled

	protected abstract void startPanel();
	protected abstract void finishPanel();
	protected int level;

	
	private List<Editor<?>> getGroupParams(List<Editor<?>> editors, String group)
	{
		List<Editor<?>> selected = new ArrayList<>();
		for (Editor<?> editor : editors)
		{
			if (editor.getParameter().getGroup(level) == null) continue;
			if (editor.getParameter().getGroup(level).equals(group)) selected.add(editor);
		}
		return selected;
	}
	

	@Override
	public void addEditors(List<Editor<?>> editors) {
		
		startPanel();
		
		for (Editor<?> editor : editors)
		{
		
			if (editor.getParameter().getGroup(level) == null)
			{	
				addSingleEditor(editor);
			} 
			else if (!groups.contains(editor.getParameter().getGroup(level)))
			{
				String groupLabel = editor.getParameter().getGroup(level);
				
				//create the subpanel
				addEditorGroup(getGroupParams(editors, groupLabel), groupLabel);
				
				//put this in the list of groups already handled
				groups.add(groupLabel);
			}
		}
		
		finishPanel();
	}


	
	
	
	
	protected abstract void addEditorGroup(List<Editor<?>> editors, String title);
	
	protected abstract void addSingleEditor(Editor<?> param);
	
	
	
}
