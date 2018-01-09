package apresentador.equipamento;

import dao.FabricaDao;
import modelo.Equipamento;
import espectador.equipamento.EspectadorPanelEquipamento;
import gui.swing.equipamento.ModeloTabelaEquipamento;


public class ApresentadorPanelEquipamento {
	private EspectadorPanelEquipamento espectador;
    
    public ApresentadorPanelEquipamento(EspectadorPanelEquipamento espectador){
        this.espectador = espectador;
    }
    
    public void procurarTodos(){
    	espectador.setModeloTabelaEquipamento(
    			new ModeloTabelaEquipamento(FabricaDao.getEquipamentoDao().procurarTodos(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public void procurarManutencaoDia(){
    	espectador.setModeloTabelaEquipamento(
    			new ModeloTabelaEquipamento(FabricaDao.getEquipamentoDao().procurarManutencaoDia(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public void procurarManutencaoAtrasada(){
    	espectador.setModeloTabelaEquipamento(
    			new ModeloTabelaEquipamento(FabricaDao.getEquipamentoDao().procurarManutencaoAtrasada(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public void procurarManutencao30Dias(){
    	espectador.setModeloTabelaEquipamento(
    			new ModeloTabelaEquipamento(FabricaDao.getEquipamentoDao().procurarManutencao30Dias(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public void procurarLixeira(){
    	espectador.setModeloTabelaEquipamento(
    			new ModeloTabelaEquipamento(FabricaDao.getEquipamentoDao().procurarLixeira(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public boolean desativar(Equipamento equipamento){
    	return FabricaDao.getEquipamentoDao().desativar(equipamento);
    }
    
}
