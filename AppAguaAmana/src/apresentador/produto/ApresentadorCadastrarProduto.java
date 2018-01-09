package apresentador.produto;

import java.util.List;

import dao.FabricaDao;

import modelo.Item;
import modelo.Produto;
import espectador.produto.EspectadorCadastrarProduto;

public class ApresentadorCadastrarProduto {
	private EspectadorCadastrarProduto espectador;
	
	public ApresentadorCadastrarProduto(EspectadorCadastrarProduto espectador){
		this.espectador = espectador;
	}
	
	public boolean cadastrar(){
		Produto produto = new Produto(-1);
		produto.setNome(espectador.getNome());
		produto.setValor(espectador.getValor());
		List<Item> itens = espectador.getItens();
		List<Integer> quantidades = espectador.getQuantidades();
		for(int i = 0; i < itens.size(); i++){
			produto.addItem(itens.get(i), quantidades.get(i));
		}
		return (FabricaDao.getProdutoDao().salvar(produto) != -1);
	}
}
