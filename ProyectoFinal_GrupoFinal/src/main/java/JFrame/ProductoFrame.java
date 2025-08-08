package JFrame;

import modelo.Producto;
import repositorio.ProductoRepositorio;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import conexion.ConexionOracle;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import repositorio.RecetasRepositorio;

public class ProductoFrame extends JFrame {
    private JTextField nombreField, tipoField, precioField, descripcionField, cantidadField;
    private JComboBox<String> comboRecetas;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private ProductoRepositorio repo;

    public ProductoFrame() {
        setTitle("Gestión de Productos");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        repo = new ProductoRepositorio();

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(20, 20, 100, 25);
        add(nombreLabel);

        nombreField = new JTextField();
        nombreField.setBounds(120, 20, 200, 25);
        add(nombreField);

        JLabel tipoLabel = new JLabel("Tipo:");
        tipoLabel.setBounds(20, 60, 100, 25);
        add(tipoLabel);

        tipoField = new JTextField();
        tipoField.setBounds(120, 60, 200, 25);
        add(tipoField);

        JLabel precioLabel = new JLabel("Precio:");
        precioLabel.setBounds(20, 100, 100, 25);
        add(precioLabel);

        precioField = new JTextField();
        precioField.setBounds(120, 100, 200, 25);
        add(precioField);

        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionLabel.setBounds(20, 140, 100, 25);
        add(descripcionLabel);

        descripcionField = new JTextField();
        descripcionField.setBounds(120, 140, 200, 25);
        add(descripcionField);

        JLabel recetaLabel = new JLabel("Receta:");
        recetaLabel.setBounds(20, 180, 100, 25);
        add(recetaLabel);

        comboRecetas = new JComboBox<>();
        comboRecetas.setBounds(120, 180, 200, 25);
        add(comboRecetas);
               cargarRecetas(); 

//        JLabel cantidadLabel = new JLabel("Cantidad:");
//        cantidadLabel.setBounds(20, 220, 100, 25);
//        add(cantidadLabel);

//        cantidadField = new JTextField();
//        cantidadField.setBounds(120, 220, 200, 25);
//        add(cantidadField);

        JButton agregarBtn = new JButton("Agregar");
        agregarBtn.setBounds(400, 20, 120, 30);
        add(agregarBtn);

        JButton editarBtn = new JButton("Actualizar");
        editarBtn.setBounds(400, 60, 120, 30);
        add(editarBtn);

        JButton eliminarBtn = new JButton("Eliminar");
        eliminarBtn.setBounds(400, 100, 120, 30);
        add(eliminarBtn);

        JButton listarBtn = new JButton("Listar");
        listarBtn.setBounds(400, 140, 120, 30);
        add(listarBtn);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Tipo", "Precio", "Descripción", "ID Receta"}, 0);
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBounds(20, 270, 840, 260);
        add(scrollPane);

        listarBtn.addActionListener(e -> cargarProductos());
        agregarBtn.addActionListener(e -> agregarProducto());
        editarBtn.addActionListener(e -> editarProducto());
        eliminarBtn.addActionListener(e -> eliminarProducto());

        tablaProductos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cargarProductoDesdeTabla();
            }
        });

        

        setVisible(true);
    }

   private void cargarRecetas() {
    RecetasRepositorio recetasRepo = new RecetasRepositorio();
    List<String> recetas = recetasRepo.obtenerRecetas();
    comboRecetas.removeAllItems();
    for (String receta : recetas) {
        comboRecetas.addItem(receta);
    }
}


    

    private void cargarProductos() {
        modeloTabla.setRowCount(0);
        List<Producto> lista = repo.listarProductos();
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                    p.getIdProducto(),
                    p.getNombre(),
                    p.getTipo(),
                    p.getPrecio(),
                    p.getDescripcion(),
                    p.getIdReceta(),
                    
            });
        }
    }


    private void agregarProducto() {
        try {
            String nombre = nombreField.getText().trim();
            String tipo = tipoField.getText().trim();
            double precio = Double.parseDouble(precioField.getText().trim());
            String descripcion = descripcionField.getText().trim();
//            int cantidad = Integer.parseInt(cantidadField.getText().trim());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                return;
            }

            String recetaSeleccionada = (String) comboRecetas.getSelectedItem();
            Integer idReceta = null;
            if (recetaSeleccionada != null && !recetaSeleccionada.isEmpty()) {
                idReceta = Integer.parseInt(recetaSeleccionada.split(" - ")[0]);
            }

            Producto p = new Producto(0, nombre, tipo, precio, descripcion, idReceta);
            if (repo.agregarProducto(p)) {
                JOptionPane.showMessageDialog(this, "Producto agregado.");
                limpiarCampos();
                cargarProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar producto.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio inválido.");
        }
    }

    private void editarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para editar.");
            return;
        }

        try {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            String nombre = nombreField.getText().trim();
            String tipo = tipoField.getText().trim();
            double precio = Double.parseDouble(precioField.getText().trim());
            String descripcion = descripcionField.getText().trim();
//            int cantidad = Integer.parseInt(cantidadField.getText().trim());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                return;
            }

            String recetaSeleccionada = (String) comboRecetas.getSelectedItem();
            Integer idReceta = null;
            if (recetaSeleccionada != null && !recetaSeleccionada.isEmpty()) {
                idReceta = Integer.parseInt(recetaSeleccionada.split(" - ")[0]);
            }

            Producto p = new Producto(id, nombre, tipo, precio, descripcion, idReceta);
            if (repo.actualizarProducto(p)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado.");
                limpiarCampos();
                cargarProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar producto.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio o cantidad inválidos.");
        }
    }

    private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (repo.eliminarProducto(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado.");
                limpiarCampos();
                cargarProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar producto.");
            }
        }
    }

    private void limpiarCampos() {
        nombreField.setText("");
        tipoField.setText("");
        precioField.setText("");
        descripcionField.setText("");
//        cantidadField.setText("");
        comboRecetas.setSelectedIndex(-1);
    }

    private void cargarProductoDesdeTabla() {
        int fila = tablaProductos.getSelectedRow();
        if (fila != -1) {
            nombreField.setText(modeloTabla.getValueAt(fila, 1).toString());
            tipoField.setText(modeloTabla.getValueAt(fila, 2).toString());
            precioField.setText(modeloTabla.getValueAt(fila, 3).toString());
            descripcionField.setText(modeloTabla.getValueAt(fila, 4).toString());
//            cantidadField.setText(modeloTabla.getValueAt(fila, 6).toString());

            int idReceta = (modeloTabla.getValueAt(fila, 5) != null) ? (int) modeloTabla.getValueAt(fila, 5) : -1;
            for (int i = 0; i < comboRecetas.getItemCount(); i++) {
                String item = comboRecetas.getItemAt(i);
                if (item.startsWith(idReceta + " -")) {
                    comboRecetas.setSelectedIndex(i);
                    break;
                }
            }
        } 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductoFrame::new);
}
}