import javax.swing.*;
import java.awt.*;

public class RecetasFrame extends JFrame {
    private JTextField txtIdReceta, txtNombre, txtIdIngrediente;
    private JButton btnAgregar, btnEditar, btnEliminar;

    public RecetasFrame() {
        setTitle("Gestión de Recetas");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("ID Receta:"));
        txtIdReceta = new JTextField();
        add(txtIdReceta);

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("ID Ingrediente:"));
        txtIdIngrediente = new JTextField();
        add(txtIdIngrediente);

        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        add(btnAgregar);
        add(btnEditar);
        add(btnEliminar);

        setVisible(true);
    }
}

