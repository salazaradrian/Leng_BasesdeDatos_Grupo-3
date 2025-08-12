--Paquetes

--Paquete 1
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

--Paquete 2
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

--Paquete 3
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

--Paquete 4
CREATE OR REPLACE PACKAGE pkg_categorias AS
  PROCEDURE agregar_categoria(p_nombre VARCHAR2);
  PROCEDURE actualizar_categoria(p_id_categoria NUMBER, p_nombre VARCHAR2);
  PROCEDURE eliminar_categoria(p_id_categoria NUMBER);
  FUNCTION listar_categorias RETURN SYS_REFCURSOR;
END pkg_categorias;
/

CREATE OR REPLACE PACKAGE BODY pkg_categorias AS
  PROCEDURE agregar_categoria(p_nombre VARCHAR2) AS
  BEGIN
    INSERT INTO categorias (nombre)
    VALUES (p_nombre);
  END;

  PROCEDURE actualizar_categoria(p_id_categoria NUMBER, p_nombre VARCHAR2) AS
  BEGIN
    UPDATE categorias
    SET nombre = p_nombre
    WHERE id_categoria = p_id_categoria;
  END;

  PROCEDURE eliminar_categoria(p_id_categoria NUMBER) AS
  BEGIN
    DELETE FROM categorias WHERE id_categoria = p_id_categoria;
  END;

  FUNCTION listar_categorias RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR SELECT * FROM categorias;
    RETURN v_cur;
  END;
END pkg_categorias;
/

--Paquete 5
CREATE OR REPLACE PACKAGE pkg_proveedores AS
  PROCEDURE agregar_proveedor(p_nombre VARCHAR2, p_telefono VARCHAR2, p_email VARCHAR2);
  PROCEDURE actualizar_proveedor(p_id_proveedor NUMBER, p_nombre VARCHAR2, p_telefono VARCHAR2, p_email VARCHAR2);
  PROCEDURE eliminar_proveedor(p_id_proveedor NUMBER);
  FUNCTION listar_proveedores RETURN SYS_REFCURSOR;
END pkg_proveedores;
/

CREATE OR REPLACE PACKAGE BODY pkg_proveedores AS
  PROCEDURE agregar_proveedor(p_nombre VARCHAR2, p_telefono VARCHAR2, p_email VARCHAR2) AS
  BEGIN
    INSERT INTO proveedores (nombre, telefono, email)
    VALUES (p_nombre, p_telefono, p_email);
  END;

  PROCEDURE actualizar_proveedor(p_id_proveedor NUMBER, p_nombre VARCHAR2, p_telefono VARCHAR2, p_email VARCHAR2) AS
  BEGIN
    UPDATE proveedores
    SET nombre = p_nombre,
        telefono = p_telefono,
        email = p_email
    WHERE id_proveedor = p_id_proveedor;
  END;

  PROCEDURE eliminar_proveedor(p_id_proveedor NUMBER) AS
  BEGIN
    DELETE FROM proveedores WHERE id_proveedor = p_id_proveedor;
  END;

  FUNCTION listar_proveedores RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR SELECT * FROM proveedores;
    RETURN v_cur;
  END;
END pkg_proveedores;
/


