package modelo;
import java.util.Date;

import java.util.Date;

public class Venta {
    private int idVentas;                  
    private int idCliente;                
    private double montoTotal;            
    private int cantidadProductosTotal;  
    private int idEmpleado;               
    private int idProducto;               
    private Date fecha;                    

    public Venta() {}

    public Venta(int idVenta, int idCliente, double montoTotal, int cantidadProductosTotal,
                 int idEmpleado, int idProducto, Date fecha) {
        this.idVentas = idVenta;
        this.idCliente = idCliente;
        this.montoTotal = montoTotal;
        this.cantidadProductosTotal = cantidadProductosTotal;
        this.idEmpleado = idEmpleado;
        this.idProducto = idProducto;
        this.fecha = fecha;
    }
    
    public Venta(int idVenta, int idCliente, double montoTotal, int cantidadProductosTotal,
             int idEmpleado, int idProducto) {
    this.idVentas = idVenta;
    this.idCliente = idCliente;
    this.montoTotal = montoTotal;
    this.cantidadProductosTotal = cantidadProductosTotal;
    this.idEmpleado = idEmpleado;
    this.idProducto = idProducto;
}


    // Getters y setters

    public int getIdVenta() {
        return idVentas;
    }

    public void setIdVenta(int idVenta) {
        this.idVentas = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getCantidadProductosTotal() {
        return cantidadProductosTotal;
    }

    public void setCantidadProductosTotal(int cantidadProductosTotal) {
        this.cantidadProductosTotal = cantidadProductosTotal;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

 
}
