package apresentador.equipamento;

import modelo.Equipamento;
import dao.FabricaDao;
import espectador.equipamento.EspectadorCadastrarEquipamento;

public class ApresentadorCadastrarEquipamento {
	private EspectadorCadastrarEquipamento espectador;
    
    public ApresentadorCadastrarEquipamento(EspectadorCadastrarEquipamento espectador){
        this.espectador = espectador;
    }
    
    public boolean cadastrar(){
        Equipamento modelo = new Equipamento(-1, espectador.getNumeroSerie());
    	modelo.setNome(espectador.getNome());
        modelo.setDescricao(espectador.getDescricao());
        modelo.setFabricante(espectador.getFabricante());
        modelo.setModelo(espectador.getModelo());
        modelo.setFreq(espectador.getFreq());
    	return (FabricaDao.getEquipamentoDao().salvar(modelo) != -1);
    }
    
    /*
    public boolean existeEquipamento(){
        return (FabricaDao.getEquipamentoDao().existeEquipamento(this.getEspectador().getNumeroSerie()));
    }
    */

    /**
     * @return the espectador
     */
    public EspectadorCadastrarEquipamento getEspectador() {
        return espectador;
    }
}