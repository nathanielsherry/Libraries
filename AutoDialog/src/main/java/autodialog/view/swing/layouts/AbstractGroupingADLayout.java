package autodialog.view.swing.layouts;

import java.util.ArrayList;
import java.util.List;

import autodialog.model.Parameter;
import autodialog.view.editors.IEditor;

public abstract class AbstractGroupingADLayout extends AbstractADLayout {

	List<String> groups = new ArrayList<>();	//list of groups already handled

	protected abstract void startPanel();
	protected abstract void finishPanel();
	protected int level;

	
	private List<IEditor<?>> getGroupParams(List<IEditor<?>> editors, String group)
	{
		List<IEditor<?>> selected = new ArrayList<>();
		for (IEditor<?> editor : editors)
		{
			if (editor.getParameter().getGroup(level) == null) continue;
			if (editor.getParameter().getGroup(level).equals(group)) selected.add(editor);
		}
		return selected;
	}
	

	@Override
	public void addEditors(List<IEditor<?>> editors) {
		
		startPanel();
		
		for (IEditor<?> editor : editors)
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


	
	
	
	
	protected abstract void addEditorGroup(List<IEditor<?>> editors, String title);
	
	protected abstract void addSingleEditor(IEditor<?> param);
	
	
	
}
