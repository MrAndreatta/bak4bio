package br.ufpr.bioinfo.bak4bio.android.control;

import br.com.agivis.framework.app.AgActivity;
import br.com.agivis.framework.app.AgActivityConstants;
import br.com.agivis.framework.utils.DialogUtils;
import br.ufpr.bioinfo.bak4bio.android.R;
import br.ufpr.bioinfo.bak4bio.android.factory.ResourceFactory;
import br.ufpr.bioinfo.bak4bio.android.model.Resource;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class B4BMenuActivity extends AgActivity implements OnClickListener {
	
	private ImageView imgViewKEGG;
	private ImageView imgViewDDJB;
	private ImageView imgViewNCBI;
	private ImageView imgViewEBI;
	private ImageView imgViewPDBJ;
	private ImageView imgViewBioBox;
	private ImageView imgViewBlast;
	private ImageView imgViewExit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void defineContextView() {
		this.setContentView(R.layout.b4b_menu);
	}
	
	@Override
	public void bindComponentsControl() {
		super.bindComponentsControl();
		this.imgViewKEGG = (ImageView) findViewById(R.id.imgViewKEGG);
		this.imgViewDDJB = (ImageView) findViewById(R.id.imgViewDDBJ);
		this.imgViewNCBI = (ImageView) findViewById(R.id.imgViewNCBI);
		this.imgViewEBI = (ImageView) findViewById(R.id.imgViewEBI);
		this.imgViewPDBJ = (ImageView) findViewById(R.id.imgViewPDBJ);
		this.imgViewBioBox = (ImageView) findViewById(R.id.imgViewBioBox);
		this.imgViewBlast = (ImageView) findViewById(R.id.imgViewBlast);
		this.imgViewExit = (ImageView) findViewById(R.id.imgViewExit);
	}
	
	@Override
	public void bindListenersControl() {
		super.bindListenersControl();
		this.imgViewKEGG.setOnClickListener(this);
		this.imgViewDDJB.setOnClickListener(this);
		this.imgViewNCBI.setOnClickListener(this);
		this.imgViewEBI.setOnClickListener(this);
		this.imgViewPDBJ.setOnClickListener(this);
		this.imgViewBioBox.setOnClickListener(this);
		this.imgViewBlast.setOnClickListener(this);
		this.imgViewExit.setOnClickListener(this);
	}

	public void onClick(View v) {
		Resource resource;
		
		switch (v.getId()) {
			case R.id.imgViewKEGG:
				resource = ResourceFactory.build(Resource.KEGG);
				this.openSearchView(resource);
				break;
			case R.id.imgViewDDBJ:
				resource = ResourceFactory.build(Resource.DDJB);
				this.openSearchView(resource);
				break;
			case R.id.imgViewNCBI:
				resource = ResourceFactory.build(Resource.NCBI);
				this.openSearchView(resource);
				break;
			case R.id.imgViewEBI:
				resource = ResourceFactory.build(Resource.EBI);
				this.openSearchView(resource);
				break;
			case R.id.imgViewPDBJ:
				resource = ResourceFactory.build(Resource.PDBJ);
				this.openSearchView(resource);
				break;
			case R.id.imgViewBioBox:
				this.openBioBoxView();
				break;
			case R.id.imgViewBlast:
				this.openBlastView();
				break;	
			case R.id.imgViewExit:
				exit();
				break;
			default:
				DialogUtils.showDialog(B4BMenuActivity.this, getString(R.string.invalid_option_title), "Operação inexistente no menu principal.");
			} 
	}
	
	private void exit() {
		DialogUtils.showConfirmDialog(this, getString(R.string.confirmation_title), getString(R.string.exit_confirmation_body), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
	}
	
	private void openSearchView(Resource resource) {
		Intent intent = new Intent(this.getBaseContext(), TogowsSearchAtivity.class);
		intent.putExtra("resource", resource);
		this.startActivity(intent);
	}
	
	private void openBioBoxView() {
		Intent intent;
		boolean logged = ApplicationManager.getInstance().getCurrentToken() != null;
		
		if (logged) {
			intent = new Intent(this.getBaseContext(), BioBoxSearchActivity.class);
		}else {
			intent = new Intent(this.getBaseContext(), B4BLoginActivity.class);
			intent.putExtra(AgActivityConstants.ACTION_ATTRIBUTE, B4BLoginActivity.LOGIN_FOR_BIOBOX);
		}
		
		this.startActivity(intent);
	}
	
	private void openBlastView() {
		Intent intent;
		boolean logged = ApplicationManager.getInstance().getCurrentToken() != null;
		
		if (logged) {
			intent = new Intent(this.getBaseContext(), BlastSearchActivity.class);
		}else {
			intent = new Intent(this.getBaseContext(), B4BLoginActivity.class);
			intent.putExtra(AgActivityConstants.ACTION_ATTRIBUTE, B4BLoginActivity.LOGIN_FOR_BLAST);
		}
		
		this.startActivity(intent);
	}
}
