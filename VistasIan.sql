--vistas de las tablas ingredientes y compras



--muestra las compras mayores a 5000
CREATE OR REPLACE VIEW vista_compras_mayores_5000 AS
SELECT
    c.id_compra,
    i.nombre AS "INGREDIENTE COMPRADO",
    c.cantidad_ingredientes AS "CANTIDAD",
    c.monto_total AS "MONTO TOTAL",
    TO_CHAR(c.fecha, 'DD-MM-YYYY') AS "FECHA DE COMPRA"
FROM
    compras c
JOIN
    ingredientes i ON c.id_ingrediente = i.id_ingrediente
WHERE
    c.monto_total > 5000
ORDER BY
    c.monto_total DESC;
    
--muestra los ingredientes que están en 2 o mas recetas

CREATE OR REPLACE VIEW vista_ingredientes_mas_usados AS
SELECT
    nombre AS "NOMBRE INGREDIENTE",
    COUNT(id_receta) AS "RECETAS QUE LO USAN",
    SUM(cantidad) AS "CANTIDAD TOTAL USADA"
FROM
    ingredientes
GROUP BY
    nombre
HAVING
    COUNT(id_receta) >= 2
ORDER BY
    "CANTIDAD TOTAL USADA" DESC;