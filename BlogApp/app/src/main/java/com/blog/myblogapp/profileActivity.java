package com.blog.myblogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class profileActivity extends AppCompatActivity implements BlogAdapter.OnNoteList {

    private DatabaseReference mDatabase;
    private FirebaseDatabase minstance;
    private String userId;
    private long maxid;
    SharedPref sharedPref;
    private RecyclerView mrecycler_view;
    private RecyclerView.LayoutManager mlayout_Manager;
    TextView tv;
    BlogAdapter adapter;
    ArrayList<Blog> blogArrayList;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;



    private FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tv = findViewById(R.id.tv);
        sharedPref = new SharedPref(this);




        add= findViewById(R.id.fab);

        ObjectAnimator animY = ObjectAnimator.ofFloat(add, "translationY", -150f, 10f); //btn animation
        animY.setDuration(1000);//1sec
        animY.setInterpolator(new BounceInterpolator());
        animY.setRepeatMode(ValueAnimator.REVERSE);
        animY.setRepeatCount(2);
        animY.start();


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        minstance = FirebaseDatabase.getInstance();
        mrecycler_view = findViewById(R.id.recycler_view);
        mDatabase = minstance.getReference("data_users");
        userId = mDatabase.child("Users").push().getKey();






       /* mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            maxid = dataSnapshot.getChildrenCount();
                            System.out.println("maxid = " + maxid);
                        }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileActivity.this,CUDBlog.class);

                startActivityForResult(intent,1);

            }
        });


        go_recyclerview();

    }

    private void go_recyclerview() {



        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blogArrayList = new ArrayList<Blog>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("ds.getKey() = " + ds.getKey());
                    System.out.println("ds.getChildrenCount() = " + ds.getChildrenCount());

                    for (DataSnapshot id : ds.getChildren()) {
                        System.out.println("key " + id.getKey());


                        String dbUser = id.child("title").getValue(String.class);
                        String dbHello = id.child("desc").getValue(String.class);
                        System.out.println(dbHello + "  " + dbUser);
                        Blog blog = new Blog(dbUser, dbHello);
                        blogArrayList.add(blog);

                        System.out.println("blogArrayList = " + blogArrayList.size());

                        System.out.println("array " + blogArrayList.get(0).getTitle());


                    }
                }

                mlayout_Manager = new LinearLayoutManager(profileActivity.this,LinearLayoutManager.VERTICAL,false);
                mrecycler_view.setLayoutManager(mlayout_Manager);
                adapter = new BlogAdapter(profileActivity.this,blogArrayList,profileActivity.this);
                mrecycler_view.setAdapter(adapter);

                if(blogArrayList.isEmpty()){
                    tv.setText("Add Some Blogs");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(profileActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

       // System.out.println(blogArrayList.get(0).getTitle());


       // search2();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            assert data != null;
            String title = data.getStringExtra(CUDBlog.EXTRA_TITLE);
            String desc = data.getStringExtra(CUDBlog.EXTRA_DESCRIPTION);



            Blog blog = new Blog(title,desc);

            addBlog(title,desc);

            //blogArrayList.add(blog);


            //adapter.notifyItemInserted(adapter.getItemCount());
            // adapter.notifyDataSetChanged();







            //note saved

        }
        else if(requestCode == 2 && resultCode == RESULT_OK){
            adapter.notifyDataSetChanged();
            Intent intent = getIntent();
            System.out.println(intent);

            assert data != null;
            long id = data.getLongExtra("id",-1);
            // String string = getIntent().getStringExtra(CUDBlog.EXTRA_TITLE);

            if(id == -1){
                //invalid id
                System.out.println(id);
                return;
            }
            String title = data.getStringExtra(CUDBlog.EXTRA_TITLE);
            String desc = data.getStringExtra(CUDBlog.EXTRA_DESCRIPTION);



            Blog note = new Blog(title,desc);
        //    note.setId(id);
          //  updateBlog(title,desc,id);






            //note updated
            System.out.println("2");

        }
        else{
            //note not saved
            Toast.makeText(this, "Note NOT saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void addBlog(String title, String desc){

        //Blog blog = new Blog(title,desc);
        long c = sharedPref.get_flag();

        mDatabase.child("Users").child(userId).child(String.valueOf(c)).child("title").setValue(title);
        mDatabase.child("Users").child(userId).child(String.valueOf(c)).child("desc").setValue(desc);
        mDatabase.child("Users").child(userId).child(String.valueOf(c)).child("id").setValue(c);

        sharedPref.del_flag();
        sharedPref.save_flag(c + 1);



    }

    public void updateBlog(String title, String desc, Long id){
       // if(id == mDatabase.child("Users").child(userId).child("id").getV)
        mDatabase.child("Users").child(userId).child(String.valueOf(id)).child("title").setValue(title);
        mDatabase.child("Users").child(userId).child(String.valueOf(id)).child("desc").setValue(desc);
        mDatabase.child("Users").child(userId).child(String.valueOf(id)).child("id").setValue(id);

    }

   /* public void insert(View view){
        addBlog(etTitle.getText().toString().trim() , etDesc.getText().toString().trim());

    }*/
   /* public void update(View view){
        updateBlog(etTitle.getText().toString().trim(), etDesc.getText().toString().trim());
    }
*/



    @Override
    public void OnnoteClick(Blog userClass) {
       /* Intent intent = new Intent(profileActivity.this,CUDBlog.class);
        intent.putExtra("ready_title",userClass.getTitle() );
        intent.putExtra("ready_desc",userClass.getDesc());
        intent.putExtra("id",userClass.getId());
        startActivityForResult(intent,2);*/

        Toast.makeText(this, userClass.getTitle(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mUser == null){
            Intent intent = new Intent(profileActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}