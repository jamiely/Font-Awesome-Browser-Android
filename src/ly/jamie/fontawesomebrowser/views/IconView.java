package ly.jamie.fontawesomebrowser.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

public class IconView extends View {
	
	Paint mColorPaint;
	Paint mBackPaint;
	
	int mColor = 0xff000000;
	int mBackColor = 0xffCCCCCC;
	String mIcon = "?";
	Typeface mTypeface;
	
	Rect mRectBackground;
	
	boolean mDrawBackground = true;

	
	public boolean shouldDrawBackground() {
		return mDrawBackground;
	}

	public void setDrawBackground(boolean drawBackground) {
		this.mDrawBackground = drawBackground;
	}

	public IconView(Context context) {
		this(context, null, 0);
	}
	
	public IconView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public IconView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		init();
	}
	
	private void init(){
		mColorPaint = new Paint();
		mBackPaint = new Paint();
		mBackPaint.setColor(mBackColor);
		mRectBackground = new Rect(0,0,100,100);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// background
		if(shouldDrawBackground()) {
			canvas.drawRect(mRectBackground, mBackPaint);
		}
		
		// text
		mColorPaint.setColor(mColor);
		canvas.drawText(mIcon, 0, mRectBackground.height(), mColorPaint);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		int padding = 10;
		super.onSizeChanged(w + padding, h + padding, oldw, oldh);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = mRectBackground.width();
		int height = mRectBackground.height();
		if(width < getSuggestedMinimumWidth()) {
			width = getSuggestedMinimumWidth();
		}
		if(height < getSuggestedMinimumHeight()) {
			height = getSuggestedMinimumHeight();
		}
		setMeasuredDimension(width, height);
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
	
	public void matchWidth(int width) {
		Rect bounds = new Rect();
		
		int textSize = 5,
			boundsWidth = 0;
		
		do {
			mColorPaint.setTextSize(textSize);
			
			mColorPaint.getTextBounds(mIcon, 0, mIcon.length(), bounds);
			boundsWidth = bounds.width();
			
			textSize++;
		} while(boundsWidth < width);

		setupBounds(new Rect(0, 0, bounds.width(), bounds.height()));
		
		invalidate();
	}
	
	protected void setupBounds(Rect bounds) {
		mRectBackground = bounds;
		
		// bounds will now contain the appropriate size for the view
		setMinimumHeight(bounds.height());
		setMinimumWidth(bounds.width());
		requestLayout();
	}
	
	public Bitmap getIconBitmap() {
		Bitmap bitmap = Bitmap.createBitmap(mRectBackground.width(), mRectBackground.height(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		setDrawBackground(false);
		draw(canvas);
		setDrawBackground(true);
		return bitmap; 
	}
}
