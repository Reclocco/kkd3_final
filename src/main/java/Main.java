import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MyEncoder encoder = new MyEncoder();
        MyDecoder decoder = new MyDecoder();

        encoder.encode();
        decoder.decode();

//        MyDictionary dictionary = new MyDictionary();
//
//        System.out.println(dictionary.getKey("u"));
//        System.out.println(dictionary.getKey("uv"));
//        System.out.println(dictionary.getKey("u"));
//        System.out.println(dictionary.getKey("ug"));
//
//        System.out.println(("0123456789").substring(0, 9));
//
//        BiMap<String, String> biMap = HashBiMap.create();
//
//        biMap.put("k1", "v1");
//        biMap.put("k2", "v2");
//
//        System.out.println("k1 = " + biMap.get("k1"));
//        System.out.println("v2 = " + biMap.inverse().get("v2"));
    }
}
