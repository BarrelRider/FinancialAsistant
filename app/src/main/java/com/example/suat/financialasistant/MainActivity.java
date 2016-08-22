package com.example.suat.financialasistant;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;

import com.example.adapters.MyNavListAdapter;
import com.example.fragments.Graphics;
import com.example.fragments.History;
import com.example.fragments.Home;
import com.example.fragments.Settings;
import com.example.models.NavItem;
import com.example.models.UserInfo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

	DrawerLayout drawerLayout;
	RelativeLayout drawerPane;
	ImageView imgProfile;
	ListView lvNav;
	List<NavItem> listNavItems;
	TextView tvUsername;
	List<Fragment> listFragments;
	ActionBarDrawerToggle actionBarDrawerToggle;

    private static final String SOAP_ACTION2="http://tempuri.org/VeriKaydet";
    private static final String METHOD_NAME2 ="VeriKaydet";

    private static final String NAMESPACE ="http://tempuri.org/";
	private static final String URL="http://fintechasistant.azurewebsites.net/MySpecialWebService.asmx?wsdl";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        //LoginActivity den veri geliyor.Bu veri ID olacak ve global bir değişken olacak.
		Bundle extras=getIntent().getExtras();
		UserInfo.id=extras.getInt("send_Id");
		UserInfo.UserName=extras.getString("send_UserName");

        //Baba yine yaptı yapacağını , zaferi büyük bir dertten kurtardı , hi ha ha ha :D
        Toast.makeText(MainActivity.this, UserInfo.id+"", Toast.LENGTH_SHORT).show();

        //


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
		lvNav = (ListView) findViewById(R.id.nav_list);
		tvUsername=(TextView) findViewById(R.id.profile_Name);
		imgProfile=(ImageView) findViewById(R.id.icon);

		imgProfile.setImageResource(R.drawable.mordo);

		listNavItems = new ArrayList<>();
		listNavItems.add(new NavItem("Ana Sayfa", "Ana sayfaya geçer.",
				R.drawable.ic_action_home));
        listNavItems.add(new NavItem("Grafikler", "Grafik seçeneklerini gösterir.",
				R.drawable.ic_action_about));
		listNavItems.add(new NavItem("Ayarlar", "Ayarlarınız",
				R.drawable.ic_action_settings));
        listNavItems.add(new NavItem("Geçmiş","Geçmişinizi gösterir",R.drawable.ic_action_collection));

		MyNavListAdapter navListAdapter = new MyNavListAdapter(
				getApplicationContext(), R.layout.item_nav_list, listNavItems);

		tvUsername.setText(UserInfo.UserName);

		lvNav.setAdapter(navListAdapter);

		listFragments = new ArrayList<>();
		listFragments.add(new Home());
		listFragments.add(new Graphics());
		listFragments.add(new Settings());
		listFragments.add(new History());

		// load first fragment as default:
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.main_content, listFragments.get(0)).commit();

		setTitle(listNavItems.get(0).getTitle());
		lvNav.setItemChecked(0, true);
		drawerLayout.closeDrawer(drawerPane);

		// set listener for navigation items:
		lvNav.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

				// replace the fragment with the selection correspondingly:
				FragmentManager fragmentManager = getSupportFragmentManager();
				fragmentManager
						.beginTransaction()
						.replace(R.id.main_content, listFragments.get(position))
						.commit();

				setTitle(listNavItems.get(position).getTitle());
				lvNav.setItemChecked(position, true);


                /*Sidebar records*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME2);
                        request.addProperty("islem",listNavItems.get(position).getTitle() +" islemine girildi");
						request.addProperty("id", UserInfo.id);

                        System.out.println(listNavItems.get(position).getTitle() + "girdi");

                        SimpleDateFormat bicim2=new SimpleDateFormat("dd.M.yyyy hh:mm:ss a");
                        Date date = new Date();
                        System.out.println(bicim2.format(date).toString());
                        request.addProperty("tarih",bicim2.format(date).toString());

                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet=true;
                        envelope.setOutputSoapObject(request);

                        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                        androidHttpTransport.debug=true;
                        try{
                            System.out.println("EkleTryCalisti");
                            androidHttpTransport.call(SOAP_ACTION2,envelope);
                        }
                        catch (Exception e1){
                            System.out.println("EkleExceptionCalisti");
                        }
                    }
                }).start();
                /*Sidebar recording end*/

				drawerLayout.closeDrawer(drawerPane);

			}
		});

		// create listener for drawer layout
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.string.drawer_opened, R.string.drawer_closed) {

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();
				super.onDrawerClosed(drawerView);
			}

		};

		drawerLayout.addDrawerListener(actionBarDrawerToggle);

	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the MyHome/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (actionBarDrawerToggle.onOptionsItemSelected(item))
			return true;

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		actionBarDrawerToggle.syncState();
	}

}

