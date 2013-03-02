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
import android.app.TabActivity;
import android.os.Bundle;

/**
 * Activity customizada para abas 
 * @author Time Agivis
 *
 */
@SuppressWarnings("deprecation")
public class AgTabActivity extends TabActivity implements IAgActivity {

	protected AsyncExecutor executor = new AsyncExecutor();
	protected int action; 
	
	/**
	 * Obs: A execução do inExtraParams e executeInitialAction é realizada no onCreate e não no onResume (como o AgActivity).
	 * Isso é necessário porque é chamado o onCreate da tab e logo depois o onResume da primeira tab filha.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.defineContextView();
        this.bindComponentsControl();
        this.bindListenersControl();
        this.defineDataComponents();
        this.inExtraParams();
        this.executeInitialAction();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	this.defineInitialFocus();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	this.outExtraParams();
    }

	public void defineContextView() {
		
	}
	
	public void inExtraParams() {
		this.action = this.getIntent().getIntExtra(AgActivityConstants.ACTION_ATTRIBUTE, AgActivityConstants.NO_ACTION_DEFINED);
	}
	
	public void executeInitialAction() {
		
	}
	
	public void outExtraParams() {
		
	}

	public void bindComponentsControl() {
		
	}

	public void bindListenersControl() {
		
	}
	
	public void defineDataComponents() {
		
	}

	public void defineInitialFocus() {

	}
}
