import java.util.*;

public class Trigram {

        public static List<String> constructTriGrams(String str) {
            List<String> triGrams = new ArrayList<String>();
            for (int i = 0; i < str.length() - 2; i++)
                triGrams.add(str.substring(i, i + 3));

            return triGrams;
        }

}
