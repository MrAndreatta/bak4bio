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
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Classe utilitária para conexões
 * @author Time Agivis
 *
 */
public class ConnectionUtils {

	/**
	 * Verifica se o dispositivo está online
	 * @param activity
	 * @return
	 */
	public static boolean isOnline(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}
