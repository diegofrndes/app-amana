package apresentador.pessoa;

import modelo.PessoaJuridica;
import dao.FabricaDao;
import espectador.pessoa.EspectadorEditarPessoaJuridica;

public class ApresentadorEditarPessoaJuridica {
	private EspectadorEditarPessoaJuridica espectador;
	private PessoaJuridica modelo;
	
	public ApresentadorEditarPessoaJuridica(EspectadorEditarPessoaJuridica espectador, PessoaJuridica modelo){
		this.espectador = espectador;
		this.modelo = modelo;
		espectador.setCnpj(modelo.getCnpj());
		espectador.setEmail(modelo.getEmail());
		espectador.setEndereco(modelo.getEndereco());
		espectador.setNome(modelo.getNome());
		espectador.setObs(modelo.getObs());
		espectador.setIe(modelo.getIe());
	}
	
	public boolean editar(){
		modelo.setCnpj(espectador.getCnpj());
		modelo.setEmail(espectador.getEmail());
		modelo.setEndereco(espectador.getEndereco());
		modelo.setNome(espectador.getNome());
		modelo.setObs(espectador.getObs());
		modelo.setIe(espectador.getIe());
		return (FabricaDao.getPessoaJuridicaDao().editar(modelo));
	}
	
}
