package modelo;

import java.util.Date;

public class Compras {
    private int idCompra;
    private int idIngrediente;
    private Date fecha;
    private double cantidadIngredientes;
    private double montoTotal;

    public Compras() {}

    public Compras(int idCompra, int idIngrediente, Date fecha, double cantidadIngredientes, double montoTotal) {
        this.idCompra = idCompra;
        this.idIngrediente = idIngrediente;
        this.fecha = fecha;
        this.cantidadIngredientes = cantidadIngredientes;
        this.montoTotal = montoTotal;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getCantidadIngredientes() {
        return cantidadIngredientes;
    }

    public void setCantidadIngredientes(double cantidadIngredientes) {
        this.cantidadIngredientes = cantidadIngredientes;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra=" + idCompra +
                ", idIngrediente=" + idIngrediente +
                ", fecha=" + fecha +
                ", cantidadIngredientes=" + cantidadIngredientes +
                ", montoTotal=" + montoTotal +
                '}';
    }
}
