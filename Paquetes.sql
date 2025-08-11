
CREATE OR REPLACE PACKAGE pkg_productos AS
  PROCEDURE agregar_producto(
    p_nombre      IN productos.nombre%TYPE,
    p_tipo        IN productos.tipo%TYPE,
    p_precio      IN productos.precio%TYPE,
    p_descripcion IN productos.descripcion%TYPE,
    p_id_receta   IN productos.id_receta%TYPE
  );

  PROCEDURE actualizar_producto(
    p_id_producto  IN productos.id_producto%TYPE,
    p_nombre       IN productos.nombre%TYPE,
    p_tipo         IN productos.tipo%TYPE,
    p_precio       IN productos.precio%TYPE,
    p_descripcion  IN productos.descripcion%TYPE,
    p_id_receta    IN productos.id_receta%TYPE
  );

  PROCEDURE eliminar_producto(
    p_id_producto IN productos.id_producto%TYPE
  );

  FUNCTION listar_productos RETURN SYS_REFCURSOR;
END pkg_productos;
/


---Cuerpo del paquete de productos

CREATE OR REPLACE PACKAGE BODY pkg_productos AS

  PROCEDURE agregar_producto(
    p_nombre      IN productos.nombre%TYPE,
    p_tipo        IN productos.tipo%TYPE,
    p_precio      IN productos.precio%TYPE,
    p_descripcion IN productos.descripcion%TYPE,
    p_id_receta   IN productos.id_receta%TYPE
  ) AS
  BEGIN
    INSERT INTO productos (nombre, tipo, precio, descripcion, id_receta)
    VALUES (p_nombre, p_tipo, p_precio, p_descripcion, p_id_receta);
  END agregar_producto;

  PROCEDURE actualizar_producto(
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
  END actualizar_producto;

  PROCEDURE eliminar_producto(
    p_id_producto IN productos.id_producto%TYPE
  ) AS
  BEGIN
    DELETE FROM productos WHERE id_producto = p_id_producto;
  END eliminar_producto;

  FUNCTION listar_productos RETURN SYS_REFCURSOR AS
    productos_cursor SYS_REFCURSOR;
  BEGIN
    OPEN productos_cursor FOR
    SELECT id_producto, nombre, tipo, precio, descripcion, id_receta
    FROM productos;
    RETURN productos_cursor;
  END listar_productos;

END pkg_productos;
/





-----Paquetes de clientes------

CREATE OR REPLACE PACKAGE pkg_clientes AS
  PROCEDURE agregar_cliente(
    p_nombre           IN clientes.nombre%TYPE,
    p_primer_apellido  IN clientes.primer_apellido%TYPE,
    p_segundo_apellido IN clientes.segundo_apellido%TYPE,
    p_telefono         IN clientes.telefono%TYPE,
    p_email            IN clientes.email%TYPE,
    p_direccion        IN clientes.direccion%TYPE
  );

  PROCEDURE actualizar_cliente(
    p_id_cliente       IN clientes.id_cliente%TYPE,
    p_nombre           IN clientes.nombre%TYPE,
    p_primer_apellido  IN clientes.primer_apellido%TYPE,
    p_segundo_apellido IN clientes.segundo_apellido%TYPE,
    p_telefono         IN clientes.telefono%TYPE,
    p_email            IN clientes.email%TYPE,
    p_direccion        IN clientes.direccion%TYPE
  );

  PROCEDURE eliminar_cliente(p_id_cliente IN clientes.id_cliente%TYPE);

  FUNCTION listar_clientes RETURN SYS_REFCURSOR;
END pkg_clientes;
/


---Cuerpo del paquete de clientes
CREATE OR REPLACE PACKAGE BODY pkg_clientes AS
  PROCEDURE agregar_cliente(
    p_nombre           IN clientes.nombre%TYPE,
    p_primer_apellido  IN clientes.primer_apellido%TYPE,
    p_segundo_apellido IN clientes.segundo_apellido%TYPE,
    p_telefono         IN clientes.telefono%TYPE,
    p_email            IN clientes.email%TYPE,
    p_direccion        IN clientes.direccion%TYPE
  ) AS
  BEGIN
    INSERT INTO clientes (nombre, primer_apellido, segundo_apellido, telefono, email, direccion)
    VALUES (p_nombre, p_primer_apellido, p_segundo_apellido, p_telefono, p_email, p_direccion);
    COMMIT;
  END;

  PROCEDURE actualizar_cliente(
    p_id_cliente       IN clientes.id_cliente%TYPE,
    p_nombre           IN clientes.nombre%TYPE,
    p_primer_apellido  IN clientes.primer_apellido%TYPE,
    p_segundo_apellido IN clientes.segundo_apellido%TYPE,
    p_telefono         IN clientes.telefono%TYPE,
    p_email            IN clientes.email%TYPE,
    p_direccion        IN clientes.direccion%TYPE
  ) AS
  BEGIN
    UPDATE clientes
       SET nombre = p_nombre,
           primer_apellido = p_primer_apellido,
           segundo_apellido = p_segundo_apellido,
           telefono = p_telefono,
           email = p_email,
           direccion = p_direccion
     WHERE id_cliente = p_id_cliente;
    COMMIT;
  END;

  PROCEDURE eliminar_cliente(p_id_cliente IN clientes.id_cliente%TYPE) AS
  BEGIN
    DELETE FROM clientes WHERE id_cliente = p_id_cliente;
    COMMIT;
  END;

  FUNCTION listar_clientes RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR
      SELECT id_cliente, nombre, primer_apellido, segundo_apellido, telefono, email, direccion
        FROM clientes;
    RETURN v_cur;
  END;
END pkg_clientes;
/




----Paquete de empleados

CREATE OR REPLACE PACKAGE pkg_empleados AS
  PROCEDURE agregar_empleado(
    p_nombre           IN empleados.nombre%TYPE,
    p_primer_apellido  IN empleados.primer_apellido%TYPE,
    p_segundo_apellido IN empleados.segundo_apellido%TYPE,
    p_salario          IN empleados.salario%TYPE,
    p_cargo            IN empleados.cargo%TYPE
  );

  PROCEDURE actualizar_empleado(
    p_id_empleado      IN empleados.id_empleado%TYPE,
    p_nombre           IN empleados.nombre%TYPE,
    p_primer_apellido  IN empleados.primer_apellido%TYPE,
    p_segundo_apellido IN empleados.segundo_apellido%TYPE,
    p_salario          IN empleados.salario%TYPE,
    p_cargo            IN empleados.cargo%TYPE
  );

  PROCEDURE eliminar_empleado(p_id_empleado IN empleados.id_empleado%TYPE);

  FUNCTION listar_empleados RETURN SYS_REFCURSOR;
END pkg_empleados;
/


---Cuerpo del paquete de empleados
CREATE OR REPLACE PACKAGE BODY pkg_empleados AS
  PROCEDURE agregar_empleado(
    p_nombre           IN empleados.nombre%TYPE,
    p_primer_apellido  IN empleados.primer_apellido%TYPE,
    p_segundo_apellido IN empleados.segundo_apellido%TYPE,
    p_salario          IN empleados.salario%TYPE,
    p_cargo            IN empleados.cargo%TYPE
  ) AS
  BEGIN
    INSERT INTO empleados (nombre, primer_apellido, segundo_apellido, salario, cargo)
    VALUES (p_nombre, p_primer_apellido, p_segundo_apellido, p_salario, p_cargo);
    COMMIT;
  END;

  PROCEDURE actualizar_empleado(
    p_id_empleado      IN empleados.id_empleado%TYPE,
    p_nombre           IN empleados.nombre%TYPE,
    p_primer_apellido  IN empleados.primer_apellido%TYPE,
    p_segundo_apellido IN empleados.segundo_apellido%TYPE,
    p_salario          IN empleados.salario%TYPE,
    p_cargo            IN empleados.cargo%TYPE
  ) AS
  BEGIN
    UPDATE empleados
       SET nombre = p_nombre,
           primer_apellido = p_primer_apellido,
           segundo_apellido = p_segundo_apellido,
           salario = p_salario,
           cargo = p_cargo
     WHERE id_empleado = p_id_empleado;
    COMMIT;
  END;

  PROCEDURE eliminar_empleado(p_id_empleado IN empleados.id_empleado%TYPE) AS
  BEGIN
    DELETE FROM empleados WHERE id_empleado = p_id_empleado;
    COMMIT;
  END;

  FUNCTION listar_empleados RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR
      SELECT id_empleado, nombre, primer_apellido, segundo_apellido, salario, cargo
        FROM empleados;
    RETURN v_cur;
  END;
END pkg_empleados;
/



---Paquete de ventas
CREATE OR REPLACE PACKAGE pkg_ventas AS
  PROCEDURE agregar_venta(
    p_id_cliente               IN NUMBER,
    p_monto_total              IN NUMBER,
    p_cantidad_productos_total IN NUMBER,
    p_id_empleado              IN NUMBER,
    p_id_producto              IN NUMBER
  );

  PROCEDURE editar_venta(
    p_id_ventas                IN NUMBER,
    p_id_cliente               IN NUMBER,
    p_monto_total              IN NUMBER,
    p_cantidad_productos_total IN NUMBER,
    p_id_empleado              IN NUMBER,
    p_id_producto              IN NUMBER
  );

  PROCEDURE eliminar_venta(p_id_ventas IN NUMBER);

  FUNCTION listar_ventas RETURN SYS_REFCURSOR;
END pkg_ventas;
/


---Cuerpo del paquete de ventas
CREATE OR REPLACE PACKAGE BODY pkg_ventas AS
  PROCEDURE agregar_venta(
    p_id_cliente               IN NUMBER,
    p_monto_total              IN NUMBER,
    p_cantidad_productos_total IN NUMBER,
    p_id_empleado              IN NUMBER,
    p_id_producto              IN NUMBER
  ) AS
  BEGIN
    INSERT INTO ventas (id_cliente, monto_total, cantidad_productos_total, id_empleado, id_producto)
    VALUES (p_id_cliente, p_monto_total, p_cantidad_productos_total, p_id_empleado, p_id_producto);
    COMMIT;
  END;

  PROCEDURE editar_venta(
    p_id_ventas                IN NUMBER,
    p_id_cliente               IN NUMBER,
    p_monto_total              IN NUMBER,
    p_cantidad_productos_total IN NUMBER,
    p_id_empleado              IN NUMBER,
    p_id_producto              IN NUMBER
  ) AS
  BEGIN
    UPDATE ventas
       SET id_cliente = p_id_cliente,
           monto_total = p_monto_total,
           cantidad_productos_total = p_cantidad_productos_total,
           id_empleado = p_id_empleado,
           id_producto = p_id_producto
     WHERE id_ventas = p_id_ventas;
    COMMIT;
  END;

  PROCEDURE eliminar_venta(p_id_ventas IN NUMBER) AS
  BEGIN
    DELETE FROM ventas WHERE id_ventas = p_id_ventas;
    COMMIT;
  END;

  FUNCTION listar_ventas RETURN SYS_REFCURSOR AS
    v_cur SYS_REFCURSOR;
  BEGIN
    OPEN v_cur FOR
      SELECT id_ventas, id_cliente, monto_total, cantidad_productos_total, id_empleado, id_producto
        FROM ventas;
    RETURN v_cur;
  END;
END pkg_ventas;
/




---Paquete de Factura


CREATE OR REPLACE PACKAGE pkg_factura AS
  PROCEDURE agregar_factura(
    p_id_ventas IN FACTURA.ID_VENTAS%TYPE,
    p_fecha     IN FACTURA.FECHA%TYPE,
    p_impuesto  IN FACTURA.IMPUESTO%TYPE,
    p_subtotal  IN FACTURA.SUBTOTAL%TYPE
  );

  PROCEDURE editar_factura(
    p_id_factura IN FACTURA.ID_FACTURA%TYPE,
    p_id_ventas  IN FACTURA.ID_VENTAS%TYPE,
    p_fecha      IN FACTURA.FECHA%TYPE,
    p_impuesto   IN FACTURA.IMPUESTO%TYPE,
    p_subtotal   IN FACTURA.SUBTOTAL%TYPE
  );

  PROCEDURE eliminar_factura(
    p_id_factura IN FACTURA.ID_FACTURA%TYPE
  );

  FUNCTION listar_facturas RETURN SYS_REFCURSOR;
END pkg_factura;
/


---Cuerpo del paquete de factura

   
CREATE OR REPLACE PACKAGE BODY pkg_factura AS

  PROCEDURE agregar_factura(
    p_id_ventas IN FACTURA.ID_VENTAS%TYPE,
    p_fecha     IN FACTURA.FECHA%TYPE,
    p_impuesto  IN FACTURA.IMPUESTO%TYPE,
    p_subtotal  IN FACTURA.SUBTOTAL%TYPE
  ) AS
  BEGIN
    INSERT INTO FACTURA (ID_VENTAS, FECHA, IMPUESTO, SUBTOTAL)
    VALUES (p_id_ventas, p_fecha, p_impuesto, p_subtotal);
    COMMIT;
  END agregar_factura;

  PROCEDURE editar_factura(
    p_id_factura IN FACTURA.ID_FACTURA%TYPE,
    p_id_ventas  IN FACTURA.ID_VENTAS%TYPE,
    p_fecha      IN FACTURA.FECHA%TYPE,
    p_impuesto   IN FACTURA.IMPUESTO%TYPE,
    p_subtotal   IN FACTURA.SUBTOTAL%TYPE
  ) AS
  BEGIN
    UPDATE FACTURA
       SET ID_VENTAS = p_id_ventas,
           FECHA     = p_fecha,
           IMPUESTO  = p_impuesto,
           SUBTOTAL  = p_subtotal
     WHERE ID_FACTURA = p_id_factura;
    COMMIT;
  END editar_factura;

  PROCEDURE eliminar_factura(
    p_id_factura IN FACTURA.ID_FACTURA%TYPE
  ) AS
  BEGIN
    DELETE FROM FACTURA
     WHERE ID_FACTURA = p_id_factura;
    COMMIT;
  END eliminar_factura;

  FUNCTION listar_facturas RETURN SYS_REFCURSOR AS
    cur_facturas SYS_REFCURSOR;
  BEGIN
    OPEN cur_facturas FOR
      SELECT ID_FACTURA, ID_VENTAS, FECHA, IMPUESTO, SUBTOTAL
        FROM FACTURA;
    RETURN cur_facturas;
  END listar_facturas;

END pkg_factura;
/









