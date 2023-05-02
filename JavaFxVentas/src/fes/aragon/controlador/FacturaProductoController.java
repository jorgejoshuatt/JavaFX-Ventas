package fes.aragon.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.FacturaProducto;
import fes.aragon.mysql.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class FacturaProductoController implements Initializable{

    @FXML
    private TableColumn<FacturaProducto, Integer> FacturaID;

    @FXML
    private TableColumn<FacturaProducto, Integer> ProductoID;

    @FXML
    private TableColumn<FacturaProducto, Integer> cantidadFacturasProductos;

    @FXML
    private TableColumn<FacturaProducto, String> comando;

    @FXML
    private TableView<FacturaProducto> tblTableFacturaProducto;

    @FXML
    void nuevoFacturaProducto(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevoFacturaProducto.fxml"));
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void refrescar(MouseEvent event) {
    	this.traerDatos();
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.FacturaID.setCellValueFactory(new PropertyValueFactory<>("id_factura"));
		this.ProductoID.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
		this.cantidadFacturasProductos.setCellValueFactory(new PropertyValueFactory<>("cantidadFacturaProducto"));
		Callback<TableColumn<FacturaProducto, String>, TableCell<FacturaProducto, String>> celda = (
				TableColumn<FacturaProducto, String> parametros) -> {
			final TableCell<FacturaProducto, String> cel = new TableCell<FacturaProducto, String>() {

				@Override
				protected void updateItem(String arg0, boolean arg1) {
					// TODO Auto-generated method stub
					super.updateItem(arg0, arg1);
					if (arg1) {
						setGraphic(null);
						setText(null);
					} else {
						FontAwesomeIconView borrarIcono = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
						FontAwesomeIconView modificarIcono = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
						borrarIcono.setGlyphStyle("-fx-fill:RED; -glyph-size:18px;-fx-cursor: hand;");
						
						modificarIcono.setGlyphStyle("-fx-cursor: hand;-glyph-size:18px;-fx-fill:#00E676;");
						
						borrarIcono.setOnMouseClicked((MouseEvent evento) -> {
							FacturaProducto fp = tblTableFacturaProducto.getSelectionModel().getSelectedItem();
							borrarFacturaProducto(fp.getId_factura());
							
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento) -> {
							FacturaProducto fp = tblTableFacturaProducto.getSelectionModel().getSelectedItem();
							modificarFacturasProductos(fp);
						});
						HBox hbox = new HBox(borrarIcono, modificarIcono);
						hbox.setStyle("-fx-alignment:center");
						HBox.setMargin(borrarIcono, new Insets(2, 2, 0, 3));
						HBox.setMargin(modificarIcono, new Insets(2, 3, 0, 2));
						setGraphic(hbox);
						setText(null);
					}
				}

			};
			return cel;
		};
		this.comando.setCellFactory(celda);
		this.traerDatos();
	}
	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tblTableFacturaProducto.getItems().clear();
			this.tblTableFacturaProducto.setItems(cnn.todasFacturaProductos());
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
	private void borrarFacturaProducto(int id) {
		try {
			Conexion cnn = new Conexion();
  			cnn.eliminarFacturasProductos(id);
			this.traerDatos();
			
		} catch (Exception e) {
			
			Alert alerta = new Alert(AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error en la aplicacion");
			alerta.setContentText("Consulta al fabricante");
			alerta.showAndWait();
			e.printStackTrace();
		} 	
	}
	
	private void modificarFacturasProductos(FacturaProducto fp) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/NuevoFacturaProducto.fxml"));
			Parent parent = (Parent)alta.load();
			((NuevoFacturaProductoController)alta.getController()).modificarFactura(fp);
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
