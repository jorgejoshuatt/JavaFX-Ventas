package fes.aragon.modelo;

public class Productos {
	private Integer id;
	private String nombre_productos;
	private Double precio_productos;
	public Productos() {
		// TODO Auto-generated constructor stub
	}
	
	public Productos(Integer id, String nombre_productos, Double precio_productos) {
		super();
		this.id = id;
		this.nombre_productos = nombre_productos;
		this.precio_productos = precio_productos;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre_productos() {
		return nombre_productos;
	}
	public void setNombre_productos(String nombre_productos) {
		this.nombre_productos = nombre_productos;
	}
	public Double getPrecio_productos() {
		return precio_productos;
	}
	public void setPrecio_productos(Double precio_productos) {
		this.precio_productos = precio_productos;
	}
	
}
