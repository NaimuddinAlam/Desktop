package com.example.gulshan.newsappex2;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;

import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by gulshan on 17-11-2017.l
 */

public class VIDEONEWSADAPTER extends BaseAdapter {
    LayoutInflater inflater;
    LinearLayout show;
    NewsVideoFragment gActivity;
    List<Result> list;
    String url1=null,dis=null;
    TextView tv1,tv2;
    String urlll = "";
    //YouTubePlayerSupportFragment youTubePlayerFragment;
   // YouTubePlayer youTubePlayer;
  //  private FragmentActivity myContext;
    private YouTubePlayer YPlayer;
    private static final String DEVELOPER_KEY = "AIzaSyCluSMrNNjlwYOPhrtL0qCxsmdcyw8i9aI";
   // private static final int RECOVERY_DIALOG_REQUEST = 1;
    public VIDEONEWSADAPTER(NewsVideoFragment gActivity, List<Result> list){
        this.gActivity=gActivity;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        inflater = LayoutInflater.from(viewGroup.getContext());
        View v  = inflater.inflate(R.layout.indi_video_view,null);
        final  Result ar=list.get(i);
       //myWebView= (VideoView) v.findViewById(R.id.player);
        tv1=(TextView)v.findViewById(R.id.title);
        tv2=(TextView)v.findViewById(R.id.time);
        urlll = ar.getLink();
        Log.d("gulshanabc","directtitle"+ar.getTxt());
        Log.d("gulshanabc","direct"+ar.getLink());
        Log.e("gulshanyoutube", "Susses4!");
        tv1.setText(ar.getTitle());
        tv2.setText("Time"+currentDateTimeString);
        dis = ar.getTxt();
        Log.d("gulshan","msg3");
       final  ImageButton playPause = (ImageButton)v.findViewById(R.id.playbtn);
       final FrameLayout frameLayout  = (FrameLayout)v.findViewById(R.id.youtube_fragment);
        frameLayout.setVisibility(View.GONE);
        Picasso.with(viewGroup.getContext()).
                load("http://img.youtube.com/vi/youtube_json_news"+ar.getLink()+"/0.jpg").fit().into(playPause);
        //playPause.setImageDrawable();
        playPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                playPause.setVisibility(View.GONE);
               // Myload();
                YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
                FragmentTransaction transaction = gActivity.getChildFragmentManager().beginTransaction();
                transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
                Log.d("gulshanabc","msg2");
                youTubePlayerFragment.initialize(DEVELOPER_KEY, new OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                        if (!b) {
                            Log.d("gulshanabc","msg1");
                            YPlayer = youTubePlayer;
                            //  YPlayer.setFullscreen(true);
                            Log.d("abcdef",urlll);
                            Log.d("abcdef",ar.getLink());
                            YPlayer.loadVideo(""+ar.getLink());
                            YPlayer.play();
                            // YPlayer.cueVideo(urlll);
                        }
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                        // TODO Auto-generated method stub
                        Log.d("gulshanabc","Error");
                    }


                });


                Log.d("gulshanabc","msg5");
                frameLayout.setVisibility(View.VISIBLE);
            }
            });
        return v;
 }
public  void Myload(){
    YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
    FragmentTransaction transaction = gActivity.getChildFragmentManager().beginTransaction();
    transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
    Log.d("gulshanabc","msg2");
    youTubePlayerFragment.initialize(DEVELOPER_KEY, new OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
            if (!b) {
                Log.d("gulshanabc","msg1");
                YPlayer = youTubePlayer;
                //  YPlayer.setFullscreen(true);
                Log.d("gulshanabc",urlll);
              YPlayer.cueVideo(urlll);

                // YPlayer.cueVideo(urlll);
                 youTubePlayer.play();
            }
        }
        @Override
        public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
            // TODO Auto-generated method stub
            Log.d("gulshanabc","Error");
        }
    });
    Log.d("gulshanabc","msg5");
}
}