package plural.workers;

import fava.signatures.FnEach;

/**
 * PluralEachIndexes are intended for work which cannot be done with a standard mapping. An EachExecutor will call f once with each index in range 
 * @author Nathaniel Sherry, 2010
 *
 * @param <T1> The type of data to map to
 */

public abstract class PluralEachIndex extends AbstractPlural implements FnEach<Integer>
{

	/**
	 * Creates a new PluralEachIndex
	 * 
	 * @param name
	 *            the name of this PluralEachIndex. If a UI visualises this PluralEachIndex or {@link PluralSet}, this will be the
	 *            name assigned to this PluralEachIndex
	 */
	public PluralEachIndex(String name)
	{
		super(name);
	}


	/**
	 * Creates a new PluralEachIndex with no name
	 */
	public PluralEachIndex()
	{
		super();
	}
	
}
