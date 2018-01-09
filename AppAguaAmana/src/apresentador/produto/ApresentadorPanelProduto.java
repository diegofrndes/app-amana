package apresentador.produto;

import dao.FabricaDao;
import espectador.produto.EspectadorPanelProduto;
import gui.swing.produto.ModeloTabelaProduto;

public class ApresentadorPanelProduto {
	private EspectadorPanelProduto espectador;
	
	public ApresentadorPanelProduto(EspectadorPanelProduto espectador)
	{
		this.espectador = espectador;
	}
	
	public void procurarTodos(){
		this.espectador.setModeloTabelaProduto(
				new ModeloTabelaProduto(FabricaDao.getProdutoDao().procurarTodos(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));	
	}
}
