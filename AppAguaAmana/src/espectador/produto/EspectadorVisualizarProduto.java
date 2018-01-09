package espectador.produto;

import java.math.BigDecimal;
import java.util.List;

import modelo.Item;

public interface EspectadorVisualizarProduto {
	
	public void setNome(String nome);
	
	public void setItens(List<Item> itens, List<Integer> quantidades );
	
	public void setValor(BigDecimal valor);

}
