package br.faj.tcc.util;

import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;

public class FormularioInvalidoException extends Exception
{
	private static final long serialVersionUID = 1L;

	private AlertType tipo;
	private String cabecalho;
	private String mensagem;
	private Node componente;
	
	public FormularioInvalidoException(AlertType tipo, String cabecalho, String mensagem, Node componente)
	{
		super(mensagem);
		this.tipo = tipo;
		this.cabecalho = cabecalho;
		this.mensagem = mensagem;
		this.componente = componente;
	}
	
	public void alertar()
	{
		Alerta.mostrar(this.tipo, this.cabecalho, this.mensagem);
		this.componente.requestFocus();
	}
}
