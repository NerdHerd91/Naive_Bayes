import java.util.Map;

public class Email {
	private String eid;
	private boolean spam;
	private Map<String, Integer> words;

	public Email(String eid, boolean spam, Map<String, Integer> words) {
		this.eid = eid;
		this.spam = spam;
		this.words = words;
	}

	public String getEid() {
		return this.eid;
	}

	public boolean getSpam() {
		return this.spam;
	}

	public Map<String, Integer> getWords() {
		return words;
	}
}
