package autodialog.view.layouts;

import java.util.ArrayList;
import java.util.List;


import autodialog.model.Parameter;

public abstract class AbstractGroupingADLayout implements IADLayout {

	List<String> groups = new ArrayList<>();	//list of groups already handled

	protected abstract void startPanel();
	protected abstract void finishPanel();
	

	
	private List<Parameter<?>> getGroupParams(List<Parameter<?>> params, String group)
	{
		List<Parameter<?>> selected = new ArrayList<>();
		for (Parameter<?> param : params)
		{
			if (param.getGroup() == null) continue;
			if (param.getGroup().equals(group)) selected.add(param);
		}
		return selected;
	}
	

	@Override
	public void addParameters(List<Parameter<?>> params) {
		
		startPanel();
		
		for (Parameter<?> param : params)
		{
		
			if (param.getGroup() == null)
			{	
				addSingleParam(param);
			} 
			else if (!groups.contains(param.getGroup()))
			{
				String groupLabel = param.getGroup();
				
				//create the subpanel
				addParamGroup(getGroupParams(params, groupLabel), groupLabel);
				
				//put this in the list of groups already handled
				groups.add(groupLabel);
			}
		}
		
		finishPanel();
	}


	
	
	
	
	protected abstract void addParamGroup(List<Parameter<?>> params, String title);
	
	protected abstract void addSingleParam(Parameter<?> param);
	
	
	
}
