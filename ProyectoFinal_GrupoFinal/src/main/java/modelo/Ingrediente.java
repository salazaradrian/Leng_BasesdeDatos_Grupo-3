package modelo;

public class Ingrediente {
    private int idIngrediente;
    private String nombre;

    public Ingrediente(int idIngrediente, String nombre) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre; // Para mostrar en el JComboBox
    }
}
