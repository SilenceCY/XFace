package edu.thu.xface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import edu.thu.xface.adapter.UserInfo;
import edu.thu.xface.util.CommonUtil;
import edu.thu.xface.util.ToastUtil;

/**
 * 
 * sign up
 * 
 * @author hujiawei
 * 
 */
public class SignupActivity extends Activity {

	private EditText et_signup_name;

	private String name = "";
	private String[] names;
	private ArrayList<UserInfo> userInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		et_signup_name = (EditText) findViewById(R.id.et_signup_name);
	}

	// choose a registered name!
	public void tv_signup_name(View view) {
		userInfos = CommonUtil.getAllUsers(false);
		names = new String[userInfos.size()];
		for (int i = 0; i < userInfos.size(); i++) {
			names[i] = userInfos.get(i).getName();
		}
		if (names.length <= 0) {
			ToastUtil.showShortToast(getApplicationContext(), "No one registered!");
			return;
		}
		// **SignupActivity.this** important!
		new AlertDialog.Builder(SignupActivity.this).setTitle("��ѡ���������:")
				.setItems(names, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (which >= 0) {
							et_signup_name.setText(names[which]);
						}
						dialog.dismiss();
					}
				}).show();
	}

	// go to camera
	public void btn_signup_gotocamera(View view) {
		name = et_signup_name.getText().toString();
		int userid = -1;
		// sharedPreferences.getStringSet("allnames", null);//android-11!!!
		if (null == name || "".equalsIgnoreCase(name)) {
			ToastUtil.showShortToast(getApplicationContext(), "�������������֣�");
			return;
		}
		if (CommonUtil.userProps.contains(name)) {// contains value?
			// ToastUtil.showShortToast(getApplicationContext(), "�����ظ���Ӵ�ף�");
			for (int i = 0; i < userInfos.size(); i++) {
				if (userInfos.get(i).getName().equalsIgnoreCase(name)) {
					userid = userInfos.get(i).getUserid();
				}
			}
		}
		Intent intent = new Intent(SignupActivity.this, SignupCameraActivity.class);
		intent.putExtra("name", name);
		intent.putExtra("userid", userid);
		startActivity(intent);
		SignupActivity.this.finish();
	}
}
