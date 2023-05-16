import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception{
        Map<String, Integer> map = new HashMap<String, Integer>();

        File file = new File("src/minidico.txt");
        File file2 = new File("src/fautes.txt");
        Scanner sc = new Scanner(file);
        Scanner sc2 = new Scanner(file2);

        while (sc.hasNextLine()) {
            //System.out.println(sc.nextLine());
            map.put(sc.nextLine(), 1);
        }

        while (sc2.hasNextLine()) {
            //System.out.println(sc.nextLine());
            String wordToTest = sc2.nextLine();

            if (map.get(wordToTest) != null){
                System.out.println("Le mot " + wordToTest + " est correctement orthographié et ne nécessite aucune correction");
            } else {
                System.out.println("Le mot " +wordToTest+ " est incorrect, voulez-vous dire...");

                List<String> listOfTrigrams = Trigram.constructTriGrams(wordToTest);
                Map<String, List<String>> trigramsMap = new HashMap<String, List<String>>();

                for (int i=0; i<listOfTrigrams.size(); i++){
                    trigramsMap.put(listOfTrigrams.get(i), new ArrayList<>());
                }

                List<String> listOfCloseWords = new ArrayList<>();

                for (int i=0; i<listOfTrigrams.size(); i++){
                    int index = 0;
                    for (String key : map.keySet()) {
                        if (key.contains(listOfTrigrams.get(i))){
                            if (!listOfCloseWords.contains(key)){
                                listOfCloseWords.add(key);
                            }
                            trigramsMap.get(listOfTrigrams.get(i)).add(index, key);
                            index++;
                            List<String> updatedList = trigramsMap.get(listOfTrigrams.get(i));
                            trigramsMap.put(listOfTrigrams.get(i), updatedList);
                        }
                    }
                }




                // Calculer le nombre d'occurences de chaque mot dans la liste listOfCloseWords et tout stocker dans une hashmap


                Map<String, Integer> occurencesMap = new HashMap<String, Integer>();

                for (int index1=0; index1<listOfCloseWords.size(); index1++){
                    int numberOfOccurences = 0;
                    for (List<String> value : trigramsMap.values()) {
                        for (int index2=0; index2<value.size(); index2++){
                            if (value.get(index2) == listOfCloseWords.get(index1)){
                                numberOfOccurences ++;
                            }
                        }
                    }
                    occurencesMap.put(listOfCloseWords.get(index1), numberOfOccurences);
                }



                List<Map.Entry<String, Integer>> sortedNOccurencesList = keepNClosestWords(100, sortByDescendingValue((HashMap<String, Integer>) occurencesMap));
                List<Map.Entry<String, Integer>> suggestedWords = suggestFiveWords(sortedNOccurencesList, wordToTest);
                printSuggestedWords(suggestedWords);




            /*
            for (Map.Entry<String, List<String>> entry : trigramsMap.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                System.out.print(key + "--------->");
                for (int j=0; j<value.size(); j++){
                    System.out.print(value.get(j) + " ");
                }
                System.out.println("");
            }

             */
            /*
            System.out.println("");
            System.out.println("");
            System.out.println("");

            for (int z=0; z<listOfCloseWords.size(); z++){
                System.out.println(listOfCloseWords.get(z));
            }
            System.out.println(sortedNOccurencesList.size());

             */


            /*
            System.out.println("");
            System.out.println("");
            System.out.println("Les occurences:");

            for (Map.Entry<String, Integer> entry : sortedNOccurencesList) {
                System.out.println(entry.getKey()+" -------> "+ entry.getValue());
            }

             */

            /*
            for (Map.Entry<String, Integer> entry : sortedOccurencesMap.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                System.out.print(key + "--------->");
                System.out.print(value);
                System.out.println("");
            }
             */
            }
        }



        /*
        System.out.println("dysfonctionnement "+ LevenshteinDistance.compute_Levenshtein_distance("dysfonctionnement", wordToTest));
        System.out.println("intentionnellement "+LevenshteinDistance.compute_Levenshtein_distance("intentionnellement", wordToTest));
        System.out.println("impertinemment "+LevenshteinDistance.compute_Levenshtein_distance("impertinemment", wordToTest));
        System.out.println("démantèlement "+LevenshteinDistance.compute_Levenshtein_distance("démantèlement", wordToTest));
        System.out.println("sciemment "+LevenshteinDistance.compute_Levenshtein_distance("sciemment", wordToTest));
        System.out.println("traditionnelle "+LevenshteinDistance.compute_Levenshtein_distance("traditionnelle", wordToTest));
        System.out.println("gravitationnel "+LevenshteinDistance.compute_Levenshtein_distance("gravitationnel", wordToTest));

         */
        /*
        long begin = System.nanoTime();
        long end = System.nanoTime();
        long duration = end - begin;
        System.out.println(duration);

         */






        /*

        String s1 = "benyam";
        String s2 = "ephrem";

        long begin = System.nanoTime();
        System.out.println(LevenshteinDistance.compute_Levenshtein_distance(s1, s2));

        long end = System.nanoTime();
        long duration = end - begin;
        System.out.println(duration);

        List<String> trigrams = Trigram.constructTriGrams(s1);
        for (String trigram : trigrams){
            System.out.println(trigram);
        }

         */

    }



    public static List<Map.Entry<String, Integer>> sortByDescendingValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        return list;
        /*
        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;

         */
    }

    public static List<Map.Entry<String, Integer>> sortByAscendingValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        return list;
        /*
        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;

         */
    }


    public static List<Map.Entry<String, Integer>> keepNClosestWords(int n, List<Map.Entry<String, Integer>> sortedOccurencesList){
        int count = 0;
        List<Map.Entry<String, Integer> > nClosestWords = new LinkedList<Map.Entry<String, Integer>>();
        for (Map.Entry<String, Integer> entry : sortedOccurencesList) {
            if (count < n){
                nClosestWords.add(entry);
                count++;
            }
        }
        return nClosestWords;
    }


    public static List<Map.Entry<String, Integer>> suggestFiveWords(List<Map.Entry<String, Integer>> nClosestWords, String str){
        List<Map.Entry<String, Integer>> suggestedWords = new LinkedList<Map.Entry<String, Integer>>();;
        int counter = 0;
        Map<String, Integer> levenshteinMap = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : nClosestWords) {
            levenshteinMap.put(entry.getKey(), LevenshteinDistance.compute_Levenshtein_distance(entry.getKey(), str));
        }

        List<Map.Entry<String, Integer>> sortedDistances = sortByAscendingValue((HashMap<String, Integer>) levenshteinMap);
        for (Map.Entry<String, Integer> entry1 : sortedDistances) {
            if (counter < 5){
                suggestedWords.add(entry1);
                counter++;
            }
        }
        return suggestedWords;
    }

    public static void printSuggestedWords(List<Map.Entry<String, Integer>> list){
        System.out.print("[ ");
        for (Map.Entry<String, Integer> ent : list) {
            System.out.print(ent.getKey()+"  ");
        }
        System.out.println("]");
        System.out.println("");

    }

}