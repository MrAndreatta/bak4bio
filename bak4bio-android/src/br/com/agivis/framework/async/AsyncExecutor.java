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

import android.content.Context;

/**
 * Executor de operações assíncronas
 * @author Time Agivis
 */
public class AsyncExecutor {
	
	/**
	 * Executa uma operação assíncrona com barra de progresso.
	 * @param context
	 * @param message
	 * @param asyncOperation
	 */
	public void execute(Context context, String message, final AsyncOperationWithProgress operation) {
		doExecution(context, message, operation, true, true);
	}
	
	/**
	 * Executa uma operação assíncrona com barra de progresso.
	 * @param context
	 * @param message
	 * @param asyncOperation
	 */
	public void executeSilent(Context context, String message, final AsyncOperation operation) {
		AsyncOperationWithProgress operationWithProgress = wrapOperationWithoutProgress(operation);
		doExecution(context, message, operationWithProgress, false, false);
	}
	
	/**
	 * Executa uma operação assíncrona.
	 * @param context
	 * @param message
	 * @param asyncOperation
	 */
	public void execute(Context context, String message, final AsyncOperation operation) {
		AsyncOperationWithProgress operationWithProgress = wrapOperationWithoutProgress(operation);
		doExecution(context, message, operationWithProgress, true, false);
	}

	private AsyncOperationWithProgress wrapOperationWithoutProgress(
			final AsyncOperation operation) {
		AsyncOperationWithProgress operationWithProgress = new AsyncOperationWithProgress() {

			public void execute() throws Exception {
				operation.execute();
			}
			
			public void done() {
				operation.done();
			}
			
			public void failed(Exception e) {
				operation.failed(e);
			}
			
			public int progress() {
				return -1;
			}
		};
		return operationWithProgress;
	}
	
	/**
	 * Executa uma operação assíncrona.
	 * @param context
	 * @param message
	 * @param asyncOperation
	 */
	private void doExecution(Context context, String message, final AsyncOperationWithProgress operation, boolean showDialog, boolean showProgress) {
		AsyncOperationsTask task = new AsyncOperationsTask(context, message, showDialog, showProgress);
		task.execute(operation);
	}

}
