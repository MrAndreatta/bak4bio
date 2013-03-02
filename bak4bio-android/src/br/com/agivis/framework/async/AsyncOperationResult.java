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

/**
 * Resultado da execução de uma operação assíncrona.
 * @author Time Agivis
 */
class AsyncOperationResult {
	
	private AsyncOperation asyncOperation;
	private boolean success;
	private Exception exception;
	
	/**
	 * Cria um resultado para uma operação
	 * @param asyncOperation
	 */
	public AsyncOperationResult(AsyncOperation asyncOperation) {
		this.asyncOperation = asyncOperation;
	}
	
	/**
	 * Retorna se executou com sucesso, ou seja, sem lançar exception
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}
	
	/**
	 * Define se executou com sucesso, ou seja, sem lançar exception
	 * @param sucesso
	 */
	public void setSuccess(boolean sucesso) {
		this.success = sucesso;
	}
	
	/**
	 * Retorna a exception lançada durante a execução
	 * @return
	 */
	public Exception getException() {
		return exception;
	}
	
	/**
	 * Define a exception lançada durante a execução
	 * @param exception
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}

	/**
	 * Retorna de qual operação é o resultado
	 * @return
	 */
	public AsyncOperation getAsyncOperation() {
		return asyncOperation;
	}
}