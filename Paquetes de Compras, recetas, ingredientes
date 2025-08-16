create or replace PACKAGE pkg_compras AS
  PROCEDURE agregar_compra(p_id_ingrediente NUMBER, p_cantidad NUMBER, p_monto NUMBER);
  PROCEDURE eliminar_compra(p_id_compra NUMBER);
  FUNCTION listar_compras RETURN SYS_REFCURSOR;
END pkg_compras;
 
 
create or replace PACKAGE BODY pkg_compras AS
  PROCEDURE agregar_compra(p_id_ingrediente NUMBER, p_cantidad NUMBER, p_monto NUMBER) AS
  BEGIN
    INSERT INTO compras (id_ingrediente, cantidad_ingredientes, monto_total)
    VALUES (p_id_ingrediente, p_cantidad, p_monto);
  END;
 
  PROCEDURE eliminar_compra(p_id_compra NUMBER) AS
  BEGIN
    DELETE FROM compras WHERE id_compra = p_id_compra;
  END;
 
  FUNCTION listar_compras RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR SELECT * FROM compras ORDER BY ID_COMPRA ASC;
    RETURN v_cur;  
  END;
END pkg_compras;
 
 
-----------------------------
 
CREATE OR REPLACE PACKAGE pkg_ingredientes AS
  PROCEDURE agregar_ingrediente(p_nombre VARCHAR2, p_cantidad NUMBER);
  PROCEDURE actualizar_ingrediente(p_id_ingrediente NUMBER, p_nombre VARCHAR2);
  PROCEDURE eliminar_ingrediente(p_id_ingrediente NUMBER);
  FUNCTION listar_ingredientes RETURN SYS_REFCURSOR;
END pkg_ingredientes;
/
 
CREATE OR REPLACE PACKAGE BODY pkg_ingredientes AS
  PROCEDURE agregar_ingrediente(p_nombre VARCHAR2, p_cantidad NUMBER) AS
  BEGIN
    INSERT INTO ingredientes (nombre, cantidad)
    VALUES (p_nombre, p_cantidad);
  END;
 
  PROCEDURE actualizar_ingrediente(p_id_ingrediente NUMBER, p_nombre VARCHAR2) AS
  BEGIN
    UPDATE ingredientes
    SET nombre = p_nombre
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
 
---------------------------
 
create or replace PACKAGE pkg_recetas AS
  PROCEDURE agregar_receta(p_nombre VARCHAR2);
  PROCEDURE actualizar_receta(p_id_receta NUMBER, p_nombre VARCHAR2);
  PROCEDURE eliminar_receta(p_id_receta NUMBER);
  FUNCTION listar_recetas RETURN SYS_REFCURSOR;
END pkg_recetas;
 
create or replace PACKAGE BODY pkg_recetas AS
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
 
---------------------
