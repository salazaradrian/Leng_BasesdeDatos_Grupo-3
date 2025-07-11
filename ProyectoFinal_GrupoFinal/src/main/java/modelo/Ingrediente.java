package modelo;

public class Ingrediente {
    private int idIngrediente;
    private String nombre;
    private int cantidad;

    // Constructor vacío
    public Ingrediente() {
    }

    // Constructor con parámetros
    public Ingrediente(int idIngrediente, String nombre) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    // Getters y Setters
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    

    // Para mostrar en consola o debug
    @Override
public String toString() {
    return "Ingrediente{" +
            "idIngrediente=" + idIngrediente +
            ", nombre='" + nombre + '\'' +
            ", cantidad=" + cantidad +
            '}';
}
}
