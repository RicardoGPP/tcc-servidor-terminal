package br.faj.tcc.configuracao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
	Representa a configuração do servidor, permitindo salvar e recuperar
	em um arquivo serializado a opção da porta de escuta do servidor.
	Segue o padrão <b>Singleton</b>, ou seja, deve-se utilizar o método
	<b>recuperar()</b> para se obter uma instância desta classe.
	
	@author Ricardo Giovani Piantavinha Perandré
	@version 1.1
*/
public class Configuracao implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final String NOME_ARQUIVO = "servidor.cfg";
	
	private static Configuracao instancia = null;
	private int porta;
	private boolean tocarSomAoTratar;
	private boolean notificarAoTratar;
	private Tecla teclaAposTratar;

	public enum Tecla
	{
		ENTER("Enter", '\n'),
		TAB("Tab", '\t'),
		ESPACO("Espaço", ' ');
		
		private String nome;
		private char simbolo;
		
		public String getNome()
		{
			return this.nome;
		}
		
		public char getSimbolo()
		{
			return this.simbolo;
		}
		
		private Tecla(String nome, char simbolo)
		{
			this.nome = nome;
			this.simbolo = simbolo;
		}
		
		public String toString()
		{
			return this.nome;
		}
	}
	
	public int getPorta()
	{
		return porta;
	}

	public void setPorta(int porta)
	{
		this.porta = porta;
	}
	
	public boolean isTocarSomAoTratar()
	{
		return tocarSomAoTratar;
	}

	public void setTocarSomAoTratar(boolean tocarSomAoTratar)
	{
		this.tocarSomAoTratar = tocarSomAoTratar;
	}

	public boolean isNotificarAoTratar()
	{
		return notificarAoTratar;
	}

	public void setNotificarAoTratar(boolean notificarAoTratar)
	{
		this.notificarAoTratar = notificarAoTratar;
	}
	
	public Tecla getTeclaAposTratar()
	{
		return teclaAposTratar;
	}

	public void setTeclaAposTratar(Tecla teclaAposTratar)
	{
		this.teclaAposTratar = teclaAposTratar;
	}

	private Configuracao()
	{
		this.porta = 5555;
		this.tocarSomAoTratar = false;
		this.notificarAoTratar = true;
		this.teclaAposTratar = Tecla.ESPACO;
	}
	
	public void salvar() throws IOException
	{
		this.serializar();
	}
	
	public static Configuracao recuperar() throws IOException, ClassNotFoundException
	{
		if (instancia == null)
		{
			File arquivo = new File(NOME_ARQUIVO);
			if (arquivo.exists())
				instancia = deserializar(arquivo);
			else
				instancia = new Configuracao();
		}
		return instancia;
	}
	
	private void serializar() throws IOException
	{
		try (FileOutputStream stream = new FileOutputStream(new File(NOME_ARQUIVO));
			 ObjectOutputStream serializador = new ObjectOutputStream(stream))
		{
			serializador.writeObject(this);
		}
	}
	
	private static Configuracao deserializar(File arquivo) throws IOException, ClassNotFoundException
	{
		try (FileInputStream stream = new FileInputStream(arquivo);
			 ObjectInputStream deserializador = new ObjectInputStream(stream))
		{
			return (Configuracao) deserializador.readObject();
		}
	}
}