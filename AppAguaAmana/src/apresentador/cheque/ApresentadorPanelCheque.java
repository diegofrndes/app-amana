package apresentador.cheque;

import dao.FabricaDao;
import espectador.cheque.EspectadorPanelCheque;
import gui.swing.cheque.ModeloTabelaCheque;

public class ApresentadorPanelCheque {
private EspectadorPanelCheque espectador;
	
	public ApresentadorPanelCheque(EspectadorPanelCheque espectador)
	{
		this.espectador = espectador;
	}
	
    public void procurarTodos(){
    	espectador.setModeloTabelaCheque(
    			new ModeloTabelaCheque(FabricaDao.getChequeDao().procurarTodos(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public void procurarPendentes(){
    	espectador.setModeloTabelaCheque(
    			new ModeloTabelaCheque(FabricaDao.getChequeDao().procurarPendentes(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public void procurarVencidos(){
    	espectador.setModeloTabelaCheque(
    			new ModeloTabelaCheque(FabricaDao.getChequeDao().procurarVencidos(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public void procurarFinalizados(){
    	espectador.setModeloTabelaCheque(
    			new ModeloTabelaCheque(FabricaDao.getChequeDao().procurarFinalizados(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    
}
