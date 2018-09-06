package br.faj.tcc.configuracao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.faj.tcc.controle.Controle;
import br.faj.tcc.visao.tela.ConfiguracaoVisao;

public class ConfiguracaoControle extends Controle<ConfiguracaoVisao>
{
	public ConfiguracaoControle(ConfiguracaoVisao visao)
	{
		super(visao);
	}
	
	public Configuracao obterConfiguracao() throws ClassNotFoundException, IOException
	{
		return Configuracao.recuperar();
	}
	
	public void salvarConfiguracao(Configuracao configuracao) throws IOException
	{
		configuracao.salvar();
	}
	
	public List<Configuracao.Tecla> obterTeclas()
	{
		return new ArrayList<>(Arrays.asList(Configuracao.Tecla.values()));
	}
}