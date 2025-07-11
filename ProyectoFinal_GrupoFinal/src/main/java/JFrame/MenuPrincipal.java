import JFrame.ClienteFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Menú Principal");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnClientes = new JButton("Gestión de Clientes");
        JButton btnRecetas = new JButton("Gestión de Recetas");
        JButton btnIngredientes = new JButton("Gestión de Ingredientes");
        JButton btnFacturas = new JButton("Gestión de Facturas");
        JButton btnSalir = new JButton("Salir");

        btnClientes.addActionListener((ActionEvent e) -> new ClienteFrame().setVisible(true));
        btnRecetas.addActionListener((ActionEvent e) -> new RecetasFrame().setVisible(true));
        btnIngredientes.addActionListener((ActionEvent e) -> new IngredienteFrame().setVisible(true));
        btnFacturas.addActionListener((ActionEvent e) -> new FacturasFrame().setVisible(true));
        btnSalir.addActionListener((ActionEvent e) -> System.exit(0));

        add(btnClientes);
        add(btnRecetas);
        add(btnIngredientes);
        add(btnFacturas);
        add(btnSalir);

        setVisible(true);
        
        setLocationRelativeTo(null); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
