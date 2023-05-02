package fes.aragon.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import fes.aragon.modelo.FacturaProducto;
import fes.aragon.modelo.Facturas;
import fes.aragon.modelo.Productos;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class NuevoFacturaProductoController implements Initializable {

	@FXML
	private TableColumn<Facturas, Integer> FacturaID;

	@FXML
	private TableColumn<Productos, Integer> ProductoID;

	@FXML
	private TableView<FacturaProducto> tblTableNuevaFactura;

	@FXML
	private TextField txtCantidadFacturaProducto;

	@FXML
	private TextField txtFacturaID;

	@FXML
	private TextField txtProductoID;

	private FacturaProducto fp = null;
	private FacturaProducto factura = new FacturaProducto();
	private FacturaProducto producto = new FacturaProducto();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.FacturaID.setCellValueFactory(new PropertyValueFactory<>("id_factura"));
		this.ProductoID.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
		this.tblTableNuevaFactura.getSelectionModel().selectedItemProperty()
				.addListener((obj, oldSeleccion, newSeleccion) -> {
					if (newSeleccion != null) {
						FacturaProducto fp = tblTableNuevaFactura.getSelectionModel().getSelectedItem();
						txtFacturaID.setText(fp.getId_factura().toString());
						txtProductoID.setText(fp.getId_producto().toString());
						factura.setId_factura(factura.getId_factura());
						producto.setId_producto(producto.getId_producto());

					}
				});
		this.traerDatos();
	}

	@FXML
	void Guardar(ActionEvent event) {
		if (fp == null) {
			fp = new FacturaProducto();
		}
		if (!validar()) {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			if (fp.getId_factura() == null) {
				almacenar();
				alerta.setContentText("Se almaceno factura");
				limpiar();
			} else {
				modificar();
				alerta.setContentText("Se modifico factura");
			}
			alerta.setHeaderText(null);
			alerta.showAndWait();

		}
	}

	@FXML
	void Limpiar(ActionEvent event) {
		limpiar();
	}

	@FXML
	void btnBuscarFactura(ActionEvent event) {
		buscarFactura();
	}

	@FXML
	void btnBuscarProducto(ActionEvent event) {
		buscarProducto();
	}

	private void almacenar() {
		try {
			Conexion cnn = new Conexion();
			fp.setId_factura(Integer.parseInt(txtFacturaID.getText()));
			fp.setId_producto(Integer.parseInt(txtProductoID.getText()));
			fp.setCantidadFacturaProducto(Double.parseDouble(txtCantidadFacturaProducto.getText()));
			cnn.almacenarFacturaProducto(fp);
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
			fp.setId_factura(Integer.parseInt(txtFacturaID.getText()));
			fp.setId_producto(Integer.parseInt(txtProductoID.getText()));
			fp.setCantidadFacturaProducto(Double.parseDouble(txtCantidadFacturaProducto.getText()));
			cnn.modificarFacturaProducto(fp);
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
		this.txtFacturaID.setText("");
		this.txtProductoID.setText("");
		this.txtCantidadFacturaProducto.setText("");
	}

	private boolean validar() {
		boolean validos = false;
		if (this.txtCantidadFacturaProducto.getText().isEmpty()
				|| this.txtProductoID.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;

		}
		if (this.txtCantidadFacturaProducto.getText().isEmpty()
				|| this.txtCantidadFacturaProducto.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;

		}
		return validos;
	}

	public void modificarFacturaProducto(FacturaProducto fp) {
		try {
			Conexion cnn = new Conexion();
			fp.setCantidadFacturaProducto(Double.parseDouble(txtCantidadFacturaProducto.getText()));
			cnn.modificarFacturaProducto(fp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText(null);
			alerta.setContentText("Ocurrio un problema");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}

	public void modificarFactura(FacturaProducto fp) {
		this.fp = fp;
		this.txtCantidadFacturaProducto.setText(fp.getCantidadFacturaProducto().toString());
	}

	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tblTableNuevaFactura.getItems().clear();
			this.tblTableNuevaFactura.setItems(cnn.todasFacturaProductos());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error en la aplicacion");
			alerta.setContentText("Consulta al fabricante");
			alerta.showAndWait();
		}
	}

	private void buscarFactura() {
		if (this.txtFacturaID.getText().isEmpty() || this.txtFacturaID.getText().regionMatches(0, "", 0, 1)) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Dato incorrecto");
			alerta.setContentText("Ingresa el id de la factura a buscar");
			alerta.showAndWait();
			this.traerDatos();
		} else {
			try {
				Conexion cnn = new Conexion();

				this.tblTableNuevaFactura.getItems().clear();
				this.tblTableNuevaFactura.setItems(cnn.buscarFacturas(Integer.parseInt(this.txtFacturaID.getText())));
				if (tblTableNuevaFactura.getItems().isEmpty()) {
					Alert alerta = new Alert(Alert.AlertType.INFORMATION);
					alerta.setHeaderText("Error al buscar la factura");
					alerta.setContentText("La factura no existe");
					alerta.showAndWait();
					this.traerDatos();
				}
			} catch (Exception e) {
				Alert alerta = new Alert(Alert.AlertType.INFORMATION);
				alerta.setHeaderText("Error al buscar la factura");
				alerta.setContentText("La factura no existe");
				alerta.showAndWait();
				e.printStackTrace();
			}
		}
	}

	private void buscarProducto() {
		if (this.txtProductoID.getText().isEmpty() || this.txtProductoID.getText().regionMatches(0, "", 0, 1)) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Dato incorrecto");
			alerta.setContentText("Ingresa el id del producto a buscar");
			alerta.showAndWait();
			this.traerDatos();
		} else {
			try {
				Conexion cnn = new Conexion();

				this.tblTableNuevaFactura.getItems().clear();
				this.tblTableNuevaFactura.setItems(cnn.buscarProductos(Integer.parseInt(this.txtProductoID.getText())));
				if (tblTableNuevaFactura.getItems().isEmpty()) {
					Alert alerta = new Alert(Alert.AlertType.INFORMATION);
					alerta.setHeaderText("Error al buscar el producto");
					alerta.setContentText("El producto no existe");
					alerta.showAndWait();
					this.traerDatos();
				}
			} catch (Exception e) {
				Alert alerta = new Alert(Alert.AlertType.INFORMATION);
				alerta.setHeaderText("Error al buscar el producto");
				alerta.setContentText("El producto no existe");
				alerta.showAndWait();
				e.printStackTrace();
			}
		}
	}
}
