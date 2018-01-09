package dao.jdbc;

import dao.FabricaConexao;
import dao.FabricaDao;
import dao.PessoaFisicaDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import modelo.Endereco;
import modelo.PessoaFisica;

/**
 * 
 * @author Diego Fernandes Carlos da Costa <diego@engi42.com.br>
 */
public class JDBCPessoaFisicaDao implements PessoaFisicaDao {

	public int salvar(PessoaFisica pessoa) {
		Connection conexao = FabricaConexao.getConexao();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;

		String inserir = "INSERT INTO PESSOA" + "(nome," + "uf," + "cidade,"
				+ "cep," + "bairro," + "rua," + "numero," + "ddd,"
				+ "telefone," + "email," + "obs," + "tipo," + "flag)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,0,1)";
		try {
			conexao.setAutoCommit(false);
			pstmt = conexao.prepareStatement(inserir,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, pessoa.getNome());
			pstmt.setString(2, pessoa.getEndereco().getUf());
			pstmt.setString(3, pessoa.getEndereco().getCidade());
			pstmt.setString(4, pessoa.getEndereco().getCep());
			pstmt.setString(5, pessoa.getEndereco().getBairro());
			pstmt.setString(6, pessoa.getEndereco().getRua());
			pstmt.setString(7, pessoa.getEndereco().getNumero());
			pstmt.setString(8, pessoa.getEndereco().getDdd());
			pstmt.setString(9, pessoa.getEndereco().getTelefone());
			pstmt.setString(10, pessoa.getEmail());
			pstmt.setString(11, pessoa.getObs());
			pstmt.execute();
			rs = pstmt.getGeneratedKeys();
			rs.first();
			int chave_gerada = rs.getInt(1);
			String inserirFisica = "INSERT INTO PESSOAFISICA"
					+ "(idpessoa, rg, ufrg, cpf) " + "VALUES(?, ?, ?, ?)";
			pstmt2 = conexao.prepareStatement(inserirFisica);
			pstmt2.setInt(1, chave_gerada);
			pstmt2.setString(2, pessoa.getRg());
			pstmt2.setString(3, pessoa.getUfRg());
			pstmt2.setString(4, pessoa.getCpf());
			pstmt2.execute();
			conexao.commit();
			return chave_gerada;
		} catch (SQLException ex) {
			try {
				conexao.rollback();
			} catch (SQLException ex1) {
			}
			ex.printStackTrace();
			return -1;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) { /* ignored */
			} finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException e) { /* ignored */
				} finally {
					try {
						if (pstmt2 != null) {
							pstmt2.close();
						}
					} catch (SQLException e) { /* ignored */
					} finally {
						try {
							if (conexao != null) {
								conexao.close();
							}
						} catch (SQLException e) { /* ignored */
						}
					}
				}
			}
		}
	}

	public boolean editar(PessoaFisica pessoa) {
		Connection conexao = FabricaConexao.getConexao();
		PreparedStatement pstmt = null;
		int linhas = 0;
		String atualizar = "UPDATE PESSOA, PESSOAFISICA SET " + "nome = ?,"
				+ "uf = ?," + "cidade = ?," + "cep = ?," + "bairro = ?,"
				+ "rua = ?," + "numero = ?," + "ddd = ?," + "telefone = ?,"
				+ "email = ?," + "obs = ?," + "rg = ?," + "ufrg = ?,"
				+ "cpf = ?" + " WHERE pessoa.id = "
				+ Integer.toString(pessoa.getId()) + " AND idpessoa = "
				+ Integer.toString(pessoa.getId());
		try {
			pstmt = conexao.prepareStatement(atualizar);
			pstmt.setString(1, pessoa.getNome());
			pstmt.setString(2, pessoa.getEndereco().getUf());
			pstmt.setString(3, pessoa.getEndereco().getCidade());
			pstmt.setString(4, pessoa.getEndereco().getCep());
			pstmt.setString(5, pessoa.getEndereco().getBairro());
			pstmt.setString(6, pessoa.getEndereco().getRua());
			pstmt.setString(7, pessoa.getEndereco().getNumero());
			pstmt.setString(8, pessoa.getEndereco().getDdd());
			pstmt.setString(9, pessoa.getEndereco().getTelefone());
			pstmt.setString(10, pessoa.getEmail());
			pstmt.setString(11, pessoa.getObs());
			pstmt.setString(12, pessoa.getRg());
			pstmt.setString(13, pessoa.getUfRg());
			pstmt.setString(14, pessoa.getCpf());
			linhas = pstmt.executeUpdate();
			return (linhas != 0);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return (linhas != 0);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) { /* ignored */
			} finally {
				try {
					if (conexao != null) {
						conexao.close();
					}
				} catch (SQLException e) { /* ignored */
				}
			}
		}
	}

	/*
	 * @Override public boolean existeCliente(String cpf) { Connection conexao =
	 * FabricaConexao.getConexao(); String encontrar =
	 * "SELECT * FROM PESSOAFISICA WHERE cpf = ?"; PreparedStatement pstmt =
	 * null; ResultSet rs = null; try { pstmt =
	 * conexao.prepareStatement(encontrar); pstmt.setString(1, cpf); rs =
	 * pstmt.executeQuery(); while (rs.next()) { return true; } return false; }
	 * catch (SQLException ex) { return false; } finally { try { if (rs !=
	 * null){ rs.close(); } } catch (SQLException e) { } finally { try { if
	 * (pstmt != null){ pstmt.close(); } } catch (SQLException e) { } finally {
	 * try{ if( conexao != null ){ conexao.close(); } } catch (SQLException e) {
	 * } } } } }
	 */

	@Override
	public List<PessoaFisica> procurarTodos(String busca, String filtro,
			int pagina, int limite) {
		List<PessoaFisica> pessoas = new ArrayList<PessoaFisica>();
		String select = "SELECT * FROM pessoa, pessoafisica, limitedebito WHERE tipo = '0' AND pessoa.id = pessoafisica.idpessoa AND pessoa.id=limitedebito.idpessoa AND pessoa.flag = '1' AND "
				+ filtro
				+ " like '"
				+ busca
				+ "%' "
				+ "ORDER BY "
				+ filtro
				+ " LIMIT "
				+ Integer.toString(pagina * limite)
				+ ","
				+ Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conexao.prepareStatement(select);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PessoaFisica pessoa = new PessoaFisica(rs.getInt("id"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setObs(rs.getString("obs"));
				// pessoa.setId(rs.getInt("pessoa.id"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setObs(rs.getString("obs"));
				pessoa.setTipo(rs.getInt("tipo"));
				Endereco endereco = new Endereco(rs.getString("bairro"),
						rs.getString("rua"), rs.getString("numero"),
						rs.getString("ddd"), rs.getString("telefone"),
						rs.getString("cep"), rs.getString("uf"),
						rs.getString("cidade"));
				pessoa.setEndereco(endereco);
				pessoa.setRg(rs.getString("rg"));
				pessoa.setUfRg(rs.getString("ufrg"));
				pessoa.setLimite(rs.getBigDecimal("valor"));
				pessoa.setDebito(FabricaDao.getPessoaDao().debito(pessoa));
				pessoa.setCredito(FabricaDao.getPessoaDao().credito(pessoa));
				pessoas.add(pessoa);
			}
			return pessoas;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return pessoas;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) { /* ignored */
			} finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException e) { /* ignored */
				} finally {
					try {
						if (conexao != null) {
							conexao.close();
						}
					} catch (SQLException e) { /* ignored */
					}
				}
			}
		}
	}

	@Override
	public List<PessoaFisica> procurarClientes(String busca, String filtro,
			int pagina, int limite) {
		List<PessoaFisica> pessoas = new ArrayList<PessoaFisica>();
		String select = "SELECT pessoa.*, pessoafisica.*, limitedebito.valor FROM pessoa INNER JOIN pessoafisica on pessoa.id = pessoafisica.idpessoa "
				+ "INNER JOIN venda on venda.idcliente = pessoa.id INNER JOIN limitedebito on limitedebito.idpessoa = pessoa.id WHERE tipo = '0' AND pessoa.flag = '1' AND "
				+ filtro
				+ " like '"
				+ busca
				+ "%' "
				+ "GROUP BY pessoa.id ORDER BY "
				+ filtro
				+ " LIMIT "
				+ Integer.toString(pagina * limite)
				+ ","
				+ Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conexao.prepareStatement(select);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PessoaFisica pessoa = new PessoaFisica(rs.getInt("id"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setObs(rs.getString("obs"));
				// pessoa.setId(rs.getInt("pessoa.id"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setObs(rs.getString("obs"));
				pessoa.setTipo(rs.getInt("tipo"));
				Endereco endereco = new Endereco(rs.getString("bairro"),
						rs.getString("rua"), rs.getString("numero"),
						rs.getString("ddd"), rs.getString("telefone"),
						rs.getString("cep"), rs.getString("uf"),
						rs.getString("cidade"));
				pessoa.setEndereco(endereco);
				pessoa.setRg(rs.getString("rg"));
				pessoa.setUfRg(rs.getString("ufrg"));
				pessoa.setLimite(rs.getBigDecimal("limitedebito.valor"));
				pessoa.setDebito(FabricaDao.getPessoaDao().debito(pessoa));
				pessoa.setCredito(FabricaDao.getPessoaDao().credito(pessoa));
				pessoas.add(pessoa);
			}
			return pessoas;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return pessoas;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) { /* ignored */
			} finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException e) { /* ignored */
				} finally {
					try {
						if (conexao != null) {
							conexao.close();
						}
					} catch (SQLException e) { /* ignored */
					}
				}
			}
		}
	}

	@Override
	public List<PessoaFisica> procurarFornecedores(String busca, String filtro,
			int pagina, int limite) {
		List<PessoaFisica> pessoas = new ArrayList<PessoaFisica>();
		String select = "SELECT pessoa.*, pessoafisica.*,limitedebito.valor FROM pessoa INNER JOIN pessoafisica on pessoa.id = pessoafisica.idpessoa "
				+ "INNER JOIN item on item.idfornecedor = pessoa.id INNER JOIN limitedebito on limitedebito.idpessoa = pessoa.id WHERE tipo = '0' AND pessoa.flag = '1' AND "
				+ filtro
				+ " like '"
				+ busca
				+ "%' "
				+ "GROUP BY pessoa.id ORDER BY "
				+ filtro
				+ " LIMIT "
				+ Integer.toString(pagina * limite)
				+ ","
				+ Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conexao.prepareStatement(select);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PessoaFisica pessoa = new PessoaFisica(rs.getInt("id"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setObs(rs.getString("obs"));
				// pessoa.setId(rs.getInt("pessoa.id"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setObs(rs.getString("obs"));
				pessoa.setTipo(rs.getInt("tipo"));
				Endereco endereco = new Endereco(rs.getString("bairro"),
						rs.getString("rua"), rs.getString("numero"),
						rs.getString("ddd"), rs.getString("telefone"),
						rs.getString("cep"), rs.getString("uf"),
						rs.getString("cidade"));
				pessoa.setEndereco(endereco);
				pessoa.setRg(rs.getString("rg"));
				pessoa.setUfRg(rs.getString("ufrg"));
				pessoa.setLimite(rs.getBigDecimal("limitedebito.valor"));
				pessoa.setDebito(FabricaDao.getPessoaDao().debito(pessoa));
				pessoa.setCredito(FabricaDao.getPessoaDao().credito(pessoa));
				pessoas.add(pessoa);
			}
			return pessoas;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return pessoas;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) { /* ignored */
			} finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException e) { /* ignored */
				} finally {
					try {
						if (conexao != null) {
							conexao.close();
						}
					} catch (SQLException e) { /* ignored */
					}
				}
			}
		}
	}

	@Override
	public List<PessoaFisica> procurarTransportadores(String busca,
			String filtro, int pagina, int limite) {
		List<PessoaFisica> pessoas = new ArrayList<PessoaFisica>();
		String select = "SELECT pessoa.*, pessoafisica.*, limitedebito.valor FROM pessoa INNER JOIN pessoafisica on pessoa.id = pessoafisica.idpessoa "
				+ "INNER JOIN venda on venda.idtransportador = pessoa.id INNER JOIN limitedebito on limitedebito.idpessoa = pessoa.id WHERE tipo = '0' AND pessoa.flag = '1' AND "
				+ filtro
				+ " like '"
				+ busca
				+ "%' "
				+ "GROUP BY pessoa.id ORDER BY "
				+ filtro
				+ " LIMIT "
				+ Integer.toString(pagina * limite)
				+ ","
				+ Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conexao.prepareStatement(select);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PessoaFisica pessoa = new PessoaFisica(rs.getInt("id"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setObs(rs.getString("obs"));
				// pessoa.setId(rs.getInt("pessoa.id"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setObs(rs.getString("obs"));
				pessoa.setTipo(rs.getInt("tipo"));
				Endereco endereco = new Endereco(rs.getString("bairro"),
						rs.getString("rua"), rs.getString("numero"),
						rs.getString("ddd"), rs.getString("telefone"),
						rs.getString("cep"), rs.getString("uf"),
						rs.getString("cidade"));
				pessoa.setEndereco(endereco);
				pessoa.setRg(rs.getString("rg"));
				pessoa.setUfRg(rs.getString("ufrg"));
				pessoa.setLimite(rs.getBigDecimal("limitedebito.valor"));
				pessoa.setDebito(FabricaDao.getPessoaDao().debito(pessoa));
				pessoa.setCredito(FabricaDao.getPessoaDao().credito(pessoa));
				pessoas.add(pessoa);
			}
			return pessoas;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return pessoas;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) { /* ignored */
			} finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException e) { /* ignored */
				} finally {
					try {
						if (conexao != null) {
							conexao.close();
						}
					} catch (SQLException e) { /* ignored */
					}
				}
			}
		}
	}

}
