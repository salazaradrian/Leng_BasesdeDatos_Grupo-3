import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import modelo.Receta;
import repositorio.RecetasRepositorio;


public class RecetasFrame extends JFrame {
    private JTextField txtIdReceta, txtNombre, txtIdIngrediente;
    private JButton btnAgregar, btnEditar, btnEliminar;
    private JTable tablaRecetas;
    private RecetasRepositorio repo;
 
   
    public RecetasFrame() {
        setTitle("Gestión de Recetas");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));
        
        repo = new RecetasRepositorio();

        add(new JLabel("ID Receta:"));
        txtIdReceta = new JTextField();
        add(txtIdReceta);
        txtIdReceta.setEditable(false); // ID no editable;;

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
        
        // Tabla
        tablaRecetas = new JTable();
        JScrollPane scroll = new JScrollPane(tablaRecetas);
        scroll.setBounds(20, 320, 840, 170);
        add(scroll);
        
         // Eventos
        btnAgregar.addActionListener(e -> agregarReceta());
        btnEditar.addActionListener(e -> actualizarReceta());
        btnEliminar.addActionListener(e -> eliminarReceta());
       
    }
    
private void agregarReceta() {
        Receta receta = new Receta(
            txtNombre.getText(),    
        );

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

            Receta receta = new Receta(
                id,
                txtNombre.getText(),
               
            );

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
                JOptionPane.showMessageDialog(this, "Error al eliminar cliente.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void listarRecetas() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Receta");
        model.addColumn("Nombre");
        model.addColumn("Primer Apellido");


        for (Receta c : repo.listarRecetas()) {
            model.addRow(new Object[]{
                c.getIdReceta(),
                c.getNombre(),
            });
        }

        tablaRecetas.setModel(model);
    }

    private void cargarRecetaDesdeTabla() {
        int fila = tablaRecetas.getSelectedRow();
        if (fila != -1) {
            txtIdReceta.setText(tablaRecetas.getValueAt(fila, 0).toString());
            txtNombre.setText(tablaRecetas.getValueAt(fila, 1).toString());
        
        }
    }

    private void limpiarCampos() {
        txtIdReceta.setText("");
        txtNombre.setText("");
     
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 120, 25);
        add(label);
    }

    private JTextField addTextField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 200, 25);
        add(field);
        return field;
    }

    private JButton addButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 120, 30);
        add(button);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecetasFrame::new);
    }
}

