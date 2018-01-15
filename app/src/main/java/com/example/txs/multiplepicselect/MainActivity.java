package com.example.txs.multiplepicselect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    /**
     * 网格布局的　Recyclerview
     */
    private RecyclerView mRcv;
    /**
     * 显示所保存数据的按钮
     */
    private Button mBtn;
    /**
     * recyclerview 的适配器
     */
    private RecAdapter adapter;
    /**
     * 实际开发中用来保存联网获取的图片数据
     */
    private List<String> list;
    /**
     * 是否显示ｃｈｅｃｋｂｏｘ
     */
    private boolean isShowCheck;
    /**
     * 记录选中的ｃｈｅｃｋｂｏｘ
     */
    private List<String> checkList;
    /**
     * 屏幕宽度
     */
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        //每行3个的Recyclerview网格布局
        mRcv.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        refreshUI();
        initListener();
    }

    /**
     * 适配器
     */
    private void refreshUI() {
        if (adapter == null) {
            adapter = new RecAdapter(this, list, screenWidth);
            mRcv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
    /**
     * 点击监听
     */
    private void initListener() {
        //button的点击
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, checkList.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //ａｄａｐｔｅｒ中定义的监听事件　可以根据isShowCheck判断当前状态，设置点击Ｉｔｅｍ之后是查看大图（未实现　跳到下一个Ａｃｔｉｖｉｔｙ即可）还是选中ｃｈｅｃｋｂｏｘ*/
        adapter.setOnItemClickListener(new RecAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                if (checkList.contains(String.valueOf(pos))) {
                    checkList.remove(String.valueOf(pos));
                } else {
                    checkList.add(String.valueOf(pos));
                }
            }
            @Override
            public boolean onLongClick(View view, int pos) {
                if (isShowCheck) {
                    mBtn.setVisibility(View.GONE);
                    adapter.setShowCheckBox(false);
                    refreshUI();
                    checkList.clear();
                } else {
                    adapter.setShowCheckBox(true);
                    refreshUI();
                    mBtn.setVisibility(View.VISIBLE);
                }
                isShowCheck = !isShowCheck;
                return false;
            }

        });

    }
    private void initData() {
        list = new ArrayList<>();
        checkList = new ArrayList<>();
        list.add("1");
        //屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
    }
    private void initView() {
        mRcv = (RecyclerView) findViewById(R.id.rcv);
        mBtn = (Button) findViewById(R.id.btn);
    }
}
