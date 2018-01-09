package espectador.venda;

import java.math.BigDecimal;
import java.util.List;

import modelo.Pessoa;
import modelo.Produto;

public interface EspectadorCadastrarVenda {
	
	public Pessoa getCliente();
	
	public Pessoa getTransportador();
	
	public String getTransporte();
	
	public List<Produto> getProdutos();
	
	public List<Integer> getQuantidades();
	
	public BigDecimal getDesconto();
	
	public BigDecimal getValorRecebido();

	public BigDecimal getValor();
	
	public String getFormaPagamento();
	
	public String getObs();
	
	public String getUsuario();
	
}
