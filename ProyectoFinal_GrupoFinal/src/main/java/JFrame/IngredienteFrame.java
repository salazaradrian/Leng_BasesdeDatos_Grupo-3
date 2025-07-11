import modelo.Ingrediente;
import repositorio.IngredienteRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class IngredienteFrame extends JFrame {
    private JTextField nombreField;
    private JTable tablaIngredientes;
    private DefaultTableModel modeloTabla;
    private IngredienteRepositorio repositorio;

    public IngredienteFrame() {
        setTitle("Gestión de Ingredientes");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        repositorio = new IngredienteRepositorio();

        JLabel nombreLabel = new JLabel("Nombre del ingrediente:");
        nombreLabel.setBounds(20, 20, 200, 25);
        add(nombreLabel);

        nombreField = new JTextField();
        nombreField.setBounds(20, 45, 300, 25);
        add(nombreField);

        JButton agregarBtn = new JButton("Agregar");
        agregarBtn.setBounds(330, 45, 100, 25);
        add(agregarBtn);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tablaIngredientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaIngredientes);
        scrollPane.setBounds(20, 90, 440, 200);
        add(scrollPane);

        JButton editarBtn = new JButton("Editar");
        editarBtn.setBounds(20, 300, 100, 25);
        add(editarBtn);

        JButton eliminarBtn = new JButton("Eliminar");
        eliminarBtn.setBounds(130, 300, 100, 25);
        add(eliminarBtn);

        cargarIngredientes();

        agregarBtn.addActionListener(e -> agregarIngrediente());
        editarBtn.addActionListener(e -> editarIngrediente());
        eliminarBtn.addActionListener(e -> eliminarIngrediente());
        
        setLocationRelativeTo(null); 
    }

    private void cargarIngredientes() {
        modeloTabla.setRowCount(0);
        List<Ingrediente> lista = repositorio.listarIngredientes();
        for (Ingrediente ing : lista) {
            modeloTabla.addRow(new Object[]{ing.getIdIngrediente(), ing.getNombre()});
        }
    }

    private void agregarIngrediente() {
        String nombre = nombreField.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
            return;
        }

        Ingrediente ing = new Ingrediente();
        ing.setNombre(nombre);

        if (repositorio.agregarIngrediente(ing)) {
            JOptionPane.showMessageDialog(this, "Ingrediente agregado.");
            nombreField.setText("");
            cargarIngredientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar ingrediente.");
        }
    }

    private void editarIngrediente() {
        int fila = tablaIngredientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un ingrediente para editar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", modeloTabla.getValueAt(fila, 1));
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            Ingrediente ing = new Ingrediente(id, nuevoNombre.trim());
            if (repositorio.actualizarIngrediente(ing)) {
                JOptionPane.showMessageDialog(this, "Ingrediente actualizado.");
                cargarIngredientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar.");
            }
        }
    }

    private void eliminarIngrediente() {
        int fila = tablaIngredientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un ingrediente para eliminar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar ingrediente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (repositorio.eliminarIngrediente(id)) {
                JOptionPane.showMessageDialog(this, "Ingrediente eliminado.");
                cargarIngredientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IngredienteFrame().setVisible(true));
    }
}
