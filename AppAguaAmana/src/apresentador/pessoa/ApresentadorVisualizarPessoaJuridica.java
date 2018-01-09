package apresentador.pessoa;

import modelo.PessoaJuridica;
import espectador.pessoa.EspectadorVisualizarPessoaJuridica;

public class ApresentadorVisualizarPessoaJuridica {

	public ApresentadorVisualizarPessoaJuridica(EspectadorVisualizarPessoaJuridica espectador, PessoaJuridica modelo){
		espectador.setCnpj(modelo.getCnpj());
		espectador.setEmail(modelo.getEmail());
		espectador.setEndereco(modelo.getEndereco());
		espectador.setIe(modelo.getIe());
		espectador.setNome(modelo.getNome());
		espectador.setObs(modelo.getObs());
	}
}
