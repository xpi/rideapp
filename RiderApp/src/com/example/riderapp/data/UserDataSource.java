package com.example.riderapp.data;import java.util.Map;import org.ksoap2.serialization.SoapObject;import org.ksoap2.serialization.SoapPrimitive;import com.example.riderapp.activity.LoginActivity;import com.example.riderapp.activity.MainActivity;import com.example.riderapp.model.Client;import com.example.riderapp.webservice.WsClient;import android.content.Context;import android.content.Intent;import android.content.SharedPreferences;public class UserDataSource {	private static final String PREFKEY = "riderapp";	private SharedPreferences userInfoRecord;	public static Client client;	public UserDataSource(Context context) {				userInfoRecord = context.getSharedPreferences(PREFKEY,				Context.MODE_PRIVATE);			}	public int savelogin(Client client) {		this.client=client;		SharedPreferences.Editor editor = userInfoRecord.edit();		editor.putString("user_name", client.getName() + "");		editor.putString("user_email", client.getEmail() + "");		editor.putString("user_id", client.getId() + "");		editor.commit();		return 1;	}	public boolean isLogin() {		String loginState = userInfoRecord.getString("user_email", "notLogin");		if (loginState.equals("notLogin")) {			return false;		}		Map<String, ?> signItems = userInfoRecord.getAll();		Client nclient = new Client();		nclient.setEmail((String) signItems.get("user_email"));		nclient.setName((String) signItems.get("user_name"));		nclient.setId(Integer.valueOf((String) signItems.get("user_id")));		UserDataSource.client = nclient;		return true;	}	public SoapObject login(String email, String password) {		WsClient wsClient = new WsClient(AppConfig.WSDL,				AppConfig.WSDL_NAMESPACE);		SoapObject soap = (SoapObject) wsClient.getSoapObject("login", "email",				email, "password", password);		return soap;		}}