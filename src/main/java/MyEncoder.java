import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyEncoder {
    String raw;
    String encoded;
    MyDictionary dictionary;

    public MyEncoder() throws IOException {
        dictionary = new MyDictionary();

        raw = "C:\\Users\\micha\\Desktop\\Projekty\\4sem\\kkd3_maven\\raw.txt";
        encoded = "C:\\Users\\micha\\Desktop\\Projekty\\4sem\\kkd3_maven\\encoded.txt";
    }

    private void printStats(){
        File file1 = new File(raw);
        File file2 = new File(encoded);

//        System.out.println("Entropia: " + huffTree.getEntropy());
//        System.out.println("Kompresja: " + 1.0*file2.length()/file1.length());
//        System.out.println("Srednia długość słowa kodowego: " + huffTree.getAverage());
    }

    public void encode() throws IOException {
        StringBuilder codeBuilder = new StringBuilder();

        byte[] fileContent = Files.readAllBytes(Paths.get(raw));

        int idx = 0;
//        String prev = null;
        StringBuilder text = new StringBuilder();
//        System.out.println("text: " + text);

        while(idx < fileContent.length){
            text.append((char) fileContent[idx]);
            idx++;

            if(dictionary.getKey(text.toString()) == null){
//                System.out.println("not found: " + text.toString());
//                System.out.println("encoding text: " + text.toString().substring(0, text.length()-1) + ", with code: " + dictionary.getKey(text.toString().substring(0, text.length()-1)) + ", dec: " + Integer.parseInt(dictionary.getKey(text.toString().substring(0, text.length()-1)), 2));
                codeBuilder.append(dictionary.getKey(text.toString().substring(0, text.length()-1)));
//                System.out.println("adding to dict: " + text.toString() + ", with num: " + (dictionary.getCounter()+1));
                dictionary.addWord(text.toString());
                text = new StringBuilder();
                idx--;
            }
//            else {
//                System.out.println("found in dictionary: " + text.toString());
//            }
        }

        if(text.length() != 0)
            codeBuilder.append(dictionary.getKey(text.toString()));

        int dingling = codeBuilder.length() % 8;
        for(int i=0; i<dingling; i++){
            codeBuilder.append("0");
        }

        String code = codeBuilder.toString();
        OutputStream os = new FileOutputStream(encoded);

        for(int i=0; i<code.length()/8; i++){
            int num = Integer.parseInt(code.substring(i*8, i*8+8), 2)-128;
            byte b = (byte) num;
            os.write(b);
        }

//        System.out.println("ENCODED: " + code);

        os.close();
        printStats();
    }
}
