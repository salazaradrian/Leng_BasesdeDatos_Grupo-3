CREATE OR REPLACE VIEW Vista_de_Ventas AS
SELECT    v.id_ventas,
    c.nombre || ' ' || c.primer_apellido AS cliente,
    e.nombre || ' ' || e.primer_apellido AS empleado,
    p.nombre AS producto,
    p.precio,
    v.cantidad_productos_total,
    v.monto_total
FROM ventas v
INNER JOIN clientes c ON v.id_cliente = c.id_cliente
INNER JOIN empleados e ON v.id_empleado = e.id_empleado
INNER JOIN productos p ON v.id_producto = p.id_producto;
