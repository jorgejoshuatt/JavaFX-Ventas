package fes.aragon.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.FacturaProducto;
import fes.aragon.modelo.Facturas;
import fes.aragon.modelo.Productos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Conexion {
	private String url = "jdbc:mysql://127.0.0.1:3306/ventas?serverTimezone=UTC";
	private String usuario = "root";
	private String psw = "joshua2412";
	private Connection conexion = null;

	public Conexion() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection(url, usuario, psw);
	}

	public ObservableList<Clientes> todosClientes() throws SQLException {
		ObservableList<Clientes> lista = FXCollections.observableArrayList();
		String query = "{call todosClientes()}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Clientes cl = new Clientes();
				cl.setId(Integer.parseInt(datos.getString(1)));
				cl.setNombre(datos.getString(2));
				cl.setApellidoPaterno(datos.getString(3));
				lista.add(cl);

			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}

	public void almacenarClientes(Clientes cliente) throws SQLException {
		String query = "{call insertarClientes(?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setString(1, cliente.getNombre());
		solicitud.setString(2, cliente.getApellidoPaterno());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}

	public void eliminarClientes(int id) throws SQLException {
		String query = "{call eliminarClientes(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id);
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}

	public void modificarClientes(Clientes cliente) throws SQLException {
		String query = "{call modificarClientes(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, cliente.getId());
		solicitud.setString(2, cliente.getNombre());
		solicitud.setString(3, cliente.getApellidoPaterno());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
	
	public ObservableList<Clientes> buscarClientes(Integer id) throws SQLException {
		ObservableList<Clientes> lista = FXCollections.observableArrayList();
		String query = "{call buscarClientes(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Clientes cl = new Clientes();
				cl.setId(Integer.parseInt(datos.getString(1)));
				cl.setNombre(datos.getString(2));
				cl.setApellidoPaterno(datos.getString(3));
				lista.add(cl);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}
	
	public ObservableList<Productos> todosProductos() throws SQLException {
		ObservableList<Productos> lista = FXCollections.observableArrayList();
		String query = "{call todosProductos()}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Productos pd = new Productos();
				pd.setId(Integer.parseInt(datos.getString(1)));
				pd.setNombre_productos(datos.getString(2));
				pd.setPrecio_productos(Double.parseDouble(datos.getString(3)));
				lista.add(pd);

			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}

	public void almacenarProductos(Productos producto) throws SQLException {
		String query = "{call insertarProductos(?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setString(1, producto.getNombre_productos());
		solicitud.setDouble(2, producto.getPrecio_productos());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}

	public void eliminarProductos(int id) throws SQLException {
		String query = "{call eliminarProductos(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id);
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}

	public void modificarProductos(Productos producto) throws SQLException {
		String query = "{call modificarProductos(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, producto.getId());
		solicitud.setString(2, producto.getNombre_productos());
		solicitud.setDouble(3, producto.getPrecio_productos());
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
	
	public ObservableList<FacturaProducto> buscarProductos(Integer id) throws SQLException {
		ObservableList<FacturaProducto> lista = FXCollections.observableArrayList();
		String query = "{call buscarProductos(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				FacturaProducto pd = new FacturaProducto();
				pd.setId_producto(Integer.parseInt(datos.getString(1)));;
				lista.add(pd);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}

	public ObservableList<Facturas> todosFacturas() throws SQLException {
		ObservableList<Facturas> lista = FXCollections.observableArrayList();
		String query = "{call todosFacturas()}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Clientes cliente = new Clientes();
				Facturas fc = new Facturas();
				fc.setId_facturas(Integer.parseInt(datos.getString(1)));
				fc.setReferencia_facturas(datos.getString(2));
				fc.setFecha_facturas(datos.getDate(3));
				cliente.setId(Integer.parseInt(datos.getString(4)));
				fc.setId_clientes(cliente.getId());
				lista.add(fc);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}

	public void almacenarFacturas(Facturas factura) throws SQLException {
		String query = "{call insertarFacturas(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, factura.getId_clientes());
		solicitud.setString(2, factura.getReferencia_facturas());
		solicitud.setDate(3, new java.sql.Date(factura.getFecha_facturas().getTime()));
		solicitud.execute();
		solicitud.close();
		conexion.close();

	}

	public void eliminarFacturas(int id) throws SQLException {
		String query = "{call eliminarFacturas(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id);
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}

	public void modificarFacturas(Facturas factura) throws SQLException {
		String query = "{call modificarFacturas(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, factura.getId_facturas());
		solicitud.setString(2, factura.getReferencia_facturas());
		solicitud.setDate(3, new java.sql.Date(factura.getFecha_facturas().getTime()));
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}
	
	public ObservableList<FacturaProducto> todasFacturaProductos() throws SQLException {
		ObservableList<FacturaProducto> lista = FXCollections.observableArrayList();
		String query = "{call todosFacturasProductos()}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Facturas fc = new Facturas();
				Productos producto = new Productos();
				FacturaProducto fp = new FacturaProducto();
				fc.setId_facturas(Integer.parseInt(datos.getString(1)));
				producto.setId(Integer.parseInt(datos.getString(2)));
				fp.setCantidadFacturaProducto(Double.parseDouble(datos.getString(3)));
				fp.setId_factura(fc.getId_facturas());
				fp.setId_producto(producto.getId());
				lista.add(fp);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}

	public void almacenarFacturaProducto(FacturaProducto fp) throws SQLException {
		String query = "{call insertarFacturasProductos(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, fp.getId_factura());
		solicitud.setInt(2, fp.getId_producto());
		solicitud.setDouble(3, fp.getCantidadFacturaProducto());
		solicitud.execute();
		solicitud.close();
		conexion.close();

		
	}

	public void eliminarFacturasProductos(int id) throws SQLException {
		String query = "{call eliminarFacturasProductos(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id);
		solicitud.execute();
		solicitud.close();
		conexion.close();
	}

	public void modificarFacturaProducto(FacturaProducto fp) throws SQLException {
		String query = "{call modificarFacturasProductos(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, fp.getId_factura());
		solicitud.setInt(2, fp.getId_producto());
		solicitud.setDouble(3, fp.getCantidadFacturaProducto());
		solicitud.execute();
		solicitud.close();
		conexion.close();
		
	}

	public ObservableList<FacturaProducto> buscarFacturas(Integer id) throws SQLException {
		ObservableList<FacturaProducto> lista = FXCollections.observableArrayList();
		String query = "{call buscarFacturas(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		solicitud.setInt(1, id);
		ResultSet datos = solicitud.executeQuery();
		if (!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				
				FacturaProducto fc = new FacturaProducto();
				fc.setId_factura(Integer.parseInt(datos.getString(1)));
				lista.add(fc);
			} while (datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		return lista;
	}
}