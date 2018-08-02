package me.papercom.moviedb.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import me.papercom.moviedb.R;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView viewPoster;
    TextView txtTitle;
    TextView txtDesc;
    TextView txtDateRelease;
    RatingBar ratingBar;
    TextView txtAgeRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        Bundle extraAdapter = getIntent().getExtras();
        viewPoster = (ImageView)findViewById(R.id.img_detail_movie);
        txtTitle = (TextView)findViewById(R.id.txt_title_detail_movie);
        txtDesc = (TextView)findViewById(R.id.txt_detail_movie);
        txtDateRelease = (TextView)findViewById(R.id.txt_date_movie);
        ratingBar=(RatingBar)findViewById(R.id.rating_detail_film);
        txtAgeRating = (TextView)findViewById(R.id.txt_age_rating_movie);

        String title = extraAdapter.getString("EXTRA_TITLE");
        String overview = extraAdapter.getString("EXTRA_OVERVIEW");
        String releaseDate = extraAdapter.getString("EXTRA_RELEASE_DATE");
        String posterUrl = extraAdapter.getString("EXTRA_POSTER_URL");
        double vote = extraAdapter.getDouble("EXTRA_VOTE",0.0);
        boolean ageRating = extraAdapter.getBoolean("EXTRA_RATING",false);
        MovieDetailActivity.this.setTitle(title);
        String ageRatings;
        if (ageRating==true){
            ageRatings = "Mature (18+)";
        }
        else{
            ageRatings ="All Ages";
        }

        String ratingString = String.valueOf(vote);
        Float ratingFloat = Float.parseFloat(ratingString);
        ratingBar.setRating(ratingFloat);
        ratingBar.setStepSize(0.1f);
        txtTitle.setText(title);
        txtDesc.setText(overview);
        txtDateRelease.setText(releaseDate);
        txtAgeRating.setText(ageRatings);
        Glide.with(this).load(posterUrl).into(viewPoster);

    }
}
