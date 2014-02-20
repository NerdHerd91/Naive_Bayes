import java.util.Map;
import java.util.HashMap;

public class Email {

	private String eid;
	private String label;
	private Map<String, Integer> words;

	public Email(String eid, String label) {
		this.eid = eid;
		this.label = label;
		this.words = new HashMap<String, Integer>();
	}

	public String getEid() {
		return this.eid;
	}

	public String getLabel() {
		return this.label;
	}

	public void addWord(String word, int count) {
		this.words.put(word, count);
	}

	public Map<String, Integer> getWords() {
		return this.words;
	}
}
