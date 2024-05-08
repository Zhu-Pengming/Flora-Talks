package com.example.npm;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private Fragment ChatFragment = null;// 用于显示存档界面
    private Fragment BlogFragment = null;// 用于显示关于我们界面
    private Fragment MyFragment = null;// 用于显示隐私条款界面

    private View ChatLayout = null;// 存档显示布局
    private View BlogLayout = null;// 关于我们显示布局
    private View MyLayout = null;// 隐私条款显示布局

    /*声明组件变量*/
    private ImageView ChatImg = null;
    private ImageView BlogImg = null;
    private ImageView MyImg = null;

    private TextView ChatText = null;
    private TextView BlogText = null;
    private TextView MyText = null;


    private FragmentManager fragmentManager = null;// 用于对Fragment进行管理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化布局元素
        initViews();
        fragmentManager = getFragmentManager();//用于对Fragment进行管理
        // 设置默认的显示界面
        setTabSelection(0);


    }
    /**
     * 在这里面获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件
     */
    @SuppressLint({"NewApi", "ResourceType"})
    public void initViews() {
        fragmentManager = getFragmentManager(); // 使用正确的 FragmentManager
        ChatLayout = findViewById(R.id.chat_layout);
        BlogLayout = findViewById(R.id.blog_layout);
        MyLayout = findViewById(R.id.my_layout);

        ChatImg = findViewById(R.drawable.ic_chat);
        BlogImg = findViewById(R.drawable.ic_blog);
        MyImg = findViewById(R.drawable.ic_my);

        ChatText = findViewById(R.id.chat_text);
        BlogText = findViewById(R.id.blog_text);
        MyText = findViewById(R.id.my_text);


        // 处理点击事件
        ChatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(0); // 当点击了archive时，选中第1个tab
            }
        });


        BlogLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(1); // 当点击了about_us时，选中第3个tab
            }
        });

        MyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(2); // 当点击了privacy时，选中第4个tab
            }
        });




    }
    /**
     * 根据传入的index参数来设置选中的tab页 每个tab页对应的下标。
     */
    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // 首先隐藏所有的Fragment
        hideFragments(transaction);

        switch (index) {
            case 0:
                ChatText.setTextColor(Color.parseColor("#B3EE3A"));
                if (ChatFragment == null) {
                    ChatFragment = new ChatFragment();
                    transaction.replace(R.id.container_chat, ChatFragment);
                } else {
                    transaction.show(ChatFragment);
                }
                break;
            case 1:
                BlogText.setTextColor(Color.parseColor("#B3EE3A"));
                if (BlogFragment== null) {
                    BlogFragment = new BlogFragment();
                    transaction.add(R.id.container_blog, BlogFragment); // 添加archiveFragment到事务中
                } else {
                    transaction.show(BlogFragment);
                }
                break;
            case 2:
                MyText.setTextColor(Color.parseColor("#B3EE3A"));
                if (MyFragment == null) {
                    MyFragment = new MyFragment();
                    transaction.replace(R.id.container_my, MyFragment);
                } else {
                    transaction.show(MyFragment);
                }
                break;
        }

        transaction.commit();
    }


    /**
     * 清除掉所有的选中状态
     */
    private void clearSelection() {

        ChatText.setTextColor(Color.parseColor("#82858b"));
        //?
        BlogText.setTextColor(Color.parseColor("#82858b"));

        MyText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都设置为隐藏状态 用于对Fragment执行操作的事务
     */
    @SuppressLint("NewApi")
    private void hideFragments(FragmentTransaction transaction) {
        if (ChatFragment != null) {
            transaction.hide(ChatFragment);
        }
        if (BlogFragment != null) {
            transaction.hide(BlogFragment);
        }
        if (MyFragment != null) {
            transaction.hide(MyFragment);
        }
    }


}