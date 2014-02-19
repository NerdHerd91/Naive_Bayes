import java.io.File;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Bayes {

	public static void main(String[] args) {
		// Parse the smoothing parameter to use.
		int smooth = 1;
		try {
			smooth = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("Please be sure to include a valid smoothing parameter.");
			System.exit(0);
		}
		
		// Sets that store a list of emails
		Map<String, Map<String, Integer>> trainEmails = new HashMap<String, Map<String, Integer>>();
		Map<String, Map<String, Integer>> testEmails = new HashMap<String, Map<String, Integer>>();

		// Parse training and test data into sets.
		parseEmails(trainEmails, "./DataSet/train");
		parseEmails(testEmails, "./DataSet/test");
	}

	/**
	* Parses a file containing emails and corresponding word counts.
	*
	* @param emails Reference to the map to place spam/non-spam word counts.
	* @param fileName File path to the file containing the emails to parse.
	*/
	public static void parseEmails(Map<String, Map<String, Integer>> emails, String fileName) {
		try {
			// Initialize the spam/ham word count maps.
			emails.put("spam", new HashMap<String, Integer>());
			emails.put("ham", new HashMap<String, Integer>());

			Scanner sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()) {
				Scanner line = new Scanner(sc.nextLine());
				line.next();
				String label = line.next();

				while (line.hasNext()) {
					String word = line.next();
					int count = line.nextInt();

					if (emails.get(label).containsKey(word)) {
						int prev = emails.get(label).get(word);
						emails.get(label).put(word, prev +count);
					} else {
						emails.get(label).put(word, count);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
