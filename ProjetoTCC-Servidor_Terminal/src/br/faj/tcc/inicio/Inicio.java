package br.faj.tcc.inicio;

import br.faj.tcc.visao.tela.MenuVisao;
import javafx.application.Application;
import javafx.stage.Stage;

public class Inicio extends Application
{
	public static void main(String[] args)
	{		
		launch();
	}

	public void start(Stage inicio) throws Exception
	{
		MenuVisao menuVisao = new MenuVisao();
		menuVisao.show();
	}
}