import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class MyDictionary {
    private BiMap<String, Integer> dictionary;
//    private BiMap<String, int[]> stat;
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
            wordSize++;
        }

        dictionary.put(word, counter);
//        stat.put(word, new int[]{counter, 1});
    }

//    public void incWeight(String word){
//        int[] key = stat.get(word);
//        key[1]++;
//        stat.replace(word, key);
//    }

//    public double getEntropy(){
//        double entropy = 0;
//        int sum = 0;
//        for(int[] key: stat.values())
//            sum += key[1];
//
//        for(int[] key: stat.values())
//            entropy -= key[1]*1.0/sum * (Math.log(key[1]*1.0/sum));
//
//        return entropy;
//    }

    public void addWordDecode(String word) {
        counter++;
        if ((double) counter+1 == Math.pow(2, wordSize)) {
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
}