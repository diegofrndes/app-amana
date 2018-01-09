package apresentador.pessoa;

import dao.FabricaDao;
import espectador.pessoa.EspectadorPanelPessoaFisica;
import gui.swing.pessoa.ModeloTabelaPessoaFisica;

public class ApresentadorPanelPessoaFisica {
	private EspectadorPanelPessoaFisica espectador;
	
	public ApresentadorPanelPessoaFisica(EspectadorPanelPessoaFisica espectador)
	{
		this.espectador = espectador;
	}
	
	public void procurarTodos(){
    	espectador.setModeloTabelaPessoaFisica(
    			new ModeloTabelaPessoaFisica(FabricaDao.getPessoaFisicaDao().procurarTodos(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
	public void procurarClientes(){
    	espectador.setModeloTabelaPessoaFisica(
    			new ModeloTabelaPessoaFisica(FabricaDao.getPessoaFisicaDao().procurarClientes(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
	
	public void procurarFornecedores(){
    	espectador.setModeloTabelaPessoaFisica(
    			new ModeloTabelaPessoaFisica(FabricaDao.getPessoaFisicaDao().procurarFornecedores(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
	
	public void procurarTransportadores(){
    	espectador.setModeloTabelaPessoaFisica(
    			new ModeloTabelaPessoaFisica(FabricaDao.getPessoaFisicaDao().procurarTransportadores(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
		
}
