package apresentador.patrimonio;

import dao.FabricaDao;
import modelo.Patrimonio;
import espectador.patrimonio.EspectadorCadastrarPatrimonio;

public class ApresentadorCadastrarPatrimonio {
	private EspectadorCadastrarPatrimonio espectador;	
	public ApresentadorCadastrarPatrimonio(EspectadorCadastrarPatrimonio espectador){
		this.espectador = espectador;
	}
	
	public boolean cadastrar(){
		Patrimonio modelo = new Patrimonio(-1);
		modelo.setDescricao(espectador.getDescricao());
		modelo.setFabricante(espectador.getFabricante());
		modelo.setModelo(espectador.getModelo());
		modelo.setNome(espectador.getNome());
		modelo.setQuantidade(espectador.getQuantidade());
		modelo.setValor(espectador.getValor());
		return (FabricaDao.getPatrimonioDao().salvar(modelo) != -1);
	}
}
