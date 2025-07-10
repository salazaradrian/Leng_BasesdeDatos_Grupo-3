-- VISTA DE CONTACTO DE CLIENTES
CREATE OR REPLACE VIEW vista_clientes_contacto AS
SELECT
    ID_CLIENTE,
    NOMBRE || ' ' || PRIMER_APELLIDO || ' ' || SEGUNDO_APELLIDO AS "NOMBRE COMPLETO",
    TELEFONO,
    EMAIL
FROM
    CLIENTES
ORDER BY 
    NOMBRE ASC;
    
    
--VISTA DE CLIENTES POR PROVINCIA
CREATE OR REPLACE VIEW vista_clientes_por_provincia AS
SELECT
    id_cliente,
    nombre || ' ' || primer_apellido AS "NOMBRE DE CLIENTE",
    SUBSTR(direccion, 1, INSTR(direccion, ',') - 1) AS PROVINCIA 
FROM
    clientes
ORDER BY
    PROVINCIA ASC;
    
-- VISTA Productos con su receta
CREATE OR REPLACE VIEW vista_productos_receta AS
SELECT
    p.nombre AS "NOMBRE PRODUCTO",
    p.tipo AS "TIPO PRODUCTO",
    p.descripcion AS "DESCRIPCION PRODUCTO",
    r.nombre AS "NOMBRE RECETA"
FROM
    productos p
LEFT JOIN
    recetas r ON p.id_receta = r.id_receta;
    
-- VISTA PRODUCTOS PREMIUM PRECIO > 2000
CREATE OR REPLACE VIEW vista_productos_premium AS
SELECT
    id_producto,
    nombre AS "NOMBRE PRODUCTO",
    tipo AS "TIPO PRODUCTO",
    precio AS "PRECIO PRODUCTO",
    descripcion AS "DESCRIPCION PRODUCTO"
FROM
    PRODUCTOS
WHERE
    precio > 2000
ORDER BY
    precio DESC;