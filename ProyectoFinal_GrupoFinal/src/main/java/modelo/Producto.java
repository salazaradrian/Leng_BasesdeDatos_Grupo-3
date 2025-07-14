/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Adrian Salazar R
 */
public class Producto {
    private int idProducto;
    private String nombre;
    private String tipo;    
    private String descripcion;
    private double precio;
    private int idreceta;
    private int cantidad;

    public Producto() {}

    public Producto(int idProducto, String nombre, String tipo,String descripcion, double precio, int idreceta, int cantidad) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idreceta = idreceta;
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
        public String gettipo() {
        return nombre;
    }

    public void settipo(String tipo) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    
    public int getidreceta() {
        return idProducto;
    }

    public void setidreceta(int idreceta) {
        this.idreceta = idreceta;
    }
    
    public int getcantidad() {
        return cantidad;
    }

    public void setcantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
