CREATE OR REPLACE PROCEDURE agregar_cliente(
    p_nombre           IN clientes.nombre%TYPE,
    p_primer_apellido  IN clientes.primer_apellido%TYPE,
    p_segundo_apellido IN clientes.segundo_apellido%TYPE,
    p_telefono         IN clientes.telefono%TYPE,
    p_email            IN clientes.email%TYPE,
    p_direccion        IN clientes.direccion%TYPE
) AS
BEGIN
    INSERT INTO clientes (nombre, primer_apellido, segundo_apellido, telefono, email, direccion)
    VALUES (p_nombre, p_primer_apellido, p_segundo_apellido, p_telefono, p_email, p_direccion);
END;
/

CREATE OR REPLACE FUNCTION listar_clientes
RETURN SYS_REFCURSOR AS
    clientes_cursor SYS_REFCURSOR;
BEGIN
    OPEN clientes_cursor FOR
    SELECT id_cliente, nombre, primer_apellido, segundo_apellido, telefono, email, direccion
    FROM clientes
    ORDER BY id_cliente ASC;
    RETURN clientes_cursor;
END;
/

CREATE OR REPLACE PROCEDURE actualizar_cliente(
    p_id_cliente       IN clientes.id_cliente%TYPE,
    p_nombre           IN clientes.nombre%TYPE,
    p_primer_apellido  IN clientes.primer_apellido%TYPE,
    p_segundo_apellido IN clientes.segundo_apellido%TYPE,
    p_telefono         IN clientes.telefono%TYPE,
    p_email            IN clientes.email%TYPE,
    p_direccion        IN clientes.direccion%TYPE
) AS
BEGIN
    UPDATE clientes
    SET nombre = p_nombre,
        primer_apellido = p_primer_apellido,
        segundo_apellido = p_segundo_apellido,
        telefono = p_telefono,
        email = p_email,
        direccion = p_direccion
    WHERE id_cliente = p_id_cliente;
END;
/

CREATE OR REPLACE PROCEDURE eliminar_cliente(
    p_id_cliente IN clientes.id_cliente%TYPE
) AS
BEGIN
    DELETE FROM clientes WHERE id_cliente = p_id_cliente;
END;

