package apresentador.pessoa;

import dao.FabricaDao;
import modelo.PessoaFisica;
import espectador.pessoa.EspectadorCadastrarPessoaFisica;

public class ApresentadorCadastrarPessoaFisica {
	private EspectadorCadastrarPessoaFisica espectador;
	public ApresentadorCadastrarPessoaFisica(EspectadorCadastrarPessoaFisica espectador)
	{
		this.espectador = espectador;
	}
	
	public boolean cadastrar(){
		PessoaFisica modelo = new PessoaFisica(-1);
		modelo.setCpf(espectador.getCpf());
		modelo.setEmail(espectador.getEmail());
		modelo.setEndereco(espectador.getEndereco());
		modelo.setNome(espectador.getNome());
		modelo.setObs(espectador.getObs());
		modelo.setRg(espectador.getRg());
		modelo.setUfRg(espectador.getUfRg());
		return (FabricaDao.getPessoaFisicaDao().salvar(modelo) != -1);
	}
	
}
