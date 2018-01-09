package apresentador.equipamento;

import modelo.Equipamento;
import espectador.equipamento.EspectadorVisualizarEquipamento;

public class ApresentadorVisualizarEquipamento {

	public ApresentadorVisualizarEquipamento(EspectadorVisualizarEquipamento espectador, Equipamento modelo){
		espectador.setDescricao(modelo.getDescricao());
		espectador.setFabricante(modelo.getFabricante());
		espectador.setFreq(modelo.getFreq());
		espectador.setModelo(modelo.getModelo());
		espectador.setNome(modelo.getNome());
		espectador.setNumeroserie(modelo.getNumeroserie());
	}

}
