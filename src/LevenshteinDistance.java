 import java.util.*;

public class LevenshteinDistance {

        public static int compute_Levenshtein_distance(String str1, String str2) {

            int[][] dpTable = new int[str1.length() + 1][str2.length() + 1];

            for (int i = 0; i <= str1.length(); i++)
            {
                for (int j = 0; j <= str2.length(); j++) {

                    if (i == 0) {
                        dpTable[i][j] = j;
                    }

                    else if (j == 0) {
                        dpTable[i][j] = i;
                    }

                    else {
                        dpTable[i][j] = Math.min(dpTable[i - 1][j - 1] + numberOfReplacements(str1.charAt(i - 1),str2.charAt(j - 1)), Math.min(dpTable[i - 1][j] + 1, dpTable[i][j - 1] + 1));
                    }
                }
            }

            return dpTable[str1.length()][str2.length()];
        }

        static int numberOfReplacements(char c1, char c2)
        {
            return c1 == c2 ? 0 : 1;
        }


}
