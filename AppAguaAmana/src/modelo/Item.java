package modelo;

public class Item {
	private int id;
	private Pessoa fornecedor;
	private String nome;
	private int limiteCritico;
	private int quantidade;
	
	public Item(int id)
	{
		this.id = id;
		setFornecedor(null);
		setNome("");
		setLimiteCritico(0);
		setQuantidade(0);
	}

	public int getId() {
		return id;
	}

	public Pessoa getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Pessoa fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getLimiteCritico() {
		return limiteCritico;
	}

	public void setLimiteCritico(int limiteCritico) {
		this.limiteCritico = limiteCritico;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
    public boolean equals(Object obj){
        if( obj instanceof Item )
            return (((Item) obj).getId() == this.getId());
        else return false;
    }

}
