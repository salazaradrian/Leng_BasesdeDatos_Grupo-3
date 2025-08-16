package repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import modelo.Empleado;
import conexion.ConexionOracle;

public class EmpleadoRepositorio {

    
    public boolean agregarEmpleado(Empleado empleado) {
        String sql = "{call pkg_empleados.agregar_empleado(?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, empleado.getNombre());
            cs.setString(2, empleado.getPrimerApellido());
            cs.setString(3, empleado.getSegundoApellido());
            cs.setDouble(4, empleado.getsalario());
            cs.setString(5, empleado.getcargo());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar empleado: " + e.getMessage());
            return false;
        }
    }

    
    public List<Empleado> listarEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "{? = call pkg_empleados.listar_empleados()}";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
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
        String sql = "{call pkg_empleados.actualizar_empleado(?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, empleado.getIdEmpleado());
            cs.setString(2, empleado.getNombre());
            cs.setString(3, empleado.getPrimerApellido());
            cs.setString(4, empleado.getSegundoApellido());
            cs.setDouble(5, empleado.getsalario());
            cs.setString(6, empleado.getcargo());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    
    public boolean eliminarEmpleado(int idEmpleado) {
        String sql = "{call pkg_empleados.eliminar_empleado(?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idEmpleado);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }
}

