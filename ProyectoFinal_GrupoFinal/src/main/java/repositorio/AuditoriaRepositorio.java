package repositorio;

import modelo.Auditoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaRepositorio {

    private Connection connection;

    public AuditoriaRepositorio() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost:1521/orclpdb", "panaderia", "1234"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public List<Auditoria> obtenerAuditorias(String tipo) {
    List<Auditoria> auditorias = new ArrayList<>();
    String tabla = tipo.equalsIgnoreCase("factura") ? "auditoria_facturas" : "auditoria_ventas";
    String query = "SELECT * FROM " + tabla + " ORDER BY fecha_accion ASC";

    try (PreparedStatement stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Auditoria auditoria = new Auditoria(
                rs.getLong(2), // id_factura o id_venta
                rs.getString("accion"),
                rs.getString("usuario"),
                rs.getDate("fecha_accion"),
                rs.getString("datos_antiguos")
            );
            auditoria.setIdAuditoria(rs.getLong("id_auditoria"));
            auditorias.add(auditoria);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return auditorias;
}
}

