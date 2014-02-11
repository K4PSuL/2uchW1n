package com.iia.touchwin.views;

import java.util.ArrayList;

import com.iia.touchwin.R;
import com.iia.touchwin.contracts.GameContract;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.TouchWinSqlLiteOpenHelper;
import com.iia.touchwin.utils.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		final EditText editPlayer1 = (EditText) findViewById(R.id.editPlayer1);
		final EditText editPlayer2 = (EditText) findViewById(R.id.editPlayer2);
		final EditText editGame = (EditText) findViewById(R.id.editGame);
		final Button btnGo = (Button) findViewById(R.id.btnGo);

		// On récupère le Player
		final Player thePlayer = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);
				
		// On popule le formulaire avec le login du Player 1
		editPlayer1.setText(thePlayer.getLogin());

		
		/* CHOIX DU JOUEUR 2 */
		editPlayer2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Create custom dialog object
				final Dialog dialog = new Dialog(PlayActivity.this);
				// Include dialog.xml file
				dialog.setContentView(R.layout.dialog_player);
				// Set dialog title
				dialog.setTitle(R.string.title_dialog_player);
				// Show the dialog
				dialog.show();

				Button btnValidPlayer = (Button) dialog.findViewById(R.id.btnValid);
				
				// if button is clicked, close the custom dialog
				btnValidPlayer.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {	

       				 	EditText editLoginDialog = (EditText) dialog.findViewById(R.id.editLogin);
       				 	EditText editPwdDialog = (EditText) dialog.findViewById(R.id.editPassword);
       		
       				 	// On vérifie les identifiants fournis par l'utilisateur
       					Player player2 = Utils.authentication(PlayActivity.this,
       							editLoginDialog.getText().toString(), editPwdDialog.getText()
       									.toString());
       					
       					if (player2 != null) {
       						if (thePlayer.getId() == player2.getId()) {
       							Toast.makeText(PlayActivity.this, Const.ERREUR_PLAYER2,
       									Toast.LENGTH_LONG).show();
							} else {
								Bundle dataBundle = new Bundle();
		     					dataBundle.putSerializable(Const.BUNDLE_PLAYER2,
		     							(Player) player2);
		
		     					// Close dialog
								dialog.dismiss();
								
								editPlayer2.setText(player2.getLogin().toString());
							}
       					}
					}
				});
			}
		});
		
		
		/* CHOIX DU JEU */
		final Game oGame = new Game();
		
		editGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Create custom dialog object
				final Dialog dialog = new Dialog(PlayActivity.this);
				// Include dialog.xml file
				dialog.setContentView(R.layout.dialog_game);
				// Set dialog title
				dialog.setTitle(R.string.title_dialog_game);
				// Show dialog
				dialog.show();

				ListView listGames = (ListView) dialog
						.findViewById(R.id.listGames);
				Button btnValidGame = (Button) dialog
						.findViewById(R.id.btnValid);
				
				
				TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
						PlayActivity.this, Const.DATABASE, null, 1);

				// Récupération de la BDD
				SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();

				// Requête sur la BDD
				Cursor oCursor = dataBase.query(GameContract.TABLE,
						GameContract.COLS, null, null, null, null, null);
				
				ArrayList<Game> games = new ArrayList<Game>();
				
				if (oCursor.moveToFirst()) {
		        	do {
		        		int id = 
		        				oCursor.getInt( 
		        						oCursor.getColumnIndex(GameContract.COL_ID));
		        		
						String libelle = 
								oCursor.getString( 
										oCursor.getColumnIndex(GameContract.COL_LIBELLE));
						
						if (libelle != null && libelle.length()>0) {
							oGame.setId(id);
							oGame.setLibelle(libelle);
							games.add(oGame);
						}
						
					} while (oCursor.moveToNext());
		        }
				
				MyAdapter adapter = new MyAdapter(PlayActivity.this, R.layout.row_game, games);		
				listGames.setAdapter(adapter);
				
				listGames.setOnItemClickListener(new OnItemClickListener()
				   {
				      @Override
				      public void onItemClick(AdapterView<?> adapter, View v, int position,
				            long arg3) 
				      {
				            Game game = (Game)adapter.getItemAtPosition(position);
				            editGame.setText(game.getLibelle());
				            dialog.dismiss();
				      }
				   });
				
				// if button is clicked, close the custom dialog
				btnValidGame.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// Close dialog
						dialog.dismiss();
					}
				});

			}
		});

		
		/* CHOIX DU TEMPS / NOMBRE DE ROUNDS */
		final RadioGroup radioGroupTime = (RadioGroup) findViewById(R.id.radioGroup);
		
		// get selected radio button from radioGroup
		int selectedId = radioGroupTime.getCheckedRadioButtonId();
		 
		// find the radiobutton by returned id
		final RadioButton radioBtn = (RadioButton) findViewById(selectedId);
		
		
		/* DEMARRAGE DU JEU */
		btnGo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final Bundle dataBundle = new Bundle();
				dataBundle.putSerializable(Const.BUNDLE_PLAYER, (Player) thePlayer);
				dataBundle.putSerializable(Const.BUNDLE_PLAYER2, (Player) thePlayer);
				dataBundle.putSerializable(Const.BUNDLE_GAME, (Game) oGame);
				dataBundle.putInt(Const.BUNDLE_TIME, Integer.valueOf(radioBtn.getText().toString()));
				
				// Controle des champs vides
				if (editPlayer1.getText().length() < 1 
						|| editPlayer2.getText().length() < 1
						|| editGame.getText().length() < 1) {
					Toast.makeText(PlayActivity.this, Const.ERREUR_FORMVIDE,
								Toast.LENGTH_LONG).show();
				} else {
					Intent intentOpenGame = new Intent(PlayActivity.this,
							GameActivity.class);

					intentOpenGame.putExtras(dataBundle);

					startActivity(intentOpenGame);
				}
			}
		});

	}
	
	private static class MyAdapter extends ArrayAdapter<Game> {

		private Context context;
		private int resource;
		private LayoutInflater inflater;
		
		public MyAdapter(Context context, int resource,
				java.util.List<Game> items) {
			super(context, resource, items);
			this.context = context;
			this.resource = resource;
			
			inflater = LayoutInflater.from(this.context);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) { // appelée à chaque création de  ligne
			View view = inflater.inflate(this.resource, null);
			
			TextView lbGame = (TextView)view.findViewById(R.id.lbGame);
			
			Game game = this.getItem(position);
			
			lbGame.setText(game.getLibelle().toString());
			
			return view;
		}
	}
}
