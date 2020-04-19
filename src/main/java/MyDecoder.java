import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyDecoder {
    String encoded;
    String decoded;
    MyDictionary dictionary;

    public MyDecoder() throws IOException {
        dictionary = new MyDictionary();

        encoded = "C:\\Users\\micha\\Desktop\\Projekty\\4sem\\kkd3_maven\\encoded.txt";
        decoded = "C:\\Users\\micha\\Desktop\\Projekty\\4sem\\kkd3_maven\\decoded.txt";
    }
    public void decode() throws IOException {
        StringBuilder codeBuilder = new StringBuilder();

        byte[] fileContent = Files.readAllBytes(Paths.get(encoded));

        for(byte b: fileContent){
            int num = b + 128;
            StringBuilder bin = new StringBuilder(Integer.toBinaryString(num));

            int how_many_0 = 8-bin.length();
            for(int i=0; i<how_many_0; i++){
                bin.insert(0, "0");
            }

            codeBuilder.append(bin);
        }

        String code = codeBuilder.toString();

        System.out.println("DECODED: " + code);
        System.out.println("DECODED LEN: " + code.length());

        OutputStream os = new FileOutputStream(decoded);

        int idx=0;
        String prev = null;
//        dictionary.counter++;

        while(true){
            try{
                int id = Integer.parseInt(code.substring(idx, idx+dictionary.getWordSize()), 2);
                idx+=dictionary.getWordSize();

                System.out.print("Word ID: " + id + "\n");
                try{
                    System.out.print("WORD: " + dictionary.getWord(id) + "\n");
                    for(char e: dictionary.getWord(id).toCharArray())
                        os.write(e);

                    if(prev != null) {
                        System.out.print("IM ADDING TO DICT: " + prev + dictionary.getWord(id).charAt(0) + ", with no: " + (dictionary.getCounter()+1) + "\n");
                        dictionary.addWordDec(prev + dictionary.getWord(id).charAt(0));
                    }

                } catch (NullPointerException e) {
                    assert prev != null;
                    System.out.print("ADDING TO DICT: " + prev + prev.charAt(0) + ", with no: " + (dictionary.getCounter()+1) + "\n");
                    dictionary.addWordDec(prev + prev.charAt(0));
//                    id = Integer.parseInt(code.substring(idx, idx+dictionary.getWordSize()), 2);
                    System.out.println("WORD: " + dictionary.getWord(id));

                    for(char c: dictionary.getWord(id).toCharArray())
                        os.write(c);
                }
                prev = dictionary.getWord(id);
                System.out.println("\n");

            } catch (StringIndexOutOfBoundsException e){
                break;
            }
        }

        os.close();
    }
}
