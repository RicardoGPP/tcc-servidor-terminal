package br.faj.tcc.visao.tela;

import java.io.IOException;
import java.util.regex.Pattern;

import br.faj.tcc.configuracao.Configuracao;
import br.faj.tcc.configuracao.ConfiguracaoControle;
import br.faj.tcc.util.Alerta;
import br.faj.tcc.util.FormularioInvalidoException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConfiguracaoVisao extends Visao<ConfiguracaoControle, BorderPane>
{
	private VBox vboxFormulario;
	private VBox vboxPorta;
	private Label labelPorta;
	private TextField textfieldPorta;
	private VBox vboxAoTratarRequisicao;
	private Label labelAoTratarRequisicao;
	private CheckBox checkboxTocarSom;
	private CheckBox checkboxNotificar;
	private HBox hboxPressionarTecla;
	private CheckBox checkboxPressionarTecla;
	private ComboBox<Configuracao.Tecla> comboboxTecla;
	private HBox hboxBotoes;
	private Button buttonSalvar;
	private Button buttonCancelar;
	
	public ConfiguracaoVisao()
	{
		super();
		try
		{
			this.escreverConfiguracao(getControle().obterConfiguracao());
		} catch (ClassNotFoundException | IOException e)
		{
			Alerta.mostrar(AlertType.ERROR, "Ocorreu um erro no sistema.", "Erro: " + e.getMessage() + ".");
			this.close();
		}
	}
	
	protected void instanciarComponentes()
	{
		this.vboxFormulario = new VBox();
		this.vboxPorta = new VBox();
		this.labelPorta = new Label();
		this.textfieldPorta = new TextField();
		this.vboxAoTratarRequisicao = new VBox();
		this.labelAoTratarRequisicao = new Label();
		this.checkboxTocarSom = new CheckBox();
		this.checkboxNotificar = new CheckBox();
		this.hboxPressionarTecla = new HBox();
		this.checkboxPressionarTecla = new CheckBox();
		this.comboboxTecla = new ComboBox<>();
		this.hboxBotoes = new HBox();
		this.buttonSalvar = new Button();
		this.buttonCancelar = new Button();
	}

	protected void definirEstrutura()
	{
		getCena().getStylesheets().add(ConfiguracaoVisao.class.getResource(DIRETORIO_ESTILO + "/ConfiguracaoVisao.css").toExternalForm());
		
		this.setTitle("Configurações");
		this.setWidth(300);
		this.setHeight(250);
		this.setResizable(false);
		
		this.vboxFormulario.setId("vbox-formulario");
		getPainel().setCenter(this.vboxFormulario);
		
		this.vboxPorta.setId("vbox-porta");
		this.vboxFormulario.getChildren().add(this.vboxPorta);
		
		this.labelPorta.setId("label-porta");
		this.labelPorta.setText("*Porta:");
		this.vboxPorta.getChildren().add(this.labelPorta);
		
		this.textfieldPorta.setId("textfield-porta");
		this.vboxPorta.getChildren().add(this.textfieldPorta);
		
		this.vboxAoTratarRequisicao.setId("vbox-ao_tratar_requisicao");
		this.vboxFormulario.getChildren().add(this.vboxAoTratarRequisicao);
		
		this.labelAoTratarRequisicao.setId("label-ao_tratar_requisicao");
		this.labelAoTratarRequisicao.setText("Ao tratar requisição:");
		this.vboxAoTratarRequisicao.getChildren().add(this.labelAoTratarRequisicao);
		
		this.checkboxTocarSom.setId("checkbox-tocar_som");
		this.checkboxTocarSom.setText("Tocar som de \"beep\"");
		this.vboxAoTratarRequisicao.getChildren().add(this.checkboxTocarSom);
		
		this.checkboxNotificar.setId("checkbox-notificar");
		this.checkboxNotificar.setText("Notificar com um pop-up");
		this.vboxAoTratarRequisicao.getChildren().add(this.checkboxNotificar);
		
		this.hboxPressionarTecla.setId("hbox-pressionar_tecla");
		this.vboxAoTratarRequisicao.getChildren().add(this.hboxPressionarTecla);
		
		this.checkboxPressionarTecla.setId("checkbox-pressionar_tecla");
		this.checkboxPressionarTecla.setText("Pressionar a tecla: ");
		this.hboxPressionarTecla.getChildren().add(this.checkboxPressionarTecla);
		
		this.comboboxTecla.setId("combobox-tecla");
		for (Configuracao.Tecla tecla : getControle().obterTeclas())
			this.comboboxTecla.getItems().add(tecla);
		this.hboxPressionarTecla.getChildren().add(this.comboboxTecla);
		
		this.hboxBotoes.setId("hbox-botoes");
		getPainel().setBottom(this.hboxBotoes);
		
		this.buttonSalvar.setId("button-salvar");
		this.buttonSalvar.setText("Salvar");
		this.hboxBotoes.getChildren().add(this.buttonSalvar);
		
		this.buttonCancelar.setId("button-cancelar");
		this.buttonCancelar.setText("Cancelar");
		this.hboxBotoes.getChildren().add(this.buttonCancelar);
	}

	protected void definirEventos()
	{
		this.checkboxPressionarTecla.setOnAction(this.eventoCliqueCheckBoxPressionarTecla());
		this.buttonSalvar.setOnAction(this.eventoCliqueBotaoSalvar());
		this.buttonCancelar.setOnAction(this.eventoCliqueBotaoCancelar());
	}

	protected ConfiguracaoControle definirControle()
	{
		return new ConfiguracaoControle(this);
	}

	protected BorderPane definirPainel()
	{
		BorderPane borderPane = new BorderPane();
		borderPane.setId("borderpane-painel_geral");
		return borderPane;
	}
	
	private void escreverConfiguracao(Configuracao configuracao) throws ClassNotFoundException, IOException
	{
		this.textfieldPorta.setText(Integer.toString(configuracao.getPorta()));
		this.checkboxTocarSom.setSelected(configuracao.isTocarSomAoTratar());
		this.checkboxNotificar.setSelected(configuracao.isNotificarAoTratar());
		if (configuracao.getTeclaAposTratar() == null)
		{
			this.checkboxPressionarTecla.setSelected(false);
			this.comboboxTecla.getSelectionModel().select(null);
			this.comboboxTecla.setDisable(true);
		} else
		{
			this.checkboxPressionarTecla.setSelected(true);
			this.comboboxTecla.getSelectionModel().select(configuracao.getTeclaAposTratar());
		}					
	}
	
	private Configuracao lerConfiguracao() throws ClassNotFoundException, IOException
	{
		Configuracao configuracao = Configuracao.recuperar();
		configuracao.setPorta(Integer.parseInt(textfieldPorta.getText()));
		configuracao.setTocarSomAoTratar(this.checkboxTocarSom.isSelected());
		configuracao.setNotificarAoTratar(this.checkboxNotificar.isSelected());
		configuracao.setTeclaAposTratar((this.checkboxPressionarTecla.isSelected()) ? this.comboboxTecla.getSelectionModel().getSelectedItem() : null);
		return configuracao;
	}
	
	private void formularioEValido() throws FormularioInvalidoException
	{				
		if (this.textfieldPorta.getText().trim().equals(""))
			throw new FormularioInvalidoException(AlertType.INFORMATION, "Ausência de preenchimento.", "O campo \"Porta\" não foi preenchido", this.textfieldPorta);
		Pattern pattern = Pattern.compile("^(\\d)+$");
		if (!pattern.matcher(this.textfieldPorta.getText().trim()).matches())
			throw new FormularioInvalidoException(AlertType.ERROR, "Valor inválido.", "O valor do campo \"Porta\" é inválido.", this.textfieldPorta);
		int porta = Integer.parseInt(this.textfieldPorta.getText().trim());
		if ((porta < 0) || (porta > 65536))
			throw new FormularioInvalidoException(AlertType.ERROR, "Valor inválido.", "A porta deve estar entre 0 e 65536.", this.textfieldPorta);
		if ((this.checkboxPressionarTecla.isSelected()) && (this.comboboxTecla == null))
			throw new FormularioInvalidoException(AlertType.INFORMATION, "Ausência de preenchimento.", "O campo \"Tecla\" não foi preenchido", this.comboboxTecla);
 	}
	
	private EventHandler<ActionEvent> eventoCliqueCheckBoxPressionarTecla()
	{
		return new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent actionEvent)
			{
				comboboxTecla.setDisable(!checkboxPressionarTecla.isSelected());
				comboboxTecla.getSelectionModel().select(null);
			}
		};
	}
	
	private EventHandler<ActionEvent> eventoCliqueBotaoSalvar()
	{
		return new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent actionEvent)
			{
				try
				{
					formularioEValido();
					getControle().salvarConfiguracao(lerConfiguracao());
					Alerta.mostrar(AlertType.INFORMATION, "Status da operação.", "A configuração foi salva com sucesso!");
					textfieldPorta.requestFocus();
				} catch (FormularioInvalidoException e)
				{
					e.alertar();
				} catch (ClassNotFoundException | IOException e)
				{
					Alerta.mostrar(AlertType.ERROR, "Ocorreu um erro no sistema.", "Erro: " + e.getMessage() + ".");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> eventoCliqueBotaoCancelar()
	{
		return new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent actionEvent)
			{
				close();
			}
		};
	}
}
