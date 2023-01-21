DROP DATABASE IF EXISTS Makako_Burger;
CREATE DATABASE Makako_Burger;
USE Makako_Burger;

/*---------------------- CREACIÓN DE TABLAS ----------------------*/
/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Distrito (
	idDistrito INT,
	nomDistrito VARCHAR(50),
	CONSTRAINT PKidDistrito PRIMARY KEY (idDistrito)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS ZonaReparto (
	idZonaReparto INT,
	nombreZona VARCHAR(100),
	idDistrito INT,
	latitud VARCHAR(100),
	longitud VARCHAR(100),
	CONSTRAINT PKIDZonaReparto PRIMARY KEY (idZonaReparto),
	FOREIGN KEY (idDistrito) REFERENCES Distrito (idDistrito)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Establecimiento (
	idEstablecimiento INT , 
	nomEstablecimiento VARCHAR(100),
	telefonoEstablecimiento CHAR(15),
	idZonaReparto INT,
	SobreNosotros VARCHAR(500),
	CONSTRAINT PKidEstablecimiento PRIMARY KEY (idEstablecimiento),
	FOREIGN KEY (idZonaReparto) REFERENCES ZonaReparto (idZonaReparto)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Cargo (
	IdCargo INT,
	Cargo VARCHAR (80) NOT NULL,
	CONSTRAINT PKIdCargo PRIMARY KEY (IdCargo)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS DNI (
	Dni CHAR(8) UNIQUE NOT NULL,
	CONSTRAINT PKDNI PRIMARY KEY (DNI) 
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Empleados (
    ID_Empleado INT,
    nom_Empleado VARCHAR(50),
    ape_Empleado VARCHAR(50),
    IdCargo INT NOT NULL,
    fechaRegistro_Empleado DATETIME,
    telf_Empleado CHAR(15),
    Dni CHAR(8) NOT NULL,
    CONSTRAINT PKID_Empleado PRIMARY KEY (ID_Empleado),
    FOREIGN KEY (IdCargo) REFERENCES Cargo (IdCargo),
    FOREIGN KEY (Dni) REFERENCES DNI (Dni),
    FULLTEXT (nom_Empleado, ape_Empleado, telf_Empleado, Dni) -- PARA PODER REALIZAR EL MATCH
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Cliente (
	idCliente INT NOT NULL,
	nomCliente VARCHAR (50) NOT NULL,
	apeCliente VARCHAR(50) NOT NULL,
	Dni CHAR(8) NOT NULL,
	FOREIGN KEY (Dni) REFERENCES DNI (Dni),
	CONSTRAINT PKCLI PRIMARY KEY (idCliente) 
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS tipoUsuario (
	idTipoUsuario INT NOT NULL,
	nomTipoUsuario VARCHAR(50),
	CONSTRAINT idTipoUsuario PRIMARY KEY (idTipoUsuario)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Usuario (
	IdUsuario INT NOT NULL,
	idTipoUsuario INT NOT NULL, 
	Dni CHAR(8) UNIQUE NULL,
	correo VARCHAR(50) UNIQUE NOT NULL,
	contraseña VARCHAR(25) NOT NULL,
	CONSTRAINT IdUsuario PRIMARY KEY (IdUsuario),
	FOREIGN KEY (idTipoUsuario) REFERENCES tipoUsuario (idTipoUsuario),
	FOREIGN KEY (Dni) REFERENCES DNI (Dni)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Cat_Producto (
	Id_CatProd INT NOT NULL,
	nombre_CatProd VARCHAR(20) NOT NULL,
	CONSTRAINT PKID_CatProd PRIMARY KEY (Id_CatProd)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Producto (
	codPro INT,
	nomPro VARCHAR(50) NOT NULL,
	precioPro DECIMAL(8,2) NOT NULL,
	Id_CatProd INT NOT NULL,
	imagenProd VARCHAR(300) NOT NULL,
	CONSTRAINT codPro PRIMARY KEY (codPro),
	FOREIGN KEY (Id_CatProd) REFERENCES Cat_Producto (Id_CatProd)  
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Promocion (
	codPromo INT,
	precioPromo DECIMAL (8,2) NOT NULL,
	nomPromo VARCHAR(50) NOT NULL,
	fechaVigencia DATE NULL, 
	imagenCombo VARCHAR(300) NOT NULL,
	CONSTRAINT PKcodPromo PRIMARY KEY (codPromo)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS DetallePromo (
	codDetallePromo INT NOT NULL,
	codPromo INT NOT NULL,
	codPro INT NOT NULL,
	cantidad INT NOT NULL,
	CONSTRAINT PKcodDetallePromo PRIMARY KEY (codDetallePromo),
	FOREIGN KEY (codPromo) REFERENCES  Promocion (codPromo),
	FOREIGN KEY (codPro) REFERENCES Producto (codPro)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS MetPago (
	codMetPago INT,
	NomTipoPago VARCHAR(40) NOT NULL,
	CONSTRAINT PKCodMetPago PRIMARY KEY (codMetPago)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS estadosPedido (
	codEstadoPedido INT,
	nomTipoEstado VARCHAR(40) NOT NULL,
	CONSTRAINT PKCodEstadoPedido PRIMARY KEY (codEstadoPedido)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS metodoRecojo (
	codMetodoRecojo INT,
	nomTipoRecojo VARCHAR(40) NOT NULL,
	CONSTRAINT PKCodMetodoRecojo PRIMARY KEY(codMetodoRecojo)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Pedido(
	codPedido INT,
	fecPedido DATE NOT NULL,
	idCliente INT NOT NULL,
	precTotPedido DECIMAL(8,2) NOT NULL,
	codEstadoPedido INT,
	codMetodoRecojo INT,
	codMetPago INT NOT NULL,
	CONSTRAINT PKcodPedido PRIMARY KEY (codPedido),
	FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente),
	FOREIGN KEY (codMetPago) REFERENCES MetPago (codMetPago),
	FOREIGN KEY (codEstadoPedido) REFERENCES estadosPedido (codEstadoPedido),
	FOREIGN KEY (codMetodoRecojo) REFERENCES metodoRecojo (codMetodoRecojo)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Detalle_De_Pedido (
	numDetalle INT NOT NULL,
    codPedido INT NOT NULL,
    codPro INT NULL,
    codPromo INT NULL,
    cant INT NOT NULL,
    precioPedidoTot DECIMAL(8, 2),
    CONSTRAINT PKnumDetalle PRIMARY KEY (numDetalle),
    FOREIGN KEY (codPedido) REFERENCES Pedido (codPedido),
    FOREIGN KEY (codPro) REFERENCES Producto (codPro),
    FOREIGN KEY (codPromo) REFERENCES Promocion (codPromo)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS tipoReclamo(
	idTipoReclamo INT PRIMARY KEY NOT NULL,
	nomTipoReclamo VARCHAR(50) NOT NULL
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Reclamos(
    numReclamo INT ,
    idPedido INT,
    fechaReclamo DATE NOT NULL,
    idCliente INT NOT NULL,
    idTipoReclamo INT NOT NULL,
    descripcionReclamo  VARCHAR(200) NOT NULL,
    CONSTRAINT PKnumReclamo PRIMARY KEY (numReclamo),
    CONSTRAINT FKtipoReclamo FOREIGN KEY (idTipoReclamo) REFERENCES tipoReclamo (idTipoReclamo),
    CONSTRAINT FKpedido FOREIGN KEY (idPedido) REFERENCES Pedido (codPedido),
    FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Comprobante_De_Pago(
	codComprobante INT,
	idEstablecimiento INT, 
	codPedido INT,
    fecEmitido DATE NOT NULL,
    precTotPedido DECIMAL(8,2) NOT NULL,
    precCostoEnvio DECIMAL(8, 2) NULL,
	CONSTRAINT PKcodComprobante PRIMARY KEY (codComprobante ),
    FOREIGN KEY (idEstablecimiento) REFERENCES Establecimiento (idEstablecimiento),
    FOREIGN KEY (codPedido) REFERENCES Pedido (codPedido)
);

/*------------------------------------------------*/
CREATE TABLE IF NOT EXISTS Hoja_De_envio(
	codHojaEnvio INT,
	codPedido INT,
	idZonaReparto INT,
	telefono VARCHAR(15) NOT NULL,
    codEstadoPedido INT NULL,
	CONSTRAINT PKcodHojaEnvio  PRIMARY KEY (codHojaEnvio),
	FOREIGN KEY (codPedido) REFERENCES Pedido (codPedido),
    FOREIGN KEY (codEstadoPedido) REFERENCES estadosPedido (codEstadoPedido),
    FOREIGN KEY (idZonaReparto) REFERENCES ZonaReparto (idZonaReparto)
);

/*---------------------- INSERCIÓN DE DATAOS ----------------------*/
/*------------------------------------------------*/
INSERT INTO Distrito VALUES 
(1, 'San Martin de Porres');

INSERT INTO zonaReparto VALUES 
(1, 'Jiron Las Vascongadas 110 Urb. Los Girasoles - La Molina, 15024',
	1,'-11.939517189210175','-77.08463024528645');

INSERT INTO Establecimiento VALUES 
(1,'Makako Burger','966313252', 1,'Somos Makako Burguer un restuarante con una variedad de comida extensa, En ese momento existían cadenas nacionales que ofrecían hamburguesas dentro de su menú, pero no se había desarrollado el hábito de consumo de hamburguesa en el público peruano ni habían llegado al país las grandes cadenas internacionales.');
 
INSERT INTO Cat_Producto VALUES
(1, 'Hamburguesas'),
(2, 'Salchipapas'),
(3, 'Alitas'),
(4, 'Complementos'),
(5, 'Bebidas'),
(6, 'A la Carta');

INSERT INTO estadosPedido VALUES
(1, 'Generado'),
(2, 'Atendido');

INSERT INTO metodoRecojo VALUES
(1, 'Delivery'),
(2, 'En tienda');

INSERT INTO tipoUsuario VALUES
(1, 'Empleado'),
(2, 'Cliente');

INSERT INTO Cargo VALUES
(1, 'Administrador'),
(2, 'Cajero'),
(3, 'Repartidor'),
(4, 'Gerente');

INSERT INTO DNI VALUES 
('77797607');

INSERT INTO Empleados VALUES
(1, 'Admin Admin', 'Admin Admin', '1', CURDATE(), '999999999', '88888888');

INSERT INTO Usuario VALUES
(1, 1,'88888888', 'prueba@outlook.com', 'admin');

INSERT INTO MetPago VALUES
(1, 'YAPE'),
(2, 'PLIN'),
(3, 'VISA'),
(4, 'MASTERCARD'),
(5, 'CRÉDITO');

INSERT INTO tipoReclamo VALUES
(1,'Servicio'),
(2,'Producto');

/*---------------------- CREACIÓN DE PROCEDIMINENTOS ALMACENADOS ----------------------*/
/*---------------------- LOGIN ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_buscar_correo`
	(vCorreo VARCHAR(50))
BEGIN
	SELECT correo FROM Usuario WHERE correo = vCorreo;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_cambiar_contraseña`
	(vContraseña VARCHAR(25), vCorreo VARCHAR(50))
BEGIN
	UPDATE Usuario SET contraseña = vContraseña WHERE correo = vCorreo;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE CATEGORÍA PRODUCTOS ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_listar_categoria_prod`()
BEGIN
	SELECT * FROM Cat_Producto;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE PRODUCTOS ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_listar_producto`
	(vConsulta VARCHAR(255), vId_CatProd VARCHAR(10), vPrecioInicio DOUBLE, vPrecioFinal DOUBLE)
BEGIN
	SELECT P.*, C.nombre_CatProd  FROM Producto AS P JOIN Cat_Producto AS C
		ON C.Id_CatProd = P.Id_CatProd
	WHERE P.nomPro LIKE CONCAT("%", vConsulta,"%")
		AND C.Id_CatProd LIKE CONCAT("%", vId_CatProd, "%") 
		AND P.precioPro BETWEEN IFNULL(vPrecioInicio, 0)
		AND IFNULL(vPrecioFinal, 999999);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_agregar_prod`
	(vIdProd INT, vNom_prod VARCHAR(50), vPrecio_prod DECIMAL(7,2), vNom_Imagen VARCHAR(255), vId_CatProd INT)
BEGIN
	INSERT Producto VALUES (vIdProd, vNom_prod, vPrecio_prod, vId_CatProd, vNom_Imagen);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_actualizar_prod`
	(vIdProd INT, vNom_prod VARCHAR(50), vPrecio_prod DECIMAL(7,2), vNom_Imagen VARCHAR(255), vId_CatProd INT)
BEGIN
	IF (vNom_Imagen <> NULL) THEN
		UPDATE Producto SET nomPro = vNom_prod, precioPro = vPrecio_prod, imagenProd = vNom_Imagen, Id_CatProd = vId_CatProd
		WHERE codPro = vIdProd;
	ELSE 
		UPDATE Producto SET nomPro = vNom_prod, precioPro = vPrecio_prod, Id_CatProd = vId_CatProd
		WHERE codPro = vIdProd;
    END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_eliminar_prod`
	(vIdProd INT)
BEGIN
	DELETE FROM Producto WHERE codPro = vIdProd;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_getLastIdProd`()
BEGIN
	SELECT P.codPro FROM Producto AS P ORDER BY codPro DESC;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE PROMOCIONES ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_getLastIdPromo`()
BEGIN
	SELECT P.codPromo FROM Promocion AS P ORDER BY codPromo DESC;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_getLastIdDetallePromo`()
BEGIN
	SELECT P.codDetallePromo FROM DetallePromo AS P ORDER BY codDetallePromo DESC;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_agregar_promocion`
	(vNom_promo VARCHAR(50), vPrecio_promo DECIMAL(7,2), vfechaVigencia DATE, vruta_Imagen VARCHAR(255), vcodPro INT, vCantidad INT)
BEGIN
	SET @id = IFNULL((SELECT codPromo + 1 FROM Promocion ORDER BY codPromo DESC LIMIT 1), 1);
	SET @id2 = IFNULL((SELECT codDetallePromo + 1 FROM DetallePromo ORDER BY codDetallePromo DESC LIMIT 1), 1);

	INSERT Promocion VALUES (@id, vPrecio_promo, vNom_promo, vfechaVigencia, vruta_Imagen);
	INSERT DetallePromo VALUES (@id2, @id, vcodPro, vCantidad);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_actualizar_promo`
    (vnomPromo VARCHAR(50), vPrecio_promo DECIMAL(7,2), vfechaVigencia DATE,  vruta_Imagen VARCHAR(255), vcodPromo INT)
BEGIN
	IF (vruta_Imagen <> NULL) THEN
		UPDATE Promocion SET precioPromo  = vPrecio_promo, fechaVigencia = vfechaVigencia, imagenCombo = vruta_Imagen, nomPromo = vnomPromo
		WHERE codPromo = vcodPromo;
	ELSE 
		UPDATE Promocion SET precioPromo  = vPrecio_promo, fechaVigencia = vfechaVigencia, nomPromo = vnomPromo
		WHERE codPromo = vcodPromo;
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_eliminar_promo`
	(vIdPromo INT)
BEGIN
	DELETE FROM DetallePromo WHERE codPromo = vIdPromo;
	DELETE FROM Promocion WHERE codPromo = vIdPromo;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_listar_promocion`
	(vConsulta VARCHAR(255), vPrecioInicio DOUBLE, vPrecioFinal DOUBLE, vFechaInicio DATE, vFechaFinal DATE)
BEGIN
	SELECT P.*  FROM Promocion AS P 
	WHERE P.nomPromo LIKE CONCAT("%", vConsulta,"%")
		AND P.precioPromo BETWEEN IFNULL(vPrecioInicio, 0)
		AND IFNULL(vPrecioFinal, 999999)
		AND P.fechaVigencia BETWEEN IFNULL(vFechaInicio, '2000-01-01')
		AND IFNULL(vFechaFinal, '3000-01-01')
    ORDER BY codPromo;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_listar_Detallepromocion`
	(vIdPromo VARCHAR(10))
BEGIN
    SELECT D.*, P.nomPro, P.Id_CatProd, C.nombre_CatProd, P.imagenProd FROM DetallePromo AS D
		JOIN Producto AS P ON P.codPro = D.codPro
		JOIN Cat_Producto AS C ON C.Id_CatProd = P.Id_CatProd
    WHERE D.codPromo LIKE IF(vIdPromo IS NULL OR vIdPromo = "", "%%" ,vIdPromo);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_ActualizarDetallePromo`
	(vIdPromo INT, vcodPro INT, vCantidad INT, vTipoOp INT)
BEGIN
	CASE
		WHEN vTipoOp = 1 THEN
			SET @id := (SELECT codDetallePromo FROM DetallePromo ORDER BY codDetallePromo DESC LIMIT 1);
			INSERT DetallePromo VALUES(@id + 1, vIdPromo, vcodPro, vCantidad);
		WHEN vTipoOp = 2 THEN
			UPDATE DetallePromo SET cantidad = vCantidad
			WHERE codPromo = vIdPromo AND codPro = vcodPro;
		WHEN vTipoOp = 3 THEN
			DELETE FROM DetallePromo WHERE codPromo = vIdPromo AND codPro = vcodPro;
    END CASE;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE USUARIOS ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_Iniciar_Sesion`
	(cCorreo VARCHAR(50), cClave VARCHAR(25))
BEGIN
	SELECT u.IdUsuario, t.idTipoUsuario, d.Dni, u.correo, u.contraseña
    FROM Usuario AS u INNER JOIN tipoUsuario AS t
		ON u.idTipoUsuario = t.idTipoUsuario
		INNER JOIN Dni AS d 
		ON u.Dni = d.Dni
    WHERE u.correo = cCorreo AND u.contraseña = cClave;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_Listar_Usuario`()
BEGIN
	SELECT * FROM Usuario;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE EMPLEADOS ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_listar_empleado`
	(vConsulta VARCHAR(255), vIdCargo VARCHAR(10), vFechaInicio DATETIME, vFechaFinal DATETIME)
BEGIN
	IF (vConsulta <> '') THEN
		SELECT E.*, C.Cargo, U.IdUsuario, U.correo FROM Empleados AS E JOIN
			Usuario AS U ON U.Dni = E.Dni JOIN Cargo AS C
			ON C.IdCargo = E.IdCargo 
		WHERE MATCH (nom_Empleado, ape_Empleado, telf_Empleado, E.Dni) 
			AGAINST (CONCAT(vConsulta,'*')  IN BOOLEAN MODE) 
			AND C.IdCargo LIKE CONCAT("%", vIdCargo,"%")
			AND E.fechaRegistro_Empleado BETWEEN IFNULL(vFechaInicio, '2000-01-01')
			AND IFNULL(vFechaFinal, '2024-10-10')
        ORDER BY ID_Empleado; 
	ELSE 
		SELECT E.*, C.Cargo, U.IdUsuario, U.correo FROM Empleados AS E JOIN
			Usuario AS U ON U.Dni = E.Dni JOIN Cargo AS C
			ON C.IdCargo = E.IdCargo 
        WHERE C.IdCargo LIKE CONCAT("%", vIdCargo,"%") 
			AND E.fechaRegistro_Empleado BETWEEN IFNULL(vFechaInicio, '2000-01-01')
			AND IFNULL(vFechaFinal, '2024-10-10')
        ORDER BY ID_Empleado; 
    END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_agregar_empleado`
	(vCorreo VARCHAR(50), vContraseña VARCHAR(25), vNom_Empleado VARCHAR(50), vApe_Empleado VARCHAR(50), 
	vTelf_Empleado CHAR(15), vDni_empleado CHAR(8), vIdCargo INT, vFechaRegistro_Empleado DATETIME)
BEGIN
	SELECT @id := ID_Empleado  FROM Empleados ORDER BY ID_Empleado DESC LIMIT 1;
    SELECT @id2 := IdUsuario FROM usuario ORDER BY IdUsuario DESC LIMIT 1;
   
	INSERT DNI VALUES (vDni_empleado);
    INSERT Empleados VALUES (@id + 1, vNom_Empleado, vApe_Empleado, vIdCargo, vFechaRegistro_Empleado, vTelf_Empleado, vDni_empleado);
	INSERT Usuario VALUES (@id2 + 1, 1, vDni_empleado, vCorreo, vContraseña);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_actualizar_empleado`
	(vIdUsuario INT, vIdEmpleado INT, vCorreo VARCHAR(50), vNom_Empleado VARCHAR(50), vApe_Empleado VARCHAR(50), vTelf_Empleado CHAR(15), 
	vIdCargo INT)
BEGIN
	UPDATE Usuario SET correo = vCorreo WHERE IdUsuario = vIdUsuario;
    UPDATE Empleados SET nom_Empleado = vNom_Empleado, ape_Empleado = vApe_Empleado, telf_Empleado = vTelf_Empleado, IdCargo = vIdCargo 
	WHERE ID_Empleado = vIdEmpleado;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_eliminar_empleado`
	(vIdUsuario INT, vIdEmpleado INT, vDni VARCHAR(8))
BEGIN
	DELETE FROM Usuario WHERE IdUsuario = vIdUsuario;
    DELETE FROM Empleados WHERE ID_Empleado = vIdEmpleado;
    DELETE FROM DNI WHERE Dni = vDni;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_getLastIdEmpleado`()
BEGIN
	SELECT E.ID_Empleado FROM Empleados AS E ORDER BY ID_Empleado DESC;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_listar_cargo`()
BEGIN
	SELECT * FROM Cargo;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE DNI ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_Listar_dni`()
BEGIN
	SELECT * FROM dni;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE CLIENTES ----------------------*/
DELIMITER $$ 
CREATE PROCEDURE `sp_Insert_cliente`
	(cNombre VARCHAR(50), cApellido VARCHAR(50), cDni CHAR(8), cCorreo VARCHAR(50), cContraseña VARCHAR(25))
BEGIN
	SET @id = IFNULL((SELECT IdCliente FROM cliente ORDER BY IdCliente DESC LIMIT 1), 1);
	SET @id2 = IFNULL((SELECT idUsuario FROM  usuario ORDER BY IdUsuario DESC LIMIT 1), 1);
	
    INSERT INTO Dni VALUES(cDni);
	INSERT INTO Cliente VALUES(@id + 1, cNombre, cApellido, cDni);
	INSERT Usuario VALUES(@id2 + 1, 2, cDni, cCorreo, cContraseña);
END $$
DELIMITER ; 

/*---------------------- PROCEDIMIENTO ALMACENADO DE ESTABLECIMIENTO ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_listar_establecimiento`()
BEGIN
	SELECT E.*, D.nomDistrito, Z.nombreZona FROM Establecimiento AS E 
		JOIN ZonaReparto AS Z ON Z.idZonaReparto = E.idZonaReparto 
		JOIN Distrito AS D ON D.idDistrito = Z.idDistrito;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE`sp_actualizar_establecimiento`
	(vId INT, vSobreNosotros VARCHAR(500))
BEGIN
    UPDATE Establecimiento SET SobreNosotros = vSobreNosotros
    WHERE idEstablecimiento = vId;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE PEDIDOS ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_listar_pedidos`
	(vConsulta VARCHAR(255), vFechaInicio DATETIME, vFechaFinal DATETIME, vEstadoPedido VARCHAR(30))
BEGIN
	SELECT P.*, E.nomTipoEstado, M.NomTipoPago, R.nomTipoRecojo, C.nomCliente, C.apeCliente FROM Pedido AS P
		JOIN MetPago AS M ON M.codMetPago = P.codMetPago
		JOIN metodoRecojo AS R ON R.codMetodoRecojo = P.codMetodoRecojo
		JOIN estadosPedido AS E ON E.codEstadoPedido = P.codEstadoPedido
		JOIN Cliente AS C ON C.idCliente = P.idCliente
    WHERE C.Dni LIKE CONCAT("%", vConsulta, "%")
		AND P.fecPedido BETWEEN IFNULL(vFechaInicio, '2000-01-01')
		AND IFNULL (vFechaFinal, CURDATE())
		AND P.codEstadoPedido LIKE CONCAT("%", vEstadoPedido, "%")
    ORDER BY P.codPedido;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_listar_detalle_pedidos_productos`
	(vCodPromo VARCHAR(255))
BEGIN
	SELECT D.numDetalle, D.codPedido, D.codPro, D.cant, D.precioPedidoTot, P.nomPro, P.precioPro, P.Id_CatProd, C.nombre_CatProd, P.imagenProd FROM Detalle_De_Pedido AS D
		JOIN Producto AS P ON P.codPro = D.codPro
		JOIN Cat_Producto AS C ON C.Id_CatProd = P.Id_CatProd
	WHERE D.codPedido LIKE IF(vCodPromo IS NULL OR vCodPromo = "", "%%", vCodPromo)
		AND D.codPro <> 0;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_listar_detalle_pedidos_promos`
	(vCodPromo VARCHAR(255))
BEGIN
	SELECT  D.numDetalle, D.codPedido, D.codPromo, D.cant, D.precioPedidoTot, P.precioPromo, P.nomPromo, P.fechaVigencia, P.imagenCombo FROM Detalle_De_Pedido AS D
		JOIN Promocion AS P ON P.codPromo = D.codPromo
	WHERE D.codPedido LIKE IF(vCodPromo IS NULL OR vCodPromo = "", "%%", vCodPromo) 
		AND D.codPromo <> 0;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_actualizar_pedido`
	(vEstado INT, vCod INT)
BEGIN
	UPDATE Pedido SET codEstadoPedido = vEstado WHERE codPedido = vCod; 
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_agregar_pedido`
	(vidCliente INT, vprecTot DECIMAL (8,2), vmetRecojo INT, vmetPago INT)
BEGIN
	SET @id  = IFNULL((SELECT codPedido + 1 FROM Pedido ORDER BY codPedido DESC LIMIT 1), 1);
    INSERT Pedido VALUES (@id, CURDATE(), vidCliente, vprecTot, 1, vmetRecojo, vmetPago);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_agregar_detalle_pedido`
	(vcodProd INT, vcodPromo INT, vcant INT, vprecioPedidoTot DECIMAL(8,2))
BEGIN
	SET @id = IFNULL((SELECT codPedido FROM Pedido ORDER BY codPedido DESC LIMIT 1), 1);
    SET @id2 = IFNULL((SELECT numDetalle + 1 FROM Detalle_De_Pedido ORDER BY numDetalle  DESC LIMIT 1), 1);
    
    IF(vcodProd <> 0) THEN
        INSERT Detalle_De_Pedido VALUES (@id2, @id, VcodProd, NULL, vcant, vprecioPedidoTot);
	END IF;
    
    IF(vcodPromo <> 0) THEN
		INSERT Detalle_De_Pedido VALUES (@id2, @id, NULL, vcodPromo, vcant, vprecioPedidoTot);
	END IF;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE MÉTODOS DE PAGO ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_listar_metPago`()
BEGIN
	SELECT * FROM MetPago;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE ESTADOS DE PEDIDO ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_listar_estadosPedido`()
BEGIN
	SELECT * FROM estadosPedido;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE MÉTODOS DE RECOJO ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_listar_metRecojo`()
BEGIN
	SELECT * FROM metodoRecojo;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE RECLAMOS ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_registrar_Reclamo`
    (vidPedido INT, vfechaReclamo DATETIME, vidCliente INT, vidTipoReclamo INT, vdescripReclamo VARCHAR(8000))
BEGIN
    SET @id = IFNULL((SELECT numReclamo + 1 FROM Reclamos ORDER BY numReclamo DESC LIMIT 1), 1);
    INSERT Reclamos VALUES(@id, vidPedido, vfechaReclamo, vidCliente, vidTipoReclamo, vdescripReclamo);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_listar_reclamo`
	(vDni VARCHAR(255), vIdTipoReclamo VARCHAR(10), vFecInicio DATE, vFecFinal DATE)
BEGIN
	SELECT R.*, CL.DNI, TR.nomTipoReclamo FROM Reclamos AS R 
		JOIN Cliente AS CL ON CL.idCliente = R.idCliente  
		JOIN TipoReclamo AS TR ON R.idTipoReclamo = TR.idTipoReclamo
    WHERE CL.Dni LIKE CONCAT("%", vDni, "%")
		AND R.idTipoReclamo LIKE CONCAT("%", vIdTipoReclamo, "%")
		AND R.fechaReclamo BETWEEN IFNULL(vFecInicio, "2000-01-01")
		AND IFNULL(vFecFinal, NOW());
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_listar_tipo_reclamo`()
BEGIN
	SELECT * FROM TipoReclamo;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE CLIENTES ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_Listar_Cliente` ()
BEGIN
	SELECT * FROM Cliente;
END$$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE CDP ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_listar_cdp`
	(vDni VARCHAR(255), vFecInicio DATE, vFecFinal DATE, vPrecioInicio DOUBLE, vPrecioFinal DOUBLE)
BEGIN
	SELECT C.*, CL.DNI FROM Comprobante_De_Pago AS C 
		JOIN Pedido AS P ON C.codPedido = P.codPedido 
		JOIN Cliente AS CL ON CL.idCliente = P.idCliente  
    WHERE CL.Dni LIKE CONCAT("%", vDni, "%")
		AND C.fecEmitido  BETWEEN IFNULL(vFecInicio, "2000-01-01")
		AND IFNULL(vFecFinal, NOW())
		AND C.precTotPedido BETWEEN IFNULL(vPrecioInicio, 0)
		AND IFNULL(vPrecioFinal, 999999);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_agregar_cdp`
	(vprecTotPedido DECIMAL(8,2), vprecCostoEnvio DECIMAL(8,2))
BEGIN
	SET @id = IFNULL((SELECT codPedido FROM Pedido ORDER BY codPedido DESC LIMIT 1), 1);
    SET @id2 = IFNULL((SELECT codComprobante + 1 FROM Comprobante_De_Pago ORDER BY codComprobante DESC LIMIT 1), 1);
   
   INSERT Comprobante_De_Pago VALUES(@id2, 1, @id, CURDATE(), vprecTotPedido, vprecCostoEnvio);
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE HOJA DE ENVIO ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_agregar_hojaEnvio`
	(vidZonaReparto INT, vtelefono VARCHAR(50))
BEGIN
	SET @id = IFNULL((SELECT codPedido FROM Pedido ORDER BY codPedido DESC LIMIT 1), 1);
    SET @id2 = IFNULL((SELECT codHojaEnvio + 1 FROM Hoja_De_envio ORDER BY codHojaEnvio DESC LIMIT 1), 1);
    
    INSERT Hoja_De_envio VALUES(@id2, @id, vidZonaReparto, vtelefono, 1);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_actualizar_hoja_Envio`
	(vEstado INT, vCod INT)
BEGIN
	UPDATE Hoja_De_envio SET codEstadoPedido = vEstado
    WHERE codPedido = vCod;
	
    UPDATE Pedido SET codEstadoPedido = vEstado 
    WHERE codPedido = vCod; 
END $$
DELIMITER;

DELIMITER $$
CREATE PROCEDURE `sp_listar_hoja_envio`
	(vidZona VARCHAR(100), vEstadoPedido VARCHAR(50))
BEGIN
	SELECT H.*, Z.nombreZona, D.idDistrito , D.nomDistrito, E.nomTipoEstado FROM Hoja_De_envio AS H
		JOIN ZonaReparto AS Z ON Z.idZonaReparto = H.idZonaReparto 
		JOIN Distrito AS D ON D.idDistrito  = Z.idDistrito
		JOIN estadosPedido AS E ON E.codEstadoPedido = H.codEstadoPedido
    WHERE Z.nombreZona LIKE IF(vidZona IS NULL OR vidZona = "", "%%", vidZona) 
		AND H.codEstadoPedido LIKE IF(vEstadoPedido IS NULL OR vEstadoPedido = "", "%%", vEstadoPedido)
    ORDER BY H.codHojaEnvio ASC; 
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE ZONA DE REPARTO ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_Listar_ZonaDeReparto`
	(vidDistrito VARCHAR(50),zNombreZona VARCHAR(100))
BEGIN
    SELECT z.*,d.nomDistrito FROM ZonaReparto AS z JOIN Distrito AS d
		ON z.idDistrito=d.idDistrito
    WHERE z.idDistrito LIKE IF(vidDistrito IS NULL OR vidDistrito = "", "%%", vidDistrito)
		AND z.nombreZona LIKE IF(zNombreZona IS NULL OR zNombreZona = "", "%%", CONCAT("%", zNombreZona, "%"));
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_DeleteZonaReparto`
	(id INT)
BEGIN
	DELETE FROM  ZonaReparto WHERE idZonaReparto=id;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_Insert_ZonaDeReparto`
	(nombreZona VARCHAR(100), distrito VARCHAR(50), latitud VARCHAR(100), longitud VARCHAR(100))
BEGIN
	SET @encontrado = (SELECT idDistrito FROM distrito WHERE UPPER(nomDistrito) = UPPER(distrito) LIMIT 1);
    SET @id2 = IFNULL((SELECT idZonaReparto FROM ZonaReparto ORDER BY idZonaReparto DESC LIMIT 1),1);
    
    IF(@encontrado IS NOT NULL OR @encontrado <> "")then 
		INSERT INTO ZonaReparto VALUES(@id2 + 1, nombreZona, @encontrado, latitud, longitud);
    ELSE 
		SET @id = IFNULL((SELECT idDistrito FROM Distrito ORDER BY idDistrito DESC LIMIT 1), 1);
		
        INSERT INTO Distrito VALUES(@id + 1, distrito);
		INSERT INTO ZonaReparto VALUES(@id2 + 1, nombreZona, @id + 1, latitud, longitud);
	END IF;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE DISTRITOS ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_Listar_Distrito`()
BEGIN
    SELECT * FROM Distrito;
END $$
DELIMITER ;

/*---------------------- PROCEDIMIENTO ALMACENADO DE REPORTES ----------------------*/
DELIMITER $$
CREATE PROCEDURE `sp_generar_cdp_reporte_productos`
	(vcodComprobante INT)
BEGIN
	SELECT C.codPedido, Cli.Dni, MeP.NomTipoPago, MeR.nomTipoRecojo, Pro.nomPro, Pro.precioPro, C.precTotPedido, C.fecEmitido, Det.cant FROM Comprobante_De_Pago AS C
		JOIN Pedido AS Pe ON Pe.codPedido = C.codPedido 
		JOIN Cliente AS Cli ON Cli.idCliente = Pe.idCliente
		JOIN MetPago AS MeP ON Mep.codMetPago = Pe.codMetPago
		JOIN metodoRecojo AS MeR ON MeR.codMetodoRecojo = Pe.codMetodoRecojo
		JOIN Detalle_De_Pedido AS Det ON Det.codPedido = Pe.codPedido
		JOIN Producto AS Pro ON Pro.codPro = Det.codPro
    WHERE C.codComprobante  = vcodComprobante;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_generar_cdp_reporte_promociones`(vcodComprobante INT)
BEGIN
	SELECT C.codPedido, Cli.Dni, MeP.NomTipoPago, MeR.nomTipoRecojo, Pro.nomPromo, Pro.precioPromo, C.precTotPedido, C.fecEmitido, Det.cant FROM Comprobante_De_Pago AS C
		JOIN Pedido AS Pe ON Pe.codPedido = C.codPedido 
		JOIN Cliente AS Cli ON Cli.idCliente = Pe.idCliente
		JOIN MetPago AS MeP ON Mep.codMetPago = Pe.codMetPago
		JOIN metodoRecojo AS MeR ON MeR.codMetodoRecojo = Pe.codMetodoRecojo
		JOIN Detalle_De_Pedido AS Det ON Det.codPedido = Pe.codPedido
		JOIN Promocion AS Pro ON Pro.codPromo  = Det.codPromo 
    WHERE C.codComprobante  = vcodComprobante;
END $$
DELIMITER ;
