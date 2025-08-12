--Triggers

--Trigger 1
CREATE OR REPLACE TRIGGER trg_log_creacion_receta
AFTER INSERT ON recetas
FOR EACH ROW
BEGIN
  DBMS_OUTPUT.PUT_LINE('Receta creada: ' || :NEW.nombre || ' (ID: ' || :NEW.id_receta || ')');
END;
/

--Trigger 2 
CREATE OR REPLACE TRIGGER trg_validar_cantidad_ingredientes
BEFORE INSERT OR UPDATE ON ingredientes
FOR EACH ROW
BEGIN
  IF :NEW.cantidad < 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'La cantidad no puede ser negativa.');
  END IF;
END;
/

--Trigger 3
CREATE OR REPLACE TRIGGER trg_auditoria_compras
AFTER INSERT OR UPDATE OR DELETE ON compras
FOR EACH ROW
BEGIN
  DBMS_OUTPUT.PUT_LINE('Operación en compras: ' ||
    CASE
      WHEN INSERTING THEN 'INSERT'
      WHEN UPDATING THEN 'UPDATE'
      WHEN DELETING THEN 'DELETE'
    END || ' en ID ' || NVL(:NEW.id_compra, :OLD.id_compra));
END;
/