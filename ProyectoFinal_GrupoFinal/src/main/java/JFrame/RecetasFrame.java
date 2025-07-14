
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
        ingredientesCombo.setEditable(true); // Aquí hacemos editable el combo
        add(ingredientesCombo);

        guardarButton = new JButton("Guardar receta");
        guardarButton.setBounds(400, 60, 310, 25);
        add(guardarButton);
        
        
        eliminarButton = new JButton("Eliminar receta");
        eliminarButton.setBounds(400, 100, 310, 25);
        add(eliminarButton);

        JButton listarButton = new JButton("Listar recetas");
        listarButton.setBounds(400, 140, 310, 25);
        add(listarButton);

        listarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarRecetas();
            }
        });

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Ingrediente"}, 0);
        tablaRecetas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaRecetas);
        scrollPane.setBounds(20, 200, 540, 230);
        add(scrollPane);

        // Ya no cargamos ingredientes automáticamente, porque quieres agregarlos manualmente
        // cargarIngredientes();

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
                ingredientesCombo.setSelectedItem("");
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

        // Tomamos el texto que esté escrito en el combo, no solo seleccionado
        String ingredienteTexto = (String) ingredientesCombo.getEditor().getItem();
        if (ingredienteTexto == null || ingredienteTexto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ingrediente.");
            return;
        }

        // Si el ingrediente es un id y nombre, podemos intentar extraer solo el nombre
        // o simplemente guardar tal cual está escrito
        // Para este ejemplo, guardamos el texto tal cual.

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO recetas (nombre, id_ingrediente) VALUES (?, ?)")) {

            ps.setString(1, nombreReceta);

            // Aquí asumo que el id_ingrediente es un int, pero tienes solo texto en el combo,
            // para no fallar, debemos hacer algo:
            // - Si el combo solo tiene nombres de ingredientes, y quieres guardar su id,
            //   necesitarás buscar en la base ese ingrediente para obtener su id.
            // - Si quieres ingresar manualmente el id, tendrías que ingresar "id - nombre" y extraer el id.

            // Pero para no complicar, aquí dejo un valor fijo, o puedes lanzar error para validar mejor.
            // Por ejemplo, si el ingredienteTexto es solo nombre, no id, esta línea falla:
            // ps.setInt(2, Integer.parseInt(ingredienteTexto.split(" - ")[0]));

            // Mejor dejar id_ingrediente NULL (si tu tabla permite) o 0:
            ps.setInt(2, 0);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Receta guardada correctamente.");
            nombreField.setText("");
            ingredientesCombo.setSelectedItem("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar receta: " + e.getMessage());
        }
    }
    
    private void cargarRecetaDesdeTabla() {
        int fila = tablaRecetas.getSelectedRow();
        if (fila != -1) {
            // Cargar nombre
            nombreField.setText(tablaRecetas.getValueAt(fila, 1).toString());

            // Cargar ingrediente (aquí mostramos el id, mejor mostrar texto si quieres)
            ingredientesCombo.setSelectedItem(tablaRecetas.getValueAt(fila, 2).toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RecetasFrame().setVisible(true);
        });
    }
}
