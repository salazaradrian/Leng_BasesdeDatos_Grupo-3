package modelo;

import java.util.Date;


public class Factura {

    private int idFactura;
    private int idVentas;
    private Date fecha;
    private double impuesto;
    private double subtotal;

    
    public Factura() {
    }
   
    public Factura(int idFactura, int idVentas, Date fecha, double impuesto, double subtotal) {
        this.idFactura = idFactura;
        this.idVentas = idVentas;
        this.fecha = fecha;
        this.impuesto = impuesto;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdVentas() {
        return idVentas;
    }

    public void setIdVentas(int idVentas) {
        this.idVentas = idVentas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "Factura{" +
               "idFactura=" + idFactura +
               ", idVentas=" + idVentas +
               ", fecha=" + fecha +
               ", impuesto=" + impuesto +
               ", subtotal=" + subtotal +
               '}';
    }
}



//package modelo;
//
//import java.util.Date;
//
//public class Factura {
//    private int idFactura;
//    private int idVentas;
//    private Date fecha;
//    private double impuesto;
//    private double subtotal;
//    private int idEstado;
//
//    public Factura() {}
//
//    public Factura(int idFactura, int idVentas, Date fecha, double impuesto, double subtotal, int idEstado) {
//        this.idFactura = idFactura;
//        this.idVentas = idVentas;
//        this.fecha = fecha;
//        this.impuesto = impuesto;
//        this.subtotal = subtotal;
//        this.idEstado = idEstado;
//    }
//
//    public int getIdFactura() {
//        return idFactura;
//    }
//
//    public void setIdFactura(int idFactura) {
//        this.idFactura = idFactura;
//    }
//
//    public int getIdVentas() {
//        return idVentas;
//    }
//
//    public void setIdVentas(int idVentas) {
//        this.idVentas = idVentas;
//    }
//
//    public Date getFecha() {
//        return fecha;
//    }
//
//    public void setFecha(Date fecha) {
//        this.fecha = fecha;
//    }
//
//    public double getImpuesto() {
//        return impuesto;
//    }
//
//    public void setImpuesto(double impuesto) {
//        this.impuesto = impuesto;
//    }
//
//    public double getSubtotal() {
//        return subtotal;
//    }
//
//    public void setSubtotal(double subtotal) {
//        this.subtotal = subtotal;
//    }
//
//    public int getIdEstado() {
//        return idEstado;
//    }
//
//    public void setIdEstado(int idEstado) {
//        this.idEstado = idEstado;
//    }
//}
