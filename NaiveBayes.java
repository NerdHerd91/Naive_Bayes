import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class NaiveBayes {
	private Map<String, Map<String, Integer>> labelCounts;
	private Map<String, Map<String, Double>> labelProbs;
	private Map<String, Double> priors;
	private Set<String> vocab;
	private int smooth;

	public NaiveBayes(int smooth) {
		this.labelCounts = new HashMap<String, Map<String, Integer>>();
		this.labelProbs = new HashMap<String, Map<String, Double>>();
		this.priors = new HashMap<String, Double>();
		this.vocab = new HashSet<String>();
		this.smooth = smooth;
	}

	/**
	* Trains the data by first building up a Map from label->(word->count)),
	* Next it builds up the Map of prior probabilities,
	* Then it computes the sum of total word positions for each label,
	* Finally it build up a Map from label->(word->probability)).
	*
	* @param emails Set of Email objects for the training data.
	*/
	public void train(Set<Email> emails) {
		this.computeLabelCounts(emails);
		this.computePriors(emails.size());

		// Compute the total number of word positions per label.
		// Create a set of all words in our vocabulary.
		Map<String, Integer> positions = new HashMap<String, Integer>();
		for (String label : labelCounts.keySet()) {
			int total = 0;
			for (String word : labelCounts.get(label).keySet()) {
				total += labelCounts.get(label).get(word);
				vocab.add(word);
			}
			positions.put(label, total);
		}
		this.computeWordProbs(positions);
	}

	/**
	* Predicts the classification of the Email datapoint (ie spam/ham).
	*
	* @param email Email object for the email we wish to predict on.
	* @return Returns a String representing the predicted Classification.
	*/
	public String predict(Email email) {
		String classifier = null;
		double prob = Integer.MIN_VALUE;

		for (String label : labelProbs.keySet()) {
			double p = priors.get(label);
			for (String word : email.getWords().keySet()) {
				// Uses additive logs since probabilities are so small they will 
				// often drop to about 0 when multiplied together. Also support
				// case for when word doesn't exist.
				if (labelProbs.get(label).containsKey(word)) {
					p += Math.log(labelProbs.get(label).get(word)) * email.getWords().get(word); 
				} else {
					p += Math.log(labelProbs.get(label).get("")) * email.getWords().get(word); 
				}
			}
			if (prob < p) {
				classifier = label;
				prob = p;
			}
		}
		return classifier;
	}

	/**
	* Helper method for computing the Map from label->(word->count)).
	*
	* @param emails Set of email objects for the training data.
	*/
	private void computeLabelCounts(Set<Email> emails) {
		for (Email e : emails) {
			if (!this.labelCounts.containsKey(e.getLabel())) {
				this.labelCounts.put(e.getLabel(), new HashMap<String, Integer>());
			}

			// Update a total count of labels seen.
			if (this.priors.containsKey(e.getLabel())) {
				this.priors.put(e.getLabel(), this.priors.get(e.getLabel()) + 1.0);
			} else {
				this.priors.put(e.getLabel(), 1.0);
			}

			// Update the count of each word seen per label.
			for (String word : e.getWords().keySet()) {
				Map<String, Integer> counts = this.labelCounts.get(e.getLabel());
				if (counts.containsKey(word)) {
					counts.put(word, e.getWords().get(word) + counts.get(word));
				} else {
					counts.put(word, e.getWords().get(word));
				}
			}
		}
	}

	/**
	* Helper method for computing the prior probabilites of each label,
	* Prior probabilities are stored in the priors map.
	*
	* @param total Integer representing total number of emails processed.
	*/
	private void computePriors(int total) {
		for (String label : this.priors.keySet()) {
			this.priors.put(label, this.priors.get(label) / total);
		}
	}

	/**
	* Helper method for building up a Map from label->(word->probability)).
	*
	* @param positions Map from positions to total number of word counts.
	*/
	private void computeWordProbs(Map<String, Integer> positions) {
		for (String label : this.labelCounts.keySet()) {
			Map<String, Double> probs = new HashMap<String, Double>();

			// Denominator is: N * (|Vocabulary| + 1) * smooth.
			double den = positions.get(label) + this.smooth * (vocab.size() + 1);
			for (String word : this.labelCounts.get(label).keySet()) {
				// Numerator is: N_word + smooth.
				double num = this.labelCounts.get(label).get(word) + this.smooth;
				probs.put(word, num / den);
			}
			// Default probability for when the word doesn't exist in the vocab.
			probs.put("", this.smooth / den);
			this.labelProbs.put(label, probs);
		}
	}
}
