package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import modelo.EntradaSaidaItem;
import modelo.Item;
import modelo.Pessoa;
import dao.FabricaConexao;
import dao.ItemDao;

public class JDBCItemDao implements ItemDao {

	@Override
	public boolean existeItem(String nome) {
		Connection conexao = FabricaConexao.getConexao();
        String encontrar = "SELECT * FROM ITEM WHERE nome = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(encontrar);
            pstmt.setString(1, nome);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
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
	public int salvar(Item item) {
        String inserir = "INSERT INTO ITEM "
                + "(nome, "
                + "limitecritico, "
                + "idfornecedor, "
                + "quantidade, "
                + "flag) "
                + "VALUES(?, ?, ?, 0, 1)";
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int chave_gerada = -1;
        try {
            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, item.getNome());
            pstmt.setInt(2, item.getLimiteCritico());
            pstmt.setInt(3, item.getFornecedor().getId());
            pstmt.execute();
            rs = pstmt.getGeneratedKeys();
            rs.first();
            chave_gerada = rs.getInt(1);
            return chave_gerada;
        } catch (SQLException ex) {
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
	public int entradaSaida(EntradaSaidaItem entradasaida) {
        String inserir = "INSERT INTO entradasaidaitem "
                + "(iditem, "
                + "quantidade, "
                + "motivo) "
                + "VALUES(?, ?, ?)";
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int chave_gerada = -1;
        try {
            conexao.setAutoCommit(false);
            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, entradasaida.getItem().getId());
            pstmt.setInt(2, entradasaida.getQuantidade());
            pstmt.setString(3, entradasaida.getMotivo());
            pstmt.execute();
            rs = pstmt.getGeneratedKeys();
            rs.first();
            chave_gerada = rs.getInt(1);
            conexao.commit();
            return chave_gerada;
        } catch (SQLException ex) {
        	try {
        		conexao.rollback();
            } catch (SQLException ex1) {
            }
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
	public List<Item> procurarTodos(String busca, String filtro, int pagina,
			int limite) {
		List<Item> itens = new ArrayList<Item>();
		String select = "SELECT * FROM ITEM, PESSOA WHERE item.idfornecedor = pessoa.id AND pessoa.flag = '1' AND item.flag = '1' AND " + filtro + " like '" + busca + "%' " + " ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	Item item = new Item(rs.getInt("item.id"));
            	Pessoa fornecedor = new Pessoa(rs.getInt("pessoa.id"));
            	item.setLimiteCritico(rs.getInt("limitecritico"));
            	item.setNome(rs.getString("item.nome"));
                item.setQuantidade(rs.getInt("quantidade"));
                fornecedor.setNome(rs.getString("pessoa.nome"));
                item.setFornecedor(fornecedor);
            	itens.add(item);
            }
            return itens;
        } catch (SQLException ex) {
            return itens;
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
	public List<Item> procurarLimiteCriticoOk(String busca, String filtro,
			int pagina, int limite) {
		List<Item> itens = new ArrayList<Item>();
		String select = "SELECT * FROM ITEM, PESSOA WHERE item.idfornecedor = pessoa.id AND quantidade > limitecritico AND pessoa.flag = '1' AND item.flag = '1' AND " + filtro + " like '" + busca + "%' " + " ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	Item item = new Item(rs.getInt("item.id"));
            	Pessoa fornecedor = new Pessoa(rs.getInt("pessoa.id"));
            	item.setLimiteCritico(rs.getInt("limitecritico"));
            	item.setNome(rs.getString("item.nome"));
                item.setQuantidade(rs.getInt("quantidade"));
                fornecedor.setNome(rs.getString("pessoa.nome"));
                item.setFornecedor(fornecedor);
            	itens.add(item);
            }
            return itens;
        } catch (SQLException ex) {
            return itens;
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
	public List<Item> procurarLimiteCriticoUltrapassado(String busca, String filtro,
			int pagina, int limite) {
		List<Item> itens = new ArrayList<Item>();
		String select = "SELECT * FROM ITEM, PESSOA WHERE item.idfornecedor = pessoa.id AND quantidade <= limitecritico AND pessoa.flag = '1' AND item.flag = '1' AND " + filtro + " like '" + busca + "%' " + " ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	Item item = new Item(rs.getInt("item.id"));
            	Pessoa fornecedor = new Pessoa(rs.getInt("pessoa.id"));
            	item.setLimiteCritico(rs.getInt("limitecritico"));
            	item.setNome(rs.getString("item.nome"));
                item.setQuantidade(rs.getInt("quantidade"));
                fornecedor.setNome(rs.getString("pessoa.nome"));
                item.setFornecedor(fornecedor);
            	itens.add(item);
            }
            return itens;
        } catch (SQLException ex) {
            return itens;
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
	public boolean editar(Item item) {
		String update = "UPDATE item SET "
        		+ "idfornecedor = ?,"
                + "limitecritico = ?"
                + " WHERE id = " + Integer.toString(item.getId());
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        int linhasAfetadas = 0;
        try {
            pstmt = conexao.prepareStatement(update);
            pstmt.setInt(1, item.getFornecedor().getId());
            pstmt.setInt(2, item.getLimiteCritico());
            linhasAfetadas = pstmt.executeUpdate();
            return (linhasAfetadas == 1);
        } catch (SQLException ex) {
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

	@Override
	public List<EntradaSaidaItem> procurarMovimentacaoEstoque(
			Item item, String dataInicial, String dataFinal) {
		List<EntradaSaidaItem> analise = new ArrayList<EntradaSaidaItem>();
		String select = "SELECT quantidade, data FROM entradasaidaitem WHERE iditem = '" + Integer.toString(item.getId()) + "' AND data >= '" + dataInicial + "' AND data <= '" + dataFinal + "' ORDER BY data";
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	EntradaSaidaItem es = new EntradaSaidaItem();
            	es.setQuantidade(rs.getInt("quantidade"));
            	Timestamp time = rs.getTimestamp("data");
            	String data = time.toString();
            	es.setData(data);
            	analise.add(es);
            }
            return analise;
        } catch (SQLException ex) {
            return analise;
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
	public int quantidade(
			Item item, String data) {
		String select = "SELECT SUM(quantidade) FROM entradasaidaitem WHERE iditem = '" + Integer.toString(item.getId()) + "' AND data < '" + data + "'";
		int quantidade = 0;
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	quantidade = rs.getInt("SUM(quantidade)");
            }
            return quantidade;
        } catch (SQLException ex) {
            return quantidade;
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
	public String motivo(Item item, String data) {
		String select = "SELECT motivo FROM entradasaidaitem WHERE iditem = '" + Integer.toString(item.getId()) + "' AND data = '" + data + "'";
		String motivo = "";
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	motivo = rs.getString("motivo");
            }
            return motivo;
        } catch (SQLException ex) {
            return motivo;
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
