package br.ufpr.bioinfo.bak4bio.android.control;

import java.util.List;

import br.com.agivis.framework.app.AgActivity;
import br.com.agivis.framework.async.AsyncOperation;
import br.com.agivis.framework.utils.ConnectionUtils;
import br.com.agivis.framework.utils.DialogUtils;
import br.com.agivis.framework.utils.KeyboardUtils;
import br.ufpr.bioinfo.bak4bio.android.R;
import br.ufpr.bioinfo.bak4bio.android.adapter.TogowsSearchRowArrayAdapter;
import br.ufpr.bioinfo.bak4bio.android.model.Database;
import br.ufpr.bioinfo.bak4bio.android.model.Resource;
import br.ufpr.bioinfo.bak4bio.android.operations.TogowsSearchOperation;
import br.ufpr.bioinfo.bak4bio.android.operations.Operations;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.Intent;

public class TogowsSearchAtivity extends AgActivity implements OnClickListener, OnItemClickListener, OnEditorActionListener {
	
	private Resource resource; 
	
	private Spinner spinDatabase;
	private EditText etSearch;
	private ImageView btnSearch;
	private ListView listViewSearch;
	
	private Operations operations = new Operations(this);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		operations.start();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void defineContextView() {
		this.setContentView(R.layout.togows_search);
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
		this.resource = (Resource) target.getSerializableExtra("resource");
		this.setTitle(this.resource.getName() + " " + getString(R.string.search_title));
		
		this.searchDataBases();
	};
	
	private void searchDataBases() {
		ArrayAdapter<Database> adapter = new ArrayAdapter<Database> (TogowsSearchAtivity.this, 
																android.R.layout.simple_spinner_item, 
																resource.getDatabases());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinDatabase.setAdapter(adapter);
	}

	@Override
	public void bindComponentsControl() {
		super.bindComponentsControl();
		this.spinDatabase = (Spinner) findViewById(R.id.spinDatabase);
		this.etSearch = (EditText) findViewById(R.id.etSearch);
		this.listViewSearch = (ListView) findViewById(R.id.listViewSearch);
		this.btnSearch = (ImageView) findViewById(R.id.imgViewSearch);
	}
	
	@Override
	public void bindListenersControl() {
		super.bindListenersControl();
		this.etSearch.setOnEditorActionListener(this);
		this.btnSearch.setOnClickListener(this);
		this.listViewSearch.setOnItemClickListener(this);
	}
	
	public void onClick(View v) {
		
		switch (v.getId()) {
			case R.id.imgViewSearch:
				this.searchClick();
				break;
		}
	}

	private void searchClick() {
		this.doSearch();
	}

	private void doSearch() {
		if (!ConnectionUtils.isOnline(this)) {
			DialogUtils.showDialog(TogowsSearchAtivity.this, "Info", getString(R.string.need_internet_body));
			return;
		}
		
		KeyboardUtils.hideKeyBoard(this, etSearch);
		
		final Database dataBase = (Database) this.spinDatabase.getSelectedItem();
		String query = this.etSearch.getText().toString();
		
		final TogowsSearchOperation searchOperation = operations.togowsSearchOperation();
		searchOperation.databaseSetting(dataBase.getName());
		searchOperation.filter(query);
		
		executor.execute(this, getString(R.string.waiting_label), new AsyncOperation() {
			public void execute() throws Exception {
				searchOperation.search();
			}
			
			public void done() {
				List<String> result = searchOperation.getResult();
				
				if (result.size() == 0) {
					listViewSearch.setAdapter(null);
					DialogUtils.showDialog(TogowsSearchAtivity.this, "Info", getString(R.string.no_records_found_body));
					return;
				}
				
				TogowsSearchRowArrayAdapter<String> adapter = new TogowsSearchRowArrayAdapter<String>(TogowsSearchAtivity.this, result, resource, operations, dataBase);
				listViewSearch.setAdapter(adapter);
			}
			
			public void failed(Exception e) {
				DialogUtils.showDialog(TogowsSearchAtivity.this, "Error", "Search problem: " + e.getMessage());
			}
		}); 
		
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long idList) {
		String id = (String) parent.getItemAtPosition(position);
		
		Intent intent = new Intent(this.getBaseContext(), TogowsDetailActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("database", (Database) spinDatabase.getSelectedItem());

		this.startActivity(intent);
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
