import java.io.File;
import java.util.*;

public class NLP
{
    public Word_Map wmap;
    private int total_num_of_doc = 0;

    /*You should not use File_Map class in this file since only word hash map is aware of it.
    In fact, you can define the File_Map class as a nested class in Word_Map,
     but for easy evaluation we defined it separately.
     If you need to access the File_Map instances, write wrapper methods in Word_Map class.
    * */

    /* Reads the dataset from the given dir and created a word map */
    public void readDataset(String dir) {
        if (wmap == null)
            wmap = new Word_Map();

        // Reads all dataset files in path if there is no file return
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    int index = 0;
                    try {
                        Scanner sc2 = new Scanner(new File(dir + "/" + file.getName()));
                        // read file word by word then put to Word_Map word and it's index
                        while (sc2.hasNextLine()) {
                            Scanner s2 = new Scanner(sc2.nextLine());
                            while (s2.hasNext()) {
                                String s = s2.next();
                                String word = s.trim().replaceAll("\\p{Punct}", "");
                                wmap.put(word, new MyEntry<String,Integer>(file.getName(), index));
                                index++;
                            }
                        }
                        total_num_of_doc++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("There is no file in path: " + dir);
        }
    }


    /*Finds all the bigrams starting with the given word*/
    public List<String> bigrams(String word) {
        if (word == null || word.equals(""))
            return null;

        List<String> bi_grams = new ArrayList<>();

        if(wmap.containsKey(word)){
            File_Map fileMap = (File_Map) wmap.get(word);
            Iterator it = wmap.iterator();
            while (it.hasNext()) {
                Word_Map.Node current_node = (Word_Map.Node) it.next();

                label:
                for (String text_name : fileMap.fnames) {
                    List<Integer> word_occurance = (List<Integer>) fileMap.get(text_name);

                    for (Integer index : word_occurance) {
                        if (current_node.value.containsKey(text_name) && current_node.value.containsValue(new MyEntry<String, Integer>(text_name, (index + 1) ))) {
                            bi_grams.add(word + " " + current_node.key);
                            break label;
                        }
                    }
                }
            }
        } else
            return null;

        return bi_grams;
    }


    /*Calculates the tfIDF value of the given word for the given file */
    public float tfIDF(String word, String fileName) {
        if (word == null || word.equals("") || fileName == null || fileName.equals("")) {
            System.out.print("Error! Null value for the given word or for the given fileName");
            return 0f;
        }

        int total_num_word =0, total_num_terms = 0, num_of_doc_with_term = 0;
        float TF, IDF;


        File_Map file_map = (File_Map) wmap.get(word);
        if (file_map != null && file_map.containsKey(fileName)) {
            total_num_word = ( (List<Integer>) file_map.get(fileName)).size();
            num_of_doc_with_term = file_map.fnames.size();
        }

        try {
            Scanner sc2 = new Scanner(new File("dataset/"+fileName));

            while (sc2.hasNextLine()) {
                Scanner s2 = new Scanner(sc2.nextLine());
                while (s2.hasNext()) {
                    String s = s2.next();
                    total_num_terms++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TF = (float) total_num_word / (float)total_num_terms;

        IDF = (float) Math.log((float) total_num_of_doc / (float)num_of_doc_with_term );

        return (TF*IDF);
    }

    /* Print the WordMap by using its iterator */
    public  void printWordMap() {
        Iterator it = wmap.iterator();

        while (it.hasNext()){
            Word_Map.Node current_node = (Word_Map.Node) it.next();
            System.out.println("Word : " + current_node.key);
            File_Map fileMap = current_node.value;
            System.out.println("File Map : ");
            for (int i = 0; i < fileMap.fnames.size(); i++) {
                System.out.println(fileMap.fnames.get(i) + " " + fileMap.occurances.get(i));
            }
            System.out.println();
        }

    }

}
