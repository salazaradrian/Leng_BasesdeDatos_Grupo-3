/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;
import conexion.ConexionOracle;
import modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductoRepositorio {
   
   
    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO producto (nombre,tipo, descripcion, precio,cantidad) VALUES (?, ?, ?,?)";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.gettipo());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getcantidad());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar Producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conn = ConexionOracle.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idproducto"));
                p.setNombre(rs.getString("Nombre"));
                p.settipo(rs.getString("Tipo"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setDescripcion(rs.getString("Descripción"));
                p.setcantidad(rs.getInt("Cantidad"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, tipo = ?descripción = ?, precio = ?, stock = ? WHERE id_producto = ?";
     
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.gettipo());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getcantidad());
            ps.setInt(6, producto.getIdProducto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}