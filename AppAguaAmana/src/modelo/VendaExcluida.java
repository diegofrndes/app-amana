package modelo;

public class VendaExcluida {
	private int id;
	private Venda venda;
	private String motivo;
	private String usuario;
	private String data;
	
	public VendaExcluida(Venda venda){
		this.venda = venda;
		this.motivo = "";
		this.usuario = "";
	}
	
	public VendaExcluida(Venda venda, String motivo, String usuario){
		this.venda = venda;
		this.motivo = motivo;
		this.usuario = usuario;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
