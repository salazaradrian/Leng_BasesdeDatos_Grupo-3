-- PAQUETE CLIENTES

CREATE OR REPLACE PACKAGE pkg_clientes AS
  PROCEDURE agregar_cliente(p_nombre VARCHAR2, p_primer_apellido VARCHAR2, p_segundo_apellido VARCHAR2,
                            p_telefono VARCHAR2, p_email VARCHAR2, p_direccion VARCHAR2);
  PROCEDURE actualizar_cliente(p_id_cliente NUMBER, p_nombre VARCHAR2, p_primer_apellido VARCHAR2,
                               p_segundo_apellido VARCHAR2, p_telefono VARCHAR2, p_email VARCHAR2, p_direccion VARCHAR2);
  PROCEDURE eliminar_cliente(p_id_cliente NUMBER);
  FUNCTION listar_clientes RETURN SYS_REFCURSOR;
END pkg_clientes;
/
 
CREATE OR REPLACE PACKAGE BODY pkg_clientes AS
  PROCEDURE agregar_cliente(p_nombre VARCHAR2, p_primer_apellido VARCHAR2, p_segundo_apellido VARCHAR2,
                            p_telefono VARCHAR2, p_email VARCHAR2, p_direccion VARCHAR2) AS
  BEGIN
    INSERT INTO clientes (nombre, primer_apellido, segundo_apellido, telefono, email, direccion)
    VALUES (p_nombre, p_primer_apellido, p_segundo_apellido, p_telefono, p_email, p_direccion);
  END;
 
  PROCEDURE actualizar_cliente(p_id_cliente NUMBER, p_nombre VARCHAR2, p_primer_apellido VARCHAR2,
                               p_segundo_apellido VARCHAR2, p_telefono VARCHAR2, p_email VARCHAR2, p_direccion VARCHAR2) AS
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
 
  PROCEDURE eliminar_cliente(p_id_cliente NUMBER) AS
  BEGIN
    DELETE FROM clientes WHERE id_cliente = p_id_cliente;
  END;
 
  FUNCTION listar_clientes RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR SELECT * FROM clientes;
    RETURN v_cur;  
  END;
END pkg_clientes;
/



-- PAQUETE EMPLEADOS
CREATE OR REPLACE PACKAGE pkg_empleados AS
  PROCEDURE agregar_empleado(p_nombre VARCHAR2, p_primer_apellido VARCHAR2, p_segundo_apellido VARCHAR2,
                             p_salario NUMBER, p_cargo VARCHAR2);
  PROCEDURE actualizar_empleado(p_id_empleado NUMBER, p_nombre VARCHAR2, p_primer_apellido VARCHAR2,
                                p_segundo_apellido VARCHAR2, p_salario NUMBER, p_cargo VARCHAR2);
  PROCEDURE eliminar_empleado(p_id_empleado NUMBER);
  FUNCTION listar_empleados RETURN SYS_REFCURSOR;
END pkg_empleados;
/
 
CREATE OR REPLACE PACKAGE BODY pkg_empleados AS
  PROCEDURE agregar_empleado(p_nombre VARCHAR2, p_primer_apellido VARCHAR2, p_segundo_apellido VARCHAR2,
                             p_salario NUMBER, p_cargo VARCHAR2) AS
  BEGIN
    INSERT INTO empleados (nombre, primer_apellido, segundo_apellido, salario, cargo)
    VALUES (p_nombre, p_primer_apellido, p_segundo_apellido, p_salario, p_cargo);
  END;
 
  PROCEDURE actualizar_empleado(p_id_empleado NUMBER, p_nombre VARCHAR2, p_primer_apellido VARCHAR2,
                                p_segundo_apellido VARCHAR2, p_salario NUMBER, p_cargo VARCHAR2) AS
  BEGIN
    UPDATE empleados
    SET nombre = p_nombre,
        primer_apellido = p_primer_apellido,
        segundo_apellido = p_segundo_apellido,
        salario = p_salario,
        cargo = p_cargo
    WHERE id_empleado = p_id_empleado;
  END;
 
  PROCEDURE eliminar_empleado(p_id_empleado NUMBER) AS
  BEGIN
    DELETE FROM empleados WHERE id_empleado = p_id_empleado;
  END;
 
  FUNCTION listar_empleados RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR SELECT * FROM empleados;
    RETURN v_cur;  
  END;
END pkg_empleados;
/


-- PAQUETE PRODUCTOS
CREATE OR REPLACE PACKAGE pkg_productos AS
  PROCEDURE agregar_producto(p_nombre VARCHAR2, p_tipo VARCHAR2, p_precio NUMBER, p_descripcion VARCHAR2, 
                             p_id_receta NUMBER);
  PROCEDURE actualizar_producto(p_id_producto NUMBER, p_nombre VARCHAR2, p_tipo VARCHAR2, p_precio NUMBER,
                                p_descripcion VARCHAR2, p_id_receta NUMBER);
  PROCEDURE eliminar_producto(p_id_producto NUMBER);
  FUNCTION listar_productos RETURN SYS_REFCURSOR;
END pkg_productos;
/
 
CREATE OR REPLACE PACKAGE BODY pkg_productos AS
  
  PROCEDURE agregar_producto(p_nombre VARCHAR2, p_tipo VARCHAR2, p_precio NUMBER, p_descripcion VARCHAR2, p_id_receta NUMBER) AS
  BEGIN
    INSERT INTO productos (nombre, tipo, precio, descripcion, id_receta)
    VALUES (p_nombre, p_tipo, p_precio, p_descripcion, p_id_receta);
  END;
 
  PROCEDURE actualizar_producto(p_id_producto NUMBER, p_nombre VARCHAR2, p_tipo VARCHAR2, p_precio NUMBER, p_descripcion VARCHAR2, p_id_receta NUMBER) AS
  BEGIN
    UPDATE productos
    SET nombre      = p_nombre,
        tipo        = p_tipo,
        precio      = p_precio,
        descripcion = p_descripcion,
        id_receta   = p_id_receta
    WHERE id_producto = p_id_producto;
  END;
 
  PROCEDURE eliminar_producto(p_id_producto NUMBER) AS
  BEGIN
    DELETE FROM productos WHERE id_producto = p_id_producto;
  END;
 
  FUNCTION listar_productos RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR SELECT * FROM productos;
    RETURN v_cur;  
  END;

END pkg_productos;


-- PAQUETE VENTAS
-- Procedimiento para agregar una venta, con todos los parámetros originales
 CREATE OR REPLACE PACKAGE pkg_ventas AS
    PROCEDURE agregar_venta(
        p_id_cliente           IN NUMBER,
        p_monto_total          IN NUMBER,
        p_cantidad_productos_total IN NUMBER,
        p_id_empleado          IN NUMBER,
        p_id_producto          IN NUMBER
    );
    
    -- Procedimiento para actualizar una venta existente
    PROCEDURE editar_venta(
        p_id_ventas            IN NUMBER,
        p_id_cliente           IN NUMBER,
        p_monto_total          IN NUMBER,
        p_cantidad_productos_total IN NUMBER,
        p_id_empleado          IN NUMBER,
        p_id_producto          IN NUMBER
    );
    
    -- Procedimiento para eliminar una venta
    PROCEDURE eliminar_venta(
        p_id_ventas IN NUMBER
    );
    
    -- Función para listar todas las ventas
    FUNCTION listar_ventas RETURN SYS_REFCURSOR;
    
END pkg_ventas;
/

-- CUERPO DEL PAQUETE
CREATE OR REPLACE PACKAGE BODY pkg_ventas AS

    -- Implementación para agregar una venta
    PROCEDURE agregar_venta(
        p_id_cliente           IN NUMBER,
        p_monto_total          IN NUMBER,
        p_cantidad_productos_total IN NUMBER,
        p_id_empleado          IN NUMBER,
        p_id_producto          IN NUMBER
    ) AS
    BEGIN
        -- Inserta la nueva venta usando todos los parámetros pasados
        INSERT INTO ventas (
            id_cliente,
            monto_total,
            cantidad_productos_total,
            id_empleado,
            id_producto
        ) VALUES (
            p_id_cliente,
            p_monto_total,
            p_cantidad_productos_total,
            p_id_empleado,
            p_id_producto
        );
    END agregar_venta;

    -- Implementación para actualizar una venta
    PROCEDURE editar_venta(
        p_id_ventas            IN NUMBER,
        p_id_cliente           IN NUMBER,
        p_monto_total          IN NUMBER,
        p_cantidad_productos_total IN NUMBER,
        p_id_empleado          IN NUMBER,
        p_id_producto          IN NUMBER
    ) AS
    BEGIN
        -- Actualiza la venta usando los parámetros pasados
        UPDATE ventas
        SET
            id_cliente = p_id_cliente,
            monto_total = p_monto_total,
            cantidad_productos_total = p_cantidad_productos_total,
            id_empleado = p_id_empleado,
            id_producto = p_id_producto
        WHERE
            id_ventas = p_id_ventas;
    END editar_venta;

    -- Implementación para eliminar una venta
    PROCEDURE eliminar_venta(
        p_id_ventas IN NUMBER
    ) AS
    BEGIN
        DELETE FROM ventas
        WHERE id_ventas = p_id_ventas;
    END eliminar_venta;

    -- Implementación para listar todas las ventas
    FUNCTION listar_ventas RETURN SYS_REFCURSOR AS
        v_ventas_cursor SYS_REFCURSOR;
    BEGIN
        OPEN v_ventas_cursor FOR
        SELECT
            id_ventas,
            id_cliente,
            monto_total,
            cantidad_productos_total,
            id_empleado,
            id_producto
        FROM ventas;

        RETURN v_ventas_cursor;
    END listar_ventas;

END pkg_ventas;



-- PAQUETE FACTURA
CREATE OR REPLACE PACKAGE pkg_factura AS
    PROCEDURE agregar_factura(p_id_ventas NUMBER, p_fecha DATE, p_impuesto NUMBER, p_subtotal NUMBER);
    PROCEDURE editar_factura(p_id_factura NUMBER, p_id_ventas NUMBER, p_fecha DATE, p_impuesto NUMBER, p_subtotal NUMBER);
    PROCEDURE eliminar_factura(p_id_factura NUMBER);
    FUNCTION listar_facturas RETURN SYS_REFCURSOR;
    
END pkg_factura;
/


CREATE OR REPLACE PACKAGE BODY pkg_factura AS
    
    PROCEDURE agregar_factura(p_id_ventas NUMBER, p_fecha DATE, p_impuesto NUMBER, p_subtotal NUMBER) AS
    BEGIN
        INSERT INTO factura (id_ventas, fecha, impuesto, subtotal)
        VALUES (p_id_ventas, p_fecha, p_impuesto, p_subtotal);
        
        COMMIT;

    END agregar_factura;
    

    PROCEDURE editar_factura(
        p_id_factura IN NUMBER,
        p_id_ventas  IN NUMBER,
        p_fecha      IN DATE,
        p_impuesto   IN NUMBER,
        p_subtotal   IN NUMBER
    ) AS
    BEGIN
        UPDATE factura
        SET
            id_ventas = p_id_ventas,
            fecha = p_fecha,
            impuesto = p_impuesto,
            subtotal = p_subtotal
        WHERE id_factura = p_id_factura;
        
        COMMIT;
    END editar_factura;
    

    PROCEDURE eliminar_factura(p_id_factura NUMBER) AS
    BEGIN
        DELETE FROM factura WHERE id_factura = p_id_factura;
        
        COMMIT;
    END eliminar_factura;
    

    FUNCTION listar_facturas RETURN SYS_REFCURSOR AS
        v_cur SYS_REFCURSOR;
    BEGIN
        OPEN v_cur FOR SELECT * FROM factura ORDER BY ID_FACTURA ASC;
        RETURN v_cur;
    END listar_facturas;
    
END pkg_factura;
/
/*CREATE OR REPLACE PACKAGE pkg_factura AS
  PROCEDURE agregar_factura(p_id_venta NUMBER);
  PROCEDURE editar_factura(p_id_factura NUMBER, p_id_ventas NUMBER, p_fecha DATE, p_impuesto NUMBER, p_subtotal NUMBER);
  PROCEDURE eliminar_factura(p_id_factura NUMBER);
  FUNCTION listar_facturas RETURN SYS_REFCURSOR;
END pkg_factura;
/
 
CREATE OR REPLACE PACKAGE BODY pkg_factura AS
  PROCEDURE agregar_factura(p_id_venta NUMBER) AS
    v_subtotal ventas.monto_total%TYPE;
    v_impuesto NUMBER(10,2);
  BEGIN
    SELECT monto_total INTO v_subtotal FROM ventas WHERE id_ventas = p_id_venta;
    v_impuesto := ROUND(v_subtotal * 0.13, 2);
    
    INSERT INTO factura (id_ventas, fecha, impuesto, subtotal)
    VALUES (p_id_venta, SYSDATE, v_impuesto, v_subtotal);
  END;
 
   PROCEDURE editar_factura(
        p_id_factura IN NUMBER,
        p_id_ventas  IN NUMBER,
        p_fecha      IN DATE,
        p_impuesto   IN NUMBER,
        p_subtotal   IN NUMBER
    ) AS
    BEGIN
        UPDATE factura
        SET
            id_ventas = p_id_ventas,
            fecha = p_fecha,
            impuesto = p_impuesto,
            subtotal = p_subtotal
        WHERE id_factura = p_id_factura;
  END;
 
  PROCEDURE eliminar_factura(p_id_factura NUMBER) AS
  BEGIN
    DELETE FROM factura WHERE id_factura = p_id_factura;
  END;
 
  FUNCTION listar_facturas RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR SELECT * FROM factura;
    RETURN v_cur;
  END;
END pkg_factura;

/
*/
