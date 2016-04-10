package com.melapelapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;
import com.melapelapp.domain.Error;
import com.melapelapp.domain.Person;
import com.melapelapp.service.StoreService;
import com.melapelapp.service.StoreServiceFactory;
import com.melapelapp.widget.CircleImageView;
import com.melapelapp.widget.PersonArrayAdapter;
import com.melapelapp.widget.PinterestView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PersonListActivity extends AppCompatActivity implements View.OnClickListener {
    String json;
    List<Person> persons;
    List<Error> errors;
    EditText txtQuery;
    Button btnRun;
    Button btnPush;
    TextView lblResult;
    RecyclerView listView;
    private RecyclerView.LayoutManager mLayoutManager; //hola como est
    PersonArrayAdapter adapter;
    PinterestView pinterestView;
    private ProgressDialog progDialog;

    private StoreService storeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storeService = StoreServiceFactory.create();

        persons = new ArrayList<Person>();

        txtQuery = (EditText) findViewById(R.id.txtQuery);
        btnRun = (Button) findViewById(R.id.btnRun);
//        btnPush = (Button) findViewById(R.id.btnPush);

        listView = (RecyclerView) findViewById(R.id.list);
        listView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        btnRun.setOnClickListener(this);
//        btnPush.setOnClickListener(this);
        initializePinterestView();
        //runQuery();
    }

    private void initializePinterestView() {
        pinterestView = (PinterestView) findViewById(R.id.item_layout);
        /**
         * add item view into pinterestView
         */
        pinterestView.addShowView(40, createChildView(R.drawable.googleplus, "")
                , createChildView(R.drawable.linkedin, "linkedin"), createChildView(R.drawable.twitter, "twitter")
                , createChildView(R.drawable.pinterest, "pinterest"));
        /**
         * add pinterestview menu and Pre click view click
         */
        pinterestView.setPinClickListener(new PinterestView.PinMenuClickListener() {

            @Override
            public void onMenuItemClick(int childAt) {
                String tips = (String) pinterestView.getChildAt(childAt).getTag();
                Toast.makeText(PersonListActivity.this, tips + " clicked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPreViewClick() {
                Toast.makeText(PersonListActivity.this, "button clicked!", Toast.LENGTH_SHORT).show();
            }
        });


//        btnRun.setOnTouchListener(new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//                pinterestView.dispatchTouchEvent(event);
//                return true;
//        }
//});

        CircleImageView imageView = (CircleImageView) findViewById(R.id.image);
        imageView.setFillColor(getResources().getColor(R.color.accent));

        runQuery("112358", "3");
    }

    public void connect(String fullUrl) {

        ServerConnector serverConnector = new ServerConnector(this, json);


        try {
            serverConnector.execute(fullUrl);
        } catch (Exception e) {
            serverConnector.cancel(true);
        }
    }


    public void updateUI(String json) {
        String result = "";
        String title = "Gatekidper";
        String message = "";
        result = getJSONResponseType(json);

        //       if(result=="records")
        //       {
        persons = getPersonsFromJSON2(json);
        adapter = new PersonArrayAdapter(persons, pinterestView);
        listView.setAdapter(adapter);
//        }
//        else {
//                if (result == "updates") {
//                        //Error error = getError()
//                        //Publich error
//                        title="number of updates :";
//                }
//                if (result == "errors") {
//                        //Error error = getError()
//                        //Publich error
//                        title="errors are :";
//                        errors = getErrorsFromJSON(json);
//                        message = errors.get(0).getCause() + errors.get(0).getQuery();
//                }
//
//                showDialog(title,message);
//        }
    }


    private ArrayList<Error> getErrorsFromJSON(String json) {
        ArrayList<Error> errors;
        errors = new ArrayList<Error>();
// Parse JSON String and load array list
        try {
            JSONObject jsonObject;
            errors.clear();

            JSONObject jsonObjects = new JSONObject(json);
            JSONArray jsonArray = jsonObjects.getJSONArray("errors");
            // Get the URL from JSON objects and store it on
            // the ArrayList


            for (Integer i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                //publishProgress(100-((int) ((i / (float) jsonArray.length()) * 100)));

                Error error;
                error = new Error();

                String cause = jsonObject.getString("@cause");
                String query = jsonObject.getString("@query");

                error.setCause(cause);
                error.setQuery(query);
                //records.add(jsonObject.toString());

                errors.add(error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return errors;

    }


    public void setProgressPercent(Integer progress) {
        String sQuery = txtQuery.getText().toString();
        txtQuery.setText(String.valueOf(progress) + " " + sQuery);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnRun:
                String storeId = txtQuery.getText().toString();
                runQuery(storeId, "3");
                break;
//        case R.id.btnPush:
//             //runPush(txtQuery.getText().toString());
//             break;
//        case R.id.txtQuery:
//        //  runQuery();
//        break;
            default:
                //delete();
                break;
        }
    }


    private void runQuery(String storeId, String limit) {
        //String domainUrl = "http://85.25.198.11:8080/gatekidper/servlet";
        String url = "http://10.0.0.15:8080/v0/queue/" + storeId + "?size=" + limit;

        //String query="select%20*%20from%20per_cat";
        //String query="storeId";

        //String query= txtQuery.getText().toString();

        // query = query.replace(" ", "%20");

        //String fullUrl = url + "storeId=" + query + "?";

        //    String fullUrl = url + "?storeId=" + query;
        //   String fullUrl = domainUrl;
        //String response = "";

        persons = storeService.fetchPersons(11235813);
        adapter = new PersonArrayAdapter(persons, pinterestView);
        listView.setAdapter(adapter);
    }

    public View createChildView(int imageId, String tip) {
        CircleImageView imageView = new CircleImageView(this);
        imageView.setBorderWidth(0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setFillColor(getResources().getColor(R.color.accent));
        imageView.setImageResource(imageId);
        //just for save Menu item tips
        imageView.setTag(tip);
        return imageView;
    }


    private String getJSONResponseType(String json) {
        String result = "";

        if (json.contains("records")) {
            result = "records";
            return result;
        }

        if (json.contains("updates")) {
            result = "updates";
            return result;
            //{"records":[{"cause":"Query is not safe it contains DROP,CREATE","query":"drop * from per_cat"}]}
        }

        if (json.contains("errors")) {
            result = "errors";
            return result;
            //{"records":[{"cause":"Query is not safe it contains DROP,CREATE","query":"drop * from per_cat"}]}
        }


        return result;
    }
//        private ArrayList<Person> getPersonsFromJSON(String json)
//        {
//
//                persons = new ArrayList<Person>();
//// Parse JSON String and load array list
//                try {
//                        JSONObject jsonObject;
//                        persons.clear();
//
//                        JSONObject jsonObjects = new JSONObject(json);
//                        JSONArray jsonArray = jsonObjects.getJSONArray("records");
//                        // Get the URL from JSON objects and store it on
//                        // the ArrayList
//
//
//
//                        for (Integer i = 0; i < jsonArray.length(); i++) {
//                                jsonObject = jsonArray.getJSONObject(i);
//
//                                //publishProgress(100-((int) ((i / (float) jsonArray.length()) * 100)));
//
//                                Person person;
//                                person = new Person();
//
////                                String per_cve = jsonObject.getString("per_cve");
////                                String per_nom = jsonObject.getString("per_nom");
////                                String per_ap1 = jsonObject.getString("per_ap1");
////                                String per_ap2 = jsonObject.getString("per_ap2");
//
//                                String id = jsonObject.getString("id");
//                                String name = jsonObject.getString("name");
//                                String lastName = jsonObject.getString("lastName");
//
//
//                                person.setId(id);
//                                person.setName(name);
//                                person.setLastName(lastName);
//
//                                //records.add(jsonObject.toString());
//
//                                persons.add(person);
//                        }
//                } catch (JSONException e) {
//                        e.printStackTrace();
//                }
//
//                return persons;
//
//        }


    private List<Person> getPersonsFromJSON2(String json) {

        persons = new ArrayList<>();
// Parse JSON String and load array list
        try {
            JSONObject jsonObject;
            persons.clear();

            JSONObject jsonObjects = new JSONObject(json);
            JSONArray jsonArray = jsonObjects.getJSONArray("clients");

            //JSONArray jsonArray = new JSONArray(json);

            for (Integer i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                //publishProgress(100-((int) ((i / (float) jsonArray.length()) * 100)));

                Person person;
                person = new Person();

//                                String per_cve = jsonObject.getString("per_cve");
//                                String per_nom = jsonObject.getString("per_nom");
//                                String per_ap1 = jsonObject.getString("per_ap1");
//                                String per_ap2 = jsonObject.getString("per_ap2");

                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String lastName = jsonObject.getString("lastName");


                person.setId(id);
                person.setName(name);
                person.setLastName(lastName);

                //records.add(jsonObject.toString());

                persons.add(person);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return persons;

    }


    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    private void showDialog(String title, String message) {
        alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        //MainActivity.this.finish();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


}
