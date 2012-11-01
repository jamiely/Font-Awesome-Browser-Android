package ly.jamie.fontawesomebrowser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

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
    
    public class ChangeFontAdapter extends BaseAdapter {
    	private Map<String, String> _strings;
    	private String[] _keys;
    	private Context _context;
    	private Typeface _typeface;
    	
    	public ChangeFontAdapter(Context context, Map<String,String> strings, Typeface typeface) {
    		_strings = strings;
    		_context = context;
    		_typeface = typeface;
    		_keys = strings.keySet().toArray(new String[_strings.size()]);
    		int x = 1;
    	}
    	
		public int getCount() {
			// TODO Auto-generated method stub
			return _keys.length;
		}

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return _strings.get(_keys[arg0]);
		}

		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public View getView(int arg0, View arg1, ViewGroup arg2) {
			TextView tv = null;
			if(arg1 != null && arg1.getClass() == TextView.class) {
				tv = (TextView) arg1;
			}
			else {
				tv = new TextView(_context);
				tv.setTypeface(_typeface);
				tv.setTextSize(30);
			}
			tv.setText((String) getItem(arg0));
			return tv;
		}
		
		
    	
    }
}
