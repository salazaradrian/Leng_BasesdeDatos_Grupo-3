package modelo;

public class Receta {
    private int idReceta;
    private String nombre;
    private int idIngrediente;

    // Constructor vacío
    public Receta() {
    }

    // Constructor con parámetros
    public Receta(int idReceta, String nombre, int idIngrediente) {
        this.idReceta = idReceta;
        this.nombre = nombre;
        this.idIngrediente = idIngrediente;
    }

    // Getters y Setters
    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    // Para mostrar en consola o debug
    @Override
    public String toString() {
        return "Receta{" +
                "idReceta=" + idReceta +
                ", nombre='" + nombre + '\'' +
                ", idIngrediente=" + idIngrediente +
                '}';
    }
}
