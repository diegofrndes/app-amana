package espectador.produto;

import modelo.Pessoa;

public interface EspectadorEditarItem {
	
	public int getLimiteCritico();
	
	public String getNome();
	
	public Pessoa getFornecedor();
	
	public void setLimiteCritico(int limite);
	
	public void setNome(String nome);
	
	public void setFornecedor(Pessoa fornecedor);

}
