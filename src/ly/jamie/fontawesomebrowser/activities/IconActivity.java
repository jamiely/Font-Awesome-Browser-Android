package ly.jamie.fontawesomebrowser.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ly.jamie.fontawesomebrowser.R;
import ly.jamie.fontawesomebrowser.TypefaceManager;
import ly.jamie.fontawesomebrowser.R.id;
import ly.jamie.fontawesomebrowser.R.layout;
import ly.jamie.fontawesomebrowser.R.menu;
import ly.jamie.fontawesomebrowser.views.IconView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import afzkl.development.mColorPicker.views.ColorPanelView;
import afzkl.development.mColorPicker.ColorPickerDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
	
	String icon;
	String iconName;
	
	static String exportedIconName = "font-awesome-browser-exported-icon.png";
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        
        Typeface awesomeTypeface = TypefaceManager.FontAwesome(getAssets());
     
        Bundle extras = getIntent().getExtras();
        icon = extras.getString("ICON");
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
				exportIcon();
			}
		});
        
        // initialize other stuff
        setColor(0xffFF0000);
    }
    
    protected void exportIcon() {
    	Bitmap iconBitmap = iconView.getIconBitmap();
		File downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		if(!downloadsPath.exists()) {
			downloadsPath.mkdir();
		}
		File outFile = new File(downloadsPath, exportedIconName);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(outFile);
			iconBitmap.compress(CompressFormat.PNG, 100, fos);
			fos.close();
			
			emailIcon(outFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.e("IconActivity", e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("IconActivity", e.getMessage());
		}
    }
    
    protected void emailIcon(File iconFile) {
    	Uri fileUri = Uri.fromFile(iconFile);
    	Intent intent = new Intent(Intent.ACTION_SEND);
    	intent.setType("text/plain");
    	intent.putExtra(Intent.EXTRA_EMAIL, new String[] {""});
    	intent.putExtra(Intent.EXTRA_SUBJECT, "[FontAwesomeBrowser] Icon " + iconName);
    	intent.putExtra(Intent.EXTRA_TEXT, "Here's the exported icon.");
    	intent.putExtra(Intent.EXTRA_STREAM, fileUri);
    	startActivity(Intent.createChooser(intent, "Send email..."));
    }
    
    protected void setIconWidth(int width){
    	iconView.matchWidth(width);
		textViewIconWidth.setText(getString(R.string.label_icon_width) + ": " + width);
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
