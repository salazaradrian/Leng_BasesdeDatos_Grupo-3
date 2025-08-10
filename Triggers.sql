---1---
CREATE OR REPLACE TRIGGER trg_ventas_calc_totales
BEFORE INSERT OR UPDATE OF id_producto, cantidad_productos_total ON ventas
FOR EACH ROW
DECLARE
  v_precio productos.precio%TYPE;
  v_cant   ventas.cantidad_productos_total%TYPE;
BEGIN
  
  v_cant := NVL(:NEW.cantidad_productos_total, 1);


  SELECT precio
    INTO v_precio
    FROM productos
   WHERE id_producto = :NEW.id_producto;

  
  :NEW.monto_total := v_cant * NVL(v_precio, 0);
END;
/

---2---

CREATE OR REPLACE TRIGGER trg_facturas_calc_totales
BEFORE INSERT OR UPDATE OF id_ventas, subtotal, impuesto ON facturas
FOR EACH ROW
DECLARE
  v_subtotal ventas.monto_total%TYPE;
  c_iva CONSTANT NUMBER := 0.13; 
BEGIN
  
  IF :NEW.fecha IS NULL THEN
    :NEW.fecha := SYSDATE;
  END IF;


  IF :NEW.subtotal IS NULL OR INSERTING THEN
    SELECT monto_total
      INTO v_subtotal
      FROM ventas
     WHERE id_ventas = :NEW.id_ventas;

    :NEW.subtotal := NVL(v_subtotal, 0);
  END IF;


  :NEW.impuesto := ROUND(:NEW.subtotal * c_iva, 2);
  :NEW.total    := ROUND(:NEW.subtotal + :NEW.impuesto, 2);


  IF INSERTING AND :NEW.id_estado IS NULL THEN
    :NEW.id_estado := 1;
EXCEPTION
  WHEN NO_DATA_FOUND THEN

    RAISE_APPLICATION_ERROR(-20001, 'No existe la venta con ID ' || :NEW.id_ventas);
END;
/


 

