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

import br.ufpr.bioinfo.bak4bio.android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

/**
 * Classe utilitária para funções relacionadas a exibição de dialogs
 * @author Time Agivis
 *
 */
public class DialogUtils {

	private static final int TOAST_TIMEOUT = 3000;

	/**
	 * Exibe dialog com mensagem e botão OK
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void showDialog(Context context, String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();  
	    alertDialog.setTitle(title);  
	    alertDialog.setMessage(message);  
	    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
	      public void onClick(DialogInterface dialog, int which) {
	        return;  
	    } }); 
	    alertDialog.show();
	}

	/**
	 * Exibe toast (mensagem de notificação na tela)
	 * @param context
	 * @param message
	 */
	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, TOAST_TIMEOUT).show();
	}
	
	public static AlertDialog showConfirmDialog(Context context, String title, String message, OnClickListener onYesClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();  
	    alertDialog.setTitle(title);  
	    alertDialog.setMessage(message);
	    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.yes_label), onYesClickListener);
	    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.no_label), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
	    alertDialog.show();
	    
	    return alertDialog;
    }
	
	public static AlertDialog showConfirmDialog(Context context, String title, String message, OnClickListener onNoClickListener, OnClickListener onYesClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();  
	    alertDialog.setTitle(title);  
	    alertDialog.setMessage(message);
	    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", onNoClickListener);
	    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", onYesClickListener);
	    alertDialog.show();
	    
	    return alertDialog;
    }
}
