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
    private JComboBox<String> ingredientesCombo;
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

        JLabel ingredienteLabel = new JLabel("Ingrediente:");
        ingredienteLabel.setBounds(10, 65, 150, 25);
        add(ingredienteLabel);

        ingredientesCombo = new JComboBox<>();
        ingredientesCombo.setBounds(10, 90, 310, 25);
        add(ingredientesCombo);

        guardarButton = new JButton("Guardar receta");
        guardarButton.setBounds(400, 60, 310, 25);
        add(guardarButton);
        
        
        eliminarButton = new JButton("Eliminar receta");
        eliminarButton.setBounds( 400,100 , 310, 25);
        add(eliminarButton);

        
        
        
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "ID Ingrediente"}, 0);
        tablaRecetas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaRecetas);
        scrollPane.setBounds(20, 150, 540, 230);
        add(scrollPane);


        cargarIngredientes();
        cargarRecetas();

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
        
        setLocationRelativeTo(null); 
    }

    private void cargarIngredientes() {
        try (Connection conn = ConexionOracle.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_ingrediente, nombre FROM ingredientes")) {

            while (rs.next()) {
                ingredientesCombo.addItem(rs.getInt("id_ingrediente") + " - " + rs.getString("nombre"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar ingredientes: " + e.getMessage());
        }
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
            ingredientesCombo.setSelectedIndex(-1);
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar receta.");
        }
    }
}

    
    
    private void cargarRecetas() {
        modeloTabla.setRowCount(0);
       List<Receta> lista = recetasRepo.listarRecetas(); 
        for (Receta r : lista) {
            modeloTabla.addRow(new Object[]{r.getIdReceta(), r.getNombre(), r.getIdIngrediente()});
        }
    }
    
    private void guardarReceta() {
        String nombreReceta = nombreField.getText().trim();
        if (nombreReceta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
            return;
        }

        String seleccionado = (String) ingredientesCombo.getSelectedItem();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un ingrediente.");
            return;
        }

        int idIngrediente = Integer.parseInt(seleccionado.split(" - ")[0]);

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO recetas (nombre, id_ingrediente) VALUES (?, ?)")) {

            ps.setString(1, nombreReceta);
            ps.setInt(2, idIngrediente);
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
        // Cargar nombre
        nombreField.setText(tablaRecetas.getValueAt(fila, 1).toString());

        // Cargar ingrediente en el combo
        int idIngrediente = Integer.parseInt(tablaRecetas.getValueAt(fila, 2).toString());

        for (int i = 0; i < ingredientesCombo.getItemCount(); i++) {
            String item = ingredientesCombo.getItemAt(i);
            if (item.startsWith(idIngrediente + " -")) {
                ingredientesCombo.setSelectedIndex(i);
                break;
            }
        }
    }
}
        
        

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RecetasFrame().setVisible(true);
        });
    }
    
    
}

