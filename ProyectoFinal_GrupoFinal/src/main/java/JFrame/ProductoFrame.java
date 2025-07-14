/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JFrame;

/**
 *
 * @author PC
 */

import modelo.Producto;
import repositorio.ProductoRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;



public class ProductoFrame extends JFrame {
    
    private JTextField txtIdProducto, txtNombre,txttipo, txtDescripcion,txtPrecio,txtidreceta, txtcantidad;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar;
    private JTable tablaProductos;
    private ProductoRepositorio repo;
    
    
public ProductoFrame() {
        setTitle("Gestión de Producto");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        repo = new ProductoRepositorio();

        // Etiquetas
        addLabel("ID Producto:", 20, 20);
        addLabel("Nombre:", 20, 60);
        addLabel("Tipo:", 20, 100);
        addLabel("Descripcion:", 20, 140);
        addLabel("Precio:", 20, 180);
        addLabel("ID Receta:", 20, 220);
        addLabel("Cantidad:", 20, 260);
       

        // Campos de texto
        txtIdProducto = addTextField(150, 20);
        txtIdProducto.setEditable(false); // ID no editable
        txtNombre = addTextField(150, 60);
        txttipo = addTextField(150, 100);
        txtPrecio = addTextField(150, 140);
        txtDescripcion = addTextField(150, 180);
        txtidreceta = addTextField(150, 220);
        txtidreceta.setEditable(false);
        txtcantidad = addTextField (150, 260);
        
       
        // Botones
        btnAgregar = addButton("Agregar", 400, 60);
        btnActualizar = addButton("Actualizar", 400, 100);
        btnEliminar = addButton("Eliminar", 400, 140);
        btnListar = addButton("Listar", 400, 180);

        // Tabla
        tablaProductos = new JTable();
        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBounds(20, 320, 840, 170);
        add(scroll);

        // Eventos
        btnAgregar.addActionListener(e -> agregarProducto());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnListar.addActionListener(e -> listarProductos());

        // Evento para cargar datos al hacer clic en la tabla
        tablaProductos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                cargarProductosDesdeTabla();
            }
        });

        listarProductos();
        setVisible(true);
        setLocationRelativeTo(null); 
    }

    private void agregarProducto() {
        
        Producto producto = new Producto(
            0,
            txtNombre.getText(),
            txttipo.getText(),   
            txtDescripcion.getText(), 
            Double.parseDouble(txtPrecio.getText()),
            0,
            Integer.parseInt(txtcantidad.getText()));
                
           
        

        if (repo.agregarProducto(producto)) {
            JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.");
            limpiarCampos();
            listarProductos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar Producto.");
        }
    }

    private void actualizarProducto() {
        try {
            int id = Integer.parseInt(txtIdProducto.getText());

            Producto producto = new Producto(
                0,
            txtNombre.getText(),
            txttipo.getText(),
            txtDescripcion.getText(),
            Double.parseDouble(txtPrecio.getText()),
            0,       
            Integer.parseInt(txtcantidad.getText()));
                
              

            if (repo.actualizarProducto(producto)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                limpiarCampos();
                listarProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar Producto.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID invalido.");
        }
    }

    private void eliminarProducto() {
        try {
            int id = Integer.parseInt(txtIdProducto.getText());

            if (repo.eliminarProducto(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                limpiarCampos();
                listarProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar Producto.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID invalido.");
        }
    }

    private void listarProductos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Tipo");
        model.addColumn("Descripción");
        model.addColumn("Precio");
        model.addColumn("ID Receta");
        model.addColumn("cantidad");
        ;

        for (Producto p : repo.listarProductos()) {
            model.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.gettipo(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getidreceta(),
                p.getcantidad()   
            });
        }

        tablaProductos.setModel(model);
    }

    private void cargarProductosDesdeTabla() {
        int fila = tablaProductos.getSelectedRow();
        if (fila != -1) {
            txtIdProducto.setText(tablaProductos.getValueAt(fila, 0).toString());
            txtNombre.setText(tablaProductos.getValueAt(fila, 1).toString());
            txttipo.setText(tablaProductos.getValueAt(fila, 2).toString());
            txtPrecio.setText(tablaProductos.getValueAt(fila, 3).toString());
            txtDescripcion.setText(tablaProductos.getValueAt(fila,4).toString());
            txtidreceta.setText(tablaProductos.getValueAt(fila, 5).toString());
            txtcantidad.setText(tablaProductos.getValueAt(fila, 6).toString());

        }
    }

    private void limpiarCampos() {
        txtIdProducto.setText("");
        txtNombre.setText("");
        txttipo.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtidreceta.setText("");
        txtcantidad.setText("");

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
        SwingUtilities.invokeLater(ProductoFrame::new);
    }
}
