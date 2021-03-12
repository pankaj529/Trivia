package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private static final String TAG ="tag" ;
    ArrayList<Question>questionArrayList = new ArrayList<>();

    private  String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";


    public List<Question> getQuestions(final AnswerListResponse callBack)
        {
            JsonArrayRequest jsonArrayRequest = new
                    JsonArrayRequest(Request.Method.GET,
                    url,
                    (JSONArray) null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    for (int i = 0; i<response.length();i++)
                                    { try {

                                        Question question = new Question();
                                        question.setAnswer(response.getJSONArray(i).get(0).toString());
                                        question.setAnswerTrue(response.getJSONArray(i).getBoolean(1) );

                                        //add question object to arrayList

                                        questionArrayList.add(question);

                                        Log.d("Hello", "onResponse: "+ question);

                                       // Log.d(TAG,"onResponse "+response.getJSONArray(i).get(0).toString());
                                        //Log.d(TAG,"onResponse2 "+response.getJSONArray(i).getBoolean(1) );

                                    } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                    if (null !=callBack) callBack.processFinished(questionArrayList);

                                    Log.d(TAG, "onResponse"+response);


                                }
                            },
                    new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


            AppController.getInstance().addToRequestQueue(jsonArrayRequest);


            return questionArrayList;
        }

}
