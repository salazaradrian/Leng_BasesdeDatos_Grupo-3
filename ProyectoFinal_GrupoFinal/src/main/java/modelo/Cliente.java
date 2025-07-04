/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Adrian Salazar R
 */
public class Cliente {
    
private int idCliente;
private String nombre;
private String telefono;
private String direccion;

// Constructor vacío
public Cliente(){}

// Constructor con parámetros
public Cliente(int idCliente, String nombre, String telefono, String direccion) {
this.idCliente = idCliente;
this.nombre = nombre;
this.telefono = telefono;
this.direccion = direccion;
}

// Getters y Setters
public int getIdCliente() {
return idCliente;
}

public void setIdCliente(int idCliente) {
this.idCliente = idCliente;
}

public String getNombre() {
return nombre;
}

public void setNombre(String nombre) {
this.nombre = nombre;
}

public String getTelefono() {
return telefono;
}

public void setTelefono(String telefono) {
this.telefono = telefono;
}

public String getDireccion() {
return direccion;
}

public void setDireccion(String direccion) {
this.direccion = direccion; }
}




