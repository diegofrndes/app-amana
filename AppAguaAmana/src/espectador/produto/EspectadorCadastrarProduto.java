package espectador.produto;

import java.math.BigDecimal;
import java.util.List;

import modelo.Item;

public interface EspectadorCadastrarProduto {
	
	public String getNome();
	
	public List<Item> getItens();
	
	public List<Integer> getQuantidades();
	
	public BigDecimal getValor();
	
}
