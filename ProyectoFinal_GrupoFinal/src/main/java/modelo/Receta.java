package modelo;

public class Receta {
    private int idReceta;
    private String nombre;
    private int idIngrediente;

    // Constructor para agregar nueva receta (sin ID)
    public Receta(String nombre, int idIngrediente) {
        this.nombre = nombre;
        this.idIngrediente = idIngrediente;
    }

    // Constructor para actualizar receta (con ID)
    public Receta(int idReceta, String nombre, int idIngrediente) {
        this.idReceta = idReceta;
        this.nombre = nombre;
        this.idIngrediente = idIngrediente;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }
}
