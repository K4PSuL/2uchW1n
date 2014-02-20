package com.iia.touchwin.ui;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.request.PlayerRequest;
import com.iia.touchwin.ui.HomeActivity;
import com.iia.touchwin.utils.Const;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private boolean isTrue = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		final Button btnValid = (Button) findViewById(R.id.btnValid);
		final EditText editLogin = (EditText) findViewById(R.id.editLogin);
		final EditText editPassword = (EditText) findViewById(R.id.editPassword);

		editLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					if (PlayerRequest.loginExist(RegisterActivity.this,
							editLogin)) {
						editLogin.setError("Login déjà pris !");
						isTrue = false;
					} else {
						editLogin.setError(null);
						isTrue = true;
					}
				}
			}
		});

		btnValid.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (editLogin.getText().toString().length() < 2
						|| editPassword.getText().toString().length() < 2
						|| isTrue == false) {
					Toast.makeText(RegisterActivity.this,
							Const.ERREUR_REGISTER, Toast.LENGTH_LONG).show();
				} else {
					// On enregistre l'utilisateur
					PlayerRequest.register(RegisterActivity.this, editLogin,
							editPassword);

					// On authentifie l'utilisateur
					Player oPlayer1 = PlayerRequest.authentication(
							RegisterActivity.this, editLogin, editPassword);

					if (oPlayer1 != null) {
						Bundle dataBundle = new Bundle();
						dataBundle.putSerializable(Const.BUNDLE_PLAYER,
								(Player) oPlayer1);

						Intent intentOpenHome = new Intent(
								RegisterActivity.this, HomeActivity.class);

						// On envoie le Player à l'autre vue
						intentOpenHome.putExtras(dataBundle);

						finish();

						startActivity(intentOpenHome);
					}
				}
			}
		});
	}

}
