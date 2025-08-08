package JFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import conexion.ConexionOracle;
import java.util.List;
import modelo.Compras; 
import repositorio.ComprasRepositorio;


public class ComprasFrame extends JFrame {
    private JComboBox<String> ingredientesCombo;
    private JTextField cantidadField, montoField;
    private JButton guardarButton, eliminarButton, listarButton;
    private JTable tablaCompras;
    private DefaultTableModel modeloTabla;
    
    // Se declara una instancia de ComprasRepositorio para centralizar la lógica
    private ComprasRepositorio repositorio;

    public ComprasFrame() {
        setTitle("Gestión de Compras");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Se inicializa el repositorio en el constructor
        repositorio = new ComprasRepositorio();

        // ... [código de creación de componentes, sin cambios] ...
        JLabel ingredienteLabel = new JLabel("Ingrediente:");
        ingredienteLabel.setBounds(10, 10, 100, 25);
        add(ingredienteLabel);

        ingredientesCombo = new JComboBox<>();
        ingredientesCombo.setBounds(110, 10, 200, 25);
        ingredientesCombo.setEditable(true);
        add(ingredientesCombo);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setBounds(10, 50, 100, 25);
        add(cantidadLabel);

        cantidadField = new JTextField();
        cantidadField.setBounds(110, 50, 200, 25);
        add(cantidadField);

        JLabel montoLabel = new JLabel("Monto Total:");
        montoLabel.setBounds(10, 90, 100, 25);
        add(montoLabel);

        montoField = new JTextField();
        montoField.setBounds(110, 90, 200, 25);
        add(montoField);

        guardarButton = new JButton("Guardar Compra");
        guardarButton.setBounds(350, 10, 150, 25);
        add(guardarButton);

        eliminarButton = new JButton("Eliminar Compra");
        eliminarButton.setBounds(350, 50, 150, 25);
        add(eliminarButton);

        listarButton = new JButton("Listar Compras");
        listarButton.setBounds(350, 90, 150, 25);
        add(listarButton);

        modeloTabla = new DefaultTableModel(new String[]{"ID Compra", "ID Ingrediente", "Fecha", "Cantidad", "Monto Total"}, 0);
        tablaCompras = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCompras);
        scrollPane.setBounds(10, 140, 670, 300);
        add(scrollPane);
        
        // Listeners
        guardarButton.addActionListener(e -> guardarCompra());
        listarButton.addActionListener(e -> listarCompras());
        eliminarButton.addActionListener(e -> eliminarCompra());

        tablaCompras.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cargarCompraDesdeTabla();
            }
        });

        setLocationRelativeTo(null);
    }

    private void guardarCompra() {
        String ingredienteTexto = (String) ingredientesCombo.getEditor().getItem();
        if (ingredienteTexto == null || ingredienteTexto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ingrediente.");
            return;
        }

        int idIngrediente;
        try {
            idIngrediente = Integer.parseInt(ingredienteTexto.split(" - ")[0].trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "El ingrediente debe tener un ID válido al inicio (ejemplo: '5 - Azúcar').");
            return;
        }

        double cantidad;
        try {
            cantidad = Double.parseDouble(cantidadField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.");
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(montoField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Monto inválido.");
            return;
        }

        
        Compras compra = new Compras();
        compra.setIdIngrediente(idIngrediente);
        compra.setCantidadIngredientes(cantidad);
        compra.setMontoTotal(monto);
        
       
        if (repositorio.agregarCompra(compra)) {
            JOptionPane.showMessageDialog(this, "Compra guardada correctamente.");
            limpiarCampos();
            listarCompras();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la compra.");
        }
    }

    private void listarCompras() {
        modeloTabla.setRowCount(0);
        // Se llama al repositorio para obtener la lista
        List<Compras> lista = repositorio.listarCompras();
        for (Compras compra : lista) {
            modeloTabla.addRow(new Object[]{
                compra.getIdCompra(),
                compra.getIdIngrediente(),
                compra.getFecha(),
                compra.getCantidadIngredientes(),
                compra.getMontoTotal()
            });
        }
    }

    private void eliminarCompra() {
        int fila = tablaCompras.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una compra para eliminar.");
            return;
        }

        int idCompra = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar esta compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            
            if (repositorio.eliminarCompra(idCompra)) {
                JOptionPane.showMessageDialog(this, "Compra eliminada correctamente.");
                limpiarCampos();
                listarCompras();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la compra.");
            }
        }
    }

    private void cargarCompraDesdeTabla() {
        int fila = tablaCompras.getSelectedRow();
        if (fila != -1) {
            ingredientesCombo.setSelectedItem(modeloTabla.getValueAt(fila, 1) + "");
            cantidadField.setText(modeloTabla.getValueAt(fila, 3).toString());
            montoField.setText(modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    private void limpiarCampos() {
        ingredientesCombo.setSelectedItem("");
        cantidadField.setText("");
        montoField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComprasFrame().setVisible(true));
    }
}



//public class ComprasFrame extends JFrame {
//    private JComboBox<String> ingredientesCombo;
//    private JTextField cantidadField, montoField;
//    private JButton guardarButton, eliminarButton, listarButton;
//    private JTable tablaCompras;
//    private DefaultTableModel modeloTabla;
//
//    public ComprasFrame() {
//        setTitle("Gestión de Compras");
//        setSize(700, 500);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(null);
//
//        JLabel ingredienteLabel = new JLabel("Ingrediente:");
//        ingredienteLabel.setBounds(10, 10, 100, 25);
//        add(ingredienteLabel);
//
//        ingredientesCombo = new JComboBox<>();
//        ingredientesCombo.setBounds(110, 10, 200, 25);
//        ingredientesCombo.setEditable(true);
//        add(ingredientesCombo);
//
//        JLabel cantidadLabel = new JLabel("Cantidad:");
//        cantidadLabel.setBounds(10, 50, 100, 25);
//        add(cantidadLabel);
//
//        cantidadField = new JTextField();
//        cantidadField.setBounds(110, 50, 200, 25);
//        add(cantidadField);
//
//        JLabel montoLabel = new JLabel("Monto Total:");
//        montoLabel.setBounds(10, 90, 100, 25);
//        add(montoLabel);
//
//        montoField = new JTextField();
//        montoField.setBounds(110, 90, 200, 25);
//        add(montoField);
//
//        guardarButton = new JButton("Guardar Compra");
//        guardarButton.setBounds(350, 10, 150, 25);
//        add(guardarButton);
//
//        eliminarButton = new JButton("Eliminar Compra");
//        eliminarButton.setBounds(350, 50, 150, 25);
//        add(eliminarButton);
//
//        listarButton = new JButton("Listar Compras");
//        listarButton.setBounds(350, 90, 150, 25);
//        add(listarButton);
//
//        modeloTabla = new DefaultTableModel(new String[]{"ID Compra", "ID Ingrediente", "Fecha", "Cantidad", "Monto Total"}, 0);
//        tablaCompras = new JTable(modeloTabla);
//        JScrollPane scrollPane = new JScrollPane(tablaCompras);
//        scrollPane.setBounds(10, 140, 670, 300);
//        add(scrollPane);
//
//        // Listeners
//        guardarButton.addActionListener(e -> guardarCompra());
//        listarButton.addActionListener(e -> listarCompras());
//        eliminarButton.addActionListener(e -> eliminarCompra());
//
//        tablaCompras.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                cargarCompraDesdeTabla();
//            }
//        });
//
//        setLocationRelativeTo(null);
//    }
//
//    private void guardarCompra() {
//        String ingredienteTexto = (String) ingredientesCombo.getEditor().getItem();
//        if (ingredienteTexto == null || ingredienteTexto.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Debe ingresar un ingrediente.");
//            return;
//        }
//
//        int idIngrediente;
//        try {
//            
//            idIngrediente = Integer.parseInt(ingredienteTexto.split(" - ")[0].trim());
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "El ingrediente debe tener un ID válido al inicio (ejemplo: '5 - Azúcar').");
//            return;
//        }
//
//        double cantidad;
//        try {
//            cantidad = Double.parseDouble(cantidadField.getText().trim());
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Cantidad inválida.");
//            return;
//        }
//
//        double monto;
//        try {
//            monto = Double.parseDouble(montoField.getText().trim());
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Monto inválido.");
//            return;
//        }
//
//        String sql = "INSERT INTO compras (id_ingrediente, cantidad_ingredientes, monto_total) VALUES (?, ?, ?)";
//
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, idIngrediente);
//            ps.setDouble(2, cantidad);
//            ps.setDouble(3, monto);
//
//            int filas = ps.executeUpdate();
//            if (filas > 0) {
//                JOptionPane.showMessageDialog(this, "Compra guardada correctamente.");
//                limpiarCampos();
//                listarCompras();
//            } else {
//                JOptionPane.showMessageDialog(this, "No se pudo guardar la compra.");
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Error al guardar compra: " + e.getMessage());
//        }
//    }
//
//    private void listarCompras() {
//        modeloTabla.setRowCount(0);
//        String sql = "SELECT id_compra, id_ingrediente, TO_CHAR(fecha, 'DD-MM-YYYY') AS fecha, cantidad_ingredientes, monto_total FROM compras ORDER BY id_compra";
//
//        try (Connection conn = ConexionOracle.conectar();
//             Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery(sql)) {
//
//            while (rs.next()) {
//                modeloTabla.addRow(new Object[]{
//                        rs.getInt("id_compra"),
//                        rs.getInt("id_ingrediente"),
//                        rs.getString("fecha"),
//                        rs.getDouble("cantidad_ingredientes"),
//                        rs.getDouble("monto_total")
//                });
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Error al listar compras: " + e.getMessage());
//        }
//    }
//
//    private void eliminarCompra() {
//        int fila = tablaCompras.getSelectedRow();
//        if (fila == -1) {
//            JOptionPane.showMessageDialog(this, "Selecciona una compra para eliminar.");
//            return;
//        }
//
//        int idCompra = (int) modeloTabla.getValueAt(fila, 0);
//
//        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar esta compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            String sql = "DELETE FROM compras WHERE id_compra = ?";
//
//            try (Connection conn = ConexionOracle.conectar();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {
//
//                ps.setInt(1, idCompra);
//                int filas = ps.executeUpdate();
//
//                if (filas > 0) {
//                    JOptionPane.showMessageDialog(this, "Compra eliminada correctamente.");
//                    limpiarCampos();
//                    listarCompras();
//                } else {
//                    JOptionPane.showMessageDialog(this, "No se pudo eliminar la compra.");
//                }
//
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(this, "Error al eliminar compra: " + e.getMessage());
//            }
//        }
//    }
//
//    private void cargarCompraDesdeTabla() {
//        int fila = tablaCompras.getSelectedRow();
//        if (fila != -1) {
//            ingredientesCombo.setSelectedItem(modeloTabla.getValueAt(fila, 1) + "");
//            cantidadField.setText(modeloTabla.getValueAt(fila, 3).toString());
//            montoField.setText(modeloTabla.getValueAt(fila, 4).toString());
//        }
//    }
//
//    private void limpiarCampos() {
//        ingredientesCombo.setSelectedItem("");
//        cantidadField.setText("");
//        montoField.setText("");
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new ComprasFrame().setVisible(true));
//    }
//}
//
