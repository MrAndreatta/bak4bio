package br.ufpr.bioinfo.bak4bio.android.control;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import br.com.agivis.framework.app.AgActivity;
import br.com.agivis.framework.async.AsyncOperation;
import br.com.agivis.framework.utils.DialogUtils;
import br.ufpr.bioinfo.bak4bio.android.R;
import br.ufpr.bioinfo.bak4bio.android.adapter.BioBoxRowArrayAdapter;
import br.ufpr.bioinfo.bak4bio.android.model.Content;
import br.ufpr.bioinfo.bak4bio.android.operations.Operations;
import br.ufpr.bioinfo.bak4bio.android.operations.BioboxOperations;

public class BioBoxSearchActivity extends AgActivity implements OnClickListener, OnEditorActionListener {
	
	private ListView listView;
	private ImageView imgViewSearch;
	private EditText etSearch;

	private Operations operations = new Operations(this);
	private BioboxOperations bioBoxOperation;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.operations.start();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void finish() {
		this.operations.finish();
		super.finish();
	}
	
	@Override
	public void defineContextView() {
		this.setContentView(R.layout.biobox_search);
	}
	
	@Override
	public void bindComponentsControl() {
		super.bindComponentsControl();
		
		this.etSearch = (EditText) findViewById(R.id.etSearch);
		this.imgViewSearch = (ImageView) findViewById(R.id.imgViewSearch);
		this.listView = (ListView) findViewById(R.id.listViewSearch);
	}
	
	@Override
	public void bindListenersControl() {
		super.bindListenersControl();
		
		this.imgViewSearch.setOnClickListener(this);
		this.etSearch.setOnEditorActionListener(this);
	}
	
	@Override
	public void defineDataComponents() {
		super.defineDataComponents();
		
		this.doSearch();
	}

	public void onClick(View view) {
		this.doSearch();
	}
	
	private void doSearch() {
		this.bioBoxOperation = this.operations.bioboxOperations();
		
		
		executor.execute(this, getString(R.string.waiting_label), new AsyncOperation() {
			public void execute() throws Exception {
				bioBoxOperation.addParam("filter", etSearch.getEditableText().toString());
				bioBoxOperation.addParam("auth_token", ApplicationManager.getInstance().getCurrentToken().getAuthToken());
				bioBoxOperation.indexOperation();
			}
			
			public void done() {
				if (bioBoxOperation.getIndexResult().size() == 0) {
					listView.setAdapter(null);
					DialogUtils.showDialog(BioBoxSearchActivity.this, "Info", getString(R.string.no_records_found_body));
					return;
				}
				
				BioBoxRowArrayAdapter<Content> adapter = new BioBoxRowArrayAdapter<Content>(BioBoxSearchActivity.this, bioBoxOperation.getIndexResult());
				listView.setAdapter(adapter);
			}
			
			public void failed(Exception e) {
				DialogUtils.showDialog(BioBoxSearchActivity.this, "Error", e.getMessage());
			}
		});
	}
	
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (v.getId()) {
			case R.id.etSearch:
	            this.doSearch();
	            return true;
		}
		return false;
	}
	
}
