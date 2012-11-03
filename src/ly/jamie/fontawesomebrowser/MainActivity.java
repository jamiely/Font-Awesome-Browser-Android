package ly.jamie.fontawesomebrowser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

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
    	_awesome = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
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
    	lv.setAdapter(new ChangeFontAdapter(this, iconMap, _awesome));
    	
    }
    
    
}
