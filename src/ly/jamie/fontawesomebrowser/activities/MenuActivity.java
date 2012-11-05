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
import android.widget.Toast;

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
    
    public void sendContactEmail() {
    	Intent i = new Intent(Intent.ACTION_SEND);
    	i.setType("message/rfc822");
    	i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"Font Awesome Browser <font_awesome_browser@angelforge.org>"});
    	i.putExtra(Intent.EXTRA_SUBJECT, "[FontAwesomeBrowser] Suggestion");
    	i.putExtra(Intent.EXTRA_TEXT   , "I'd like to make a suggestion:\n");
    	try {
    	    startActivity(Intent.createChooser(i, "Send mail..."));
    	} catch (android.content.ActivityNotFoundException ex) {
    	    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
    	}
    }

	public void onClick(View v) {
		if(v == buttonContact) {
			sendContactEmail();
		}
		else if(v == buttonBrowse) {
			startActivity(new Intent(this, FontListActivity.class));
		}
	}
}
