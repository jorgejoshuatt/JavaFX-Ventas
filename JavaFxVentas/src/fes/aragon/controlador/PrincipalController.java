package fes.aragon.controlador;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;

public class PrincipalController {
	@FXML
	private BorderPane idPrincipal;

	// Event Listener on Button.onAction
	@FXML
	public void accionCliente(ActionEvent event) {
		// TODO Autogenerated
		Contenido contenido=new Contenido("/fes/aragon/vista/Cliente.fxml");
		idPrincipal.setCenter(contenido);
	}
	// Event Listener on Button.onAction
	@FXML
	public void accionFactura(ActionEvent event) {
		// TODO Autogenerated
		Contenido contenido=new Contenido("/fes/aragon/vista/Facturas.fxml");
		idPrincipal.setCenter(contenido);
	}
	// Event Listener on Button.onAction
	@FXML
	public void accionFacturaProd(ActionEvent event) {
		// TODO Autogenerated
		Contenido contenido=new Contenido("/fes/aragon/vista/FacturaProducto.fxml");
		idPrincipal.setCenter(contenido);
	}
	// Event Listener on Button.onAction
	@FXML
	public void accionProducto(ActionEvent event) {
		// TODO Autogenerated
		Contenido contenido=new Contenido("/fes/aragon/vista/Producto.fxml");
		idPrincipal.setCenter(contenido);
	}
}