import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MyEncoder encoder = new MyEncoder(args[0], args[1]);
        MyDecoder decoder = new MyDecoder(args[1], args[2]);

        encoder.encode();
        decoder.decode();
    }
}
