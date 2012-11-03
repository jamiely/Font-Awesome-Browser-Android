package ly.jamie.fontawesomebrowser.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class IconView extends View {
	
	Paint mColorPaint;
	Paint mBackPaint;
	
	int mColor = 0xff000000;
	int mBackColor = 0xffEEEEEE;
	String mIcon = "?";
	Typeface mTypeface;
	
	public IconView(Context context) {
		this(context, null, 0);
	}
	
	public IconView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		init();
	}
	
	private void init(){
		mColorPaint = new Paint();
		mBackPaint = new Paint();
		mBackPaint.setColor(mBackColor);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// background
		canvas.drawRect(0, 0, 100, 100, mBackPaint);
		
		// text
		mColorPaint.setColor(mColor);
		canvas.drawText(mIcon, 0, 100, mColorPaint);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		int padding = 10;
		super.onSizeChanged(w + padding, h + padding, oldw, oldh);
	}
	
	public void setColor(int color) {
		mColor = color;
		invalidate();
	}
	
	public void setIcon(String icon) {
		mIcon = icon;
		invalidate();
	}
	
	public void setTypeface(Typeface typeface) {
		mTypeface = typeface;
		mColorPaint.setTypeface(mTypeface);
		mColorPaint.setTextSize(30);
		invalidate();
	}
}
