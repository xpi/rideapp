package com.example.riderapp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import com.example.riderapp.R;
import com.example.riderapp.data.AppConfig;
import com.example.riderapp.data.UserDataSource;
import com.example.riderapp.model.Client;
import com.example.riderapp.webservice.WsClient;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button login_btn;

	WsClient wsClient;
	ImageView imageView;

	String imageToBeUpload;
	EditText et_email, et_pwd;
	String email, password;
	UserDataSource userDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout_normal);
		userDataSource = new UserDataSource(LoginActivity.this);

		login_btn = (Button) findViewById(R.id.btn_login);
		et_email = (EditText) findViewById(R.id.text_username);
		et_pwd = (EditText) findViewById(R.id.text_password);
		imageView = (ImageView) findViewById(R.id.login_logo);
		wsClient = new WsClient(AppConfig.WSDL, AppConfig.WSDL_NAMESPACE);

		Client client = new Client();
		client.setEmail("397110686@qq.com");
		client.setId(1);
		client.setName("hhehe");
		userDataSource.savelogin(client);
		login_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				email = et_email.getText().toString() + "";
				password = et_pwd.getText().toString() + "";
				LoginTask loginTask = new LoginTask();
				loginTask.execute(email, password);
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.bar_regist) {

			Intent intent = new Intent(this, RegistActivity.class);
			startActivity(intent);
		}
		if (id == R.id.bar_change_pwd) {
			Intent intent = new Intent(this, ChangePwdActivity.class);
			startActivity(intent);
		}
		if (id == R.id.bar_findpwd) {
			Intent intent = new Intent(this, FindPwdActivity.class);
			startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login_menu, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap bitmap = (Bitmap) extras.get("data");
			imageView.setImageBitmap(bitmap);// 将图片显示在ImageView里

			File myDir = new File(Environment.getExternalStorageDirectory()
					+ "/savedimages");
			myDir.mkdirs();
			String fname = "Image-" + System.currentTimeMillis() + ".jpg";
			File file = new File(myDir, fname);
			FileOutputStream out;
			try {
				out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				imageToBeUpload = file.getPath();
				Toast.makeText(LoginActivity.this, file.getPath(),
						Toast.LENGTH_LONG).show();
				new LoginTask().execute();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class LoginTask extends AsyncTask<String, SoapObject, SoapObject> {

		@Override
		protected SoapObject doInBackground(String... params) {

			return userDataSource.login(params[0], params[1]);

		}

		@Override
		protected void onPostExecute(SoapObject result) {
			SoapPrimitive loginState = (SoapPrimitive) result
					.getProperty("loginState");

			boolean isVaild = loginState == null;
			if (!isVaild) {
				et_email.setText("");
				et_pwd.setText("");
				et_email.setHint(loginState + "");
			} else {
				Client nclient = new Client();
				nclient.setEmail(result.getProperty("email").toString());
				nclient.setName(result.getProperty("name").toString());
				nclient.setId(Integer.valueOf(result.getProperty("id")
						.toString()));
				userDataSource.savelogin(nclient);

				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
			super.onPostExecute(result);
		}
	}
}


// private static final String IMGUR_CLIENT_ID = "...";
// private static final MediaType MEDIA_TYPE_PNG = MediaType
// .parse("image/jpeg");
// Object result = wsClient.getSoapObject("getPerson", "name",
// "aahha", "age", "1");
// Vector response = (Vector) result;
// Object[] objs = response.toArray();
// Object result = "success";
// OkHttpClient client = new OkHttpClient();
// RequestBody requestBody = new MultipartBuilder()
// .type(MultipartBuilder.FORM)
// .addPart(
// Headers.of("Content-Disposition",
// "form-data; name=\"title\" "),
// RequestBody.create(null, "Square Logo"))
// .addPart(
// Headers.of("Content-Disposition",
// "form-data; name=\"image\" ;filename=\"love.jpg\" "),
// RequestBody.create(MEDIA_TYPE_PNG, new File(
// imageToBeUpload))).build();
//
// Request request = new Request.Builder()
// .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
// .url("http://192.168.1.102:8080/testapp/Testdb")
// .post(requestBody).build();
//
// Response response;
// try {
// response = client.newCall(request).execute();
// System.out.println(response.body().string());
//
// } catch (IOException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }

