package br.ufpr.bioinfo.bak4bio.android.control;

import java.util.List;

import br.com.agivis.framework.app.AgActivity;
import br.ufpr.bioinfo.bak4bio.android.R;

import br.com.agivis.framework.async.AsyncOperation;
import br.com.agivis.framework.utils.ConnectionUtils;
import br.com.agivis.framework.utils.DialogUtils;
import br.com.agivis.framework.utils.KeyboardUtils;
import br.ufpr.bioinfo.bak4bio.android.adapter.BlastSearchRowArrayAdapter;
import br.ufpr.bioinfo.bak4bio.android.model.Blast;
import br.ufpr.bioinfo.bak4bio.android.operations.BlastOperations;
import br.ufpr.bioinfo.bak4bio.android.operations.Operations;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.Intent;

public class BlastSearchActivity extends AgActivity implements OnClickListener, OnItemClickListener, OnEditorActionListener {
	
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
		this.setContentView(R.layout.blast_search);
	}
	
	@Override
	protected void onDestroy() {
		operations.finish();
		super.onDestroy();
	}
	
	@Override
	public void bindComponentsControl() {
		super.bindComponentsControl();
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
	
	@Override
	public void defineDataComponents() {
		this.doSearch();
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
			DialogUtils.showDialog(BlastSearchActivity.this, "Info", getString(R.string.need_internet_body));
			return;
		}
		
		KeyboardUtils.hideKeyBoard(this, etSearch);
		
		final BlastOperations blastOperations = this.operations.blastOperations();
		
		executor.execute(this, getString(R.string.waiting_label), new AsyncOperation() {
			public void execute() throws Exception {
				blastOperations.addParam("filter", etSearch.getText().toString());
				blastOperations.addParam("auth_token", ApplicationManager.getInstance().getCurrentToken().getAuthToken());
				blastOperations.indexOperation();
			}
			
			public void done() {
				List<Blast> result = blastOperations.getIndexResult();
				
				if (result.size() == 0) {
					listViewSearch.setAdapter(null);
					DialogUtils.showDialog(BlastSearchActivity.this, "Info", getString(R.string.no_records_found_body));
					return;
				}
				
				BlastSearchRowArrayAdapter<Blast> adapter = new BlastSearchRowArrayAdapter<Blast>(BlastSearchActivity.this, result);
				listViewSearch.setAdapter(adapter);
			}
			
			public void failed(Exception e) {
				DialogUtils.showDialog(BlastSearchActivity.this, "Error", "Blast Search problem: " + e.getMessage());
			}
		}); 
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long idList) {
		Blast blast = (Blast) parent.getItemAtPosition(position);
		
		this.openDetailBlast(blast);
	}

	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (v.getId()) {
			case R.id.etSearch:
	            this.doSearch();
	            return true;
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.blast_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.new_blast:
			this.openNewBlast();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void openNewBlast() {
		Intent intent = new Intent(this.getBaseContext(), BlastNewActivity.class);
		this.startActivity(intent);
	}
	
	private void openDetailBlast(Blast blast) {
		Intent intent = new Intent(this.getBaseContext(), BlastDetailActivity.class);
		intent.putExtra("blast", blast);

		this.startActivity(intent);
	}
}
