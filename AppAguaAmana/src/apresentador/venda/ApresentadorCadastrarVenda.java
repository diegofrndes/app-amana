package apresentador.venda;

import modelo.Venda;
import dao.FabricaDao;
import espectador.venda.EspectadorCadastrarVenda;

public class ApresentadorCadastrarVenda {
	private EspectadorCadastrarVenda espectador;
	
	public ApresentadorCadastrarVenda(EspectadorCadastrarVenda espectador){
		this.espectador = espectador;
	}
	
	public int cadastrar(){
		Venda venda = new Venda(-1);
		venda.setCliente(espectador.getCliente());
		venda.setTransportador(espectador.getTransportador());
		venda.setTransporte(espectador.getTransporte());
		venda.setFormaPagamento(espectador.getFormaPagamento());
		venda.setObs(espectador.getObs());
		venda.setDesconto(espectador.getDesconto());
		venda.setValor(espectador.getValor());
		venda.setValorRecebido(espectador.getValorRecebido());
		venda.setProdutos(espectador.getProdutos());
		venda.setQuantidades(espectador.getQuantidades());
		venda.setUsuario(espectador.getUsuario());
		return FabricaDao.getVendaDao().salvar(venda);
	}
}
