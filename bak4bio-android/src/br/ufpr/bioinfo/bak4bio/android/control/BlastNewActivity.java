package br.ufpr.bioinfo.bak4bio.android.control;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import br.com.agivis.framework.app.AgActivity;
import br.com.agivis.framework.async.AsyncOperation;
import br.com.agivis.framework.utils.ConnectionUtils;
import br.com.agivis.framework.utils.DialogUtils;
import br.ufpr.bioinfo.bak4bio.android.R;
import br.ufpr.bioinfo.bak4bio.android.model.Blast;
import br.ufpr.bioinfo.bak4bio.android.model.Content;
import br.ufpr.bioinfo.bak4bio.android.operations.BioboxOperations;
import br.ufpr.bioinfo.bak4bio.android.operations.BlastOperations;
import br.ufpr.bioinfo.bak4bio.android.operations.Operations;

public class BlastNewActivity extends AgActivity implements OnClickListener {
	
	private EditText etTitle;
	private Spinner spinEntry;
	private Spinner spinProgram;
	private Spinner spinDatabase;
	private Spinner spinMaxTargetSequence;
	private EditText etExpect;
	private Spinner spinWordSize;
	private Spinner spinMScores;
	private Spinner spinGapCosts;
	private EditText etMaxMatchRange;
	private Button btnSubmit;
	
	private Operations operations = new Operations(this);
	private Blast blast;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		operations.start();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void defineContextView() {
		this.setContentView(R.layout.blast_new);
	}
	
	@Override
	protected void onDestroy() {
		operations.finish();
		super.onDestroy();
	}
	
	@Override
	public void bindComponentsControl() {
		super.bindComponentsControl();
		this.etTitle = (EditText) findViewById(R.id.etTitle);
		this.spinEntry = (Spinner) findViewById(R.id.spinEntry);
		this.spinProgram = (Spinner) findViewById(R.id.spinProgram);
		this.spinDatabase = (Spinner) findViewById(R.id.spinDatabase);
		this.spinMaxTargetSequence = (Spinner) findViewById(R.id.spinMaxTargetSequence);
		this.etExpect = (EditText) findViewById(R.id.etExpect);
		this.spinWordSize = (Spinner) findViewById(R.id.spinWordSize);
		this.etMaxMatchRange = (EditText) findViewById(R.id.etMaxMatchesRange);
		this.spinMScores = (Spinner) findViewById(R.id.spinMScores);
		this.spinGapCosts = (Spinner) findViewById(R.id.spinGapCosts);
		this.btnSubmit = (Button) findViewById(R.id.btnSubmit);
	}
	
	@Override
	public void bindListenersControl() {
		super.bindListenersControl();
		this.btnSubmit.setOnClickListener(this);
	}
	
	@Override
	public void defineDataComponents() {
		this.loadSpinnersFromXMLs();
		this.loadSpinnersFromServices();
		this.doNew();
	}
	
	private void loadSpinnersFromServices() {
		final BioboxOperations bioboxOperations = this.operations.bioboxOperations();
		
		executor.execute(this, getString(R.string.waiting_label), new AsyncOperation() {
			public void execute() throws Exception {
				bioboxOperations.addParam("auth_token", ApplicationManager.getInstance().getCurrentToken().getAuthToken());
				bioboxOperations.indexOperation();
			}
			
			public void done() {
				ArrayAdapter<Content> adapter = new ArrayAdapter<Content> (BlastNewActivity.this, 
						android.R.layout.simple_spinner_item, 
						bioboxOperations.getIndexResult());
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				spinEntry.setAdapter(adapter);
			}
			
			public void failed(Exception e) {
				DialogUtils.showDialog(BlastNewActivity.this, "Error", e.getMessage());
			}
		}); 
	}

	private void loadSpinnersFromXMLs() {
		ArrayAdapter<CharSequence> adapterDataBase = ArrayAdapter.createFromResource(this, R.array.blast_databases_array, android.R.layout.simple_spinner_item);
		adapterDataBase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinDatabase.setAdapter(adapterDataBase);
		
		ArrayAdapter<CharSequence> adapterProgram = ArrayAdapter.createFromResource(this, R.array.blast_programs_array, android.R.layout.simple_spinner_item);
		adapterProgram.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinProgram.setAdapter(adapterProgram);
		
		ArrayAdapter<CharSequence> adapterMaxTargetSequence = ArrayAdapter.createFromResource(this, R.array.blast_max_target_sequences_array, android.R.layout.simple_spinner_item);
		adapterMaxTargetSequence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinMaxTargetSequence.setAdapter(adapterMaxTargetSequence);
		
		ArrayAdapter<CharSequence> adapterWordSize = ArrayAdapter.createFromResource(this, R.array.blast_word_sizes_array, android.R.layout.simple_spinner_item);
		adapterWordSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinWordSize.setAdapter(adapterWordSize);
		
		ArrayAdapter<CharSequence> adapterMScores = ArrayAdapter.createFromResource(this, R.array.blast_m_scores_array, android.R.layout.simple_spinner_item);
		adapterMScores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinMScores.setAdapter(adapterMScores);
		
		ArrayAdapter<CharSequence> adapterGapCosts = ArrayAdapter.createFromResource(this, R.array.blast_gap_costs_array, android.R.layout.simple_spinner_item);
		adapterGapCosts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinGapCosts.setAdapter(adapterGapCosts);
	}

	private void doNew() {
		if (!ConnectionUtils.isOnline(this)) {
			DialogUtils.showDialog(BlastNewActivity.this, "Info", getString(R.string.need_internet_body));
			return;
		}
		
		final BlastOperations blastOperations = this.operations.blastOperations();
		
		
		executor.execute(this, getString(R.string.waiting_label), new AsyncOperation() {
			public void execute() throws Exception {
				blastOperations.addParam("auth_token", ApplicationManager.getInstance().getCurrentToken().getAuthToken());
				blastOperations.newOperation();
			}
			
			public void done() {
				blast = blastOperations.getNewResult();
			}
			
			public void failed(Exception e) {
				DialogUtils.showDialog(BlastNewActivity.this, "Error", e.getMessage());
			}
		}); 
	}

	public void onClick(View view) {
		if (view.getId() == R.id.btnSubmit) {
			this.doSubmitBlast();
		}
		
	}

	private void doSubmitBlast() {
		this.formToEntity();
		
		executor.execute(this, getString(R.string.waiting_label), new AsyncOperation() {
			public void execute() throws Exception {
				BlastOperations blastOperations = operations.blastOperations();
				blastOperations.addParam("auth_token", ApplicationManager.getInstance().getCurrentToken().getAuthToken());
				
				JSONObject jsonBlastParams = new JSONObject();
				jsonBlastParams.put("entry_id", blast.getEntry().getId().toString());
				jsonBlastParams.put("database", blast.getDataBase());
				jsonBlastParams.put("expect", blast.getExpect());
		        jsonBlastParams.put("gap_costs", blast.getGapCosts());
		        jsonBlastParams.put("m_scores", blast.getmScores());
		        jsonBlastParams.put("max_matches_range", blast.getMaxMatchesRange());
		        jsonBlastParams.put("max_target_sequence", blast.getMaxTargetSequence());
		        jsonBlastParams.put("program", blast.getProgram());
		        jsonBlastParams.put("title", blast.getTitle());
		        jsonBlastParams.put("word_size", blast.getWord_size());
		        
				blastOperations.addParam("blast", jsonBlastParams); 
				blastOperations.createOperation();
			}
			
			public void done() {
				DialogUtils.showToast(BlastNewActivity.this, "New blast sent successfully");
			}
			
			public void failed(Exception e) {
				DialogUtils.showDialog(BlastNewActivity.this, "Error", e.getMessage());
			}
		}); 
	}

	private void formToEntity() {
		this.blast.setEntry((Content) this.spinEntry.getSelectedItem());
		this.blast.setDataBase((String) this.spinDatabase.getSelectedItem());
		this.blast.setExpect(this.etExpect.getText().toString());
		this.blast.setGapCosts((String) this.spinGapCosts.getSelectedItem());
		this.blast.setmScores((String) this.spinMScores.getSelectedItem());
		this.blast.setMaxMatchesRange(this.etMaxMatchRange.getText().toString());
		this.blast.setMaxTargetSequence((String) this.spinMaxTargetSequence.getSelectedItem());
		this.blast.setProgram((String) this.spinProgram.getSelectedItem());
		this.blast.setTitle(this.etTitle.getText().toString());
		this.blast.setWord_size((String) this.spinWordSize.getSelectedItem());
		
	}
	
}