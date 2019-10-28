import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainClass {

    /*
        MainClass
        path(String) path del file json
        generateFile(Boolean) true se si vuole generare il file json, false altrimenti
    */
    public static void main(String[] args) throws Exception {
        String path = args[0];
        boolean generateFile = Boolean.parseBoolean(args[1]);

        if(generateFile) generaFile(path);

        GestoreOccorrenze.getInstance();
        Lettore l = new Lettore(path);
        l.start();
    }

    public static void generaFile(String path) throws Exception {
        List<Conto> conti = new ArrayList<>();
        Conto c = new Conto("Conto 1");
        c.addMovimento(new Data(10, 2, 2017), Causali.BOLLETTINO);
        c.addMovimento(new Data(15, 12, 2018), Causali.ACCREDITO);
        c.addMovimento(new Data(12, 3, 2018), Causali.F24);
        conti.add(c);

        c = new Conto("Conto 2");
        c.addMovimento(new Data(20, 1, 2018), Causali.BOLLETTINO);
        c.addMovimento(new Data(30, 12, 2019), Causali.ACCREDITO);
        c.addMovimento(new Data(22, 8, 2019), Causali.F24);
        conti.add(c);

        c = new Conto("Conto 3");
        c.addMovimento(new Data(1, 4, 2017), Causali.PAGOBANCOMAT);
        c.addMovimento(new Data(5, 11, 2017), Causali.ACCREDITO);
        c.addMovimento(new Data(2, 3, 2019), Causali.BONFICO);
        conti.add(c);

        FileChannel file = new FileOutputStream(path).getChannel();
        JSONArray a = new JSONArray();
        for(Conto co : conti)
            a.add(co.serialize());

        file.write(Charset.forName("ISO-8859-1").encode(a.toJSONString()));
        file.close();
    }

}
