package io;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class cadastroProduto {
	
	private Stage telaCadastroProduto = new Stage();
	private Group group = new Group();
	private Scene scene = new Scene(group, 690, 510);
	
	
	
	
	public void cadastrarProduto() {
		telaCadastroProduto.setScene(scene);
		telaCadastroProduto.setTitle("Cadastro de Produto");
		telaCadastroProduto.show();
	}
}
