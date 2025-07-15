CREATE OR REPLACE VIEW Vista_de_Productos_caros AS
SELECT nombre,tipo,precio
FROM productos
WHERE precio >= 2000
