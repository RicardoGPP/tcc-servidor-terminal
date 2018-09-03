package br.faj.tcc.gestao;

import java.net.ServerSocket;
import java.net.Socket;
import br.faj.tcc.compartilhado.Mensagem;
import br.faj.tcc.servidor.gestao.TratadorEvento;
import br.faj.tcc.servidor.util.Requisicao;
import br.faj.tcc.visao.tela.MenuVisao;

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
				getMenuVisao().mudarTextoBotaoIniciarParar("Parar");
				getMenuVisao().mudarEstadoBotaoConfiguracao(false);
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
				getMenuVisao().mudarTextoBotaoIniciarParar("Iniciar");
				getMenuVisao().mudarEstadoBotaoConfiguracao(true);	
			}
		});		
	}
}