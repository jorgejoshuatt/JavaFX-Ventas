package fes.aragon.modelo;

import java.util.Date;

public class Facturas {
	private Integer id_facturas;
	private Integer id_clientes;
	private String referencia_facturas;
	private Date fecha_facturas;
	public Facturas() {
		// TODO Auto-generated constructor stub
	}
	public Facturas(Integer id_facturas, Integer id_clientes, String referencia_facturas, Date fecha_facturas) {
		super();
		this.id_facturas = id_facturas;
		this.id_clientes = id_clientes;
		this.referencia_facturas = referencia_facturas;
		this.fecha_facturas = fecha_facturas;
	}
	public Integer getId_facturas() {
		return id_facturas;
	}
	public void setId_facturas(Integer id_facturas) {
		this.id_facturas = id_facturas;
	}
	public Integer getId_clientes() {
		return id_clientes;
	}
	public void setId_clientes(Integer id_clientes) {
		this.id_clientes = id_clientes;
	}
	public String getReferencia_facturas() {
		return referencia_facturas;
	}
	public void setReferencia_facturas(String referencia_facturas) {
		this.referencia_facturas = referencia_facturas;
	}
	public Date getFecha_facturas() {
		return fecha_facturas;
	}
	public void setFecha_facturas(Date localDate) {
		this.fecha_facturas = localDate;
	}
}
