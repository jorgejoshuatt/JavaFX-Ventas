CREATE 
	ALGORITHM=UNDEFINED
    DEFINER=`root`@`localhost`
    SQL SECURITY DEFINER
VIEW `ventas`.`todosclientes` AS
	SELECT
		`ventas`.`clientes`.`id_clientes` AS `id_clientes`,
        `ventas`.`clientes`.`nombre_clientes` AS `nombre_clientes`,
        `ventas`.`clientes`.`apellido_clientes` AS `apellido_clientes`
	FROM
		`ventas`.`clientes`
