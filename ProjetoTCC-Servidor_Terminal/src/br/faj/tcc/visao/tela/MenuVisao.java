package br.faj.tcc.visao.tela;

import br.faj.tcc.controle.MenuControle;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MenuVisao extends Visao<MenuControle, BorderPane>
{
	private HBox hboxBotoes;
	private Button buttonIniciarParar;
	private Button buttonConfiguracao;
	private TextArea textAreaLog;
	
	protected void instanciarComponentes()
	{
		this.hboxBotoes = new HBox();
		this.buttonIniciarParar = new Button();
		this.buttonConfiguracao = new Button();
		this.textAreaLog = new TextArea();
	}
	
	protected void definirEstrutura()
	{
		this.setTitle("Servidor TCP");
		this.setWidth(400);
		this.setHeight(250);
		
		this.hboxBotoes.setPadding(new Insets(5));
		this.hboxBotoes.setSpacing(5);
		this.getPainel().setTop(this.hboxBotoes);
		
		this.buttonIniciarParar.setText("Iniciar");
		this.hboxBotoes.getChildren().add(this.buttonIniciarParar);
		
		this.buttonConfiguracao.setText("Configurações");
		this.hboxBotoes.getChildren().add(this.buttonConfiguracao);
		
		this.textAreaLog.setEditable(false);
		this.getPainel().setCenter(this.textAreaLog);
	}

	protected void definirEventos()
	{

	}

	protected MenuControle definirControle()
	{
		return new MenuControle(this);
	}

	protected BorderPane definirPainel()
	{
		return new BorderPane();
	}
}