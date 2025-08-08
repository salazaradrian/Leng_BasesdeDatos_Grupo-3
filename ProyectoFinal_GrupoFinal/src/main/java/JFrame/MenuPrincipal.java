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
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1, 10, 10));

        JButton btnClientes = new JButton("Gestión de Clientes");
        JButton btnEmpleado = new JButton("Gestión de Empleados");
        JButton btnProducto = new JButton("Gestión de Productos");
        JButton btnRecetas = new JButton("Gestión de Recetas");
        JButton btnIngredientes = new JButton("Gestión de Ingredientes");
        JButton btnCompras = new JButton("Gestión de Compras");
        JButton btnVentas = new JButton("Gestión de Ventas");
        JButton btnFacturas = new JButton("Gestión de Facturas");
        JButton btnSalir = new JButton("Salir");

        btnClientes.addActionListener((ActionEvent e) -> new ClienteFrame().setVisible(true));
        btnEmpleado.addActionListener((ActionEvent e) -> new EmpleadoFrame().setVisible(true));
        btnProducto.addActionListener((ActionEvent e) -> new ProductoFrame().setVisible(true));
        btnRecetas.addActionListener((ActionEvent e) -> new RecetasFrame().setVisible(true));
        btnIngredientes.addActionListener((ActionEvent e) -> new IngredienteFrame().setVisible(true));
        btnCompras.addActionListener((ActionEvent e) -> new ComprasFrame().setVisible(true));
        btnVentas.addActionListener((ActionEvent e) -> new VentasFrame().setVisible(true));
        btnFacturas.addActionListener((ActionEvent e) -> new FacturaFrame().setVisible(true));
        btnSalir.addActionListener((ActionEvent e) -> System.exit(0));

        add(btnClientes);
        add(btnEmpleado);
        add(btnProducto);
        add(btnRecetas);
        add(btnIngredientes);
        add(btnCompras);
        add(btnVentas);
        add(btnFacturas);
        add(btnSalir);

        setVisible(true);
        
        setLocationRelativeTo(null); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}

