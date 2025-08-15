CREATE OR REPLACE PACKAGE pkg_compras AS
  PROCEDURE agregar_compra(p_id_ingrediente NUMBER, p_fecha DATE, p_cantidad NUMBER, p_monto NUMBER);
  PROCEDURE actualizar_compra(p_id_compra NUMBER, p_id_ingrediente NUMBER, p_fecha DATE, p_cantidad NUMBER, p_monto NUMBER);
  PROCEDURE eliminar_compra(p_id_compra NUMBER);
  FUNCTION listar_compras RETURN SYS_REFCURSOR;
END pkg_compras;
/

CREATE OR REPLACE PACKAGE BODY pkg_compras AS
  PROCEDURE agregar_compra(p_id_ingrediente NUMBER, p_fecha DATE, p_cantidad NUMBER, p_monto NUMBER) AS
  BEGIN
    INSERT INTO compras (id_ingrediente, fecha, cantidad_ingredientes, monto_total)
    VALUES (p_id_ingrediente, p_fecha, p_cantidad, p_monto);
  END;

  PROCEDURE actualizar_compra(p_id_compra NUMBER, p_id_ingrediente NUMBER, p_fecha DATE, p_cantidad NUMBER, p_monto NUMBER) AS
  BEGIN
    UPDATE compras
    SET id_ingrediente = p_id_ingrediente,
        fecha = p_fecha,
        cantidad_ingredientes = p_cantidad,
        monto_total = p_monto
    WHERE id_compra = p_id_compra;
  END;

  PROCEDURE eliminar_compra(p_id_compra NUMBER) AS
  BEGIN
    DELETE FROM compras WHERE id_compra = p_id_compra;
  END;

  FUNCTION listar_compras RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR SELECT * FROM compras;
    RETURN v_cur;
  END;
END pkg_compras;
/


CREATE OR REPLACE PACKAGE pkg_recetas AS
  PROCEDURE agregar_receta(p_nombre VARCHAR2);
  PROCEDURE actualizar_receta(p_id_receta NUMBER, p_nombre VARCHAR2);
  PROCEDURE eliminar_receta(p_id_receta NUMBER);
  FUNCTION listar_recetas RETURN SYS_REFCURSOR;
END pkg_recetas;
/

CREATE OR REPLACE PACKAGE BODY pkg_recetas AS
  PROCEDURE agregar_receta(p_nombre VARCHAR2) AS
  BEGIN
    INSERT INTO recetas (nombre)
    VALUES (p_nombre);
  END;

  PROCEDURE actualizar_receta(p_id_receta NUMBER, p_nombre VARCHAR2) AS
  BEGIN
    UPDATE recetas
    SET nombre = p_nombre
    WHERE id_receta = p_id_receta;
  END;

  PROCEDURE eliminar_receta(p_id_receta NUMBER) AS
  BEGIN
    DELETE FROM recetas WHERE id_receta = p_id_receta;
  END;

  FUNCTION listar_recetas RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR SELECT * FROM recetas;
    RETURN v_cur;
  END;
END pkg_recetas;
/


CREATE OR REPLACE PACKAGE pkg_ingredientes AS
  PROCEDURE agregar_ingrediente(p_nombre VARCHAR2, p_cantidad NUMBER, p_id_receta NUMBER);
  PROCEDURE actualizar_ingrediente(p_id_ingrediente NUMBER, p_nombre VARCHAR2, p_cantidad NUMBER, p_id_receta NUMBER);
  PROCEDURE eliminar_ingrediente(p_id_ingrediente NUMBER);
  FUNCTION listar_ingredientes RETURN SYS_REFCURSOR;
END pkg_ingredientes;
/

CREATE OR REPLACE PACKAGE BODY pkg_ingredientes AS
  PROCEDURE agregar_ingrediente(p_nombre VARCHAR2, p_cantidad NUMBER, p_id_receta NUMBER) AS
  BEGIN
    INSERT INTO ingredientes (nombre, cantidad, id_receta)
    VALUES (p_nombre, p_cantidad, p_id_receta);
  END;

  PROCEDURE actualizar_ingrediente(p_id_ingrediente NUMBER, p_nombre VARCHAR2, p_cantidad NUMBER, p_id_receta NUMBER) AS
  BEGIN
    UPDATE ingredientes
    SET nombre = p_nombre,
        cantidad = p_cantidad,
        id_receta = p_id_receta
    WHERE id_ingrediente = p_id_ingrediente;
  END;

  PROCEDURE eliminar_ingrediente(p_id_ingrediente NUMBER) AS
  BEGIN
    DELETE FROM ingredientes WHERE id_ingrediente = p_id_ingrediente;
  END;

  FUNCTION listar_ingredientes RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR SELECT * FROM ingredientes;
    RETURN v_cur;
  END;
END pkg_ingredientes;
/

CREATE OR REPLACE PACKAGE pkg_gestion_ventas AS

    estado_pagado   CONSTANT VARCHAR2(15) := 'Pagado';
    estado_pendiente CONSTANT VARCHAR2(15) := 'Pendiente';
    estado_anulado  CONSTANT VARCHAR2(15) := 'Anulado';

    
    CURSOR cur_detalle_venta(p_id_venta NUMBER) IS
        SELECT v.id_ventas,
               c.nombre || ' ' || c.primer_apellido AS cliente,
               p.nombre AS producto,
               v.cantidad_productos_total AS cantidad,
               v.monto_total AS total
        FROM ventas v
        INNER JOIN clientes c ON v.id_cliente = c.id_cliente
        INNER JOIN productos p ON v.id_producto = p.id_producto
        WHERE v.id_ventas = p_id_venta;

  
    FUNCTION obtener_total_venta(p_id_venta NUMBER)
    RETURN NUMBER;

    
    FUNCTION total_cliente_anual(p_id_cliente NUMBER, p_year NUMBER)
    RETURN NUMBER;
END pkg_gestion_ventas;
/

--Cuerpo del paquete
CREATE OR REPLACE PACKAGE BODY pkg_gestion_ventas AS

    FUNCTION obtener_total_venta(p_id_venta NUMBER)
    RETURN NUMBER AS
        v_total NUMBER;
    BEGIN
        SELECT monto_total
        INTO v_total
        FROM ventas
        WHERE id_ventas = p_id_venta;

        RETURN v_total;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN NULL;
    END obtener_total_venta;

    FUNCTION total_cliente_anual(p_id_cliente NUMBER, p_year NUMBER)
    RETURN NUMBER AS
        v_total_cliente NUMBER;
    BEGIN
        SELECT NVL(SUM(monto_total),0)
        INTO v_total_cliente
        FROM ventas
        WHERE id_cliente = p_id_cliente
          AND EXTRACT(YEAR FROM (SELECT f.fecha
                                 FROM facturas f
                                 INNER JOIN ventas v ON f.id_ventas = v.id_ventas
                                 WHERE v.id_cliente = p_id_cliente
                                 AND v.id_ventas = ventas.id_ventas)) = p_year;

        RETURN v_total_cliente;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END total_cliente_anual;

END pkg_gestion_ventas;
/

CREATE OR REPLACE PACKAGE pkg_gestion_facturas AS
    -- Constantes de estado de factura
    estado_pagada    CONSTANT VARCHAR2(15) := 'Pagada';
    estado_pendiente CONSTANT VARCHAR2(15) := 'Pendiente';
    estado_anulada   CONSTANT VARCHAR2(15) := 'Anulada';

    -- Cursor para ver detalle de una factura específica
    CURSOR cur_detalle_factura(p_id_factura NUMBER) IS
        SELECT f.id_factura,
               f.fecha,
               c.nombre || ' ' || c.primer_apellido AS cliente,
               p.nombre AS producto,
               v.cantidad_productos_total AS cantidad,
               (f.subtotal + f.impuesto) AS total
        FROM facturas f
        INNER JOIN ventas v ON f.id_ventas = v.id_ventas
        INNER JOIN clientes c ON v.id_cliente = c.id_cliente
        INNER JOIN productos p ON v.id_producto = p.id_producto
        WHERE f.id_factura = p_id_factura;

    -- Obtener el total de una factura (subtotal + impuesto)
    FUNCTION obtener_total_factura(p_id_factura NUMBER)
    RETURN NUMBER;

    -- Obtener el total facturado a un cliente en un año específico
    FUNCTION total_cliente_anual(p_id_cliente NUMBER, p_year NUMBER)
    RETURN NUMBER;
END pkg_gestion_facturas;
/

--cuerpo del paquete
CREATE OR REPLACE PACKAGE BODY pkg_gestion_facturas AS

    FUNCTION obtener_total_factura(p_id_factura NUMBER)
    RETURN NUMBER AS
        v_total NUMBER;
    BEGIN
        SELECT (subtotal + impuesto)
        INTO v_total
        FROM facturas
        WHERE id_factura = p_id_factura;

        RETURN v_total;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN NULL;
    END obtener_total_factura;

    FUNCTION total_cliente_anual(p_id_cliente NUMBER, p_year NUMBER)
    RETURN NUMBER AS
        v_total_cliente NUMBER;
    BEGIN
        SELECT NVL(SUM(f.subtotal + f.impuesto), 0)
        INTO v_total_cliente
        FROM facturas f
        INNER JOIN ventas v ON f.id_ventas = v.id_ventas
        WHERE v.id_cliente = p_id_cliente
          AND EXTRACT(YEAR FROM f.fecha) = p_year;

        RETURN v_total_cliente;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END total_cliente_anual;

END pkg_gestion_facturas;
/