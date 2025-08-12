CREATE OR REPLACE PROCEDURE agregar_empleado(
    p_nombre           IN empleados.nombre%TYPE,
    p_primer_apellido  IN empleados.primer_apellido%TYPE,
    p_segundo_apellido IN empleados.segundo_apellido%TYPE,
    p_salario          IN empleados.salario%TYPE,
    p_cargo            IN empleados.cargo%TYPE
) AS
BEGIN
    INSERT INTO empleados (nombre, primer_apellido, segundo_apellido, salario, cargo)
    VALUES (p_nombre, p_primer_apellido, p_segundo_apellido, p_salario, p_cargo);
END;
/

CREATE OR REPLACE FUNCTION listar_empleados
RETURN SYS_REFCURSOR AS
    empleados_cursor SYS_REFCURSOR;
BEGIN
    OPEN empleados_cursor FOR
    SELECT id_empleado, nombre, primer_apellido, segundo_apellido, salario, cargo
    FROM empleados
    ORDER BY id_empleado ASC;
    RETURN empleados_cursor;
END;
/

CREATE OR REPLACE PROCEDURE actualizar_empleado(
    p_id_empleado      IN empleados.id_empleado%TYPE,
    p_nombre           IN empleados.nombre%TYPE,
    p_primer_apellido  IN empleados.primer_apellido%TYPE,
    p_segundo_apellido IN empleados.segundo_apellido%TYPE,
    p_salario          IN empleados.salario%TYPE,
    p_cargo            IN empleados.cargo%TYPE
) AS
BEGIN
    UPDATE empleados
    SET nombre = p_nombre,
        primer_apellido = p_primer_apellido,
        segundo_apellido = p_segundo_apellido,
        salario = p_salario,
        cargo = p_cargo
    WHERE id_empleado = p_id_empleado;
END;
/

CREATE OR REPLACE PROCEDURE eliminar_empleado(
    p_id_empleado IN empleados.id_empleado%TYPE
) AS
BEGIN
    DELETE FROM empleados WHERE id_empleado = p_id_empleado;
END;
/