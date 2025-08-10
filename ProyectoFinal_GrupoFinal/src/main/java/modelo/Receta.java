package modelo;

public class Receta {
    private int idReceta;
    private String nombre;

    // Constructor vacío
    public Receta() {
    }

    // Constructor con parámetros
    public Receta(int idReceta, String nombre) {
        this.idReceta = idReceta;
        this.nombre = nombre;
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

    // Para mostrar en consola o debug
    @Override
    public String toString() {
        return "Receta{" +
                "idReceta=" + idReceta +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
