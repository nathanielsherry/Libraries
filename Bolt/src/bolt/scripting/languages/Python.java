package bolt.scripting.languages;

public class Python extends Language{

	@Override
	public String getName() {
		return "python";
	}

	@Override
	public boolean isCompilable() {
		return true;
	}

}
