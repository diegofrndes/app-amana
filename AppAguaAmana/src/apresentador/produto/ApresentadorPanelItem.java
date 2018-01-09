package apresentador.produto;

import dao.FabricaDao;
import espectador.produto.EspectadorPanelItem;
import gui.swing.produto.ModeloTabelaItem;

public class ApresentadorPanelItem {
	private EspectadorPanelItem espectador;
	
	public ApresentadorPanelItem(EspectadorPanelItem espectador)
	{
		this.espectador = espectador;
	}
	
	public void procurarTodos(){
    	espectador.setModeloTabelaItem(
    			new ModeloTabelaItem(FabricaDao.getItemDao().procurarTodos(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
	
	public void procurarLimiteCriticoOk(){
    	espectador.setModeloTabelaItem(
    			new ModeloTabelaItem(FabricaDao.getItemDao().procurarLimiteCriticoOk(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
	
	public void procurarLimiteCriticoUltrapassado(){
    	espectador.setModeloTabelaItem(
    			new ModeloTabelaItem(FabricaDao.getItemDao().procurarLimiteCriticoUltrapassado(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
}
