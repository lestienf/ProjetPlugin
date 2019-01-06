package plugin;

public interface Plugin {
	
	/**
	 * Transform the text
	 * @param s the text tranformed
	 * @return
	 */
	public String transform(String s);

	public String getLabel();

	public String helpMessage();
}