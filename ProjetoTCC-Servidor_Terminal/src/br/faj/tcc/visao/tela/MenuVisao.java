package br.faj.tcc.visao.tela;

import java.text.SimpleDateFormat;
import java.util.Date;
import br.faj.tcc.controle.MenuControle;
import br.faj.tcc.gestao.Comando;
import br.faj.tcc.servidor.util.Fila;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.WindowEvent;

public class MenuVisao extends Visao<MenuControle, BorderPane> implements Runnable
{
	private Fila<Comando> comandos;
	private HBox hboxBotoes;
	private Button buttonIniciarParar;
	private Button buttonConfiguracao;
	private TextArea textAreaLog;
	
	public MenuVisao()
	{
		this.comandos = new Fila<>();
		Platform.runLater(this);
	}
	
	public void run()
	{
		Comando comando = this.comandos.remover();
		if (comando != null)
			comando.executar();
		Platform.runLater(this);
	}
	
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
		this.setWidth(450);
		this.setHeight(300);
		
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
		this.buttonIniciarParar.setOnAction(this.eventoCliqueBotaoIniciarParar());
		this.buttonConfiguracao.setOnAction(this.eventoCliqueBotaoConfiguracao());
		this.setOnCloseRequest(this.eventoFecharTela());
	}

	protected MenuControle definirControle()
	{
		return new MenuControle(this);
	}

	protected BorderPane definirPainel()
	{
		return new BorderPane();
	}
	
	public void adicionarComando(Comando comando)
	{
		this.comandos.adicionar(comando);
	}
	
	public void escreverLog(String log)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.textAreaLog.appendText
		(
			((this.textAreaLog.getText()).equals("") ? "" : "\n") +
			"[" + simpleDateFormat.format(new Date()) + "]: " +
			log
		);
		this.textAreaLog.setScrollTop(Double.MAX_VALUE);
	}
	
	public void mudarTextoBotaoIniciarParar(String texto)
	{
		this.buttonIniciarParar.setText(texto);
	}
	
	public void mudarEstadoBotaoConfiguracao(boolean ativo)
	{
		this.buttonConfiguracao.setDisable(!ativo);
	}

	private EventHandler<ActionEvent> eventoCliqueBotaoIniciarParar()
	{
		return new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent actionEvent)
			{
				try
				{
					if (!getControle().servidorEstaEmExecucao())
						getControle().iniciarServidor();
					else
						getControle().pararServidor();
				} catch (Exception e)
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erro");
					alert.setHeaderText("Ocorreu um erro no sistema.");
					alert.setContentText("Erro: " + e.getMessage() + ".");
					alert.showAndWait();
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> eventoCliqueBotaoConfiguracao()
	{
		return new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent actionEvent)
			{
				
			}
		};
	}
	
	private EventHandler<WindowEvent> eventoFecharTela()
	{
		return new EventHandler<WindowEvent>()
		{
			public void handle(WindowEvent arg0)
			{
				try
				{
					if (getControle().servidorEstaEmExecucao())
						getControle().pararServidor();
				} catch (Exception e)
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erro");
					alert.setHeaderText("Ocorreu um erro no sistema.");
					alert.setContentText("Erro: " + e.getMessage() + ".");
					alert.showAndWait();
				}
			}
		};
	}
}