package JFrame;

import modelo.Cliente;
import repositorio.ClienteRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ClienteFrame extends JFrame {

    private JTextField txtIdCliente, txtNombre, txtPrimerApellido, txtSegundoApellido, txtTelefono, txtEmail, txtDireccion;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar;
    private JTable tablaClientes;
    private ClienteRepositorio repo;

    public ClienteFrame() {
        setTitle("Gestión de Clientes");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        repo = new ClienteRepositorio();

        // Etiquetas
        addLabel("ID Cliente:", 20, 20);
        addLabel("Nombre:", 20, 60);
        addLabel("Primer Apellido:", 20, 100);
        addLabel("Segundo Apellido:", 20, 140);
        addLabel("Teléfono:", 20, 180);
        addLabel("Email:", 20, 220);
        addLabel("Dirección:", 20, 260);

        // Campos de texto
        txtIdCliente = addTextField(150, 20);
        txtIdCliente.setEditable(false); // ID no editable
        txtNombre = addTextField(150, 60);
        txtPrimerApellido = addTextField(150, 100);
        txtSegundoApellido = addTextField(150, 140);
        txtTelefono = addTextField(150, 180);
        txtEmail = addTextField(150, 220);
        txtDireccion = addTextField(150, 260);

        // Botones
        btnAgregar = addButton("Agregar", 400, 60);
        btnActualizar = addButton("Actualizar", 400, 100);
        btnEliminar = addButton("Eliminar", 400, 140);
        btnListar = addButton("Listar", 400, 180);

        // Tabla
        tablaClientes = new JTable();
        JScrollPane scroll = new JScrollPane(tablaClientes);
        scroll.setBounds(20, 320, 840, 170);
        add(scroll);

        // Eventos
        btnAgregar.addActionListener(e -> agregarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnListar.addActionListener(e -> listarClientes());

        // Evento para cargar datos al hacer clic en la tabla
        tablaClientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                cargarClienteDesdeTabla();
            }
        });

        listarClientes();
        setVisible(true);
        setLocationRelativeTo(null); 
    }

    private void agregarCliente() {
        Cliente cliente = new Cliente(
            txtNombre.getText(),
            txtPrimerApellido.getText(),
            txtSegundoApellido.getText(),
            txtTelefono.getText(),
            txtEmail.getText(),
            txtDireccion.getText()
        );

        if (repo.agregarCliente(cliente)) {
            JOptionPane.showMessageDialog(this, "Cliente agregado exitosamente.");
            limpiarCampos();
            listarClientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar cliente.");
        }
    }

    private void actualizarCliente() {
        try {
            int id = Integer.parseInt(txtIdCliente.getText());

            Cliente cliente = new Cliente(
                id,
                txtNombre.getText(),
                txtPrimerApellido.getText(),
                txtSegundoApellido.getText(),
                txtTelefono.getText(),
                txtEmail.getText(),
                txtDireccion.getText()
            );

            if (repo.actualizarCliente(cliente)) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
                limpiarCampos();
                listarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar cliente.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void eliminarCliente() {
        try {
            int id = Integer.parseInt(txtIdCliente.getText());

            if (repo.eliminarCliente(id)) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                limpiarCampos();
                listarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar cliente.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void listarClientes() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Primer Apellido");
        model.addColumn("Segundo Apellido");
        model.addColumn("Teléfono");
        model.addColumn("Email");
        model.addColumn("Dirección");

        for (Cliente c : repo.listarClientes()) {
            model.addRow(new Object[]{
                c.getIdCliente(),
                c.getNombre(),
                c.getPrimerApellido(),
                c.getSegundoApellido(),
                c.getTelefono(),
                c.getEmail(),
                c.getDireccion()
            });
        }

        tablaClientes.setModel(model);
    }

    private void cargarClienteDesdeTabla() {
        int fila = tablaClientes.getSelectedRow();
        if (fila != -1) {
            txtIdCliente.setText(tablaClientes.getValueAt(fila, 0).toString());
            txtNombre.setText(tablaClientes.getValueAt(fila, 1).toString());
            txtPrimerApellido.setText(tablaClientes.getValueAt(fila, 2).toString());
            txtSegundoApellido.setText(tablaClientes.getValueAt(fila, 3).toString());
            txtTelefono.setText(tablaClientes.getValueAt(fila, 4).toString());
            txtEmail.setText(tablaClientes.getValueAt(fila, 5).toString());
            txtDireccion.setText(tablaClientes.getValueAt(fila, 6).toString());
        }
    }

    private void limpiarCampos() {
        txtIdCliente.setText("");
        txtNombre.setText("");
        txtPrimerApellido.setText("");
        txtSegundoApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
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
        SwingUtilities.invokeLater(ClienteFrame::new);
    }
}

