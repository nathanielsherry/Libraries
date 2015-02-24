package net.sciencestudio.autodialogfx.model.group;

import java.util.List;

import net.sciencestudio.autodialogfx.model.Model;

public interface Group extends Model<List<Model<?>>> {

	Group add(Model<?> model);
	Group addAll(Model<?>... models);
	Group addAll(List<Model<?>> models);
	
	List<Model<?>> getModels();
	
}
