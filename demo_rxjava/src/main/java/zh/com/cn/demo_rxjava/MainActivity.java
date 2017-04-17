package zh.com.cn.demo_rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private TextView tv;
    private ImageView iv;
    private List<StudentModel> list;
    private Call<BookBean> call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        initData();
    }

    private void initData() {
        list = new ArrayList<StudentModel>();
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("语文","大明"));
        courses.add(new Course("数学","大海"));
        courses.add(new Course("英语","大狗子"));
        list.add(new StudentModel(10, courses , "小明" ));
        list.add(new StudentModel(20, courses , "小海" ));
        list.add(new StudentModel(30, courses , "小狗子" ));


    }

    // 观察者。
    Subscriber<Course> subscriber = new Subscriber<Course>() {

        @Override
        public void onStart() {

            super.onStart();
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable throwable) {

        }
        @Override
        public void onNext(Course course) {
            Log.i(TAG,"看看我的课程名字:"+course.getName()+"....教师名字:"+course.getTearcher());
        }
    };

    Action1<Bitmap> action1 = new Action1<Bitmap>() {
        @Override
        public void call(Bitmap bitmap) {
            if (bitmap != null){
                iv.setImageBitmap(bitmap);
            }
        }
    };
    Action1<Throwable> throwableAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
        }
    };
   Action0 action0 = new Action0(){
       @Override
       public void call() {
       }
   };

    Callback<BookBean> callBack = new Callback<BookBean>() {
        @Override
        public void onResponse(Call<BookBean> call, Response<BookBean> response) {
            tv.setText( response.body().getResult().getData().get(0).getTitle());
        }
        @Override
        public void onFailure(Call<BookBean> call, Throwable throwable) {

        }
    };
    public void onClick(View view){
        final int imgId = R.mipmap.ic_launcher;
        // 创建被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgId);
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(action1, throwableAction,action0);


        Subscription subscribe = Observable.from(list).flatMap(new Func1<StudentModel, Observable<Course>>() {
            @Override
            public Observable<Course> call(StudentModel studentModel) {
                List<Course> l = studentModel.getList();
                return Observable.from(l);
            }
        }).subscribe(subscriber);


        call =  NetWork.getBookAPI().getBook("95620aad7c84cc8cb16cf9a5954927d2", 1, 1);
        call.enqueue(new Callback<BookBean>() {
            @Override
            public void onResponse(Call<BookBean> call, Response<BookBean> response) {

                tv.setText(response.body().getResult().getData().get(0).getTitle());
            }

            @Override
            public void onFailure(Call<BookBean> call, Throwable throwable) {

            }
        });
    }
}
