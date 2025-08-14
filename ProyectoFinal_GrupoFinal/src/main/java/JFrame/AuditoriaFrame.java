package JFrame;

import modelo.Auditoria;
import repositorio.AuditoriaRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AuditoriaFrame extends JFrame {

    private JTable tablaFacturas, tablaVentas;
    private JButton btnListarFacturas, btnListarVentas;
    private AuditoriaRepositorio repo;

    public AuditoriaFrame() {
        setTitle("Gestión de Auditorías");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        repo = new AuditoriaRepositorio();

        // Botones
        btnListarFacturas = new JButton("Listar Auditoría Facturas");
        btnListarFacturas.setBounds(50, 20, 200, 30);
        add(btnListarFacturas);

        btnListarVentas = new JButton("Listar Auditoría Ventas");
        btnListarVentas.setBounds(300, 20, 200, 30);
        add(btnListarVentas);

        // Tablas
        tablaFacturas = new JTable();
        JScrollPane scrollFacturas = new JScrollPane(tablaFacturas);
        scrollFacturas.setBounds(50, 70, 850, 200);
        add(scrollFacturas);

        tablaVentas = new JTable();
        JScrollPane scrollVentas = new JScrollPane(tablaVentas);
        scrollVentas.setBounds(50, 300, 850, 200);
        add(scrollVentas);

        // Eventos
        btnListarFacturas.addActionListener((ActionEvent e) -> listarAuditoria("factura"));
        btnListarVentas.addActionListener((ActionEvent e) -> listarAuditoria("venta"));

        setVisible(true);
    }

    private void listarAuditoria(String tipo) {
        List<Auditoria> auditorias = repo.obtenerAuditorias(tipo);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Auditoría");
        model.addColumn("ID Entidad");
        model.addColumn("Acción");
        model.addColumn("Usuario");
        model.addColumn("Fecha");
        model.addColumn("Datos Antiguos");

        for (Auditoria a : auditorias) {
            model.addRow(new Object[]{
                a.getIdAuditoria(),
                a.getIdEntidad(),
                a.getAccion(),
                a.getUsuario(),
                a.getFechaAccion(),
                a.getDatosAntiguos()
            });
        }

        if (tipo.equals("factura")) {
            tablaFacturas.setModel(model);
        } else {
            tablaVentas.setModel(model);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AuditoriaFrame::new);
    }
}
