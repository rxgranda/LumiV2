package com.qualcomm.QCARSamples.CloudRecognition;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.qualcomm.QCARSamples.CloudRecognition.R;


public class InfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

//	public PictureOverlayView(Context context)
//    {
//        this(context, null);
//    }
//
//
//    public PictureOverlayView(Context context, AttributeSet attrs)
//    {
//        this(context, attrs, 0);
//    }
//
//
//    public PictureOverlayView(Context context, AttributeSet attrs, int defStyle)
//    {
//        super(context, attrs, defStyle);
//        inflateLayout(context);
//
//    }


    /** Inflates the Custom View Layout */
//    private void inflateLayout(Context context)
//    {
//
//        final LayoutInflater inflater = LayoutInflater.from(context);
//
//        // Generates the layout for the view
//        inflater.inflate(R.layout.bitmap_layout, this, true);
//    }


    /** Sets Picture title in View */
    public void setPictureTitle(String pictureTitle)
    {
        TextView tv = (TextView) findViewById(R.id.custom_view_title);
        tv.setText(pictureTitle);
    }


    /** Sets Picture Author in View */
    public void setPictureAuthor(String pictureAuthor)
    {
        TextView tv = (TextView) findViewById(R.id.custom_view_author);
        tv.setText(pictureAuthor);
    }


    /** Sets Picture Price in View */
    public void setPictureYear(String pictureYear)
    {
        TextView tv = (TextView) findViewById(R.id.custom_view_price_old);
        tv.setText(pictureYear);
    }


//    /** Sets Picture Number of Ratings in View */
//    public void setPictureRatingCount(String ratingCount)
//    {
//        TextView tv = (TextView) findViewById(R.id.custom_view_rating_text);
//        tv.setText(getContext().getString(R.string.string_openParentheses)
//                + ratingCount + getContext().getString(R.string.string_ratings)
//                + getContext().getString(R.string.string_closeParentheses));
//    }


    /** Sets Picture Description in View */
//    public void setPictureDescription(String pictureDescription)
//    {
//        TextView tv = (TextView) findViewById(R.id.picture_description);
//        tv.setText(getContext().getString(R.string.string_$) + pictureDescription);
//    }


    /** Sets Picture in View from a bitmap */
    public void setCoverViewFromBitmap(Bitmap picture)
    {
        ImageView iv = (ImageView) findViewById(R.id.custom_view_book_cover);
        iv.setImageBitmap(picture);
    }


    /** Sets Picture Rating in View */
    public void setRating(String rating)
    {
        RatingBar rb = (RatingBar) findViewById(R.id.custom_view_rating);
        rb.setRating(Float.parseFloat(rating));
    }
}

