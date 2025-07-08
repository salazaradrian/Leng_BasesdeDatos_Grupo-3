package modelo;

public class Receta {
    private int idReceta;
    private String nombre;
    private int idIngrediente;

    public Receta() {}

    public Receta(int idReceta, String nombre, int idIngrediente) {
        this.idReceta = idReceta;
        this.nombre = nombre;
        this.idIngrediente = idIngrediente;
    }

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
}
