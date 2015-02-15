package com.eiffelyk.www.sharedpreferencesavabean;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * sharedpreference中存储对象实例。同时提供一个可以sharedpreference自己封装的工具类
 * Create by 馋猫 at 2015年2月15日17:43:02
 */
public class MainActivity extends Activity {
    private TextView textView;
    private MyBean myBean;
    private MyBean myBean2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        myBean = new MyBean(1, "a1", "", "http://gdown.baidu.com/data/wisegame/4f9b25fb0e093ac6/QQ_220.apk", 11);
        textView.setText("存储前==" + myBean.toString());
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveGridViewBean(gridViewBean);
                Shared.getInstance(MainActivity.this).putExtra("key", myBean);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBean2 = (MyBean) Shared.getInstance(MainActivity.this).getObj("key");
                textView.setText("读取后"+ myBean2.toString());
            }
        });
    }

    /**
     * 存储的基本原理就是将object转成base64后获取String之后进行存储
     * @param bean 保存的对象
     */
    public void saveGridViewBean(MyBean bean) {
        try {
            SharedPreferences preferences = getSharedPreferences("base64",MODE_PRIVATE);
            // 创建字节输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(bean);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encodeBase64(baos
                    .toByteArray()));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("bean", oAuth_Base64);
            editor.commit();
        } catch (IOException e) {
        }
        Log.i("馋猫", "存储成功");
    }

    /**
     * 获取的原理是存储的逆向原理，就是将当时存储的base64后的String进行反解，得到最终的Objet
     * @return 对应的bean
     */
    public MyBean readGridViewBean() {
        SharedPreferences preferences = getSharedPreferences("base64",MODE_PRIVATE);
        String productBase64 = preferences.getString("bean", "");
        //读取字节
        byte[] base64 = Base64.decodeBase64(productBase64.getBytes());
        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            try {
                //读取对象
                return (MyBean) bis.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
