package apresentador.pessoa;

import dao.FabricaDao;
import modelo.PessoaFisica;
import espectador.pessoa.EspectadorEditarPessoaFisica;

public class ApresentadorEditarPessoaFisica {
	private EspectadorEditarPessoaFisica espectador;
	private PessoaFisica modelo;
	
	public ApresentadorEditarPessoaFisica(EspectadorEditarPessoaFisica espectador, PessoaFisica modelo){
		this.espectador = espectador;
		this.modelo = modelo;
		espectador.setCpf(modelo.getCpf());
		espectador.setEmail(modelo.getEmail());
		espectador.setEndereco(modelo.getEndereco());
		espectador.setNome(modelo.getNome());
		espectador.setObs(modelo.getObs());
		espectador.setRg(modelo.getRg());
		espectador.setUfRg(modelo.getUfRg());
	}
	
	public boolean editar(){
		modelo.setCpf(espectador.getCpf());
		modelo.setEmail(espectador.getEmail());
		modelo.setEndereco(espectador.getEndereco());
		modelo.setNome(espectador.getNome());
		modelo.setObs(espectador.getObs());
		modelo.setRg(espectador.getRg());
		modelo.setUfRg(espectador.getUfRg());
		return (FabricaDao.getPessoaFisicaDao().editar(modelo));
	}
	
}
