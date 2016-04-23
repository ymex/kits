package cn.ymex.cocccute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ymex.cocccute.entity.Student;
import cn.ymex.cute.log.L;
import cn.ymex.cute.log.LPrinter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Student student = new Student("ymex",30,"red");
        L.setPrinter(new LPrinter());
        L.d(student);
        student.setAge(24);
        L.p(student);
    }
}
