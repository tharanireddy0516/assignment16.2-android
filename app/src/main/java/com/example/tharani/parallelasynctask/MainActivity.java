package com.example.tharani.parallelasynctask;
/*import is libraries imported for writing the code
* AppCompatActivity is base class for activities
* Bundle handles the orientation of the activity
*/
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    //declaring variables
    Button buttonStart;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    MyTask mytask1, mytask2, mytask3, mytask4;

    @Override
    /*onCreate is the first method in the life cycle of an activity
     savedInstance passes data to super class,data is pull to store state of application
   * setContentView is used to set layout for the activity
   *R is a resource and it is auto generate file
   * activity_main assign an integer value*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting up UI components
        progressBar1 =  findViewById(R.id.progressbar1);
        progressBar2 = findViewById(R.id.progressbar2);
        progressBar3 = findViewById(R.id.progressbar3);
        progressBar4 =  findViewById(R.id.progressbar4);
        buttonStart = findViewById(R.id.start);

        buttonStart.setOnClickListener(new View.OnClickListener() {//here we are creating the onclick method
            @Override
            public void onClick(View view) {
                //creating object of myTask by using new
                mytask1 = new MyTask(progressBar1);
                //passing parameter mytask in  StartAsyncTaskInParallel
                StartAsyncTaskInParallel(mytask1);
                mytask2 = new MyTask(progressBar2);
                StartAsyncTaskInParallel(mytask2);
                mytask3 = new MyTask(progressBar3);
                StartAsyncTaskInParallel(mytask3);
                mytask4 = new MyTask(progressBar4);
                StartAsyncTaskInParallel(mytask4);

            }
        });

    }
    //method StartAsyncTaskInParallel
    private void StartAsyncTaskInParallel(MyTask task) {
        //Applying condition for asyncTask
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //if statement decides whether this statement will get executes or not
        else
            task.execute();
    }
  /*  creating method MyTask extending AsyncTask
  * AsyncTask is designed to be a helper class around Thread and Handler and does not constitute a generic threading framework*/
    public class MyTask extends AsyncTask<Void, Integer, Void> {
        ProgressBar mprogressbar;
        //creating constructor,constructor is used to construct objects
        public MyTask(ProgressBar mprogressbar) {
            this.mprogressbar = mprogressbar;
        }
        @Override
        //Creating method doInBackground()
        protected Void doInBackground(Void... voids) {
            //Applying loop for progressbar
            for(int i=0; i<100; i++){//using for loop
                try {// The try block contains set of statements where an exception can occur.
                    //applying sleep for some milliSecond
                    Thread.sleep(100);//Thread.sleep() is used to pause the main thread execution for seconds
                    /*Individual programs appear to do multiple task at same time,each sub task is called thread*/
                } catch (InterruptedException e) { //A try block is always followed by a catch block, which handles the exception
                    e.printStackTrace();//printStackTrace used to print the description
                }
                //publishing progress
                publishProgress(i);
            }

            return null;//returns null
        }
        /*created method onPreExecute
        * onPreExecute() : This method contains the code which is executed before the background processing starts*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        /*created method onPostExecute
        * onPostExecute() : This method is called after doInBackground method completes processing. Result from doInBackground is passed to this method*/
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
        /*created method  onProgressUpdate
        * onProgressUpdate() : This method receives progress updates from doInBackground method, which is published via publishProgress method
        * This method can use this progress update to update the UI thread*/
        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];//initializing values
            //setting progress of Mytask
            mprogressbar.setProgress(progress);//storing progress using set method
            super.onProgressUpdate(values[0]);//here super calling the parent constructor
            //The super keyword in java is a reference variable which is used to refer immediate parent class object
        }
    }
}
