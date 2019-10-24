import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaMovimenti {
    private List<Movimento> movimenti;

    public ListaMovimenti() {
        movimenti = new ArrayList<>();
    }

    public ListaMovimenti add(Movimento m) {
        movimenti.add(m);
        return this;
    }

    public JSONArray serialize() {
        JSONArray a = new JSONArray();

        for(Movimento m : movimenti)
            a.add(m.serialize());

        return a;
    }

    public void deserialize(JSONArray a) {
        for(Object o : a) {
            Movimento m = new Movimento();
            m.deserialize((JSONObject) o);
            movimenti.add(m);
        }
    }

    public String toString() {
        String s = "[";

        for(Movimento m : movimenti)
            s += ", " + m;

        s += " ]";

        return s;

    }
}
