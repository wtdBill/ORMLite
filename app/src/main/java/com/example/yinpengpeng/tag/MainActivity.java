package com.example.yinpengpeng.tag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private Dao peopleDao;
    private Button tag1;
    private Button tag2;
    private Button tag3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tag1=findViewById(R.id.tag1);
        tag2=findViewById(R.id.tag2);
        tag3=findViewById(R.id.tag3);

        tag1.setTag(1);
        tag2.setTag(2);
        tag3.setTag(3);

        tag1.setOnClickListener(this);
        tag2.setOnClickListener(this);
        tag3.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch ((Integer)view.getTag()){
            case 1:
                Log.d(TAG, "click1");
                PeopleBean bean=new PeopleBean("hanmeimei","man",18);
                People people=new People(bean);
                try {
                    addData(people);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "success");
                break;
            case 2:
                Log.d(TAG, "onClick: 2");
                PeopleBean abean=new PeopleBean("hanmeimei","man",18);
                People apeople=new People(abean);
                try {
                    deleteDara(apeople);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "success");
                break;
            case 3:
                Log.d(TAG, "onClick: 3");
                try {
                    updateData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
                default:
                    break;
        }
    }

    private void addData(People people) throws SQLException {
        DatabaseHelper helper=DatabaseHelper.getInstance(MainActivity.this);
        Dao dao=helper.getDao(People.class);
        dao.create(people);
        dao.delete(people);
    }

    private List<People> deleteDara(People people)throws SQLException{
        DatabaseHelper helper=DatabaseHelper.getInstance(MainActivity.this);
        Dao dao=helper.getDao(People.class);

        List peo=dao.queryForEq("name",people.name);
        Log.d(TAG, peo.size()+"");

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
}
