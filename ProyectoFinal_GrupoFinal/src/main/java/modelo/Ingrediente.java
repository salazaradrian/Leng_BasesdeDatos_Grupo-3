package modelo;

public class Ingrediente {
    private int idIngrediente;
    private String nombre;
    private double cantidad;
    private int idReceta;

    public Ingrediente() {}

    public Ingrediente(int idIngrediente, String nombre, double cantidad, int idReceta) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.idReceta = idReceta;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }
}
