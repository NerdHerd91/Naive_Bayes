import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class NaiveBayes {
	private Map<String, Map<String, Integer>> labelCounts;
	private Map<String, Double> priors;

	public NaiveBayes() {
		this.labelCounts = new HashMap<String, Map<String, Integer>>();
		this.priors = new HashMap<String, Double>();
	}

	public void train(Set<Email> emails) {
		// DO STUFF
	}

	public String predict(Email email) {
		return "";
	}
}
