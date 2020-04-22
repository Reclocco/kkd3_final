import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class MyEncoder {
    String raw;
    String encoded;
    MyDictionary dictionary;
    HashMap<Integer, Integer> statRaw;
    HashMap<Integer, Integer> statEncode;

    public MyEncoder(String raw, String encoded) {
        dictionary = new MyDictionary();

        statRaw = new HashMap<>();
        for(int i = -128; i < 128; i++){
            statRaw.put(i, 0);
        }

        statEncode = new HashMap<>();
        for(int i = -128; i < 128; i++){
            statEncode.put(i, 0);
        }

//        raw = "C:\\Users\\micha\\Desktop\\Projekty\\4sem\\kkd3_maven\\raw.txt";
//        encoded = "C:\\Users\\micha\\Desktop\\Projekty\\4sem\\kkd3_maven\\encoded.txt";

        this.raw = raw;
        this.encoded = encoded;
    }

    private void printStats(){
        File file1 = new File(raw);
        File file2 = new File(encoded);

        float sum = 0;
        float entropyRaw = 0;

        for(int w: statRaw.values()) {
            sum += w;
        }

        for(int w: statRaw.values()) {
            if(w!=0)
                entropyRaw -= w / sum * (Math.log(w / sum));
        }

        sum = 0;
        float entropyEncoded = 0;

        for(int w: statEncode.values())
            sum += w;

        for(int w: statEncode.values()) {
            if(w!=0)
                entropyEncoded -= w / sum * (Math.log(w / sum));
        }

        System.out.println("Entropia surowego: " + entropyRaw);
        System.out.println("Entropia zakodowanego: " + entropyEncoded);
        System.out.println("Kompresja: " + 1.0*file2.length()/file1.length());
        System.out.println("Długość surowego pliku: " + file1.length());
        System.out.println("Długość zakodowanego pliku: " + file2.length());
    }

    public void encode() throws IOException {
        StringBuilder codeBuilder = new StringBuilder();

        byte[] fileContent = Files.readAllBytes(Paths.get(raw));

        for(byte b: fileContent){
            int count = statRaw.get((int) b);
            statRaw.put((int) b, count + 1);
        }

        int idx = 0;
        StringBuilder text = new StringBuilder();

        while(idx < fileContent.length){
            text.append((char) fileContent[idx]);
            idx++;

            if(dictionary.getKey(text.toString()) == null){
                codeBuilder.append(dictionary.getKey(text.toString().substring(0, text.length()-1)));
                dictionary.addWord(text.toString());
                text = new StringBuilder();
                idx--;
            }
        }

        if(text.length() != 0)
            codeBuilder.append(dictionary.getKey(text.toString()));

        int dingling = (8 - codeBuilder.length() % 8) % 8;
        for(int i=0; i<dingling; i++){
            codeBuilder.append("0");
        }

        String code = codeBuilder.toString();
        OutputStream os = new FileOutputStream(encoded);

        for(int i=0; i<code.length()/8; i++){
            int num = Integer.parseInt(code.substring(i*8, i*8+8), 2)-128;

            int count = statEncode.get(num);
            statEncode.put(num, count + 1);

            byte b = (byte) num;
            os.write(b);
        }

        os.close();
        printStats();
    }
}
