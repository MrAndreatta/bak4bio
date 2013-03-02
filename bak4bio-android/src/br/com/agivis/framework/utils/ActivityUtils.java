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
package br.com.agivis.framework.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Classe utilitária para funções relacionadas a uma activity
 * @author Time Agivis
 *
 */
public class ActivityUtils {
	
	/**
	 * Realiza a abertura de uma determinada activity 
	 */
	public static void startActivity(Activity source, Class<?> target) {
		Intent intent = new Intent(source.getBaseContext(), target);
		source.startActivity(intent);
	}
}
