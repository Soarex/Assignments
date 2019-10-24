import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Conto {
    private String nomeCorrentista;
    private ListaMovimenti movimenti;

    public Conto(String nomeCorrentista) {
        this.nomeCorrentista = nomeCorrentista;
        movimenti = new ListaMovimenti();
    }

    public Conto addMovimento(Movimento m) {
        movimenti.add(m);
        return this;
    }

    public Conto addMovimento(Data d, Causali c) {
        movimenti.add(new Movimento(d, c));
        return this;
    }

    public JSONObject serialize() {
        JSONObject o = new JSONObject();
        o.put("Nome Correntista", nomeCorrentista);
        o.put("Movimenti", movimenti.serialize());
        return o;
    }

    public void deserialize(JSONObject o) {
        nomeCorrentista = (String)o.get("Nome Correntista");
        movimenti.deserialize((JSONArray)o.get("Movimenti"));
    }

    public String toString() {
        return "{ " + nomeCorrentista + ", " + movimenti + "}";
    }



}
