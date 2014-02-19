package com.iia.touchwin.views;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

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
			    if(hasFocus){
			        Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();
			    }else {
			        Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
			    }
			   }
			});
		
		/*
		 * // On vérifie les identifiants fournis par l'utilisateur
				Player oPlayer1 = Utils.authentication(MainActivity.this,
						editLogin, editPassword);

				if (oPlayer1 != null) {
					Bundle dataBundle = new Bundle();
					dataBundle.putSerializable(Const.BUNDLE_PLAYER,
							(Player) oPlayer1);

					Intent intentOpenHome = new Intent(MainActivity.this,
							HomeActivity.class);

					// On envoie le Player à l'autre vue
					intentOpenHome.putExtras(dataBundle);

					startActivity(intentOpenHome);
				}
		 */
	}
	
}
