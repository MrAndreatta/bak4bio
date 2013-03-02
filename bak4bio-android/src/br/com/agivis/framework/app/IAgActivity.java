/*
 * Copyright (c) 2012, Agivis Sistemas LTDA. Todos os direitos reservados.
 *
 * Os Programas desta Aplicação (que incluem tanto o software quanto a sua
 * documentação) contém informações proprietárias da Agivis Sistemas LTDA; eles são
 * licenciados de acordo com um contrato de licença contendo restrições de uso e
 * confidencialidade, e são também protegidos pela Lei 9609/98 e 9/610/98,
 * respectivamente Lei do Software e Lei dos Direitos Autorais. Engenharia
 * reversa, descompilação e desmontagem dos programas são proibidos. Nenhuma
 * parte destes programas pode ser reproduzida ou transmitida de nenhuma forma e
 * por nenhum meio, eletrônico ou mecânico, por motivo algum, sem a permissão
 * escrita da Agivis Sistemas LTDA.
 */
package br.com.agivis.framework.app;

/**
 * Comportamento padrão de uma activity
 * @author Time Agivis
 *
 */
public interface IAgActivity extends IAgBaseComponent {
	
	/**
	 * Definição da view a manipulada na activity
	 */
	void defineContextView();
	
	/**
	 * Define os parâmetros de entrada (oriundos de outras activities)
	 */
	void inExtraParams();
	
	/**
	 * Executa a ação inicial conforme o parâmetro (AgActivityConstants.ACTION_ATTRIBUTE) 
	 */
	void executeInitialAction();
	
	/**
	 * Define os parâmetros extras de saída
	 */
	void outExtraParams();
	
	/**
	 * Define o focus inicial para a tela  
	 */
	void defineInitialFocus();
}
