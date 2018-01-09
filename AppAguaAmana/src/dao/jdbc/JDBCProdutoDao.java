package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Item;
import modelo.Produto;
import dao.FabricaConexao;
import dao.ProdutoDao;

public class JDBCProdutoDao implements ProdutoDao {

	@Override
	public int salvar(Produto produto) {
	    Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        
        String inserir = "INSERT INTO PRODUTO"
                + "(nome,"
                + "valor," +
                "flag)"
                + " VALUES(?,?,1)";
        try {
            conexao.setAutoCommit(false);
            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, produto.getNome());
            pstmt.setBigDecimal(2, produto.getValor());
            pstmt.execute();
            rs = pstmt.getGeneratedKeys();
            rs.first();
            int chave_gerada = rs.getInt(1);
            List<Item> itens = produto.getItens();
            List<Integer> quantidades = produto.getQuantidades();
            for(int i = 0; i < itens.size(); i++){
            	Item item = (Item) itens.get(i);
            	int qt = (int) quantidades.get(i);
            	String inserirItemProduto = "INSERT INTO ITEMPRODUTO"
                        + "(idproduto, " +
                        "iditem, " +
                        "quantidade) "
                        + "VALUES(?,?,?)";	
            	pstmt2 = conexao.prepareStatement(inserirItemProduto);
                pstmt2.setInt(1, chave_gerada);
                pstmt2.setInt(2, item.getId());
                pstmt2.setInt(3, qt);
                pstmt2.execute();
                pstmt2.close();
            }
            conexao.commit();
            return chave_gerada;
        } catch (SQLException ex) {
        	try {
        		conexao.rollback();
            } catch (SQLException ex1) {
            }
            ex.printStackTrace();
            return -1;
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
                	try {
                		if (pstmt2 != null){
                			pstmt2.close();
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

	@Override
	public boolean editar(Produto produto) {
		String update = "UPDATE produto SET "
        		+ "nome = ?,"
                + "valor = ?"
                + " WHERE id = " + Integer.toString(produto.getId());
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        int linhasAfetadas = 0;
        try {
            pstmt = conexao.prepareStatement(update);
            pstmt.setString(1, produto.getNome());
            pstmt.setBigDecimal(2, produto.getValor());
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
	public List<Produto> procurarTodos(String busca, String filtro, int pagina,
			int limite) {
		List<Produto> produtos = new ArrayList<Produto>();
		String select = "SELECT * FROM produto WHERE flag = '1' AND " + filtro + " like '" + busca + "%' " + " ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getBigDecimal("valor"));
                
                String selectItens = "SELECT item.id, item.quantidade, nome, itemproduto.quantidade FROM item, itemproduto WHERE itemproduto.idproduto = "
                + Integer.toString(produto.getId()) + " AND item.id = iditem";  
        		pstmt2 = conexao.prepareStatement(selectItens);
        		rs2 = pstmt2.executeQuery();
        		while(rs2.next()){
        			Item item = new Item(rs2.getInt("item.id"));
        			int qt = 0;
        			item.setNome(rs2.getString("nome"));
        			item.setQuantidade(rs2.getInt("item.quantidade"));
        			qt = rs2.getInt("itemproduto.quantidade");
        			produto.addItem(item, qt);
        		}
        		rs2.close();
    			pstmt2.close();
    			produtos.add(produto);
            }
            
            return produtos;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return produtos;
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

	public Produto procurarProduto(String id) {
		Produto produto = null;
		String select = "SELECT * FROM produto WHERE flag = '1' AND id = " + id;
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                produto = new Produto(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getBigDecimal("valor"));
                
                String selectItens = "SELECT item.id, item.quantidade, nome, itemproduto.quantidade FROM item, itemproduto WHERE itemproduto.idproduto = "
                + Integer.toString(produto.getId()) + " AND item.id = iditem";  
        		pstmt2 = conexao.prepareStatement(selectItens);
        		rs2 = pstmt2.executeQuery();
        		while(rs2.next()){
        			Item item = new Item(rs2.getInt("item.id"));
        			int qt = 0;
        			item.setNome(rs2.getString("nome"));
        			item.setQuantidade(rs2.getInt("item.quantidade"));
        			qt = rs2.getInt("itemproduto.quantidade");
        			produto.addItem(item, qt);
        		}
        		rs2.close();
    			pstmt2.close();
    		}
            
            return produto;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return produto;
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
