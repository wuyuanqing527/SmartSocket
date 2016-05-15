package com.wuyuanqing.smartsocket.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.wuyuanqing.smartsocket.R;
import com.wuyuanqing.smartsocket.constant.SmartSocketUrl;
import com.wuyuanqing.smartsocket.fragment.ControlFragment;
import com.wuyuanqing.smartsocket.model.ResLogin;
import com.wuyuanqing.smartsocket.model.ResSocketLsit;
import com.wuyuanqing.smartsocket.util.JsonStringUtil;
import com.wuyuanqing.smartsocket.util.OkHttpClientManager;
import com.wuyuanqing.smartsocket.widget.RoundImageViewByXfermode;

import java.lang.reflect.Method;
import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private View leftView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ListView listView;
    private RoundImageViewByXfermode headImage;
    private TextView textName;
    private ArrayList<String> drawerList = null;
    private SharedPreferences preferences;

    // public  static boolean ISLOGIN=false;//用户是否登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        //如果存有上次保存的用户名和密码，则显示数据。如果没有保存的用户名和密码，则不显示数据，提示用户登录
        if (!ISLOGIN) {
            if (!preferences.getString("username", "").equals("") && !preferences.getString("password", "").equals("")) {
                loginAndShow(preferences.getString("username", ""), preferences.getString("password", ""));

            } else {
                ISLOGIN = false;
                textName.setText("请点击头像登录");
            }

        } else {
            //headImage.setClickable(false);
            OkHttpClientManager.postAsyn(SmartSocketUrl.getSocketListUrl, new OkHttpClientManager.ResultCallback<String>() {
                @Override
                public void onError(Request request, Exception e) {
                    l("载入数据出错");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(String response) {
                    l(response);
                    initDrawerData(response);
                    setDefaultFragment(response);
                }
            }, new OkHttpClientManager.Param("", JsonStringUtil.getSocketList(preferences.getString("username", ""))));
            // l(JsonStringUtil.getSocketList("testuser1"));
        }


        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // mAnimationDrawable.stop();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //  mAnimationDrawable.start();
            }
        };
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);


    }

    private void loginAndShow(String username, String password) {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.show();
        OkHttpClientManager.postAsyn(SmartSocketUrl.loginUrl, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                if (response == null) {
                    l("服务器返回值为空");
                } else {
                    dialog.dismiss();
                    ResLogin resLogin = new Gson().fromJson(response, ResLogin.class);
                    if (resLogin.getErrorCode() == 0) {//登录成功
                        ISLOGIN = true;
                        //headImage.setClickable(false);
                        OkHttpClientManager.postAsyn(SmartSocketUrl.getSocketListUrl, new OkHttpClientManager.ResultCallback<String>() {
                            @Override
                            public void onError(Request request, Exception e) {
                                l("载入数据出错");
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(String response) {
                                l(response);
                                initDrawerData(response);
                                setDefaultFragment(response);
                            }
                        }, new OkHttpClientManager.Param("", JsonStringUtil.getSocketList(preferences.getString("username", ""))));
                        // l(JsonStringUtil.getSocketList("testuser1"));
                    } else if (resLogin.getErrorCode() == 1) {//密码错误
                        t("请检查密码");
                    } else if (resLogin.getErrorCode() == 2) {//没有用户
                        t("请检查用户名");
                    }
                }
            }
        }, new OkHttpClientManager.Param("loginAndShow", JsonStringUtil.loginStr(username, password)));


    }

    private void setDefaultFragment(String response) {
        toolbar.setTitle(drawerList.get(0));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        Fragment fragment = new ControlFragment();
        Bundle bundle = new Bundle();
        bundle.putString("socketName", drawerList.get(0));
        bundle.putString("response", response);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
    }

    private void initDrawerData(final String response) {
        ResSocketLsit socketLsit = new Gson().fromJson(response, ResSocketLsit.class);
        drawerList = new ArrayList<String>();
        // drawerList.add("添加插座");
        for (int i = 0; i < socketLsit.getSocketers().size(); i++) {
            drawerList.add(socketLsit.getSocketers().get(i).getSocketName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, drawerList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    toolbar.setTitle("添加插座");
//                    toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
//                    getFragmentManager().beginTransaction().replace(R.id.layout_content, new AddSocketFragment()).commit();
//                } else {
                toolbar.setTitle(drawerList.get(position));
                toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

                Fragment fragment = new ControlFragment();
                Bundle bundle = new Bundle();
                bundle.putString("socketName", drawerList.get(position));
                bundle.putString("response", response);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
                //    }

                drawerLayout.closeDrawer(leftView);
            }
        });
    }


    private void initView() {

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        leftView = (View) findViewById(R.id.left_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        listView = (ListView) findViewById(R.id.listview_left_menu);
        headImage = (RoundImageViewByXfermode) findViewById(R.id.image_head);
        textName = (TextView) findViewById(R.id.text_name);
        textName.setText(preferences.getString("username", "请点击头像登录"));
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        toolbar.setTitle("智能插座");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SetActivity.class));
        } else if (id == R.id.action_logout) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * *显示溢出菜单图标
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    public void l(String s) {
        Log.i("MainActivity", s);
    }
}
