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

	/**
	* Builds up a Map containg the word/count pair for spam/ham subsets.
	*
	* @param emails Set of Email objects for the training data.
	*/
	public void train(Set<Email> emails) {
		// Initialize spam/ham maps
		this.labelCounts.put("spam", new HashMap<String, Integer>());
		this.labelCounts.put("ham", new HashMap<String, Integer>());

		// Build up word/count map for spam and ham classifications
		int spam = 0;
		for (Email e : emails) {
			if (e.getLabel().equals("spam")) { spam++; }
			
			for (String word : e.getWords().keySet()) {
				Map<String, Integer> counts = this.labelCounts.get(e.getLabel());
				if (counts.containsKey(word)) {
					counts.put(word, e.getWords().get(word) + counts.get(word));
				} else {
					counts.put(word, e.getWords().get(word));
				}
			}
		}

		// Build up priors map
		this.priors.put("spam", ((double)spam) / emails.size());
		this.priors.put("ham", ((double)(emails.size() - spam)) / emails.size());
	}

	public String predict(Email email) {
		return "";
	}
}
