package repositorio;

import modelo.Factura;
import conexion.ConexionOracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaRepositorio {

    public boolean agregarFactura(Factura factura) {
        String sql = "INSERT INTO facturas (id_ventas, fecha, impuesto, subtotal, total, id_estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, factura.getIdVentas());
            stmt.setDate(2, new java.sql.Date(factura.getFecha().getTime()));
            stmt.setDouble(3, factura.getImpuesto());
            stmt.setDouble(4, factura.getSubtotal());
            stmt.setDouble(5, factura.getSubtotal() + factura.getImpuesto());
            stmt.setInt(6, factura.getIdEstado());


            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Factura> listarFacturas() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas";

        try (Connection conn = ConexionOracle.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Factura f = new Factura();
                f.setIdFactura(rs.getInt("id_factura"));
                f.setIdVentas(rs.getInt("id_ventas"));
                f.setFecha(rs.getDate("fecha"));
                f.setImpuesto(rs.getDouble("impuesto"));
                f.setSubtotal(rs.getDouble("subtotal"));
                f.setIdEstado(rs.getInt("id_estado"));
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean actualizarFactura(Factura factura) {
        String sql = "UPDATE facturas SET id_ventas = ?, fecha = ?, impuesto = ?, subtotal = ?, id_estado = ? WHERE id_factura = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, factura.getIdVentas());
            stmt.setDate(2, new java.sql.Date(factura.getFecha().getTime()));
            stmt.setDouble(3, factura.getImpuesto());
            stmt.setDouble(4, factura.getSubtotal());
            stmt.setInt(5, factura.getIdEstado());
            stmt.setInt(6, factura.getIdFactura());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarFactura(int idFactura) {
        String sql = "DELETE FROM facturas WHERE id_factura = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFactura);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
