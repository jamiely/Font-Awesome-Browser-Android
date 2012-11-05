package ly.jamie.fontawesomebrowser.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import ly.jamie.fontawesomebrowser.R;
import ly.jamie.fontawesomebrowser.TypefaceManager;
import ly.jamie.fontawesomebrowser.R.id;
import ly.jamie.fontawesomebrowser.R.layout;
import ly.jamie.fontawesomebrowser.R.menu;
import ly.jamie.fontawesomebrowser.views.IconView;
import android.os.Bundle;
import android.os.Environment;
import afzkl.development.mColorPicker.views.ColorPanelView;
import afzkl.development.mColorPicker.ColorPickerDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.util.Log;

public class IconActivity extends Activity
	implements DialogInterface.OnClickListener {
	ColorPickerDialog colorPicker;
	ColorPanelView colorPanel;
	TextView textViewIconName;
	TextView textViewIconWidth;
	IconView iconView;
	SeekBar seekBarWidth;
	Button buttonExport;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        
        Typeface awesomeTypeface = TypefaceManager.FontAwesome(getAssets());
     
        Bundle extras = getIntent().getExtras();
        String icon = extras.getString("ICON"),
        		iconName = extras.getString("ICON_NAME");
        
        iconView = (IconView) findViewById(R.id.iconView);
        iconView.setTypeface(awesomeTypeface);
        iconView.setIcon(icon);
   
        // the icon name
        textViewIconName = (TextView) findViewById(R.id.textViewIconName);
        textViewIconName.setText(iconName);	
        
        textViewIconWidth = (TextView) findViewById(R.id.textViewIconWidth);
        
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
				setIconWidth(progress);
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// do nothing	
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// only update when we're done
			}
		});
        
        buttonExport = (Button) findViewById(R.id.buttonExport);
        buttonExport.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Bitmap iconBitmap = iconView.getIconBitmap();
				File downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
				if(!downloadsPath.exists()) {
					downloadsPath.mkdir();
				}
				File outFile = new File(downloadsPath, "new.png");
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(outFile);
					iconBitmap.compress(CompressFormat.PNG, 100, fos);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("IconActivity", e.getMessage());
				}
				
			}
		});
        
        // initialize other stuff
        setColor(0xffFF0000);
    }
    
    protected void setIconWidth(int width){
    	iconView.matchWidth(width);
		textViewIconWidth.setText(getString(R.string.label_icon_width) 
			+ ": " + iconView.getWidth());
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
		iconView.setColor(color);
    }
}
