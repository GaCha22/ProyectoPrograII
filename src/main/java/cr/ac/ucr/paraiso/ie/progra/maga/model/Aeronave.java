package cr.ac.ucr.paraiso.ie.progra.maga.model;

public class Aeronave {
    private String placa;
    private int tipo;
    private final int COMERCIAL = 1;
    private final int CARGA= 2;
    private final int AVIONETA = 3;
    private int estado = 3;

    public Aeronave(String placa, int tipo) {
        this.placa = placa;
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public int getTipo() {
        return tipo;
    }

    public int getEstado() {
        return this.estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Placa: " + this.placa +
                ", Tipo: " +
                (this.tipo == COMERCIAL ? "COMERCIAL" : this.tipo == CARGA ? "CARGA" : "AVIONETA");
    }
}