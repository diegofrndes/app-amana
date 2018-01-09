package dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.FabricaConexao;
import dao.PatrimonioDao;

import modelo.Patrimonio;

public class JDBCPatrimonioDao implements PatrimonioDao {

	@Override
	public int salvar(Patrimonio patrimonio) {
		String inserir = "INSERT INTO PATRIMONIO "
                + "(nome, "
                + "descricao, "
                + "fabricante, "
                + "modelo, "
                + "valor, "
                + "quantidade, " 
                + "flag) "
                + "VALUES(?, ?, ?, ?, ?, ?, 1)";
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int chave_gerada = -1;
        try {
            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, patrimonio.getNome());
            pstmt.setString(2, patrimonio.getDescricao());
            pstmt.setString(3, patrimonio.getFabricante());
            pstmt.setString(4, patrimonio.getModelo());
            pstmt.setBigDecimal(5, patrimonio.getValor());
            pstmt.setInt(6, patrimonio.getQuantidade());
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
	public List<Patrimonio> procurarTodos(String busca, String filtro,
			int pagina, int limite) {
		List<Patrimonio> patrimonios = new ArrayList<Patrimonio>();
		String select = "SELECT * FROM PATRIMONIO WHERE flag = '1' AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Patrimonio patrimonio = new Patrimonio(rs.getInt("id"));
                patrimonio.setDescricao(rs.getString("descricao"));
                patrimonio.setFabricante(rs.getString("fabricante"));
                patrimonio.setQuantidade(rs.getInt("quantidade"));
                patrimonio.setModelo(rs.getString("modelo"));
                patrimonio.setNome(rs.getString("nome"));
                patrimonio.setValor(rs.getBigDecimal("valor"));
                patrimonios.add(patrimonio);
            }
            return patrimonios;
        } catch (SQLException ex) {
            return patrimonios;
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
	public List<Patrimonio> procurarLixeira(String busca, String filtro,
			int pagina, int limite) {
		List<Patrimonio> patrimonios = new ArrayList<Patrimonio>();
		String select = "SELECT * FROM PATRIMONIO WHERE flag = '0' AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Patrimonio patrimonio = new Patrimonio(rs.getInt("id"));
                patrimonio.setDescricao(rs.getString("descricao"));
                patrimonio.setFabricante(rs.getString("fabricante"));
                patrimonio.setQuantidade(rs.getInt("quantidade"));
                patrimonio.setModelo(rs.getString("modelo"));
                patrimonio.setNome(rs.getString("nome"));
                patrimonio.setValor(rs.getBigDecimal("valor"));
                patrimonios.add(patrimonio);
            }
            return patrimonios;
        } catch (SQLException ex) {
            return patrimonios;
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
	public boolean desativar(Patrimonio patrimonio) {
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        try {
            String update = "UPDATE patrimonio set flag = '0' WHERE id = ?";
            pstmt = conexao.prepareStatement(update);
            pstmt.setInt(1, patrimonio.getId());
            int linhas = pstmt.executeUpdate();
            pstmt.close();
            if (conexao != null) {
                conexao.close();
            }
            return (linhas == 1);
        } catch (SQLException ex) {
            return false;
        }finally {
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
	public BigDecimal estimativaPatrimonio() {
		String select = "SELECT SUM(QUANTIDADE*VALOR) FROM patrimonio WHERE flag = '1'";
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	return rs.getBigDecimal("sum(quantidade*valor)");
            }
            return null;
        } catch (SQLException ex) {
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

	@Override
	public boolean editar(Patrimonio patrimonio) {
		String update = "UPDATE patrimonio SET "
        		+ "nome = ?,"
                + "descricao = ?,"
                + "fabricante = ?,"
                + "modelo = ?,"
                + "valor = ?,"
                + "quantidade = ?" 
                + " WHERE id = " + Integer.toString(patrimonio.getId());
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        int linhasAfetadas = 0;
        try {
            pstmt = conexao.prepareStatement(update);
            pstmt.setString(1, patrimonio.getNome());
            pstmt.setString(2, patrimonio.getDescricao());
            pstmt.setString(3, patrimonio.getFabricante());
            pstmt.setString(4, patrimonio.getModelo());
            pstmt.setBigDecimal(5, patrimonio.getValor());
            pstmt.setInt(6, patrimonio.getQuantidade());
            linhasAfetadas = pstmt.executeUpdate();
            return (linhasAfetadas == 1);
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return (linhasAfetadas == 1);
        } finally {
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
