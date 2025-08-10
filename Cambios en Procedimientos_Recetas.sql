
----------Borrar Funcion de Listar_recetas y crear el siguiente
CREATE OR REPLACE FUNCTION listar_recetas
RETURN SYS_REFCURSOR
IS
    recetas_cursor SYS_REFCURSOR;
BEGIN
    OPEN recetas_cursor FOR
        SELECT id_receta, nombre FROM recetas;
    RETURN recetas_cursor;
END;
/


--------Borrar Procedimiento de Actualizar_Recetas y crear el siguiente
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
