package modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Produto {

	private int id;
	private String nome;
	private List<Item> itens;
	private List<Integer> quantidades;
	private BigDecimal valor;
	
	public Produto(int id)
	{
		this.id = id;
		itens = new ArrayList<Item>();
		quantidades = new ArrayList<Integer>();
		//setValor(0.0f);
		setNome("");
	}
	
	public void addItem(Item item, int quantidade){
		itens.add(item);
		quantidades.add(quantidade);
	}
	
	public List<Item> getItens(){
		return itens;
	}
	
	public List<Integer> getQuantidades(){
		return quantidades;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
    public boolean equals(Object obj){
        if( obj instanceof Produto )
            return (((Produto) obj).getId() == this.getId());
        else return false;
    }
	
}
