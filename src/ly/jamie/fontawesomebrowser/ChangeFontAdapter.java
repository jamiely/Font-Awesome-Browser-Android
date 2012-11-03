package ly.jamie.fontawesomebrowser;

import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
	}
	
	public int getCount() {
		return _keys.length;
	}

	public Object getItem(int position) {
		return _strings.get(_keys[position]);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv = null;
		if(convertView != null && convertView.getClass() == TextView.class) {
			tv = (TextView) convertView;
		}
		else {
			tv = new TextView(_context);
			tv.setTypeface(_typeface);
			tv.setTextSize(30);
		}
		tv.setText((String) getItem(position));
		return tv;
	}
}
