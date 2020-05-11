package com.app.erudite.administrator.eruditevalley.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.erudite.administrator.eruditevalley.R;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    public static  String Content="content";
    private EditText editviewEdit;
    private Button editviewOk;
    private Button navBackBtn;
    private  int request=2;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editviewEdit = (EditText) findViewById(R.id.editview_edit);
        editviewOk = (Button) findViewById(R.id.editview_ok);
        navBackBtn = (Button) findViewById(R.id.nav_back_btn);
        editviewOk.setOnClickListener(this);
        navBackBtn.setOnClickListener(this);
        content=getIntent().getStringExtra("content");
        editviewEdit.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editview_ok:
                Intent intent=new Intent();
                intent.putExtra(Content,editviewEdit.getText().toString().trim());
                setResult(2,intent);
                finish();
                break;
            case R.id.nav_back_btn:
                finish();
                break;
        }
    }
}
