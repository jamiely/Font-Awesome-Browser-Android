package ly.jamie.fontawesomebrowser;

import ly.jamie.fontawesomebrowser.views.IconView;
import android.os.Bundle;
import afzkl.development.mColorPicker.views.ColorPanelView;
import afzkl.development.mColorPicker.ColorPickerDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class IconActivity extends Activity
	implements DialogInterface.OnClickListener {
	ColorPickerDialog colorPicker;
	ColorPanelView colorPanel;
	TextView textViewIcon;
	TextView textViewIconName;
	IconView iconView;
	SeekBar seekBarWidth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        
        Typeface awesomeTypeface = TypefaceManager.FontAwesome(getAssets());
     
        Bundle extras = getIntent().getExtras();
        String icon = extras.getString("ICON"),
        		iconName = extras.getString("ICON_NAME");
        
        // set the icon
        textViewIcon = (TextView) findViewById(R.id.textViewIcon);
        textViewIcon.setText(icon);
        textViewIcon.setTypeface(awesomeTypeface);
        textViewIcon.setTextSize(50);
        
        //iconView = (IconView) findViewById(R.id.icon_view);
        //iconView.setTypeface(awesomeTypeface);
        iconView = new IconView(this);
        iconView.setTypeface(awesomeTypeface);
        iconView.setIcon(icon);
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutIcon);
        layout.addView(iconView);
        
        //addContentView(iconView, null);
        
        // the icon name
        textViewIconName = (TextView) findViewById(R.id.textViewIconName);
        textViewIconName.setText(iconName);	
        
        // create the color panel
        colorPanel = (ColorPanelView) findViewById(R.id.color_panel);
        colorPanel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				colorPicker.show();
			}
		});
        
        // and the color picker
        colorPicker = new ColorPickerDialog(this, 0xffff0000);
        colorPicker.setAlphaSliderVisible(false);
        colorPicker.setButton("OK", this);
        colorPicker.setButton2("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				colorPicker.hide();
			}
		});
        
        seekBarWidth = (SeekBar) findViewById(R.id.seekBarWidth);
        seekBarWidth.setMax(400);
        seekBarWidth.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {
				int progress = seekBar.getProgress();
				if(progress < 5) {
					progress = 5;
				}
				iconView.matchWidth(progress);
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// do nothing	
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// only update when we're done
			}
		});
        
        // initialize other stuff
        setColor(0xffFF0000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_icon, menu);
        return true;
    }
    
    public void onClick(DialogInterface dialog, int which) {
    	int color = colorPicker.getColor();
		setColor(color);
	}
    
    public void setColor(int color) {
    	colorPanel.setColor(color);
		textViewIcon.setTextColor(color);
		iconView.setColor(color);
    }
}
