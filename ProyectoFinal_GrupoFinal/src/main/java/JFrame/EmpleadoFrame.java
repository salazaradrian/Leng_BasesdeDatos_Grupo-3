/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JFrame;

/**
 *
 * @author PC
 */

import modelo.Empleado;
import repositorio.EmpleadoRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;



public class EmpleadoFrame extends JFrame {
   
    private JTextField txtIdEmpleado, txtNombre, txtPrimerApellido, txtSegundoApellido, txtsalario, txtcargo;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar;
    private JTable tablaEmpleados;
    private EmpleadoRepositorio repo;
   
   
public EmpleadoFrame() {
        setTitle("Gestión de Empleado");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        repo = new EmpleadoRepositorio();

        // Etiquetas
        addLabel("ID Empleado:", 20, 20);
        addLabel("Nombre:", 20, 60);
        addLabel("Primer Apellido:", 20, 100);
        addLabel("Segundo Apellido:", 20, 140);
        addLabel("salario:", 20, 180);
        addLabel("cargo:", 20, 220);
       

        // Campos de texto
        txtIdEmpleado = addTextField(150, 20);
        txtIdEmpleado.setEditable(false); // ID no editable
        txtNombre = addTextField(150, 60);
        txtPrimerApellido = addTextField(150, 100);
        txtSegundoApellido = addTextField(150, 140);
        txtsalario = addTextField(150, 180);
        txtcargo = addTextField(150, 220);
       
        // Botones
        btnAgregar = addButton("Agregar", 400, 60);
        btnActualizar = addButton("Actualizar", 400, 100);
        btnEliminar = addButton("Eliminar", 400, 140);
        btnListar = addButton("Listar", 400, 180);

        // Tabla
        tablaEmpleados = new JTable();
        JScrollPane scroll = new JScrollPane(tablaEmpleados);
        scroll.setBounds(20, 320, 840, 170);
        add(scroll);

        // Eventos
        btnAgregar.addActionListener(e -> agregarEmpleado());
        btnActualizar.addActionListener(e -> actualizarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        btnListar.addActionListener(e -> listarEmpleados());

        // Cargar datos al hacer clic en la tabla
        tablaEmpleados.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                cargarEmpleadosDesdeTabla();
            }
        });


        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void agregarEmpleado() {
        Empleado empleado = new Empleado(
                0,
            txtNombre.getText(),
            txtPrimerApellido.getText(),
            txtSegundoApellido.getText(),
            Double.parseDouble(txtsalario.getText()),
            txtcargo.getText());
               
           
       

        if (repo.agregarEmpleado(empleado)) {
            JOptionPane.showMessageDialog(this, "Empleado agregado exitosamente.");
            limpiarCampos();
            listarEmpleados();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar Empleado.");
        }
    }

    private void actualizarEmpleado() {
        try {
            int id = Integer.parseInt(txtIdEmpleado.getText());

            Empleado empleado = new Empleado(
                id,
            txtNombre.getText(),
            txtPrimerApellido.getText(),
            txtSegundoApellido.getText(),
            Double.parseDouble(txtsalario.getText()),
            txtcargo.getText()
               
            );

            if (repo.actualizarEmpleado(empleado)) {
                JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
                limpiarCampos();
                listarEmpleados();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar Empleado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID invalido.");
        }
    }

    private void eliminarEmpleado() {
        try {
            int id = Integer.parseInt(txtIdEmpleado.getText());

            if (repo.eliminarEmpleado(id)) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
                limpiarCampos();
                listarEmpleados();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar Empleado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID invalido.");
        }
    }

    private void listarEmpleados() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Primer Apellido");
        model.addColumn("Segundo Apellido");
        model.addColumn(" Salario ");
        model.addColumn("Cargo");
        ;

        for (Empleado e : repo.listarEmpleados()) {
            model.addRow(new Object[]{
                e.getIdEmpleado(),
                e.getNombre(),
                e.getPrimerApellido(),
                e.getSegundoApellido(),
                e.getsalario(),
                e.getcargo()
            });
        }

        tablaEmpleados.setModel(model);
    }

    private void cargarEmpleadosDesdeTabla() {
        int fila = tablaEmpleados.getSelectedRow();
        if (fila != -1) {
            txtIdEmpleado.setText(tablaEmpleados.getValueAt(fila, 0).toString());
            txtNombre.setText(tablaEmpleados.getValueAt(fila, 1).toString());
            txtPrimerApellido.setText(tablaEmpleados.getValueAt(fila, 2).toString());
            txtSegundoApellido.setText(tablaEmpleados.getValueAt(fila, 3).toString());
            txtsalario.setText(tablaEmpleados.getValueAt(fila, 2).toString());
            txtcargo.setText(tablaEmpleados.getValueAt(fila, 4).toString());

        }
    }

    private void limpiarCampos() {
        txtIdEmpleado.setText("");
        txtNombre.setText("");
        txtPrimerApellido.setText("");
        txtSegundoApellido.setText(" ");
        txtsalario.setText(" ");
        txtcargo.setText(" ");

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
        SwingUtilities.invokeLater(EmpleadoFrame::new);
    }
}
