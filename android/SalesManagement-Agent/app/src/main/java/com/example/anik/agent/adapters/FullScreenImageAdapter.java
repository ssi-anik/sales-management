package com.example.anik.agent.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.anik.agent.R;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.httpService.ImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 14-Aug-15, 014.
 */
public class FullScreenImageAdapter extends PagerAdapter {

    private Activity activity;
    private List<String> imageUrls = new ArrayList<>();
    private LayoutInflater inflater;

    public FullScreenImageAdapter(Activity activity, List<String> imageUrls) {
        this.activity = activity;
        this.imageUrls.addAll(imageUrls);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        ImageView imageView;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_full_screen_image_view_adapter, viewGroup, false);

        imageView = (ImageView) viewLayout.findViewById(R.id.fullScreenImageView);
        String productImageUrl = imageUrls.get(position);
        final String image_name = productImageUrl.substring(productImageUrl.lastIndexOf("/") + 1);

        File file = new File(AppStorage.getFilesDirectory(), image_name);
        if (file.exists()) {
            AppHelper.setImageToImageView(imageView, file);
        } else {
            ImageDownloader.pushJob(productImageUrl, imageView).execute();
        }

        /*BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
        imageView.setImageBitmap(bitmap);*/

        ((ViewPager) viewGroup).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int position, Object object) {
        ((ViewPager) viewGroup).removeView((View) object);
    }
}
