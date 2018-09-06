package br.faj.tcc.visao.tela;

import java.text.SimpleDateFormat;
import java.util.Date;
import br.faj.tcc.controle.MenuControle;
import br.faj.tcc.gestao.Comando;
import br.faj.tcc.servidor.util.Fila;
import br.faj.tcc.util.Alerta;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

public class MenuVisao extends Visao<MenuControle, BorderPane> implements Runnable
{	
	private Fila<Comando> comandos;	
	private HBox hboxMenu;
	private VBox vboxStatus;
	private Label labelStatus;
	private Label labelDataHoraStatus;
	private HBox hboxBotoes;
	private Button buttonIniciarParar;
	private Button buttonConfiguracao;
	private TextArea textAreaLog;

	public enum ModoTela
	{
		SERVIDOR_EM_EXECUCAO,
		SERVIDOR_PARADO
	}
	
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
		this.hboxMenu = new HBox();
		this.vboxStatus = new VBox();
		this.labelStatus = new Label();
		this.labelDataHoraStatus = new Label();
		this.hboxBotoes = new HBox();
		this.buttonIniciarParar = new Button();
		this.buttonConfiguracao = new Button();
		this.textAreaLog = new TextArea();
	}
	
	protected void definirEstrutura()
	{
		getCena().getStylesheets().add(MenuVisao.class.getResource(DIRETORIO_ESTILO +  "/MenuVisao.css").toExternalForm());
		
		this.setTitle("Servidor");
		this.setWidth(500);
		this.setHeight(320);
		this.setResizable(false);
		
		this.hboxMenu.setId("hbox-menu");
		this.getPainel().setTop(this.hboxMenu);		
		
		this.vboxStatus.setId("vbox-status");
		this.hboxMenu.getChildren().add(this.vboxStatus);
		
		this.labelStatus.setId("label-status");
		this.labelStatus.setText("Parado");
		this.labelStatus.setStyle("-fx-text-fill: #FF0000");
		this.vboxStatus.getChildren().add(this.labelStatus);
		
		this.labelDataHoraStatus.setId("label-data_hora_status");
		this.labelDataHoraStatus.setText("Aguardando início do servidor.");
		this.vboxStatus.getChildren().add(this.labelDataHoraStatus);
		
		this.hboxBotoes.setId("hbox-botoes");
		this.hboxMenu.getChildren().add(this.hboxBotoes);
		
		this.buttonIniciarParar.setId("button-iniciar_parar");
		this.buttonIniciarParar.setText("Iniciar");
		this.hboxBotoes.getChildren().add(this.buttonIniciarParar);
		
		this.buttonConfiguracao.setId("button-configuracao");
		this.buttonConfiguracao.setText("Configurações");
		this.hboxBotoes.getChildren().add(this.buttonConfiguracao);		
		
		this.textAreaLog.setId("textarea-log");
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
	
	public void definirModoTela(ModoTela modoTela)
	{
		if (modoTela == ModoTela.SERVIDOR_PARADO)
		{
			this.labelStatus.setText("Parado");
			this.labelStatus.setStyle("-fx-text-fill: #FF0000");
			this.labelDataHoraStatus.setText("Aguardando início do servidor.");
			this.buttonIniciarParar.setText("Iniciar");
			this.buttonConfiguracao.setDisable(false);
		} else if (modoTela == ModoTela.SERVIDOR_EM_EXECUCAO)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");			
			this.labelStatus.setText("Em execução");
			this.labelStatus.setStyle("-fx-text-fill: #4876FF");
			this.labelDataHoraStatus.setText("Iniciado em: " + simpleDateFormat.format(new Date()) + ".");
			this.buttonIniciarParar.setText("Parar");
			this.buttonConfiguracao.setDisable(true);
		}
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
					Alerta.mostrar(AlertType.ERROR, "Ocorreu um erro no sistema", "Erro: " + e.getMessage() + ".");
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
				ConfiguracaoVisao configuracaoVisao = new ConfiguracaoVisao();
				configuracaoVisao.showAndWait();
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
					System.exit(0);
				} catch (Exception e)
				{
					Alerta.mostrar(AlertType.ERROR, "Ocorreu um erro no sistema", "Erro: " + e.getMessage() + ".");
				}
			}
		};
	}
}