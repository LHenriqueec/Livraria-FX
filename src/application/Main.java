package application;

import java.io.IOException;

import br.com.casadocodigo.livraria.Autor;
import br.com.casadocodigo.livraria.produtos.LivroFisico;
import br.com.casadocodigo.livraria.produtos.Produto;
import dao.produtoDAO;
import io.Exportador;
import io.cadastroProduto;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class Main extends Application {
	Group g = new Group();
	@Override
	public void start(Stage primaryStage) throws IOException {
		Group group = new Group();
		Scene scene = new Scene(group, 690, 510);
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		
		// Lista de Produtos
		ObservableList<Produto> produtos = new produtoDAO().lista();

		// Labels
		Label label = new Label("Listagem de Livros");
		label.setId("label-listagem");
		
		//Tem seu valor alterado no botão
		Label progresso = new Label();
		progresso.setId("label-progresso");
		
		
		double valorTotal = produtos.stream().mapToDouble(Produto::getValor).sum();
		Label labelFooter = new Label(String.format("Você tem R$%.2f em estoque " + "com um total de %d produtos",
				valorTotal, produtos.size()));
		labelFooter.setId("label-footer");
		
		//Botão
		Button btnExport = new Button("Exportar para CSV");
		btnExport.setId("btnExport");
		btnExport.setOnAction(event -> {
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() {
					dormePorVinteSegundos();
					exportaEmCSV(produtos);
					return null;
				}
			};
			task.setOnRunning(e -> progresso.setText("Exportando..."));
			task.setOnSucceeded(e -> progresso.setText("Concluído!"));
			new Thread(task).start();
		});
		
		Button btnCadast = new Button("Cadastrar Produto");
		btnCadast.setId("btnCadast");
		btnCadast.setOnAction(event -> {
			primaryStage.hide();
			new cadastroProduto().cadastrarProduto();
		});
		
		// Tabela e suas Colunas
		TableView<Produto> tableView = new TableView<>(produtos);

		TableColumn<Produto, String> nomeColumn = criaColuna("Nome", 180, "nome");
		TableColumn<Produto, String> descColumn = criaColuna("Descrição", 230, "descricao");
		TableColumn<Produto, String> valorColumn = criaColuna("Valor", 60, "valor");
		TableColumn<Produto, String> isbnColumn = criaColuna("ISBN", 180, "isbn");

		tableView.getColumns().addAll(nomeColumn, descColumn, valorColumn, isbnColumn);
		final VBox vbox = new VBox(tableView);
		vbox.setId("vbox");

		group.getChildren().addAll(label, vbox, btnCadast, btnExport, progresso, labelFooter);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sistema de livraria com Java FX");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private <S, T> TableColumn<S, T> criaColuna(String nome, int largura, String atribuito) {

		TableColumn<S, T> column = new TableColumn<S, T>(nome);
		column.setMinWidth(largura);
		column.setCellValueFactory(new PropertyValueFactory<>(atribuito));

		return column;
	}


	private void dormePorVinteSegundos() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println("Ops, ocorreu um erro: " + e);
		}
	}

	private void exportaEmCSV(ObservableList<Produto> produtos) {
		try {
			new Exportador().paraCSV(produtos);
		} catch (IOException e) {
			System.out.println("Erro ao exportar: " + e);
		}
	}
}
