package plural.workers;

import fava.signatures.FnMap;



public abstract class PluralMap<T1, T2> extends AbstractPlural implements FnMap<T1, T2>
{


	/**
	 * Creates a new PluralMap
	 * 
	 * @param name
	 *            the name of this PluralMap. If a UI visualises this PluralMap or {@link PluralSet}, this will be the
	 *            name assigned to this PluralMap
	 */
	public PluralMap(String name)
	{
		super(name);
	}


	/**
	 * Creates a new PluralMap with no name
	 */
	public PluralMap()
	{
		super();
	}


	
}
