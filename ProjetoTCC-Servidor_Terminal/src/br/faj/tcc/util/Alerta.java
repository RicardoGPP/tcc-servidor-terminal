package br.faj.tcc.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerta
{
	private static Alerta instancia = null;
	private Alert alert;
	
	private Alerta()
	{		
		this.alert = null;
	}
	
	private void definirAlerta(AlertType tipo, String cabecalho, String mensagem)
	{
		if (this.alert == null)
			this.alert = new Alert(tipo);
		else
			this.alert.setAlertType(tipo);
		switch (tipo)
		{
			case INFORMATION: this.alert.setTitle("Informação"); break;
			case CONFIRMATION: this.alert.setTitle("Confirmação"); break;
			case ERROR: this.alert.setTitle("Erro"); break;
			case WARNING: this.alert.setTitle("Aviso"); break;
			default: this.alert.setTitle("Mensagem");
		}
		this.alert.setHeaderText(cabecalho);
		this.alert.setContentText(mensagem);
	}
	
	public static void mostrar(AlertType tipo, String cabecalho, String mensagem)
	{
		if (instancia == null)
			instancia = new Alerta();
		instancia.definirAlerta(tipo, cabecalho, mensagem);
		instancia.alert.showAndWait();
	}
}