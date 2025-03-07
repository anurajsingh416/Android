package com.example.database;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText empId,empName, empAddr, empPhone, empSalary, empDept;
    private EditText deptNo, deptName, deptLocation;
    private EditText deleteDeptName;
    private Button insertEmpBtn, insertDeptBtn, deleteEmpBtn, displayBtn,displayDept;
    private Database myDb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new Database(this);

        // Employee Fields
        empId = findViewById(R.id.emp_id);
        empName = findViewById(R.id.emp_name);
        empAddr = findViewById(R.id.emp_address);
        empPhone = findViewById(R.id.emp_phone);
        empSalary = findViewById(R.id.emp_salary);
        empDept = findViewById(R.id.emp_dept);

        // Department Fields

        deptNo = findViewById(R.id.dept_no);
        deptName = findViewById(R.id.dept_name);
        deptLocation = findViewById(R.id.dept_location);

        // Delete Employees by Dept Name
        deleteDeptName = findViewById(R.id.delete_dept_name);

        // Buttons
        insertEmpBtn = findViewById(R.id.insert_emp);
        insertDeptBtn = findViewById(R.id.insert_dept);
        deleteEmpBtn = findViewById(R.id.delete_emp);
        displayBtn = findViewById(R.id.display_emp);
        displayDept = findViewById(R.id.displayDept);

        displayDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res2 = myDb.getAllData();
                if (res2.getCount() == 0) {
                    showMsg("Data", "No Data Found");
                } else {
                    showMsg("Data", "Data Found");
                }
                StringBuffer buffer = new StringBuffer();
                while (res2.moveToNext()) {
                    buffer.append("Dept No: ").append( res2.getString(0)).append("\n");
                    buffer.append("Dept Name: ").append( res2.getString(1)).append("\n");
                    buffer.append("Dept Location: ").append( res2.getString(2)).append("\n");
                }
                showMsg("Data", buffer.toString());
            }
        });
        // Insert Department
        insertDeptBtn.setOnClickListener(v -> {
            boolean isInserted = myDb.insertDepartmentData(
                    Integer.parseInt(deptNo.getText().toString()),
                    deptName.getText().toString(),
                    deptLocation.getText().toString());

            if (isInserted)
                showToast("Department Added");
            else
                showToast("Error Adding Department");
        });

        // Insert Employee
        insertEmpBtn.setOnClickListener(v -> {
            boolean isInserted = myDb.insertData(
                    Integer.parseInt(empId.getText().toString()),
                    empName.getText().toString(),
                    empAddr.getText().toString(),
                    empPhone.getText().toString(),
                    Integer.parseInt(empDept.getText().toString()));

            if (isInserted)
                showToast("Employee Added");
            else
                showToast("Error Adding Employee");
        });

        // Delete Employees by Department Name
        deleteEmpBtn.setOnClickListener(v -> {
            boolean isDeleted = myDb.deleteEmployees(deleteDeptName.getText().toString());
            if (isDeleted)
                showToast("Employees Deleted");
            else
                showToast("Department Not Found");
        });

        displayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    showMsg("Data", "No Data Found");
                } else {
                    showMsg("Data", "Data Found");
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Emp Id: ").append( res.getString(0)).append("\n");
                    buffer.append("Emp Name: ").append( res.getString(1)).append("\n");
                    buffer.append("Emp Address: ").append( res.getString(2)).append("\n");
                    buffer.append("Emp Phone: ").append( res.getString(3)).append("\n");
                }
                showMsg("Data", buffer.toString());
            }
        });
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void showMsg(String title,String info){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(info)
                .setPositiveButton("Ok",(dialog,which)->dialog.dismiss())
                .show();
    }
}
