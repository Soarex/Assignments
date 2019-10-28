import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Lettore extends Thread {
    private ThreadPoolExecutor threadPool;

    private String filePath;

    private String contoTemporaneo;
    private int quadreAperte;
    private int graffeAperte;

    public Lettore(String filePath) {
        filePath = filePath;
        threadPool = new ThreadPoolExecutor(4, 20, 100L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        contoTemporaneo = "";
    }

    public void run() {
        try {
            JSONParser parser = new JSONParser();
            FileChannel file = new FileInputStream("conti.json").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(512);

            String[] contiElaborati = new String[10];
            for(int i = 0; i < contiElaborati.length; i++)
                contiElaborati[i] = "";

            while(file.read(buffer) != -1) {
                buffer.flip();

                int numeroContiElaborati = elaboraDati(Charset.forName("ISO-8859-1").decode(buffer), contiElaborati);
                for(int i = 0; i < numeroContiElaborati; i++)
                {
                    Conto c = new Conto();
                    c.deserialize((JSONObject)parser.parse(contiElaborati[i]));
                    threadPool.execute(new Contatore(c));
                    contiElaborati[i] = "";
                }

                buffer.clear();
            }

            file.close();
            threadPool.shutdown();

            threadPool.awaitTermination(1L, TimeUnit.SECONDS);

            GestoreOccorrenze.getInstance().printOccorrenze();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int elaboraDati(CharBuffer buffer, String[] res) {
        int indiceInizioConto = 0;
        int contiElaborati = 0;
        for(int i = 0; i < buffer.length(); i++) {
            if(quadreAperte < 2 && buffer.get(i) == '}') {
                graffeAperte--;
                contoTemporaneo += buffer.subSequence(indiceInizioConto, i + 1).toString();
                res[contiElaborati++] = contoTemporaneo;
                contoTemporaneo = "";
            }

            if(quadreAperte < 2 && buffer.get(i) == '{') {
                graffeAperte++;
                indiceInizioConto = i;
            }

            if(buffer.get(i) == '[')
                quadreAperte++;

            if(buffer.get(i) == ']')
                quadreAperte--;
        }

        contoTemporaneo += buffer.subSequence(indiceInizioConto, buffer.length()).toString();
        return contiElaborati;
    }
}
