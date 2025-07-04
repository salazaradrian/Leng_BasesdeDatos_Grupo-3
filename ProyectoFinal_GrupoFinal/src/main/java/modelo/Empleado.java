/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Adrian Salazar R
 */
public class Empleado {
  
private int idEmpleado;
private String nombre;
private String puesto;
private String telefono;

public Empleado() {}

public Empleado(int idEmpleado, String nombre, String puesto, String telefono) {
this.idEmpleado = idEmpleado;
this.nombre = nombre;
this.puesto = puesto;
this.telefono = telefono;
}

public int getIdEmpleado() {
return idEmpleado;
}

public void setIdEmpleado(int idEmpleado) {
this.idEmpleado = idEmpleado;
}

public String getNombre() {
return nombre;
}

public void setNombre(String nombre) {
this.nombre = nombre;
}

 public String getPuesto() {
return puesto;
}

public void setPuesto(String puesto) {
this.puesto = puesto;
}

public String getTelefono() {
return telefono;
}

public void setTelefono(String telefono) {
this.telefono = telefono;
}
  
}
