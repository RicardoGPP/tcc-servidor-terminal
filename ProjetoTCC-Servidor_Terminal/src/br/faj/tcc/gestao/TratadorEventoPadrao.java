package br.faj.tcc.gestao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import br.faj.tcc.compartilhado.Mensagem;
import br.faj.tcc.configuracao.Configuracao;
import br.faj.tcc.servidor.gestao.TratadorEvento;
import br.faj.tcc.servidor.util.Requisicao;
import br.faj.tcc.util.Som;
import br.faj.tcc.visao.tela.MenuVisao;
import br.faj.tcc.visao.tela.MenuVisao.ModoTela;

public class TratadorEventoPadrao extends TratadorEvento<Mensagem>
{
	private MenuVisao menuVisao;
	
	public TratadorEventoPadrao(MenuVisao menuVisao)
	{
		this.menuVisao = menuVisao;
	}
	
	public void antesDeIniciar()
	{
		this.menuVisao.adicionarComando(new Comando(this.menuVisao)
		{
			public void executar()
			{
				getMenuVisao().escreverLog("Iniciando servidor...");
			}
		});
	}
	
	public void depoisDeIniciar(ServerSocket serverSocket)
	{
		this.menuVisao.adicionarComando(new Comando(this.menuVisao)
		{
			public void executar()
			{
				getMenuVisao().escreverLog("Servidor iniciado na porta " + serverSocket.getLocalPort() + ".");
				getMenuVisao().definirModoTela(ModoTela.SERVIDOR_EM_EXECUCAO);
			}
		});		
	}
	
	public void antesDeAceitarRequisicao()
	{
		this.menuVisao.adicionarComando(new Comando(this.menuVisao)
		{
			public void executar()
			{
				getMenuVisao().escreverLog("Aguardando requisição...");
			}
		});		
	}
	
	public void depoisDeAceitarRequisicao(Socket socket)
	{
		this.menuVisao.adicionarComando(new Comando(this.menuVisao)
		{
			public void executar()
			{
				getMenuVisao().escreverLog("Requisição recebida do cliente " + socket.getLocalAddress() + ".");
			}
		});		
	}
	
	public void antesDeGerenciarRequisicao(Requisicao<Mensagem> requisicao)
	{
		this.menuVisao.adicionarComando(new Comando(this.menuVisao)
		{
			public void executar()
			{
				getMenuVisao().escreverLog("Tratando requisição...");	
			}
		});		
	}
	
	public void depoisDeGerenciarRequisicao(Requisicao<Mensagem> requisicao)
	{
		this.menuVisao.adicionarComando(new Comando(this.menuVisao)
		{
			public void executar()
			{
				getMenuVisao().escreverLog("Requisição tratada.");	
				try
				{
					if (Configuracao.recuperar().isTocarSomAoTratar())
						Som.tocar("beep.wav");
				} catch (ClassNotFoundException | IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});		
	}
	
	public void antesDeParar(ServerSocket serverSocket)
	{
		this.menuVisao.adicionarComando(new Comando(this.menuVisao)
		{
			public void executar()
			{
				getMenuVisao().escreverLog("Parando servidor...");	
			}
		});		
	}
	
	public void depoisDeParar(ServerSocket serverSocket)
	{
		this.menuVisao.adicionarComando(new Comando(this.menuVisao)
		{
			public void executar()
			{
				getMenuVisao().escreverLog("Servidor parado.");
				getMenuVisao().definirModoTela(ModoTela.SERVIDOR_PARADO);
			}
		});		
	}
}