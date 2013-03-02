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
package br.com.agivis.framework.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Implementação de AsyncTask do Android para executar operações assíncronas
 * @author Time Agivis
 */
class AsyncOperationsTask extends AsyncTask<AsyncOperationWithProgress, Integer, AsyncOperationResult[]> {
	private ProgressDialog dialog;
	private Context context;
	private String message;
	private boolean showDialog;
	private boolean showProgress;
	
	/**
	 * Cria operação assíncrona
	 * @param context
	 * @param message
	 */
	public AsyncOperationsTask(Context context, String message, boolean showDialog, boolean showProgress) {
		this.context = context;
		this.message = message;
		this.showDialog = showDialog;
		this.showProgress = showProgress;
	}
	
	/**
	 * Exibe tela de progresso antes de executar.
	 * A API do Android roda este método na thread de UI.
	 */
	@Override
	protected void onPreExecute() {
		if (showDialog) {
			dialog = new ProgressDialog(context);
			dialog.setTitle("");
			dialog.setMessage(message);
			dialog.setCancelable(false);
			dialog.setMax(100);
			dialog.setProgress(0);
	
			if (showProgress) {
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			} else {
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setIndeterminate(true);
			}
			
			dialog.show();
		}
	}
	
	/**
	 * Executa as operações em background.
	 * A API do Android executa isso em outra thread.
	 * NÃO chamar elementos da tela neste método.
	 */
	@Override
	protected AsyncOperationResult[] doInBackground(AsyncOperationWithProgress... asyncOperations) {
		AsyncOperationResult[] results = new AsyncOperationResult[asyncOperations.length];
		int index = 0;
	 
		for (final AsyncOperationWithProgress asyncOperation : asyncOperations) {
			AsyncOperationResult result = new AsyncOperationResult(asyncOperation);
			results[index] = result; 
		
			Thread threadCheckProgress = new Thread(new Runnable() {
				public void run() {
					if (showDialog && showProgress) {
						while (true) {
							publishProgress(asyncOperation.progress());
	
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								break;
							}
						}
					}
				}
			});
			
			try {
				threadCheckProgress.start();
				
				asyncOperation.execute();
				result.setSuccess(true);
				
				threadCheckProgress.interrupt();
				
				publishProgress(asyncOperation.progress());
			} catch (Exception e) {
				Log.e("asyncOperation", e.getMessage(), e);
				
				result.setSuccess(false);
				result.setException(e);
			}
		}
	
		return results;
	}
	
	/**
	 * Atualiza o progresso de acordo com o informado pela thread de monitoramento.
	 * A API do Android roda este método na thread de UI.
	 */
	@Override
	protected void onProgressUpdate(Integer... progress) {
		if (showDialog && showProgress) {
			dialog.setProgress(progress[0]);
		}
	}

	/**
	 * Chama métodos para tratar sucesso ou falha na execução para cada operação.
	 * A API do Android roda este método na thread de UI.
	 */
	@Override
	protected void onPostExecute(AsyncOperationResult[] resultados) {
		for (AsyncOperationResult resultado : resultados) {
			if (resultado.isSuccess()) {
				resultado.getAsyncOperation().done();
			} else {
				resultado.getAsyncOperation().failed(resultado.getException());
			}
		}
    	
		if (showDialog) {
			dialog.dismiss();
		}
     }
 }