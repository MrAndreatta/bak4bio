package br.ufpr.bioinfo.bak4bio.android.control;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import br.com.agivis.framework.app.AgActivity;
import br.com.agivis.framework.async.AsyncOperation;
import br.com.agivis.framework.utils.DialogUtils;
import br.ufpr.bioinfo.bak4bio.android.R;
import br.ufpr.bioinfo.bak4bio.android.model.Database;
import br.ufpr.bioinfo.bak4bio.android.operations.TogowsEntryOperation;
import br.ufpr.bioinfo.bak4bio.android.operations.Operations;

public class TogowsDetailActivity extends AgActivity {
	
	private Database database;
	private String id;
	
	private EditText etDetail;
	private Operations operations = new Operations(this);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		operations.start();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void defineContextView() {
		this.setContentView(R.layout.togows_detail);
	}
	
	@Override
	protected void onDestroy() {
		operations.finish();
		super.onDestroy();
	}

	@Override 
	public void inExtraParams() {
		super.inExtraParams();
		Intent target = getIntent();
		this.id = (String) target.getStringExtra("id");
		this.database = (Database) target.getSerializableExtra("database");
		
		this.detailSearch();
	};
	
	private void detailSearch() {
		final TogowsEntryOperation entryOperation = operations.togowsEntryOperation();
		entryOperation.idSetting(id);
		entryOperation.databaseSetting(database.getName());
		
		executor.execute(this, getString(R.string.waiting_label), new AsyncOperation() {
			public void execute() throws Exception {
				entryOperation.search();
			}
			
			public void done() {
				etDetail.setText(entryOperation.getResult());
			}
			
			public void failed(Exception e) {
				DialogUtils.showDialog(TogowsDetailActivity.this, "Error", "Detail search problem: " + e.getMessage());
			}
		}); 
	}

	@Override
	public void bindComponentsControl() {
		super.bindComponentsControl();
		this.etDetail = (EditText) findViewById(R.id.etDetail);
	}
	
	@Override
	public void bindListenersControl() {
		super.bindListenersControl();
	}	
}
