package apresentador.patrimonio;

import modelo.Patrimonio;
import espectador.patrimonio.EspectadorVisualizarPatrimonio;

public class ApresentadorVisualizarPatrimonio {
	
	public ApresentadorVisualizarPatrimonio(EspectadorVisualizarPatrimonio espectador, Patrimonio modelo){
		espectador.setDescricao(modelo.getDescricao());
		espectador.setFabricante(modelo.getFabricante());
		espectador.setModelo(modelo.getModelo());
		espectador.setNome(modelo.getNome());
		espectador.setQuantidade(modelo.getQuantidade());
		espectador.setValor(modelo.getValor());
	}
}
