package fes.aragon.modelo;

public class FacturaProducto {
	private Integer id_factura;
	private Integer id_producto;
	private Double cantidadFacturaProducto;
	public FacturaProducto() {
		// TODO Auto-generated constructor stub
	}
	public FacturaProducto(Integer id_factura, Integer id_producto, Double cantidadFacturaProducto) {
		super();
		this.id_factura = id_factura;
		this.id_producto = id_producto;
		this.cantidadFacturaProducto = cantidadFacturaProducto;
	}
	public Integer getId_factura() {
		return id_factura;
	}
	public void setId_factura(Integer id_factura) {
		this.id_factura = id_factura;
	}
	public Integer getId_producto() {
		return id_producto;
	}
	public void setId_producto(Integer id_producto) {
		this.id_producto = id_producto;
	}
	public Double getCantidadFacturaProducto() {
		return cantidadFacturaProducto;
	}
	public void setCantidadFacturaProducto(Double cantidadFacturaProducto) {
		this.cantidadFacturaProducto = cantidadFacturaProducto;
	}
	
}
