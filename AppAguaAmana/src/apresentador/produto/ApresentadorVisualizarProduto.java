package apresentador.produto;

import modelo.Produto;
import espectador.produto.EspectadorVisualizarProduto;

public class ApresentadorVisualizarProduto {
	
	public ApresentadorVisualizarProduto(EspectadorVisualizarProduto espectador, Produto modelo){
		espectador.setNome(modelo.getNome());
		espectador.setValor(modelo.getValor());
		espectador.setItens(modelo.getItens(), modelo.getQuantidades());
	}
}
