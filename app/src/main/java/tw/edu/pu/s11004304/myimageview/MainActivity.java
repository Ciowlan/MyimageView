package tw.edu.pu.s11004304.myimageview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

private Handler handler = new Handler();
private ImageView imageView;
private int images[] = {R.drawable.abc,R.drawable.mysta,R.drawable.nine};
private int index;
private MyRunnable myRunnable = new MyRunnable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.img);
//        ImageView img = new ImageView(this);
//        img.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.abc));
//        this.setContentView(img);
          myRunnable.run();
    }


    class MyRunnable implements Runnable{
        @Override
        public void run() {
            index++;
            index = index%3;//讓它在3張圖片中不斷迴圈
            imageView.setImageResource(images[index]);//把當前圖片應用到圖片當中
            //第一要求傳入Runnable物件-----第二個是，時間。多久執行一次
            handler.postDelayed(myRunnable,2000);

            }
        }
}