package br.faj.tcc.controle;

import br.faj.tcc.compartilhado.Mensagem;
import br.faj.tcc.configuracao.Configuracao;
import br.faj.tcc.gestao.TratadorEventoPadrao;
import br.faj.tcc.gestao.TratadorRequisicaoPadrao;
import br.faj.tcc.servidor.nucleo.Servidor;
import br.faj.tcc.visao.tela.MenuVisao;

public class MenuControle extends Controle<MenuVisao>
{
	private Servidor<Mensagem> servidor;
	
	public MenuControle(MenuVisao visao)
	{
		super(visao);
		this.servidor = null;
	}
	
	public void iniciarServidor() throws Exception
	{
		if (this.servidor == null)
			this.servidor = new Servidor<>(TratadorRequisicaoPadrao.class, new TratadorEventoPadrao(this.getVisao()));
		this.servidor.iniciar(Configuracao.recuperar().getPorta());
	}
	
	public void pararServidor() throws Exception
	{
		this.servidor.parar();
	}
	
	public boolean servidorEstaEmExecucao()
	{
		return ((this.servidor != null) && (this.servidor.estaEmExecucao()));
	}
}