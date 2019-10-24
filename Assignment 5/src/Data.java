import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Data {
    int giorno, mese, anno;

    public Data(int giorno, int mese, int anno) {
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
    }

    public JSONObject serialize() {
        JSONObject o = new JSONObject();
        o.put("Giorno", giorno);
        o.put("Mese", mese);
        o.put("Anno", anno);
        return o;
    }

    public void deserialize(JSONObject o) {
        giorno = ((Long)o.get("Giorno")).intValue();
        mese = ((Long)o.get("Mese")).intValue();
        anno = ((Long)o.get("Anno")).intValue();
    }

    public String toString() {
        return giorno + "/" + mese + "/" + anno;
    }
}
