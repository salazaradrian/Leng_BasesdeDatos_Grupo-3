package repositorio;

import conexion.ConexionOracle;
import modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio {

    public boolean agregarProducto(Producto p) {
        String sql = "INSERT INTO productos (nombre, tipo, precio, descripcion, id_receta, cantidad) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getTipo());
            stmt.setDouble(3, p.getPrecio());
            stmt.setString(4, p.getDescripcion());
            stmt.setInt(5, p.getIdReceta());
            stmt.setInt(6, p.getCantidad());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection conn = ConexionOracle.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setTipo(rs.getString("tipo"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setIdReceta(rs.getInt("id_receta"));
                p.setCantidad(rs.getInt("cantidad"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarProducto(Producto p) {
        String sql = "UPDATE productos SET nombre = ?, tipo = ?, precio = ?, descripcion = ?, id_receta = ?, cantidad = ? WHERE id_producto = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getTipo());
            stmt.setDouble(3, p.getPrecio());
            stmt.setString(4, p.getDescripcion());
            stmt.setInt(5, p.getIdReceta());
            stmt.setInt(6, p.getCantidad());
            stmt.setInt(7, p.getIdProducto());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
 }
    }
}
