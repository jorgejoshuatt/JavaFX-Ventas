package fes.aragon.controlador;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.Facturas;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class NuevaFacturaController implements Initializable {

	@FXML
	private TableColumn<Clientes, String> ApellidoCliente;

	@FXML
	private TableColumn<Clientes, Integer> clienteID;

	@FXML
	private DatePicker datepickerFecha;

	@FXML
	private TableColumn<Clientes, String> nombreCliente;

	@FXML
	private TableView<Clientes> tblTableNuevaFactura;
	
	@FXML
    private TextField txtClienteID;

	@FXML
	private TextField txtApellido;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtReferencia;

	private Clientes cliente = new Clientes();
	private Facturas factura = new Facturas();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.clienteID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.nombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.ApellidoCliente.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
		this.tblTableNuevaFactura.getSelectionModel().selectedItemProperty()
				.addListener((obj, oldSeleccion, newSeleccion) -> {
					if (newSeleccion != null) {
						Clientes cl = tblTableNuevaFactura.getSelectionModel().getSelectedItem();
						txtNombre.setText(cl.getNombre());
						txtApellido.setText(cl.getApellidoPaterno());
						txtClienteID.setText(cl.getId().toString());
						factura.setId_clientes(cliente.getId());
					}
				});
		this.traerDatos();
	}
	 
	@FXML
    void Buscar(ActionEvent event) {
		buscarCliente();
    }

	@FXML
	void Guardar(ActionEvent event) {
		if (factura == null) {
			factura = new Facturas();
		}
		if (!validar()) {
			Alert alerta = new Alert(AlertType.CONFIRMATION);
			if (factura.getId_facturas() == null) {
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

	private void almacenar() {
		try {
			
			Conexion cnn = new Conexion();
			factura.setId_clientes(Integer.parseInt(txtClienteID.getText()));
			LocalDate date = this.datepickerFecha.getValue();
	    	ZoneId defaultZoneId = ZoneId.systemDefault();
	    	java.util.Date fecha = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
	    	factura.setReferencia_facturas(txtReferencia.getText());
	    	factura.setFecha_facturas(fecha);
			cnn.almacenarFacturas(factura);
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
			factura.setReferencia_facturas(txtReferencia.getText());
			LocalDate date = this.datepickerFecha.getValue();
	    	ZoneId defaultZoneId = ZoneId.systemDefault();
	    	java.util.Date fecha = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
	    	factura.setFecha_facturas(fecha);
			cnn.modificarFacturas(factura);
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
		this.txtReferencia.setText("");
	}

	private boolean validar() {
		boolean validos = false;
		LocalDate date = this.datepickerFecha.getValue();
    	ZoneId defaultZoneId = ZoneId.systemDefault();
    	java.util.Date fecha = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
    	factura.setFecha_facturas(fecha);
		if (this.txtReferencia.getText().isEmpty() || this.txtReferencia.getText().regionMatches(0, " ", 0, 1)) {
			validos = false;

		}
		if (fecha == null) {
			validos = false;

		}
		return validos;
	}

	public void modificarFactura(Facturas factura) {
		this.factura = factura;
		this.txtReferencia.setText(factura.getReferencia_facturas());
		//new java.sql.Date(factura.getFecha_facturas().getTime()).toLocalDate();
		this.datepickerFecha.setValue(new java.sql.Date(factura.getFecha_facturas().getTime()).toLocalDate());
	}

	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tblTableNuevaFactura.getItems().clear();
			this.tblTableNuevaFactura.setItems(cnn.todosClientes());
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
	
	private void buscarCliente() {
		if(this.txtClienteID.getText().isEmpty() || this.txtClienteID.getText().regionMatches(0, "", 0, 1)) {
    		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    		alerta.setHeaderText("Dato incorrecto");
			alerta.setContentText("Ingresa el id del cliente a buscar");
			alerta.showAndWait();
			this.traerDatos();
    	} else {
    		try {
    		Conexion cnn = new Conexion();
    		
    		this.tblTableNuevaFactura.getItems().clear();
			this.tblTableNuevaFactura.setItems(cnn.buscarClientes(Integer.parseInt(this.txtClienteID.getText())));
			if(tblTableNuevaFactura.getItems().isEmpty()) {
				Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    			alerta.setHeaderText("Error al buscar el cliente");
    			alerta.setContentText("El cliente no existe");
    			alerta.showAndWait();
    			this.traerDatos();
				}
    		} catch (Exception e) {
    			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    			alerta.setHeaderText("Error al buscar el cliente");
    			alerta.setContentText("El cliente no existe");
    			alerta.showAndWait();
    			e.printStackTrace();
    		}
    	} 
		
	}
	
}
