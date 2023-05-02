package fes.aragon.controlador;

import fes.aragon.modelo.Productos;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class NuevoProductoController {

    @FXML
    private TextField txtNombreProducto;

    @FXML
    private TextField txtPrecioProducto;

    @FXML
	public void accionLimpiar(ActionEvent event) {
		//
		limpiar();
	}

	private Productos producto = null;

	@FXML
	public void guardarAccion(ActionEvent event) {
		if (producto == null) {
			producto = new Productos();
		}
		if (!validar()) {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			if (producto.getId() == null) {
				almacenar();
				alerta.setContentText("Se almacenó producto");
				limpiar();
			} else {
				modificar();
				alerta.setContentText("Se modificó producto");
			}
			alerta.setHeaderText(null);
			alerta.showAndWait();

		}

	}

	private void almacenar() {
		try {
			Conexion cnn = new Conexion();
			producto.setNombre_productos(txtNombreProducto.getText());
			producto.setPrecio_productos(Double.parseDouble(txtPrecioProducto.getText()));
			cnn.almacenarProductos(producto);
		} catch (Exception e) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setHeaderText(null);
			alerta.setContentText("Ocurrio un problema");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}

	private void modificar() {
		try {
			Conexion cnn = new Conexion();
			producto.setNombre_productos(txtNombreProducto.getText());
			producto.setPrecio_productos(Double.parseDouble(txtPrecioProducto.getText()));
			cnn.modificarProductos(producto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText(null);
			alerta.setContentText("Ocurrio un problema");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}

	private void limpiar() {
		this.txtNombreProducto.setText("");
		this.txtPrecioProducto.setText("");
	}

	private boolean validar() {
		boolean validos = false;
		if (this.txtNombreProducto.getText().isEmpty() || this.txtNombreProducto.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;

		}
		if (this.txtPrecioProducto.getText().isEmpty()
				|| this.txtPrecioProducto.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;

		}
		return validos;
	}

	public void modificarProducto(Productos producto) {
		this.producto = producto;
		this.txtNombreProducto.setText(producto.getNombre_productos());
		this.txtPrecioProducto.setText(producto.getPrecio_productos().toString());
	}
}


    
