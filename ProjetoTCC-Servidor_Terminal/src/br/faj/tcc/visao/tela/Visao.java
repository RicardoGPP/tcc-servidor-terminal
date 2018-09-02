package br.faj.tcc.visao.tela;

import br.faj.tcc.controle.Controle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Visao<T1 extends Controle<? extends Visao<T1, T2>>, T2 extends Parent> extends Stage
{
	private T1 controle;
	private T2 painel;
	private Scene cena;
	
	protected T1 getControle()
	{
		return controle;
	}

	protected T2 getPainel()
	{
		return painel;
	}

	protected Scene getCena()
	{
		return cena;
	}

	public Visao()
	{
		this.controle = this.definirControle();
		this.painel = this.definirPainel();
		this.cena = new Scene(this.painel);
		this.setScene(this.cena);
		this.instanciarComponentes();
		this.definirEstrutura();
		this.definirEventos();
	}
	
	protected abstract void instanciarComponentes();
	
	protected abstract void definirEstrutura();
	
	protected abstract void definirEventos();
	
	protected abstract T1 definirControle();
	
	protected abstract T2 definirPainel();
}