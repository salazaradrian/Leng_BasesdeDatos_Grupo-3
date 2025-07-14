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
private String primerApellido;
private String segundoApellido;
private Double salario;
private String cargo;

public Empleado() {}

public Empleado(int idEmpleado, String nombre, String primerApellido, String segundoApellido, Double salario, String cargo) {
this.idEmpleado = idEmpleado;
this.nombre = nombre;
this.primerApellido = primerApellido;
this.segundoApellido = segundoApellido;
this.salario = salario;
this.cargo = cargo;
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

 public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

public void setNombre(String nombre) {
this.nombre = nombre;
}

 public Double getsalario() {
return salario;
}

public void setsalario(Double salario) {
this.salario = salario;
}

public String getcargo() {
return cargo;
}

public void setcargo(String cargo) {
this.cargo = cargo;
}
  
}
