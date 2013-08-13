package bolt.scripting;

import bolt.scripting.languages.Language;
import fava.signatures.FnCondition;

public class BoltCondition<T1> extends BoltMap<T1, Boolean> implements FnCondition<T1>
{

	public BoltCondition(Language language, String inputName, String outputName, String script)
	{
		super(language, inputName, outputName, script);
	}

}
