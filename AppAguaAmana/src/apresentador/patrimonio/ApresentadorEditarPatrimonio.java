package apresentador.patrimonio;

import modelo.Patrimonio;
import dao.FabricaDao;
import espectador.patrimonio.EspectadorEditarPatrimonio;

public class ApresentadorEditarPatrimonio {
	private EspectadorEditarPatrimonio espectador;
	private Patrimonio modelo;
	
	public ApresentadorEditarPatrimonio(EspectadorEditarPatrimonio espectador, Patrimonio modelo){
		this.espectador = espectador;
		this.modelo = modelo;
		espectador.setDescricao(modelo.getDescricao());
		espectador.setFabricante(modelo.getFabricante());
		espectador.setModelo(modelo.getModelo());
		espectador.setNome(modelo.getNome());
		espectador.setQuantidade(modelo.getQuantidade());
		espectador.setValor(modelo.getValor());
	}
	
	public boolean editar(){
		modelo.setDescricao(espectador.getDescricao());
		modelo.setFabricante(espectador.getFabricante());
		modelo.setModelo(espectador.getModelo());
		modelo.setNome(espectador.getNome());
		modelo.setQuantidade(espectador.getQuantidade());
		modelo.setValor(espectador.getValor());
		return (FabricaDao.getPatrimonioDao().editar(modelo));
	}
	
}
