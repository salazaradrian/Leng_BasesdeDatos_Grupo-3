package modelo;

import java.sql.Date;

public class Auditoria {
    private Long idAuditoria;
    private Long idEntidad;
    private String accion;
    private String usuario;
    private Date fechaAccion;
    private String datosAntiguos;

    public Auditoria(Long idEntidad, String accion, String usuario, Date fechaAccion, String datosAntiguos) {
        this.idEntidad = idEntidad;
        this.accion = accion;
        this.usuario = usuario;
        this.fechaAccion = fechaAccion;
        this.datosAntiguos = datosAntiguos;
    }

    public void setIdAuditoria(Long idAuditoria) { this.idAuditoria = idAuditoria; }

    public Long getIdAuditoria() { return idAuditoria; }
    public Long getIdEntidad() { return idEntidad; }
    public String getAccion() { return accion; }
    public String getUsuario() { return usuario; }
    public Date getFechaAccion() { return fechaAccion; }
    public String getDatosAntiguos() { return datosAntiguos; }
}

    
