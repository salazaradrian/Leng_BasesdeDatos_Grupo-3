CREATE OR REPLACE PROCEDURE agregar_factura (
    p_id_ventas  IN FACTURA.ID_VENTAS%TYPE,
    p_fecha      IN FACTURA.FECHA%TYPE,
    p_impuesto   IN FACTURA.IMPUESTO%TYPE,
    p_subtotal   IN FACTURA.SUBTOTAL%TYPE
) AS
BEGIN
    INSERT INTO FACTURA (ID_VENTAS, FECHA, IMPUESTO, SUBTOTAL)
    VALUES (p_id_ventas, p_fecha, p_impuesto, p_subtotal);
COMMIT;

END;
/


CREATE OR REPLACE PROCEDURE editar_factura (
    p_id_factura IN FACTURA.ID_FACTURA%TYPE,
    p_id_ventas  IN FACTURA.ID_VENTAS%TYPE,
    p_fecha      IN FACTURA.FECHA%TYPE,
    p_impuesto   IN FACTURA.IMPUESTO%TYPE,
    p_subtotal   IN FACTURA.SUBTOTAL%TYPE
) AS
BEGIN
    UPDATE FACTURA
    SET
        ID_VENTAS = p_id_ventas,
        FECHA     = p_fecha,
        IMPUESTO  = p_impuesto,
        SUBTOTAL  = p_subtotal
    WHERE ID_FACTURA = p_id_factura;
 COMMIT;

END;
/


CREATE OR REPLACE PROCEDURE eliminar_factura (
    p_id_factura IN FACTURA.ID_FACTURA%TYPE
) AS
BEGIN
    DELETE FROM FACTURA
    WHERE ID_FACTURA = p_id_factura;
COMMIT;
END;
/



CREATE OR REPLACE FUNCTION listar_facturas
RETURN SYS_REFCURSOR 
AS
    cur_facturas SYS_REFCURSOR;
BEGIN
    OPEN cur_facturas FOR
        SELECT ID_FACTURA, ID_VENTAS, FECHA, IMPUESTO, SUBTOTAL
        FROM FACTURA;
    RETURN cur_facturas;
END;
