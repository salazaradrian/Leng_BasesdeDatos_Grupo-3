import javax.swing.*;
import java.awt.*;

public class IngredientesFrame extends JFrame {
    private JTextField txtIdIngrediente, txtNombre, txtCantidad, txtIdReceta;
    private JButton btnAgregar, btnEditar, btnEliminar;

    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
        new IngredientesFrame().setVisible(true);
    });
}

    public IngredientesFrame() {
        setTitle("Gestión de Ingredientes");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("ID Ingrediente:"));
        txtIdIngrediente = new JTextField();
        add(txtIdIngrediente);

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        add(txtCantidad);

        add(new JLabel("ID Receta:"));
        txtIdReceta = new JTextField();
        add(txtIdReceta);

        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        add(btnAgregar);
        add(btnEditar);
        add(btnEliminar);

        setVisible(true);
    }
}
