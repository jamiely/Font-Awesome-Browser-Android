package ly.jamie.fontawesomebrowser.activities;

import ly.jamie.fontawesomebrowser.R;
import ly.jamie.fontawesomebrowser.R.id;
import ly.jamie.fontawesomebrowser.R.layout;
import ly.jamie.fontawesomebrowser.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity
	implements OnClickListener {
	
	Button buttonContact;
	Button buttonBrowse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        // setup the browse button
        buttonBrowse = (Button) findViewById(R.id.buttonBrowse);
        buttonBrowse.setOnClickListener(this);
        
        buttonContact = (Button) findViewById(R.id.buttonContact);
        buttonContact.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

	public void onClick(View v) {
		Class nextActivity = null;
		if(v == buttonContact) {
			nextActivity = null;
		}
		else if(v == buttonBrowse) {
			nextActivity = FontListActivity.class;
		}
		
		if(nextActivity != null) {
			startActivity(new Intent(this, nextActivity));
		}
	}
}
