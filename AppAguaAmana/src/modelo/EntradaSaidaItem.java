package modelo;

public class EntradaSaidaItem {

	private int id;
	private int quantidade;
	private String motivo;
	private Item item;
	private String data;
	
	public EntradaSaidaItem(){
		id = -1;
		quantidade = 0;
		motivo = "";
		item = null;
		setData("");
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public String getMotivo() {
		return motivo;
	}
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
