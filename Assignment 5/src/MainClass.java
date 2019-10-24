import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
    private static List<Conto> conti = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Causali c = Causali.BOLLETTINO;
        testCaricamento();
    }

    public static void testSalvataggio() throws Exception {
        Conto c = new Conto("Conto 1");
        c.addMovimento(new Data(10, 2, 1992), Causali.BOLLETTINO);
        c.addMovimento(new Data(15, 12, 1995), Causali.ACCREDITO);
        c.addMovimento(new Data(12, 3, 1993), Causali.F24);
        conti.add(c);

        c = new Conto("Conto 2");
        c.addMovimento(new Data(20, 1, 1992), Causali.BOLLETTINO);
        c.addMovimento(new Data(30, 12, 1999), Causali.ACCREDITO);
        c.addMovimento(new Data(22, 8, 1993), Causali.F24);
        conti.add(c);

        c = new Conto("Conto 3");
        c.addMovimento(new Data(1, 4, 2012), Causali.PAGOBANCOMAT);
        c.addMovimento(new Data(5, 11, 2017), Causali.ACCREDITO);
        c.addMovimento(new Data(2, 3, 2020), Causali.BONFICO);
        conti.add(c);

        FileWriter f = new FileWriter("conti.json");
        JSONArray a = new JSONArray();
        for(Conto co : conti)
            a.add(co.serialize());

        f.write(a.toJSONString());
        f.flush();
    }

    public static void testCaricamento() throws Exception {
        conti = new ArrayList<>();
        //FileChannel file = new FileInputStream("conti.json").getChannel();
        //ByteBuffer buffer = ByteBuffer.allocate(512);

        Reader f = new FileReader("conti.json");
        JSONParser p = new JSONParser();
        JSONArray a = (JSONArray)p.parse(f);

        int i = 0;
        for(Object o : a) {
            Conto c = new Conto("Conto " + i++);
            c.deserialize((JSONObject) o);
            conti.add(c);
        }

        System.out.println(conti);
        //while(file.read(buffer) != -1) {

        //}

    }
}
