import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Classifier {

	public static void main(String[] args) {
		// Parse the smoothing parameter to use.
		int smooth = 1;
		try {
			smooth = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("Please be sure to include a valid smoothing parameter.");
			System.exit(0);
		}
		
		// Parse training and test data into sets.
		Set<Email> trainEmails = parseEmails("./DataSet/train");
		Set<Email> testEmails = parseEmails("./DataSet/test");
		System.out.println(trainEmails.size());
		// Train the data and then predict the classifier
		NaiveBayes nb = new NaiveBayes();
		nb.train(trainEmails);
		int correctPred = 0;

		for (Email e : testEmails) {
			if (e.getLabel().equals(nb.predict(e))) { correctPred++; }
		}
		computeAccuracy(correctPred, testEmails.size());
	}

	/**
	* Parses a file containing emails and corresponding word counts.
	*
	* @param fileName File path to the file containing the emails to parse.
	* @param return Returns a Set of Email objects
	*/
	public static Set<Email> parseEmails(String fileName) {
		Set<Email> emails = new HashSet<Email>();
		try {
			Scanner sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()) {
				Scanner line = new Scanner(sc.nextLine());
				String eid = line.next();
				String label = line.next();
				Email email = new Email(eid, label);

				while (line.hasNext()) {
					String word = line.next();
					int count = line.nextInt();
					email.addWord(word, count);
				}
				emails.add(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emails;
	}

	public static void computeAccuracy(int matches, int total) {
		System.out.println("Test-Data Prediction Statistics");
		System.out.println("-------------------------------");
		System.out.printf("Matches: %d\n", matches);
		System.out.printf("Accuracy: %.2f%%\n\n", 100.0 * matches / total);
	}
}
