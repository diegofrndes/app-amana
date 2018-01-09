package apresentador.produto;

import dao.FabricaDao;
import espectador.produto.EspectadorCadastrarItem;
import modelo.Item;

public class ApresentadorCadastrarItem {
	private EspectadorCadastrarItem espectador;
	public ApresentadorCadastrarItem(EspectadorCadastrarItem espectador)
	{
		this.espectador = espectador;
	}
	
	public boolean cadastrar(){
		Item item = new Item(-1);
		item.setFornecedor(espectador.getFornecedor());
		item.setNome(espectador.getNome());
		item.setLimiteCritico(espectador.getLimiteCritico());
		return (FabricaDao.getItemDao().salvar(item) != -1);
	}
	
	public boolean existeItem(){
		return FabricaDao.getItemDao().existeItem(espectador.getNome());
	}
	
}
