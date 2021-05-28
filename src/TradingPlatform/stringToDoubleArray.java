package TradingPlatform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class stringToDoubleArray {

    public static String dblArr2str(String[][] parentArray) {
        String result = "";
        for (int i = 0; i < parentArray.length; i++) {
            result = result + "[";
            for (int j = 0; j < parentArray[i].length; j++) {
                result = result + parentArray[i][j] + ", ";
            }
            result = result.substring(0, result.length() - 2);
            result = result + "], ";
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }

    public static String[][] str2dblArr(String array) {
        String[] subArrays = array.split("]");
        ArrayList<ArrayList<String>> newArray = new ArrayList<ArrayList<String>>();
        int subArraysCount = subArrays.length;
        for (int i = 0; i < subArraysCount; i++) {
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

        String[][] result = new String[1][subArraysCount];
        for (int i = 0; i < newArray.size(); i++) {
            for (int j = 0; j < newArray.get(i).size(); j++) {
                result[i][j] = newArray.get(i).get(j);
            }
        }

        return result;
    }

    public static void printDblArr(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length;j ++) {
                System.out.println(array[i][j]);
            }
        }
    }
}
