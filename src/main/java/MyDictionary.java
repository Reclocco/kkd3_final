import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;

import java.util.Dictionary;
import java.util.Hashtable;

public class MyDictionary {
    //    private Dictionary<java.io.Serializable, Integer> dictionary;
    private BiMap<String, Integer> dictionary;
    private int counter;
    private int wordSize;
//    private MyDecoder decoder = null;

//    public MyDictionary(MyDecoder decoder){
//        this.decoder = decoder;
//        dictionary = new Hashtable<>();
//        counter = 0;
//        wordSize = 9;
//
//        for(int i=0; i<256; i++){
//            dictionary.put(String.valueOf((char) i), i);
//        }
//        counter = 256;
//    }

    public MyDictionary() {
//        dictionary = new Hashtable<>();
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