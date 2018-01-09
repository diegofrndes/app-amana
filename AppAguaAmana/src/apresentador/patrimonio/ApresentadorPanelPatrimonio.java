package apresentador.patrimonio;

import modelo.Patrimonio;
import dao.FabricaDao;
import espectador.patrimonio.EspectadorPanelPatrimonio;
import gui.swing.patrimonio.ModeloTabelaPatrimonio;

public class ApresentadorPanelPatrimonio {
	private EspectadorPanelPatrimonio espectador;
	
	public ApresentadorPanelPatrimonio(EspectadorPanelPatrimonio espectador){
		this.espectador = espectador;
	}
	
	public void procurarTodos(){
    	espectador.setModeloTabelaPatrimonio(
    			new ModeloTabelaPatrimonio(FabricaDao.getPatrimonioDao().procurarTodos(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
	public void procurarLixeira(){
    	espectador.setModeloTabelaPatrimonio(
    			new ModeloTabelaPatrimonio(FabricaDao.getPatrimonioDao().procurarLixeira(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
	public boolean desativar(Patrimonio patrimonio){
    	return FabricaDao.getPatrimonioDao().desativar(patrimonio);
    }
}
