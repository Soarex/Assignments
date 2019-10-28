import org.json.simple.JSONObject;

public enum Causali {
    DEFAULT         (-1),
    BONFICO         (0),
    ACCREDITO       (1),
    BOLLETTINO      (2),
    F24             (3),
    PAGOBANCOMAT    (4);

    int codice;

    Causali(int codice) {
        this.codice = codice;
    }

    public JSONObject serialize() {
        JSONObject o = new JSONObject();
        o.put("Codice", codice);
        return o;
    }

    /*
    public void deserialize(JSONObject o) {
        codice = ((Long)o.get("Codice")).intValue();
    }
     */

    public static Causali deserialize(JSONObject o) {
        int codice = ((Long)o.get("Codice")).intValue();
        switch(codice) {
            case -1:
                return DEFAULT;

            case 0:
                return BONFICO;

            case 1:
                return ACCREDITO;

            case 2:
                return BOLLETTINO;

            case 3:
                return F24;

            case 4:
                return PAGOBANCOMAT;
        }

        return DEFAULT;
    }

    public static String getName(int codice) {
        switch(codice) {
            case -1:
                return "Default";

            case 0:
                return "Bonfico";

            case 1:
                return "Accredito";

            case 2:
                return "Bollettino";

            case 3:
                return "F24";

            case 4:
                return "Pagobancomat";
        }

        return "Errore";
    }
}