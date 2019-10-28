public class GestoreOccorrenze {
    private static GestoreOccorrenze instance;

    private int[] occorrenze;

    private GestoreOccorrenze() {
        occorrenze = new int[Causali.values().length - 1];
    }

    public static GestoreOccorrenze getInstance() {
        if(instance == null) instance = new GestoreOccorrenze();
        return instance;
    }

    public synchronized void incrementaOccorrenza(int codiceOccorrenza) {
        occorrenze[codiceOccorrenza]++;
    }

    public synchronized String toString() {
        String s = "";

        int i = 0;
        for(int count : occorrenze) {
            s += "Occorrenze " + Causali.getName(i) + ": " + count + "\n";
            i++;
        }

        return s;
    }

    public synchronized void printOccorrenze() {
        System.out.println(this);
    }
}
