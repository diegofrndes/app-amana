package dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Item;
import modelo.Pagamento;
import modelo.Pessoa;
import modelo.Produto;
import modelo.Venda;
import modelo.VendaExcluida;
import dao.FabricaConexao;
import dao.VendaDao;

public class JDBCVendaDao implements VendaDao {

	@Override
	public int salvar(Venda venda) {
	    Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        ResultSet rs = null;
        
        String inserir = "INSERT INTO VENDA "
                + "(idcliente,"
                + "idtransportador,"
                + "transporte," 
                + "desconto,"
                + "valor,"
                + "usuario)"
                + " VALUES(?,?,?,?,?,?)";
        try {
            conexao.setAutoCommit(false);
            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, venda.getCliente().getId());
            pstmt.setInt(2, venda.getTransportador().getId());
            pstmt.setString(3, venda.getTransporte());
            pstmt.setBigDecimal(4, venda.getDesconto());
            pstmt.setBigDecimal(5, venda.getValor());
            pstmt.setString(6, venda.getUsuario());
            pstmt.execute();
            rs = pstmt.getGeneratedKeys();
            rs.first();
            int chave_gerada = rs.getInt(1);
            if(venda.getValorRecebido().signum() != 0){
            	String insertPagamento = "INSERT INTO pagamento " +
            				"(idvenda, forma, obs, valor, usuario)" +
                		"VALUES (?,?,?,?,?)";
            	pstmt4 = conexao.prepareStatement(insertPagamento);
            	pstmt4.setInt(1, chave_gerada);
            	pstmt4.setString(2, venda.getFormaPagamento());
            	pstmt4.setString(3, venda.getObs());
            	pstmt4.setBigDecimal(4, venda.getValorRecebido());
            	pstmt4.setString(5, venda.getUsuario());
            	pstmt4.execute();	
            }
            List<Produto> produtos = venda.getProdutos();
            List<Integer> quantidades = venda.getQuantidades();
            for(int i = 0; i < produtos.size(); i++){
            	Produto produto = produtos.get(i);
            	int quantidadeProduto = quantidades.get(i);
            	String inserirProdutoVenda = "INSERT INTO produtovenda"
                        + "(idproduto, " +
                        "idvenda, " +
                        "valorproduto, " +
                        "quantidade) "
                        + "VALUES(?,?,?,?)";	
            	pstmt2 = conexao.prepareStatement(inserirProdutoVenda);
                pstmt2.setInt(1, produto.getId());
                pstmt2.setInt(2, chave_gerada);
                pstmt2.setBigDecimal(3, produto.getValor());
                pstmt2.setInt(4, quantidadeProduto);
                pstmt2.execute();
                pstmt2.close();
                	
            	for(int j = 0; j < produto.getItens().size(); j++){
            		Item item = produto.getItens().get(j);
            		int quantidadeItem = produto.getQuantidades().get(j);
            		
            		String inserirSaidaItem = "INSERT INTO entradasaidaitem "
                            + "(iditem, "
                            + "quantidade, "
                            + "motivo) "
                            + "VALUES(" + Integer.toString(item.getId())
                            + ", " + Integer.toString(-quantidadeProduto*quantidadeItem)
                            + ", 'VENDA " + Integer.toString(chave_gerada) + "')";
                    pstmt3 = conexao.prepareStatement(inserirSaidaItem, Statement.RETURN_GENERATED_KEYS);
            		pstmt3.execute(inserirSaidaItem);
            		pstmt3.close();
            	}
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
                			if( pstmt3 != null ){
                				pstmt3.close();
               				}
                		} catch (SQLException e) { /* ignored */}
                		finally {
                    		try{
                    			if( pstmt4 != null ){
                    				pstmt4.close();
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
        }
	}
	
	@Override
	public boolean excluir(Venda venda){
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        try {
        	conexao.setAutoCommit(false);
        	String updateitem = "DELETE FROM ENTRADASAIDAITEM WHERE motivo = ?";
            pstmt = conexao.prepareStatement(updateitem);
            pstmt.setString(1, "VENDA " + Integer.toString(venda.getId()));
            pstmt.executeUpdate();
            String updatevenda = "DELETE FROM VENDA WHERE id = ?";
            pstmt2 = conexao.prepareStatement(updatevenda);
            pstmt2.setInt(1, venda.getId());
            int linhas = pstmt2.executeUpdate();
            conexao.commit();
            return (linhas == 1);
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return false;
        }finally {
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
	
	@Override
	public boolean lixeira(VendaExcluida vendaExcluida){
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        try {
        	conexao.setAutoCommit(false);
        	String updateitem = "DELETE FROM ENTRADASAIDAITEM WHERE motivo = ?";
            pstmt = conexao.prepareStatement(updateitem);
            pstmt.setString(1, "VENDA " + Integer.toString(vendaExcluida.getVenda().getId()));
            pstmt.executeUpdate();
            String updatevenda = "UPDATE venda set flag = '0' WHERE id = ?";
            pstmt2 = conexao.prepareStatement(updatevenda);
            pstmt2.setInt(1, vendaExcluida.getVenda().getId());
            int linhas = pstmt2.executeUpdate();
            String inserirMotivo = "INSERT INTO vendaexcluida "
                    + "(idvenda, "
                    + "motivo, "
                    + "usuario) "
                    + "VALUES(" + Integer.toString(vendaExcluida.getVenda().getId())
                    + ", '" + vendaExcluida.getMotivo()
                    + "', '" + vendaExcluida.getUsuario() + "')";
            pstmt3 = conexao.prepareStatement(inserirMotivo, Statement.RETURN_GENERATED_KEYS);
    		pstmt3.execute(inserirMotivo);
    		pstmt3.close();
            conexao.commit();
            return (linhas == 1);
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return false;
        }finally {
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
	
	@Override
	public boolean atualizarDesconto(Venda venda){
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        try {
        	String updateitem = "UPDATE VENDA SET DESCONTO = ?, VALOR = ? WHERE id = " + Integer.toString(venda.getId());
            pstmt = conexao.prepareStatement(updateitem);
            pstmt.setBigDecimal(1, venda.getDesconto());
            pstmt.setBigDecimal(2, venda.getValor());
            pstmt.executeUpdate();
            int linhas = pstmt.executeUpdate();
            return (linhas == 1);
        } catch (SQLException ex) {
        	ex.printStackTrace();
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
	public boolean cancelarPagamento(Pagamento pagamento){
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        try {
        	String updateitem = "DELETE FROM PAGAMENTO WHERE id = ?";
            pstmt = conexao.prepareStatement(updateitem);
            pstmt.setInt(1, pagamento.getId());
            int linhas = pstmt.executeUpdate();
            return (linhas == 1);
        } catch (SQLException ex) {
        	ex.printStackTrace();
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
	public int efetuarPagamento(Venda venda) {
	       String inserir = "INSERT INTO pagamento " +
           		"(idvenda, forma, obs, valor, usuario)" +
           		"VALUES (?,?,?,?,?)";
   
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int chave_gerada = -1;
        try {
            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, venda.getId());
            pstmt.setString(2, venda.getFormaPagamento());
            pstmt.setString(3, venda.getObs());
            pstmt.setBigDecimal(4, venda.getValorRecebido());
            pstmt.setString(5, venda.getUsuario());
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
	public List<Venda> procurarTodas(String busca, String filtro, int pagina, int limite){
		List<Venda> vendas = new ArrayList<Venda>();
		String select = "SELECT pessoa.id, pessoa.nome, venda.* FROM VENDA, PESSOA WHERE venda.flag = '1' AND pessoa.id = idcliente AND " + filtro + " like '" + busca + "%' " + "ORDER BY data DESC" + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda(rs.getInt("venda.id"));
                PreparedStatement pstmt2 = null;
                ResultSet rs2 = null;
                String select2 = "SELECT COALESCE(sum(valor),0.00) FROM pagamento WHERE idvenda = " + Integer.toString(venda.getId());
        		pstmt2 = conexao.prepareStatement(select2);
        		rs2 = pstmt2.executeQuery();
                if(rs2.first()){
                	BigDecimal val = rs2.getBigDecimal("COALESCE(sum(valor),0.00)");
                	if(val != null)
                		venda.setValorRecebido(val);
                	else venda.setValorRecebido(new BigDecimal(0.00f));
                }
                else venda.setValorRecebido(new BigDecimal(0.00f));
                if(rs2 != null)
                	rs2.close();
                if(pstmt2 != null)
                	pstmt2.close();
                
        		Pessoa cliente = new Pessoa(rs.getInt("pessoa.id"));
                cliente.setNome(rs.getString("pessoa.nome"));
                venda.setCliente(cliente);
                venda.setData(rs.getString("data"));
                venda.setValor(rs.getBigDecimal("valor"));
                venda.setDesconto(rs.getBigDecimal("desconto"));
                vendas.add(venda);
            }
            return vendas;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return vendas;
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
	public List<Venda> procurarEmAberto(String busca, String filtro, int pagina, int limite){
		List<Venda> vendas = new ArrayList<Venda>();
		String select = "SELECT pessoa.id, pessoa.nome, venda.* FROM VENDA, PESSOA WHERE venda.flag = '1' AND (SELECT COALESCE(sum(pagamento.valor),0.00) from pagamento WHERE idvenda = venda.id) < venda.valor" +
				" AND pessoa.id = idcliente AND " + filtro + " like '" + busca + "%' " + "ORDER BY data DESC" + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda(rs.getInt("venda.id"));
                PreparedStatement pstmt2 = null;
                ResultSet rs2 = null;
                String select2 = "SELECT COALESCE(sum(pagamento.valor),0.00) FROM pagamento WHERE idvenda = " + Integer.toString(venda.getId());
        		pstmt2 = conexao.prepareStatement(select2);
        		rs2 = pstmt2.executeQuery();
        		if(rs2.first()){
                	BigDecimal val = rs2.getBigDecimal("COALESCE(sum(pagamento.valor),0.00)");
                	if(val != null)
                		venda.setValorRecebido(val);
                	else venda.setValorRecebido(new BigDecimal(0.00f));
                }
                else venda.setValorRecebido(new BigDecimal(0.00f));
                if(rs2 != null)
                	rs2.close();
                if(pstmt2 != null)
                	pstmt2.close();
                
        		Pessoa cliente = new Pessoa(rs.getInt("pessoa.id"));
                cliente.setNome(rs.getString("pessoa.nome"));
                venda.setCliente(cliente);
                venda.setData(rs.getString("data"));
                venda.setValor(rs.getBigDecimal("valor"));
                venda.setDesconto(rs.getBigDecimal("desconto"));
                vendas.add(venda);
            }
            return vendas;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return vendas;
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
	public List<Venda> procurarFinalizadas(String busca, String filtro, int pagina, int limite){
		List<Venda> vendas = new ArrayList<Venda>();
		String select = "SELECT pessoa.id, pessoa.nome, venda.* FROM VENDA, PESSOA WHERE venda.flag = '1' AND (SELECT COALESCE(sum(pagamento.valor),0.00) from pagamento WHERE idvenda = venda.id) = venda.valor" +
				" AND pessoa.id = idcliente AND " + filtro + " like '" + busca + "%' " + "ORDER BY data DESC" + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda(rs.getInt("venda.id"));
                PreparedStatement pstmt2 = null;
                ResultSet rs2 = null;
                String select2 = "SELECT COALESCE(sum(pagamento.valor),0.00) FROM pagamento WHERE idvenda = " + Integer.toString(venda.getId());
        		pstmt2 = conexao.prepareStatement(select2);
        		rs2 = pstmt2.executeQuery();
        		if(rs2.first()){
                	BigDecimal val = rs2.getBigDecimal("COALESCE(sum(pagamento.valor),0.00)");
                	if(val != null)
                		venda.setValorRecebido(val);
                	else venda.setValorRecebido(new BigDecimal(0.00f));
                }
                else venda.setValorRecebido(new BigDecimal(0.00f));
                if(rs2 != null)
                	rs2.close();
                if(pstmt2 != null)
                	pstmt2.close();
                
        		Pessoa cliente = new Pessoa(rs.getInt("pessoa.id"));
                cliente.setNome(rs.getString("pessoa.nome"));
                venda.setCliente(cliente);
                venda.setData(rs.getString("data"));
                venda.setValor(rs.getBigDecimal("valor"));
                venda.setDesconto(rs.getBigDecimal("desconto"));
                vendas.add(venda);
            }
            return vendas;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return vendas;
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
	public List<Venda> procurarLixeira(String busca, String filtro, int pagina, int limite){
		List<Venda> vendas = new ArrayList<Venda>();
		String select = "SELECT pessoa.id, pessoa.nome, venda.* FROM VENDA, PESSOA WHERE venda.flag = '0' AND pessoa.id = idcliente AND " + filtro + " like '" + busca + "%' " + "ORDER BY data DESC" + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda(rs.getInt("venda.id"));
                PreparedStatement pstmt2 = null;
                ResultSet rs2 = null;
                String select2 = "SELECT COALESCE(sum(valor),0.00) FROM pagamento WHERE idvenda = " + Integer.toString(venda.getId());
        		pstmt2 = conexao.prepareStatement(select2);
        		rs2 = pstmt2.executeQuery();
                if(rs2.first()){
                	BigDecimal val = rs2.getBigDecimal("COALESCE(sum(valor),0.00)");
                	if(val != null)
                		venda.setValorRecebido(val);
                	else venda.setValorRecebido(new BigDecimal(0.00f));
                }
                else venda.setValorRecebido(new BigDecimal(0.00f));
                if(rs2 != null)
                	rs2.close();
                if(pstmt2 != null)
                	pstmt2.close();
                
        		Pessoa cliente = new Pessoa(rs.getInt("pessoa.id"));
                cliente.setNome(rs.getString("pessoa.nome"));
                venda.setCliente(cliente);
                venda.setData(rs.getString("data"));
                venda.setValor(rs.getBigDecimal("valor"));
                venda.setDesconto(rs.getBigDecimal("desconto"));
                vendas.add(venda);
            }
            return vendas;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return vendas;
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
	public List<Pagamento> procurarPagamentos(Venda venda){
		List<Pagamento> pagamentos = new ArrayList<Pagamento>();
		String select = "SELECT * FROM PAGAMENTO WHERE idvenda = " + Integer.toString(venda.getId());
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Pagamento pag = new Pagamento(rs.getInt("id"), rs.getBigDecimal("valor"));
                pag.setObs(rs.getString("obs"));
                pag.setForma(rs.getString("forma"));
                pag.setData(rs.getString("data"));
                pag.setUsuario(rs.getString("usuario"));
                pagamentos.add(pag);
            }
            return pagamentos;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return pagamentos;
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
	public VendaExcluida procurarMotivo(Venda venda) {
		VendaExcluida vendaExcluida = null;
		String select = "SELECT * FROM vendaexcluida WHERE idvenda = " + Integer.toString(venda.getId());
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	vendaExcluida = new VendaExcluida(venda);
                vendaExcluida.setMotivo(rs.getString("motivo"));
                vendaExcluida.setUsuario(rs.getString("usuario"));
                vendaExcluida.setData(rs.getString("data"));
            }
            return vendaExcluida;
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return vendaExcluida;
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
