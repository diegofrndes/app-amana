package apresentador.equipamento;

import dao.FabricaDao;
import espectador.equipamento.EspectadorEditarEquipamento;
import modelo.Equipamento;

public class ApresentadorEditarEquipamento {
	private EspectadorEditarEquipamento espectador;
	private Equipamento modelo;
	
	public ApresentadorEditarEquipamento(EspectadorEditarEquipamento espectador, Equipamento equipamento){
		modelo = equipamento;
		this.espectador = espectador;
		espectador.setDescricao(modelo.getDescricao());
		espectador.setFabricante(modelo.getFabricante());
		espectador.setFreq(modelo.getFreq());
		espectador.setModelo(modelo.getModelo());
		espectador.setNome(modelo.getNome());
		espectador.setNumeroserie(modelo.getNumeroserie());

	}
		
	public boolean editar(){
		modelo.setDescricao(espectador.getDescricao());
		modelo.setFabricante(espectador.getFabricante());
		modelo.setFreq(espectador.getFreq());
		modelo.setModelo(espectador.getModelo());
		modelo.setNome(espectador.getNome());
		modelo.setNumeroserie(espectador.getNumeroserie());
		return FabricaDao.getEquipamentoDao().editar(modelo);
	}
}
