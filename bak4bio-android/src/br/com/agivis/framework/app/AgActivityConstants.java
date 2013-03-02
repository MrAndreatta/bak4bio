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
 * Armazena as constantes utilizadas na activity do framework
 * @author Time Agivis
 *
 */
public class AgActivityConstants {

	/**
	 * Nome do atributo que representa a ação inical de uma activity
	 * Ao iniciar uma tela pode existir vários comportamentos
	 * Ex1: Em um cadastro é possível utilizar como inserção ou edição
	 * Ex2: Em uma consulta é possível passar ou não alguns parâmetros  
	 */
	public static final String ACTION_ATTRIBUTE = "action";
	
	public static final int NO_ACTION_DEFINED = -1;
	
}
