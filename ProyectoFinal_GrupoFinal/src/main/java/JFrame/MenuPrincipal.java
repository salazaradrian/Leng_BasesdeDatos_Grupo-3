package JFrame;

import JFrame.ClienteFrame;
import JFrame.FacturaFrame;
import JFrame.EmpleadoFrame;
import JFrame.ProductoFrame;
import JFrame.VentasFrame;
import JFrame.RecetasFrame;
import JFrame.IngredienteFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Menú Principal");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); 

        // Imagen de fondo
        ImageIcon fondo = new ImageIcon("src/imagenes/fondo.jpg");
        JLabel fondoLabel = new JLabel(fondo);
        fondoLabel.setBounds(0, 0, 600, 700);

        // Panel transparente para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(9, 1, 10, 10));
        panelBotones.setBounds(150, 100, 300, 450); 

        // Botones
        JButton btnClientes = new JButton("Gestión de Clientes");
        JButton btnEmpleado = new JButton("Gestión de Empleados");
        JButton btnProducto = new JButton("Gestión de Productos");
        JButton btnRecetas = new JButton("Gestión de Recetas");
        JButton btnIngredientes = new JButton("Gestión de Ingredientes");
        JButton btnCompras = new JButton("Gestión de Compras");
        JButton btnVentas = new JButton("Gestión de Ventas");
        JButton btnFacturas = new JButton("Gestión de Facturas");
        JButton btnSalir = new JButton("Salir");

        // Acciones
        btnClientes.addActionListener((ActionEvent e) -> new ClienteFrame().setVisible(true));
        btnEmpleado.addActionListener((ActionEvent e) -> new EmpleadoFrame().setVisible(true));
        btnProducto.addActionListener((ActionEvent e) -> new ProductoFrame().setVisible(true));
        btnRecetas.addActionListener((ActionEvent e) -> new RecetasFrame().setVisible(true));
        btnIngredientes.addActionListener((ActionEvent e) -> new IngredienteFrame().setVisible(true));
        btnCompras.addActionListener((ActionEvent e) -> new ComprasFrame().setVisible(true));
        btnVentas.addActionListener((ActionEvent e) -> new VentasFrame().setVisible(true));
        btnFacturas.addActionListener((ActionEvent e) -> new FacturaFrame().setVisible(true));
        btnSalir.addActionListener((ActionEvent e) -> System.exit(0));

        // Agregar botones al panel
        panelBotones.add(btnClientes);
        panelBotones.add(btnEmpleado);
        panelBotones.add(btnProducto);
        panelBotones.add(btnRecetas);
        panelBotones.add(btnIngredientes);
        panelBotones.add(btnCompras);
        panelBotones.add(btnVentas);
        panelBotones.add(btnFacturas);
        panelBotones.add(btnSalir);

        // componentes
        add(panelBotones);
        add(fondoLabel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal());
    }
}
