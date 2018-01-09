package dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.Adiantamento;
import modelo.Pessoa;
import dao.FabricaConexao;
import dao.FabricaDao;
import dao.PessoaDao;

public class JDBCPessoaDao implements PessoaDao {

	@Override
	public Pessoa procurarPessoa(String id) {
		Pessoa pessoa = null;
		String select = "SELECT pessoa.id, nome, valor FROM pessoa, limitedebito WHERE flag = '1' AND pessoa.id=limitedebito.idpessoa AND pessoa.id = " + id; 
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pessoa = new Pessoa(rs.getInt("pessoa.id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setLimite(rs.getBigDecimal("valor"));
				pessoa.setDebito(FabricaDao.getPessoaDao().debito(pessoa));
				pessoa.setCredito(FabricaDao.getPessoaDao().credito(pessoa));
            }
            return pessoa;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return pessoa;
        }  finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
	}

	@Override
	public boolean alterarLimite(Pessoa pessoa) {
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        int linhas = 0;
        String atualizar = "UPDATE LIMITEDEBITO SET "
        		+ "valor = ? WHERE limitedebito.idpessoa = " 
                + Integer.toString(pessoa.getId());
        try {
            pstmt = conexao.prepareStatement(atualizar);
            pstmt.setBigDecimal(1, pessoa.getLimite());
            linhas = pstmt.executeUpdate();       
            return (linhas != 0);
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return (linhas != 0);
        }  finally {
        	try {
        		if (pstmt != null){
        			pstmt.close();
            	}
            } catch (SQLException e) { /* ignored */}
            finally {
            	try{
            		if( conexao != null ){
            			conexao.close();
               		}
                } catch (SQLException e) { /* ignored */}
            } 
        }

    }

	@Override
	public BigDecimal debito(Pessoa pessoa) {
		
		String select = "SELECT COALESCE(sum(pagamento.valor),0.00) FROM pagamento INNER JOIN venda ON pagamento.idvenda = venda.id "
				+ "and venda.idcliente = '" + Integer.toString(pessoa.getId()) + "' "
				+ "AND venda.flag = 1";
		String select1 = "SELECT COALESCE(sum(venda.valor),0.00) FROM venda "
				+ "WHERE venda.idcliente = '" + Integer.toString(pessoa.getId()) + "' "
				+ "AND venda.flag = 1";
		
		BigDecimal sumPag = null;
		BigDecimal sumVenda = null;
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            pstmt1 = conexao.prepareStatement(select1);
            rs1 = pstmt1.executeQuery();
            
            while (rs.next()) {
            	sumPag = rs.getBigDecimal("COALESCE(sum(pagamento.valor),0.00)");
            }
            while (rs1.next()) {
            	sumVenda = rs1.getBigDecimal("COALESCE(sum(venda.valor),0.00)");
            }
            
            return sumPag.subtract(sumVenda);
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return null;
        }  finally {
        	try {
                if (rs != null){
                    rs.close();
                }
                if (rs1 != null){
                    rs1.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                    if (pstmt1 != null){
                        pstmt1.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
	}

	@Override
	public int efetuarAdiantamento(Adiantamento adiantamento) {
	       String inserir = "INSERT INTO adiantamento " +
	           		"(idcliente, forma, obs, valor, usuario)" +
	           		"VALUES (?,?,?,?,?)";
	   
			Connection conexao = FabricaConexao.getConexao();
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        int chave_gerada = -1;
	        try {
	            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
	            pstmt.setInt(1, adiantamento.getIdcliente());
	            pstmt.setString(2, adiantamento.getForma());
	            pstmt.setString(3, adiantamento.getObs());
	            pstmt.setBigDecimal(4, adiantamento.getValor());
	            pstmt.setString(5, adiantamento.getUsuario());
	            pstmt.execute();
	            rs = pstmt.getGeneratedKeys();
	            rs.first();
	            chave_gerada = rs.getInt(1);
	            return chave_gerada;
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	        	return -1;
	        } finally {
	        	try {
	                if (rs != null){
	                    rs.close();
	                }
	            } catch (SQLException e) { /* ignored */}
	            finally {
	                try {
	                    if (pstmt != null){
	                        pstmt.close();
	                    }
	                } catch (SQLException e) { /* ignored */}
	                finally {
	                	try{
	                		if( conexao != null ){
	                			conexao.close();
	                		}
	                	} catch (SQLException e) { /* ignored */}
	                }
	            }
	        }

	}

	@Override
	public BigDecimal credito(Pessoa pessoa) {
		String select = "SELECT COALESCE(sum(adiantamento.valor),0.00) FROM adiantamento "
				+ "WHERE adiantamento.idcliente = '" + Integer.toString(pessoa.getId()) + "' ";	
		BigDecimal sum = null;
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
            	sum = rs.getBigDecimal("COALESCE(sum(adiantamento.valor),0.00)");
            }
            
            return sum;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return null;
        }  finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
	}
	
}
