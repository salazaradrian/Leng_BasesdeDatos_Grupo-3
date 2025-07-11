
---VISTAS----

/*Ver todos los empleados*/

CREATE OR REPLACE VIEW Vista_de_Empleados AS
SELECT id_empleado, nombre, primer_apellido, segundo_apellido, salario, cargo
FROM empleados;




/*Ver el salario mas alto de los empleados*/

CREATE OR REPLACE VIEW Vista_de_Salario_Alto_de_los_Empleados AS
SELECT id_empleado, nombre, primer_apellido, segundo_apellido, salario, cargo
FROM empleados
WHERE salario > 700000;
