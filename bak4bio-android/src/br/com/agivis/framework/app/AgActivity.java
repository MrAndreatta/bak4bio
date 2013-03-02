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

import br.com.agivis.framework.async.AsyncExecutor;
import android.app.Activity;
import android.os.Bundle;

/**
 * Activity customizada
 * @author Time Agivis
 *
 */
public class AgActivity extends Activity implements IAgActivity {

	protected AsyncExecutor executor = new AsyncExecutor();
	protected int action;
	
	/**
	 * Início do ciclo da activity
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.defineContextView();
        this.bindComponentsControl();
        this.bindListenersControl();
        this.defineDataComponents();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	this.inExtraParams();
    	this.executeInitialAction();
    	this.defineInitialFocus();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	this.outExtraParams();
    }
    
    /**
     * Define a interface a ser manipulada
     */
	public void defineContextView() {
	}
    
	/**
	 * Define os parâmetros oriundos de outras activities
	 */
	public void inExtraParams() {
		this.action = this.getIntent().getIntExtra(AgActivityConstants.ACTION_ATTRIBUTE, AgActivityConstants.NO_ACTION_DEFINED);
	}
	
	/**
	 * Executa a ação inicial conforme o parâmetro (AgActivityConstants.ACTION_ATTRIBUTE) 
	 */
	public void executeInitialAction() {
		
	}
	
	public void outExtraParams() {
		
	}
	
    /**
     * Mapeamento entre os componentes da view com a activity
     */
	public void bindComponentsControl() {
	}

    /**
     * Mapeamento entre os listeners da view com a activity
     */
	public void bindListenersControl() {
	}
	
	/**
	 * Define os dados fixos para os componentes
	 */
	public void defineDataComponents() {
		
	}
	
	/**
	 * Define o focus inicial para a tela
	 */
	public void defineInitialFocus() {
		
	}
}
