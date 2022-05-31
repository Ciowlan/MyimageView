package tw.edu.pu.s11004304.myimageview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager vp_pager;
    int prePosition;
    LinearLayout ll_container;
    ArrayList<ImageView> al;

    //private Handler handler = new Handler();
//private ImageView imageView;
//private int images[] = {R.drawable.abc,R.drawable.mysta,R.drawable.nine};
//private int index;
//private MyRunnable myRunnable = new MyRunnable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp_pager = findViewById(R.id.vp_pager);
        ll_container = findViewById(R.id.ll_container);
        initData();
//        imageView = findViewById(R.id.img);
////        ImageView img = new ImageView(this);
////        img.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.abc));
////        this.setContentView(img);
//          myRunnable.run();
        PollThread pThread = new PollThread();
        pThread.start();
    }
    class PollThread extends Thread{
        @Override
        public void run() {
            boolean poll = true;
            while (poll){

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vp_pager.setCurrentItem(vp_pager.getCurrentItem()+1);
                    }
                });

            }
        }
    }

//    class MyRunnable implements Runnable{
//        @Override
//        public void run() {
//            index++;
//            index = index%3;//讓它在3張圖片中不斷迴圈
//            imageView.setImageResource(images[index]);//把當前圖片應用到圖片當中
//            //第一要求傳入Runnable物件-----第二個是，時間。多久執行一次
//            handler.postDelayed(myRunnable,2000);
//
//            }
//        }


    private void initData() {
        MainActivity mContext = this;
        int[] i = new int[]{R.drawable.abc, R.drawable.mysta, R.drawable.nine, R.drawable.abc};
        al = new ArrayList<ImageView>();
        for (int x = 0; x < i.length; x++) {
            ImageView iv = new ImageView(mContext);
            iv.setBackgroundResource(i[x]);
            al.add(iv);
            View v = new View(mContext);
            v.setBackgroundResource(R.drawable.point_normal);
            //有多少張圖就放置幾個點
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
            layoutParams.leftMargin = 30;
            ll_container.addView(v, layoutParams);
        }
        vp_pager.setAdapter(new Myadapter());
        vp_pager.setOnPageChangeListener(new MyPageListener());
        vp_pager.setCurrentItem(al.size() * 1000);  //這個是無線輪詢的關鍵
        ll_container.getChildAt(0).setBackgroundResource(R.drawable.point_select);
        prePosition = 0;
    }


    class Myadapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE; // 要無限輪播
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int position1 = position % al.size();
            ImageView imageView = al.get(position1);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class MyPageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int newPosition = position % al.size();
            ll_container.getChildAt(newPosition).setBackgroundResource(R.drawable.point_select);
            ll_container.getChildAt(prePosition).setBackgroundResource(R.drawable.point_normal);
            prePosition=newPosition;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}

