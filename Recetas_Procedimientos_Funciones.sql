-- Obtener recetas
CREATE OR REPLACE FUNCTION obtener_recetas
RETURN SYS_REFCURSOR AS
    recetas_cursor SYS_REFCURSOR;
BEGIN
    OPEN recetas_cursor FOR
    SELECT id_receta, nombre FROM recetas;
    RETURN recetas_cursor;
END;
/

-- Agregar receta
CREATE OR REPLACE PROCEDURE agregar_receta (
    p_nombre IN recetas.nombre%TYPE
) AS
BEGIN
    INSERT INTO recetas (nombre)
    VALUES (p_nombre);
END;
/

-- Listar recetas
CREATE OR REPLACE FUNCTION listar_recetas
RETURN SYS_REFCURSOR AS
    recetas_cursor SYS_REFCURSOR;
BEGIN
    OPEN recetas_cursor FOR
    SELECT id_receta, nombre FROM recetas;
    RETURN recetas_cursor;
END;
/

-- Actualizar receta
CREATE OR REPLACE PROCEDURE actualizar_receta (
    p_id_receta IN recetas.id_receta%TYPE,
    p_nombre    IN recetas.nombre%TYPE
) AS
BEGIN
    UPDATE recetas
    SET nombre = p_nombre
    WHERE id_receta = p_id_receta;
END;
/

-- Eliminar receta
CREATE OR REPLACE PROCEDURE eliminar_receta (
    p_id_receta IN recetas.id_receta%TYPE
) AS
BEGIN
    DELETE FROM recetas WHERE id_receta = p_id_receta;
END;
/
