package apresentador.venda;

import dao.FabricaDao;
import espectador.venda.EspectadorPanelVenda;
import gui.swing.venda.ModeloTabelaVenda;

public class ApresentadorPanelVenda {

	private EspectadorPanelVenda espectador;
	
	public ApresentadorPanelVenda(EspectadorPanelVenda espectador){
		this.espectador = espectador;
	}
	
	public void procurarTodas(){
	 	espectador.setModeloTabelaVenda(
    			new ModeloTabelaVenda(FabricaDao.getVendaDao().procurarTodas(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
   
	}
	
	public void procurarEmAberto(){
	 	espectador.setModeloTabelaVenda(
    			new ModeloTabelaVenda(FabricaDao.getVendaDao().procurarEmAberto(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
   
	}
	
	public void procurarFinalizadas(){
	 	espectador.setModeloTabelaVenda(
    			new ModeloTabelaVenda(FabricaDao.getVendaDao().procurarFinalizadas(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
   
	}
	
	public void procurarLixeira(){
	 	espectador.setModeloTabelaVenda(
    			new ModeloTabelaVenda(FabricaDao.getVendaDao().procurarLixeira(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
   
	}
}
