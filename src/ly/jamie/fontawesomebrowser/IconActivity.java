package ly.jamie.fontawesomebrowser;

import android.os.Bundle;
import afzkl.development.mColorPicker.views.ColorPanelView;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.TextView;

public class IconActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
     
        Bundle extras = getIntent().getExtras();
        String icon = extras.getString("ICON"),
        		iconName = extras.getString("ICON_NAME");
        
        // set the icon
        TextView textViewIcon = (TextView) findViewById(R.id.textViewIcon);
        textViewIcon.setText(icon);
        textViewIcon.setTypeface(TypefaceManager.FontAwesome(getAssets()));
        textViewIcon.setTextSize(50);
        
        TextView textViewIconName = (TextView) findViewById(R.id.textViewIconName);
        textViewIconName.setText(iconName);
        
        ColorPanelView colorPanel = (ColorPanelView) findViewById(R.id.color_panel);
        colorPanel.setColor(0xffff0000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_icon, menu);
        return true;
    }
}
