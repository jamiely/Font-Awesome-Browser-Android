package ly.jamie.fontawesomebrowser.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

import ly.jamie.fontawesomebrowser.ChangeFontAdapter;
import ly.jamie.fontawesomebrowser.R;
import ly.jamie.fontawesomebrowser.TypefaceManager;
import ly.jamie.fontawesomebrowser.R.id;
import ly.jamie.fontawesomebrowser.R.layout;
import ly.jamie.fontawesomebrowser.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FontListActivity extends Activity {

	protected Typeface _awesome;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        loadFont();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    // # Font loading and displaying functions
    
    protected void loadFont() {
    	_awesome = TypefaceManager.FontAwesome(getAssets());
    	//getFontView().setTypeface(_awesome);
    	
    	Map<String, String> iconMap = new Hashtable<String, String>();
    	
    	try {
			InputStreamReader is = new InputStreamReader(getAssets().open("list.txt"));
			BufferedReader reader = new BufferedReader(is, 5000);
			String line = null;
			while((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if(parts.length >= 3) {
					int charCode = Integer.parseInt(parts[2]);
					iconMap.put(parts[0], String.valueOf((char) charCode));
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	ListView lv = (ListView) findViewById(R.id.fontListView);
    	final ChangeFontAdapter fontAdapter = new ChangeFontAdapter(this, iconMap, _awesome);
    	lv.setAdapter(fontAdapter);
    	lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent iconIntent = new Intent(parent.getContext(), IconActivity.class);
				iconIntent.putExtra("ICON_NAME", fontAdapter.getKey(position));
				iconIntent.putExtra("ICON", fontAdapter.getIcon(position));
				startActivity(iconIntent);
			}
		});
    }
    
    
}
