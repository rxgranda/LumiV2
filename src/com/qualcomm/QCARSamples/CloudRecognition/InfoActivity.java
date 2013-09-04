package com.qualcomm.QCARSamples.CloudRecognition;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

public class InfoActivity extends Activity {

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_info);
	        // Show the Up button in the action bar.

	    }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.info_activity, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case android.R.id.home:
	            // This ID represents the Home or Up button. In the case of this
	            // activity, the Up button is shown. Use NavUtils to allow users
	            // to navigate up one level in the application structure. For
	            // more details, see the Navigation pattern on Android Design:
	            //
	            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
	            //
	            NavUtils.navigateUpFromSameTask(this);
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	    
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


//	    /** Sets Picture Number of Ratings in View */
//	    public void setPictureRatingCount(String ratingCount)
//	    {
//	        TextView tv = (TextView) findViewById(R.id.custom_view_rating_text);
//	        tv.setText(getContext().getString(R.string.string_openParentheses)
//	                + ratingCount + getContext().getString(R.string.string_ratings)
//	                + getContext().getString(R.string.string_closeParentheses));
//	    }


	    /** Sets Picture Description in View */
//	    public void setPictureDescription(String pictureDescription)
//	    {
//	        TextView tv = (TextView) findViewById(R.id.picture_description);
//	        tv.setText(getContext().getString(R.string.string_$) + pictureDescription);
//	    }


	    /** Sets Picture in View from a bitmap */
//	    public void setCoverViewFromBitmap(Bitmap picture)
//	    {
//	        ImageView iv = (ImageView) findViewById(R.id.custom_view_book_cover);
//	        iv.setImageBitmap(picture);
//	    }


	    /** Sets Picture Rating in View */
	    public void setRating(String rating)
	    {
	        RatingBar rb = (RatingBar) findViewById(R.id.custom_view_rating);
	        rb.setRating(Float.parseFloat(rating));
	    }

}
