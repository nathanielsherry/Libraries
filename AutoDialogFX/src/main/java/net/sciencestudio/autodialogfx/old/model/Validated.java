package net.sciencestudio.autodialogfx.old.model;

import java.util.List;
import java.util.function.Predicate;

public interface Validated<T> {

	void addValidator(Predicate<T> validator);
	void removeValidator(Predicate<T> validator);
	
	/**
	 * Returns a copy of the list of validators for this Validated
	 */
	List<Predicate<T>> getValidators();
	
	void clearValidators();
	
	default boolean validate(T value) {
		return getValidators().stream().map(v -> v.test(value)).reduce(true, (a, b) -> a & b);
	}
	
	
}
