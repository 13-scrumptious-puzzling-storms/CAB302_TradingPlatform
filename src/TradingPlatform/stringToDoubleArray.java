package TradingPlatform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class stringToArray {

    public static ArrayList<ArrayList<String>> str2dblArr(String array) {
        String[] subArrays = array.split("]");
        ArrayList<ArrayList<String>> newArray = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < subArrays.length; i++) {
            String[] data = subArrays[i].split(", \"");
            ArrayList<String> newData = new ArrayList<String>();
            for (int j = 0; j < data.length; j++) {
                data[j] = data[j].replace("[", "");
                data[j] = data[j].replace(", ", "");
                data[j] = data[j].replace("\"", "");
                newData.add(data[j]);
            }
            newArray.add(newData);
        }
        return newArray;
    }

    public static void test() throws IOException, ClassNotFoundException {
        ArrayList<ArrayList<String>> test = str2dblArr("[[\"test\", \"like\", \"this\"], [\"1\", \"2\", \"3\", \"4\"], [\"yo\", \"what\", \"are\", \"you\"]]");
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }

        for (int i = 0; i < test.size(); i++) {
            for (int j = 0; j < test.get(i).size();j ++) {
                System.out.println(test.get(i).get(j));
            }
        }

    }
}
