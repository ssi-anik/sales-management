package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.helpers.Offer;
import com.example.anik.agent.httpService.ImageDownloader;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class ShowOffer extends ActionBarActivity {

    TextView offerTitle, offerDescription, offerPrice, offerPercentage, offerDateWithin, offerLink, offerYoutubeLink;
    ImageView imageViewForOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offer);
        Intent intent = getIntent();
        Offer offer = (Offer) intent.getSerializableExtra("offer");
        setTitle(offer.getTitle());

        offerTitle = (TextView) findViewById(R.id.offerTitle);
        offerDescription = (TextView) findViewById(R.id.offerDescription);
        offerPrice = (TextView) findViewById(R.id.offerPrice);
        offerPercentage = (TextView) findViewById(R.id.offerPercentage);
        offerDateWithin = (TextView) findViewById(R.id.offerDateWithin);
        offerLink = (TextView) findViewById(R.id.offerLink);
        offerYoutubeLink = (TextView) findViewById(R.id.offerYoutubeLink);
        imageViewForOffer = (ImageView) findViewById(R.id.imageViewForOffer);

        offerTitle.setText(String.format("Title: %s", offer.getTitle()));
        offerDescription.setText(String.format("Description: %s", offer.getDescription()));
        offerPrice.setText(String.format("Price: %s", offer.getPrice()));
        offerPercentage.setText(String.format("Percentage: %s", offer.getPercentage()));
        String validWithin = this.formatDate(offer.getStartDate()) + " - " + this.formatDate(offer.getEndDate());
        offerDateWithin.setText(String.format("Valid within: %s", validWithin));

        if (!offer.getLink().isEmpty()) {
            offerLink.setText(Html.fromHtml(String.format("Link: <a href=\"%s\">%s</a>", offer.getLink(), "Click here to see offer")));
            offerLink.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            offerLink.setVisibility(View.GONE);
        }
        String offerImageUrl = offer.getImageLink();
        if (!offerImageUrl.isEmpty()) {
            final String image_name = offerImageUrl.substring(offerImageUrl.lastIndexOf("/") + 1);

            File file = new File(AppStorage.getFilesDirectory(), image_name);
            if (file.exists()) {
                AppHelper.setImageToImageView(imageViewForOffer, file);
            } else {
                ImageDownloader.pushJob(offerImageUrl, imageViewForOffer).execute();
            }
        } else {
            imageViewForOffer.setVisibility(View.GONE);
        }

        if (!offer.getYoutube().isEmpty()) {
            offerYoutubeLink.setText(Html.fromHtml(String.format("Youtube Link: <a href=\"%s\">%s</a>", offer.getYoutube(), "Click here to see video")));
            offerYoutubeLink.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            offerYoutubeLink.setVisibility(View.GONE);
        }
    }

    private String formatDate(String date) {
        try {
            return new SimpleDateFormat("E MMM dd, yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid date";
        }
    }
}
