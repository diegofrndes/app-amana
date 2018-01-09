package dao;

import java.math.BigDecimal;

import modelo.Adiantamento;
import modelo.Pessoa;

public interface PessoaDao {
	public Pessoa procurarPessoa(String id);
	public boolean alterarLimite(Pessoa pessoa);
	public BigDecimal debito(Pessoa pessoa);
	public BigDecimal credito(Pessoa pessoa);
	public int efetuarAdiantamento(Adiantamento adiantamento);

}
