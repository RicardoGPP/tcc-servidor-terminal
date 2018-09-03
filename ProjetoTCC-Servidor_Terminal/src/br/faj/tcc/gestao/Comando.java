package br.faj.tcc.gestao;

import br.faj.tcc.visao.tela.MenuVisao;

public abstract class Comando
{
	private MenuVisao menuVisao;
	
	protected MenuVisao getMenuVisao()
	{
		return menuVisao;
	}

	public Comando(MenuVisao menuVisao)
	{
		this.menuVisao = menuVisao;
	}
	
	public abstract void executar();
}
