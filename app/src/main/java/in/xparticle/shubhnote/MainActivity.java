package in.xparticle.shubhnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.zip.Inflater;

import in.xparticle.shubhnote.Adapter.MyAdapter;
import in.xparticle.shubhnote.Interfacee.CheckboxInterface;
import in.xparticle.shubhnote.Interfacee.DeleteInterface;
import in.xparticle.shubhnote.Model.ReModel;

public class MainActivity extends AppCompatActivity implements CheckboxInterface, DeleteInterface {

    FloatingActionButton floatingActionButton;
    DataBaseHelper dataBaseHelper;
    RecyclerView mRecyclerView;
    MyAdapter myAdapter;
    ArrayList<ReModel>mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList= new ArrayList<>();
        mRecyclerView=findViewById(R.id.recycler);
        myAdapter= new MyAdapter(mList,this, this, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setHasFixedSize(true);

        floatingActionButton=findViewById(R.id.floating);


        dataBaseHelper=new DataBaseHelper(this);
        getAllDataFromDatabase();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                final EditText edittext = new EditText(MainActivity.this);

                alert.setTitle("Add your list");

                alert.setView(edittext);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String saveItem = edittext.getText().toString();

                        if(saveItem.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please enter Todo", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            boolean isInserted= dataBaseHelper.insertData(saveItem,"unChecked");
                            if (isInserted==true){
                                Toast.makeText(MainActivity.this, "Data save successfully", Toast.LENGTH_SHORT).show();

                           getAllDataFromDatabase();

                               }else{
                                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                            Log.e("cat", "onClick:cat "+saveItem );




                        }

                    }
                    //here
                });

                alert.show();
            }
        });
    }
    /**
     * this method is to get data from database
     *
     * */
    void getAllDataFromDatabase(){
        mList.clear();
        Cursor cursor= dataBaseHelper.getAllData();
        while (cursor.moveToNext()){
            ReModel model= new ReModel();
            model.setId(cursor.getString(0));
            model.setItem(cursor.getString(1));
            model.setCheckbox(cursor.getString(2));

            mList.add(model);

            Log.e("cat", "onClick: cat"+cursor.getString(0)+cursor.getString(1)+cursor.getString(2) );
        }
        myAdapter.notifyDataSetChanged();


    }


    @Override
    public void updateCheckBoxState(String checkboxState, String id) {
        boolean isUpdated=dataBaseHelper.updateData(id,checkboxState);

        if(isUpdated==true){
            Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "oppsss", Toast.LENGTH_SHORT).show();
        }


        Log.e("TAG", "updateCheckBoxState: "+checkboxState +" "+id);

    }

    @Override
    public void deleteTodo(String id) {
        Integer deleteRow= dataBaseHelper.deleteData(id);
        if(deleteRow>0){
            Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
            getAllDataFromDatabase();
        }else{
            Log.e("TAG", "deleteTodo: ");
            Toast.makeText(MainActivity.this, "Ooopss", Toast.LENGTH_SHORT).show();
        }
    }
}