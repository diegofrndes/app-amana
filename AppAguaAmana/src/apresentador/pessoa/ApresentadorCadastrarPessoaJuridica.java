package apresentador.pessoa;

import dao.FabricaDao;
import modelo.PessoaJuridica;
import espectador.pessoa.EspectadorCadastrarPessoaJuridica;

public class ApresentadorCadastrarPessoaJuridica {
	private EspectadorCadastrarPessoaJuridica espectador;
	public ApresentadorCadastrarPessoaJuridica(EspectadorCadastrarPessoaJuridica espectador)
	{
		this.espectador = espectador;
	}
	
	public boolean cadastrar(){
		PessoaJuridica modelo = new PessoaJuridica(-1);
		modelo.setCnpj(espectador.getCnpj());
		modelo.setEmail(espectador.getEmail());
		modelo.setEndereco(espectador.getEndereco());
		modelo.setNome(espectador.getNome());
		modelo.setObs(espectador.getObs());
		modelo.setIe(espectador.getIe());
		return (FabricaDao.getPessoaJuridicaDao().salvar(modelo) != -1);
	}
	
}
