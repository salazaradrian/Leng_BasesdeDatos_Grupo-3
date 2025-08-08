-- Agregar producto
CREATE OR REPLACE PROCEDURE agregar_producto (
    p_nombre      IN productos.nombre%TYPE,
    p_tipo        IN productos.tipo%TYPE,
    p_precio      IN productos.precio%TYPE,
    p_descripcion IN productos.descripcion%TYPE,
    p_id_receta   IN productos.id_receta%TYPE
) AS
BEGIN
    INSERT INTO productos (nombre, tipo, precio, descripcion, id_receta)
    VALUES (p_nombre, p_tipo, p_precio, p_descripcion, p_id_receta);
END;
/

-- Listar productos
CREATE OR REPLACE FUNCTION listar_productos
RETURN SYS_REFCURSOR AS
    productos_cursor SYS_REFCURSOR;
BEGIN
    OPEN productos_cursor FOR
    SELECT * FROM productos;
    RETURN productos_cursor;
END;
/

-- Actualizar producto
CREATE OR REPLACE PROCEDURE actualizar_producto (
    p_id_producto  IN productos.id_producto%TYPE,
    p_nombre       IN productos.nombre%TYPE,
    p_tipo         IN productos.tipo%TYPE,
    p_precio       IN productos.precio%TYPE,
    p_descripcion  IN productos.descripcion%TYPE,
    p_id_receta    IN productos.id_receta%TYPE
) AS
BEGIN
    UPDATE productos
    SET nombre = p_nombre,
        tipo = p_tipo,
        precio = p_precio,
        descripcion = p_descripcion,
        id_receta = p_id_receta
    WHERE id_producto = p_id_producto;
END;
/

-- Eliminar producto
CREATE OR REPLACE PROCEDURE eliminar_producto (
    p_id_producto IN productos.id_producto%TYPE
) AS
BEGIN
    DELETE FROM productos WHERE id_producto = p_id_producto;
END;
/