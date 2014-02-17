import java.io.File;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Bayes {

	public static void main(String[] args) {
		// Sets that store a list of emails
		Set<Email> trainEmails = new HashSet<Email>();
		Set<Email> testEmails = new HashSet<Email>();

		// Parse training and test data into sets.
		parseEmails(trainEmails, "./DataSet/train");
		parseEmails(testEmails, "./DataSet/test");
	}

	/**
	* Parses a file containing emails and corresponding word counts.
	*
	* @param emails Reference to the set to place emails we create into.
	* @param fileName File path to the file containing the emails to parse.
	*/
	public static void parseEmails(Set<Email> emails, String fileName) {
		try {
			Scanner sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()) {
				String[] tokens = sc.nextLine().split(" ");
				String eid = tokens[0];
				boolean spam = (tokens[1].equals("spam")) ? true : false;
				Email email = new Email(eid, spam, new HashMap<String, Integer>());

				for (int i = 2; i < tokens.length; i+=2) {
					String word = tokens[i];
					int count = Integer.parseInt(tokens[i+1]);
					email.getWords().put(word, count);
				}
				emails.add(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
