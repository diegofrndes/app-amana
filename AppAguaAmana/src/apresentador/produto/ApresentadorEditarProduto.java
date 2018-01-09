package apresentador.produto;

import dao.FabricaDao;
import modelo.Produto;
import espectador.produto.EspectadorEditarProduto;

public class ApresentadorEditarProduto {
	private EspectadorEditarProduto espectador;
	private Produto modelo;
	
	public ApresentadorEditarProduto(EspectadorEditarProduto espectador, Produto modelo){
		this.espectador = espectador;
		this.modelo = modelo;
		espectador.setItens(modelo.getItens(), modelo.getQuantidades());
		espectador.setNome(modelo.getNome());
		espectador.setValor(modelo.getValor());
	}
	
	public boolean editar(){
		modelo.setNome(espectador.getNome());
		modelo.setValor(espectador.getValor());
		return FabricaDao.getProdutoDao().editar(modelo);
	}
}
