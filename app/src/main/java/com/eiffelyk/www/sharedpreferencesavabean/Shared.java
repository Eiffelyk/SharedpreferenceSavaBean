/**
 * @FileName: Shared.java
 * @Package com.application.base
 * @author 馋猫   Email： eiffelyk@gmail.com
 * @date 2014年9月6日 下午3:47:36  
 */
package com.eiffelyk.www.sharedpreferencesavabean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * SharedPreference存储帮助类
 * 注意：base64部分需要  在build.gradle中加入 compile 'commons-codec:commons-codec:1.10'  这个库
 * create by 馋猫 at 2015年2月13日10:59:18
 */
public class Shared {
    private static final int STRING_TYPE = 1;
    private static final int INT_TYPE = 2;
    private static final int BOOLEAN_TYPE = 3;
    private static final int FLOAT_TYPE = 4;
    private static final int LONG_TYPE = 5;
    private static final int OBJ_TYPE = 6;
    private static String USER_TABLE_NAME = "shareprefrences.xml";
    private static Shared instance;
    private SharedPreferences sharedPreferences;


    private Shared(Context context) {
        initSpManager(context);
    }

    public static Shared getInstance(Context context) {
        synchronized (Shared.class) {
            if (instance == null) {
                synchronized (Shared.class) {
                    if (instance == null) {
                        instance = new Shared(context);
                    }
                }
            }
            
        }
        return instance;
    }

    private void initSpManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_TABLE_NAME, Context.MODE_PRIVATE);
    }

    public void putExtra(String key, Object content) {
        remove(key);//删除是为了保证原有数据类型和新类型不一致，但是key一致的情况，造成存储失败
        Editor editor = sharedPreferences.edit();
        if (content instanceof String) {
            insertConfigure(editor, key, STRING_TYPE, content);
        } else if (content instanceof Integer) {
            insertConfigure(editor, key, INT_TYPE, content);
        } else if (content instanceof Boolean) {
            insertConfigure(editor, key, BOOLEAN_TYPE, content);
        } else if (content instanceof Float) {
            insertConfigure(editor, key, FLOAT_TYPE, content);
        } else if (content instanceof Long) {
            insertConfigure(editor, key, LONG_TYPE, content);
        }else{
            insertConfigure(editor, key, OBJ_TYPE, content);
        }
        editor.commit();
    }


    public void putExtras(HashMap<String, Object> map) {
        Editor editor = sharedPreferences.edit();
        for (Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object content = entry.getValue();
            putExtra(key, content);
        }
        editor.commit();
    }

    public void putExtraObj(String key,Object object) {
        try {
            Editor editor = sharedPreferences.edit();
            // 创建字节输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(object);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encodeBase64(baos.toByteArray()));
            remove(key);//放到这里 防止提前删除，结果后边在Object转化成String失败，还错误的将原有数据删除了
            editor.putString(key, oAuth_Base64);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Object getObj(String key){
       String string = getString(key);
        try {
            //读取字节
            byte[] base64 = Base64.decodeBase64(string.getBytes());
            //封装到字节流
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
                //读取对象
            return  (Object) bis.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void insertConfigure(Editor editor, String key, int type, Object content) {
        switch (type) {
            case STRING_TYPE:
                editor.putString(key, String.valueOf(content));
                break;
            case BOOLEAN_TYPE:
                editor.putBoolean(key, Boolean.parseBoolean(String.valueOf(content)));
                break;
            case INT_TYPE:
                editor.putInt(key, Integer.parseInt(String.valueOf(content)));
                break;
            case FLOAT_TYPE:
                editor.putFloat(key, Float.parseFloat(String.valueOf(content)));
                break;
            case LONG_TYPE:
                editor.putLong(key, Long.parseLong(String.valueOf(content)));
                break;
            case OBJ_TYPE:
                try {
                    // 创建字节输出流
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    // 创建对象输出流，并封装字节流
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    // 将对象写入字节流
                    oos.writeObject(content);
                    // 将字节流编码成base64的字符窜
                    String oAuth_Base64 = new String(Base64.encodeBase64(baos.toByteArray()));
                    editor.putString(key, oAuth_Base64);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }


    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }


    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public Float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0);
    }

    public Long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }


    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }


    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public Float getFloat(String key, Float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public Long getLong(String key, Long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }


    public Map<String, ?> getAllSharedPreferences() {
        return sharedPreferences.getAll();
    }


    public void clearAllSharedPreferences() {
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    public void remove(String key) {
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
