package com.andrei.template.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.andrei.template.BaseActivity;
import com.andrei.template.R;
import com.andrei.template.data.model.User;
import com.andrei.template.data.remote.SampleAPI;
import com.andrei.template.utils.DialogFactory;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

  @Bind(R.id.button_get_all) Button button_get_all;
  @Bind(R.id.button_delete) Button button_delete;
  @Bind(R.id.button_get_one) Button button_get_one;
  @Bind(R.id.button_post) Button button_post;
  @Bind(R.id.editText_email) EditText editText_email;
  @Bind(R.id.editText_name) EditText editText_name;
  @Bind(R.id.editText_record_id) EditText editText_record_id;
  @Bind(R.id.textView_output) TextView textView_output;
  @Bind(R.id.linlayout_main) LinearLayout linlayout_main;

  private MainActivity mContext;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    getSupportActionBar().setElevation(0);
    mContext = MainActivity.this;
  }

  @OnClick(R.id.button_get_all) public void onClick_button_get_all() {

    SampleAPI sampleAPI = SampleAPI.Factory.getInstance(mContext);

    sampleAPI.getUsers().enqueue(new Callback<List<User>>() {
      @Override public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if (response.isSuccessful()) {

          List<User> userList = response.body();
          textView_output.setText(userList.toString());
        } else {
          DialogFactory.showErrorSnackBar(mContext, linlayout_main, new Throwable("failed with error code " + response.code()));
        }
      }

      @Override public void onFailure(Call<List<User>> call, Throwable t) {
        DialogFactory.showErrorSnackBar(mContext, linlayout_main, t);
      }
    });
  }

  @OnClick(R.id.button_get_one) public void onClick_button_get_one() {

    SampleAPI sampleAPI = SampleAPI.Factory.getInstance(mContext);

    sampleAPI.getUser(editText_record_id.getText().toString().trim()).enqueue(new Callback<User>() {
      @Override public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {

          User user = response.body();
          textView_output.setText(user.toString());
        } else {
          DialogFactory.showErrorSnackBar(mContext, linlayout_main, new Throwable("failed with error code " + response.code()));
        }
      }

      @Override public void onFailure(Call<User> call, Throwable t) {
        DialogFactory.showErrorSnackBar(mContext, linlayout_main, t);
      }
    });
  }

  @OnClick(R.id.button_delete) public void onClick_button_delete() {

    SampleAPI sampleAPI = SampleAPI.Factory.getInstance(mContext);

    sampleAPI.deleteUser(editText_record_id.getText().toString().trim()).enqueue(new Callback<Object>() {
      @Override public void onResponse(Call<Object> call, Response<Object> response) {
        if (response.isSuccessful()) {

          textView_output.setText("deleted.");
        } else {
          DialogFactory.showErrorSnackBar(mContext, linlayout_main, new Throwable("failed with error code " + response.code()));
        }
      }

      @Override public void onFailure(Call<Object> call, Throwable t) {
        DialogFactory.showErrorSnackBar(mContext, linlayout_main, t);
      }
    });
  }

  @OnClick(R.id.button_post) public void onClick_button_post() {

    SampleAPI sampleAPI = SampleAPI.Factory.getInstance(mContext);

    String email = editText_email.getText().toString().trim();
    String name = editText_name.getText().toString().trim();

    sampleAPI.createUser(email, name).enqueue(new Callback<Object>() {
      @Override public void onResponse(Call<Object> call, Response<Object> response) {
        if (response.isSuccessful()) {
          textView_output.setText("created!");
        } else {
          DialogFactory.showErrorSnackBar(mContext, linlayout_main, new Throwable("failed with error code " + response.code()));
        }
      }

      @Override public void onFailure(Call<Object> call, Throwable t) {
        DialogFactory.showErrorSnackBar(mContext, linlayout_main, t);
      }
    });
  }
}


