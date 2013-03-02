package br.ufpr.bioinfo.bak4bio.android.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.agivis.framework.app.AgActivity;
import br.com.agivis.framework.async.AsyncOperation;
import br.com.agivis.framework.utils.ConnectionUtils;
import br.com.agivis.framework.utils.DialogUtils;
import br.ufpr.bioinfo.bak4bio.android.R;
import br.ufpr.bioinfo.bak4bio.android.operations.Operations;
import br.ufpr.bioinfo.bak4bio.android.operations.TokenOperations;

public class B4BLoginActivity extends AgActivity implements OnClickListener {
	
	public static final int LOGIN_FOR_BLAST = 1;
	public static final int LOGIN_FOR_BIOBOX = 2;
	
	private EditText etEmail;
	private EditText etPassword;
	
	private Button btnLogin;
	private Button btnAnotherUser;
	
	private Operations operations = new Operations(this);

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
		this.setContentView(R.layout.b4b_login);
	}
	
	@Override
	public void bindComponentsControl() {
		this.etEmail = (EditText) findViewById(R.login.etEmail);
		this.etPassword = (EditText) findViewById(R.login.etPassword);
		this.btnLogin = (Button) findViewById(R.login.btnLogin);
		this.btnAnotherUser = (Button) findViewById(R.login.btnAnotherUser);
	}
	
	@Override
	public void bindListenersControl() {
		this.btnLogin.setOnClickListener(this);
		this.btnAnotherUser.setOnClickListener(this);
	}

	public void onClick(View target) {
		if (target == this.btnLogin) {
			this.onClickLogin();
			return;
		}
		
		if (target == this.btnAnotherUser) {
			this.onClickAnotherUser();
			return;
		}
		
	}
	
	private void onClickLogin() {
		if (!ConnectionUtils.isOnline(this)) {
			DialogUtils.showDialog(this, "Info", getString(R.string.need_internet_body));
			return;
		}
		
		final TokenOperations tokenOperations = operations.tokenOperations();
		final String email = this.etEmail.getText().toString();
		final String password = this.etPassword.getText().toString();

		boolean emailIsBlank = email.equals("");
		boolean passwordIsBlank = password.equals("");
		
		if (emailIsBlank || passwordIsBlank ) {
			DialogUtils.showDialog(this, "Validation", "Email and password is required");
			return;
		}
		
		executor.execute(this, getString(R.string.waiting_label), new AsyncOperation() {
			public void execute() throws Exception {
				tokenOperations.addParam("email", email);
				tokenOperations.addParam("password", password);
				tokenOperations.createOperation();
			}
			
			public void done() {
				ApplicationManager.getInstance().setCurrentToken(tokenOperations.getCreateResult());
				DialogUtils.showToast(B4BLoginActivity.this, "Login successful");
				
				if (action == LOGIN_FOR_BIOBOX) {
					openBioBoxSearch();
				}else if (action == LOGIN_FOR_BLAST) {
					openBlastSearch();
				}
			}
			
			public void failed(Exception e) {
				DialogUtils.showDialog(B4BLoginActivity.this, "Error", e.getMessage());
			}
		});
	}
	
	private void onClickAnotherUser() {
		this.etEmail.setText("");
		this.etPassword.setText("");
	}
	
	private void openBioBoxSearch() {
		Intent intent = new Intent(this.getBaseContext(), BioBoxSearchActivity.class);
		this.startActivity(intent);
		this.finish();
	}
	
	private void openBlastSearch() {
		Intent intent = new Intent(this.getBaseContext(), BlastSearchActivity.class);
		this.startActivity(intent);
		this.finish();
	}
}
