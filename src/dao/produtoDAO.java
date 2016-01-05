package dao;

/**
 * Cuida do acesso aos dados da Classe {@link Produto}.
 * 
 * @author Luiz Henrique <luizsnip@gmail.com>
 * @since 1.0 
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.casadocodigo.livraria.Autor;
import br.com.casadocodigo.livraria.produtos.LivroFisico;
import br.com.casadocodigo.livraria.produtos.Produto;
import db.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class produtoDAO {

	
	/**
	 * Lista todos os produtos do banco de dados
	 * 
	 * @return {@link ObservableList} com todos os produtos
	 * @throws RuntimeException em caso de erros
	 */
	
	public ObservableList<Produto> lista() {

		ObservableList<Produto> produtos = FXCollections.observableArrayList();

		String sql = "SELECT * FROM produtos";
		PreparedStatement ps;

		try (Connection con = new ConnectionFactory().getConnection()) {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				LivroFisico livro = new LivroFisico(new Autor());
				livro.setNome(rs.getString("nome"));
				livro.setDescricao(rs.getString("descricao"));
				livro.setValor(rs.getDouble("valor"));
				livro.setIsbn(rs.getString("isbn"));
				produtos.add(livro);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return produtos;

	}

	
	/**
	 * @param produto que deve ser adicionado no banco.
	 * @throws RuntimeException em caso de erros
	 */
	public void adiciona(Produto produto) {
		PreparedStatement ps;
		String sql = "INSERT INTO produtos(nome, descricao, valor, isbn)" + " VALUE (?, ?, ?, ?)";
		
		try (Connection con = new ConnectionFactory().getConnection()) {
			ps = con.prepareStatement(sql);
			ps.setString(1, produto.getNome());
			ps.setString(2, produto.getDescricao());
			ps.setDouble(3, produto.getValor());
			ps.setString(4, produto.getIsbn());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
