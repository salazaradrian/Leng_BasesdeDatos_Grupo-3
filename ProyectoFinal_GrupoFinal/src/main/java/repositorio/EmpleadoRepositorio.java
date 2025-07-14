/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;
import conexion.ConexionOracle;
import modelo.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian Salazar R
 */
public class EmpleadoRepositorio {
    
    
    public boolean agregarEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleados  "
                   + "(nombre, primer_apellido, segundo_apellido,  puesto, telefono) VALUES (?, ?, ?)";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getPrimerApellido());
            ps.setString(3, empleado.getSegundoApellido());
            ps.setDouble(4, empleado.getsalario());
            ps.setString(5, empleado.getcargo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar empleado: " + e.getMessage());
            return false;
        }
    }

    public List<Empleado> listarEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (Connection conn = ConexionOracle.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("id_empleado"));
                e.setNombre(rs.getString("nombre"));
                e.setPrimerApellido(rs.getString("primer_apellido"));
                e.setSegundoApellido(rs.getString("segundo_apellido"));
                e.setsalario(rs.getDouble("salario"));
                e.setcargo(rs.getString("cargo"));
                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, primer_apellido = ?, segundo_apellido = ?, salario = ?, cargo = ? WHERE id_empleado = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getPrimerApellido());
            ps.setString(3, empleado.getSegundoApellido());
            ps.setDouble(4, empleado.getsalario());
            ps.setString(5, empleado.getcargo());
            ps.setInt(6, empleado.getIdEmpleado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarEmpleado(int idEmpleado) {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }
}
 
