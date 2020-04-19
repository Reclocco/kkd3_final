import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class MyDictionary {
    private BiMap<String, Integer> dictionary;
    public int counter;
    private int wordSize;

    public MyDictionary() {
        dictionary = HashBiMap.create();
        counter = 0;
        wordSize = 9;

        for (int i = 0; i < 256; i++) {
            dictionary.put(String.valueOf((char) i), i);
        }
        counter = 256;
    }

    public void addWord(String word) {
        counter++;
        if ((double) counter == Math.pow(2, wordSize)) {
            System.out.println("INCREASING WORD SIZE");
            wordSize++;
        }

        dictionary.put(word, counter);
    }
    public void addWordDec(String word) {
        counter++;
        if ((double) counter+1 == Math.pow(2, wordSize)) {
            System.out.println("INCREASING WORD SIZE");
            wordSize++;
        }

        dictionary.put(word, counter);
    }

    public String getKey(String word) {
        try {
            return fitBit(word);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getWord(int key) {
        return dictionary.inverse().get(key);
    }

    public String fitBit(String word) {
        StringBuilder fit = new StringBuilder(Integer.toBinaryString(dictionary.get(word)));

        int iter = wordSize - fit.length();
        for (int i = 0; i < iter; i++) {
            fit.insert(0, "0");
        }

        return fit.toString();
    }

    public int getWordSize() {
        return wordSize;
    }

    public int getCounter() {
        return counter;
    }
}