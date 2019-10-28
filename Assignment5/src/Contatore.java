public class Contatore implements Runnable {
    private Conto conto;

    public Contatore(Conto conto) {
        this.conto = conto;
    }

    public void run() {
        for (Movimento m : conto)
            GestoreOccorrenze.getInstance().incrementaOccorrenza(m.getCodiceCausale());
    }
}