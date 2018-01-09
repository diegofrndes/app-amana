package apresentador.produto;

import dao.FabricaDao;
import espectador.produto.EspectadorEditarItem;
import modelo.Item;

public class ApresentadorEditarItem {
	private EspectadorEditarItem espectador;
	private Item item;
	
	public ApresentadorEditarItem(EspectadorEditarItem espectador, Item item){
		this.espectador = espectador;
		this.item = item;
		atualizarTela();
	}
	
	private void atualizarTela()
	{
		espectador.setFornecedor(item.getFornecedor());
		espectador.setLimiteCritico(item.getLimiteCritico());
		espectador.setNome(item.getNome());
	}
	
	public boolean editar(){
		item.setFornecedor(espectador.getFornecedor());
		item.setLimiteCritico(espectador.getLimiteCritico());
		return (FabricaDao.getItemDao().editar(item));
	}
	
}
