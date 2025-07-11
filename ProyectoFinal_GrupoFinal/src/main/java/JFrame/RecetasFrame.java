import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import modelo.Receta;
import modelo.Ingrediente;
import repositorio.RecetasRepositorio;
import repositorio.IngredientesRepositorio;

public class RecetasFrame extends JFrame {
    private JTextField txtIdReceta, txtNombre;
    private JComboBox<Ingrediente> comboIngredientes;
    private JButton btnAgregar, btnEditar, btnEliminar;
    private JTable tablaRecetas;
    private RecetasRepositorio repo;
    private IngredientesRepositorio ingredientesRepo;

    public RecetasFrame() {
        setTitle("Gestión de Recetas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        repo = new RecetasRepositorio();
        ingredientesRepo = new IngredientesRepositorio();

        add(new JLabel("ID Receta:"));
        txtIdReceta = new JTextField();
        txtIdReceta.setEditable(false);
        add(txtIdReceta);

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Ingrediente:"));
        comboIngredientes = new JComboBox<>();
        cargarIngredientes();
        add(comboIngredientes);

        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        add(btnAgregar);
        add(btnEditar);
        add(btnEliminar);

        tablaRecetas = new JTable();
        JScrollPane scroll = new JScrollPane(tablaRecetas);
        add(new JLabel("Listado de Recetas:"));
        add(scroll);

        btnAgregar.addActionListener(e -> agregarReceta());
        btnEditar.addActionListener(e -> actualizarReceta());
        btnEliminar.addActionListener(e -> eliminarReceta());
        tablaRecetas.getSelectionModel().addListSelectionListener(e -> cargarRecetaDesdeTabla());

        listarRecetas();
        setVisible(true);
    }

    private void cargarIngredientes() {
        for (Ingrediente ing : ingredientesRepo.listarIngredientes()) {
            comboIngredientes.addItem(ing);
        }
    }

    private void agregarReceta() {
        Ingrediente seleccionado = (Ingrediente) comboIngredientes.getSelectedItem();
        Receta receta = new Receta(txtNombre.getText(), seleccionado.getIdIngrediente());

        if (repo.agregarReceta(receta)) {
            JOptionPane.showMessageDialog(this, "Receta agregada exitosamente.");
            limpiarCampos();
            listarRecetas();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar receta.");
        }
    }

    private void actualizarReceta() {
        try {
            int id = Integer.parseInt(txtIdReceta.getText());
            Ingrediente seleccionado = (Ingrediente) comboIngredientes.getSelectedItem();
            Receta receta = new Receta(id, txtNombre.getText(), seleccionado.getIdIngrediente());

            if (repo.actualizarReceta(receta)) {
                JOptionPane.showMessageDialog(this, "Receta actualizada correctamente.");
                limpiarCampos();
                listarRecetas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar receta.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void eliminarReceta() {
        try {
            int id = Integer.parseInt(txtIdReceta.getText());

            if (repo.eliminarReceta(id)) {
                JOptionPane.showMessageDialog(this, "Receta eliminada correctamente.");
                limpiarCampos();
                listarRecetas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar receta.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void listarRecetas() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Receta");
        model.addColumn("Nombre");
        model.addColumn("ID Ingrediente");

        for (Receta c : repo.listarRecetas()) {
            model.addRow(new Object[]{
                c.getIdReceta(),
                c.getNombre(),
                c.getIdIngrediente()
            });
        }

        tablaRecetas.setModel(model);
    }

    private void cargarRecetaDesdeTabla() {
        int fila = tablaRecetas.getSelectedRow();
        if (fila != -1) {
            txtIdReceta.setText(tablaRecetas.getValueAt(fila, 0).toString());
            txtNombre.setText(tablaRecetas.getValueAt(fila, 1).toString());

            int idIngrediente = Integer.parseInt(tablaRecetas.getValueAt(fila, 2).toString());
            for (int i = 0; i < comboIngredientes.getItemCount(); i++) {
                if (comboIngredientes.getItemAt(i).getIdIngrediente() == idIngrediente) {
                    comboIngredientes.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void limpiarCampos() {
        txtIdReceta.setText("");
        txtNombre.setText("");
        comboIngredientes.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecetasFrame::new);
    }
}
