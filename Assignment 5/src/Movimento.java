import org.json.simple.JSONObject;

public class Movimento {
    private Data data;
    private Causali causale;

    public Movimento() {
        data = new Data(0, 0, 0);
        causale = Causali.ACCREDITO;
    }

    public Movimento(Data data, Causali causale) {
        this.causale = causale;
        this.data = data;
    }

    public JSONObject serialize() {
        JSONObject o = new JSONObject();
        o.put("Data", data.serialize());
        o.put("Causale", causale.serialize());
        return o;
    }

    public void deserialize(JSONObject o) {
        data.deserialize((JSONObject)o.get(("Data")));
        causale = Causali.deserialize((JSONObject)o.get(("Causale")));
    }

    public String toString() {
        return "{" + data + ", " + causale + "}";
    }
}
