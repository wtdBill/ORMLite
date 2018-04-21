package com.example.yinpengpeng.tag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private Context mContext;
    private TextView show;
    private Button tag1;
    private Button tag2;
    private Button tag3;

    private String name="a";
    private int age=1;
    private String sex="man";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        tag1=findViewById(R.id.tag1);
        tag2=findViewById(R.id.tag2);
        tag3=findViewById(R.id.tag3);
        show=findViewById(R.id.show);

        tag1.setTag(1);
        tag2.setTag(2);
        tag3.setTag(3);

        tag1.setOnClickListener(this);
        tag2.setOnClickListener(this);
        tag3.setOnClickListener(this);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch ((Integer)view.getTag()){
            case 1:
               try {
                   name=name+"a";
                   age++;
                   People people=new People(name,sex,age);
                   addData(people);
                   show.setText("添加成功"+"name"+people.name+"sex"+people.age);
               }catch (SQLException e){
                   e.printStackTrace();
               }
                break;
            case 3:
                try {
                    People people=new People(name,sex,age);
                    deleteDara(people);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    queryData("man");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
                default:
                    break;
        }
    }

    private void addData(People people) throws SQLException {
        DatabaseHelper helper=DatabaseHelper.getInstance(this);
        Dao dao=helper.getDao(People.class);
        if (dao.queryForEq("name",people.name).size()==0){
            dao.create(people);
        }else {
            Toast.makeText(this,"数据已存在",Toast.LENGTH_SHORT).show();
        }
    }

    private List<People> deleteDara(People people)throws SQLException{
        DatabaseHelper helper=DatabaseHelper.getInstance(this);
        Dao dao=helper.getDao(People.class);
        List<People>peopleList=dao.queryForEq("name",people.name);
        if (peopleList.size()>0){
            for (People people1:peopleList){
                dao.delete(peopleList);
                show.setText("共有"+peopleList.size()+"项");
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"没有数据",Toast.LENGTH_SHORT).show();
        }

        return dao.queryForEq("name","hanmeimei");
    }

    private void updateData() throws SQLException {
        DatabaseHelper helper=DatabaseHelper.getInstance(MainActivity.this);
        Dao dao=helper.getDao(People.class);
        CloseableIterator<People>iterator=dao.closeableIterator();
        QueryBuilder builder=dao.queryBuilder();
        List<People>list=builder.where().eq("age",18).query();


        Log.d(TAG, list.size()+"");
        Log.d(TAG, list.get(1).name);
    }

    private void queryData(String sex)throws SQLException{
        try {
            DatabaseHelper helper=DatabaseHelper.getInstance(this);
            Dao dao=helper.getDao(People.class);
            int size=dao.queryForEq("sex",sex).size();
            show.setText("共有"+size+"项");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
