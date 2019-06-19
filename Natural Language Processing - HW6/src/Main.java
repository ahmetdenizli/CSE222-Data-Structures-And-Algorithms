import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // create NLP object
        NLP nlp_obj = new NLP();

        nlp_obj.readDataset("dataset");

        // Prints the WordMap of dataset files
        nlp_obj.printWordMap();

        // Reads input.txt for queries
        try {
            Scanner sc2 = new Scanner(new File("input.txt"));

            // if input.txt is not null then prints queries results orderly
            while (sc2.hasNextLine()) {
                Scanner s2 = new Scanner(sc2.nextLine());
                if (s2.hasNext()) {
                    String query = s2.next();
                    switch (query) {
                        case "bigram": {
                            String word = s2.next();
                            System.out.println( nlp_obj.bigrams(word) );
                            break;
                        }
                        case "tfidf": {
                            String word = s2.next();
                            String filename = s2.next();

                            System.out.println( nlp_obj.tfIDF(word, filename) );
                            break;
                        }
                    }
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
