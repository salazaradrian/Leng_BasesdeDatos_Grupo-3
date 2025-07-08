import javax.swing.*;
import java.awt.*;

public class FacturasFrame extends JFrame {
    private JTextField txtIdFactura, txtIdVentas, txtFecha, txtImpuesto, txtSubtotal, txtIdEstado;
    private JButton btnAgregar, btnEditar, btnEliminar;

    public FacturasFrame() {
        setTitle("Gestión de Facturas");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new JLabel("ID Factura:"));
        txtIdFactura = new JTextField();
        add(txtIdFactura);

        add(new JLabel("ID Ventas:"));
        txtIdVentas = new JTextField();
        add(txtIdVentas);

        add(new JLabel("Fecha:"));
        txtFecha = new JTextField();
        add(txtFecha);

        add(new JLabel("Impuesto:"));
        txtImpuesto = new JTextField();
        add(txtImpuesto);

        add(new JLabel("Subtotal:"));
        txtSubtotal = new JTextField();
        add(txtSubtotal);

        add(new JLabel("ID Estado:"));
        txtIdEstado = new JTextField();
        add(txtIdEstado);

        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        add(btnAgregar);
        add(btnEditar);
        add(btnEliminar);

        setVisible(true);
    }
}
