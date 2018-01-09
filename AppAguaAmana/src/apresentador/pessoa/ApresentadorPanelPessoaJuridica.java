package apresentador.pessoa;

import dao.FabricaDao;
import espectador.pessoa.EspectadorPanelPessoaJuridica;
import gui.swing.pessoa.ModeloTabelaPessoaJuridica;

public class ApresentadorPanelPessoaJuridica {
	private EspectadorPanelPessoaJuridica espectador;
	
	public ApresentadorPanelPessoaJuridica(EspectadorPanelPessoaJuridica espectador)
	{
		this.espectador = espectador;
	}
	
	public void procurarTodos(){
    	espectador.setModeloTabelaPessoaJuridica(
    			new ModeloTabelaPessoaJuridica(FabricaDao.getPessoaJuridicaDao().procurarTodos(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
	
	public void procurarClientes(){
		espectador.setModeloTabelaPessoaJuridica(
    			new ModeloTabelaPessoaJuridica(FabricaDao.getPessoaJuridicaDao().procurarClientes(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
	
	public void procurarFornecedores(){
		espectador.setModeloTabelaPessoaJuridica(
    			new ModeloTabelaPessoaJuridica(FabricaDao.getPessoaJuridicaDao().procurarFornecedores(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
	
	public void procurarTransportadores(){
    	espectador.setModeloTabelaPessoaJuridica(
    			new ModeloTabelaPessoaJuridica(FabricaDao.getPessoaJuridicaDao().procurarTransportadores(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
}
