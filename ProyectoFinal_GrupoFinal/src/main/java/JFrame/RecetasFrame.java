package JFrame;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import conexion.ConexionOracle;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Receta;
import repositorio.RecetasRepositorio;

public class RecetasFrame extends JFrame {
    private JTextField nombreField;
    private JButton guardarButton;
    private DefaultTableModel modeloTabla;
    private JTable tablaRecetas;
    private RecetasRepositorio recetasRepo; 
    private final JButton eliminarButton;

    public RecetasFrame() {
        setTitle("Gestión de Recetas");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        
        recetasRepo = new RecetasRepositorio(); 

        JLabel nombreLabel = new JLabel("Nombre de la receta:");
        nombreLabel.setBounds(10, 10, 150, 25);
        add(nombreLabel);

        nombreField = new JTextField();
        nombreField.setBounds(10, 35, 310, 25);
        add(nombreField);

        guardarButton = new JButton("Guardar");
        guardarButton.setBounds(400, 30, 150, 25);
        add(guardarButton);
        
        eliminarButton = new JButton("Eliminar");
        eliminarButton.setBounds(400, 60, 150, 25);
        add(eliminarButton);

        JButton listarButton = new JButton("Listar");
        listarButton.setBounds(400, 90, 150, 25);
        add(listarButton);

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBounds(400, 120, 150, 25);
        add(actualizarButton);

        listarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarRecetas();
            }
        });

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tablaRecetas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaRecetas);
        scrollPane.setBounds(20, 200, 540, 230);
        add(scrollPane);

        guardarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarReceta();
            }
        });
        
        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarReceta();
            }
        });

        actualizarButton.addActionListener(e -> actualizarReceta());

        tablaRecetas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cargarRecetaDesdeTabla();
            }
        });

        setLocationRelativeTo(null); 
    }

    private void eliminarReceta() {
        int fila = tablaRecetas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una receta para eliminar.");
            return;
        }

        int id = (int) tablaRecetas.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar esta receta?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (recetasRepo.eliminarReceta(id)) {
                JOptionPane.showMessageDialog(this, "Receta eliminada correctamente.");
                cargarRecetas(); 
                nombreField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar receta.");
            }
        }
    }

    private void cargarRecetas() {
        modeloTabla.setRowCount(0);
        List<Receta> lista = recetasRepo.listarRecetas(); 
        for (Receta r : lista) {
            modeloTabla.addRow(new Object[]{r.getIdReceta(), r.getNombre()});
        }
    }
    
    private void guardarReceta() {
        String nombreReceta = nombreField.getText().trim();
        if (nombreReceta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
            return;
        }

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO recetas (nombre) VALUES (?)")) {

            ps.setString(1, nombreReceta);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Receta guardada correctamente.");
            nombreField.setText("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar receta: " + e.getMessage());
        }
    }
    
    private void cargarRecetaDesdeTabla() {
        int fila = tablaRecetas.getSelectedRow();
        if (fila != -1) {
            nombreField.setText(tablaRecetas.getValueAt(fila, 1).toString());
        }
    }

    private void actualizarReceta() {
        int fila = tablaRecetas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una receta para actualizar.");
            return;
        }

        int id = (int) tablaRecetas.getValueAt(fila, 0);
        String nombreReceta = nombreField.getText().trim();

        if (nombreReceta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
            return;
        }

        Receta receta = new Receta();
        receta.setIdReceta(id);
        receta.setNombre(nombreReceta);

        if (recetasRepo.actualizarReceta(receta)) {
            JOptionPane.showMessageDialog(this, "Receta actualizada correctamente.");
            cargarRecetas();
            nombreField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar receta.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RecetasFrame().setVisible(true);
        });
    }
}
