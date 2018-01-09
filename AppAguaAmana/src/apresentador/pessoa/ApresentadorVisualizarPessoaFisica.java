package apresentador.pessoa;

import modelo.PessoaFisica;
import espectador.pessoa.EspectadorVisualizarPessoaFisica;

public class ApresentadorVisualizarPessoaFisica {

	public ApresentadorVisualizarPessoaFisica(EspectadorVisualizarPessoaFisica espectador, PessoaFisica modelo){
		espectador.setCpf(modelo.getCpf());
		espectador.setEmail(modelo.getEmail());
		espectador.setEndereco(modelo.getEndereco());
		espectador.setNome(modelo.getNome());
		espectador.setObs(modelo.getObs());
		espectador.setRg(modelo.getRg());
		espectador.setUfRg(modelo.getUfRg());
	}
	
}
