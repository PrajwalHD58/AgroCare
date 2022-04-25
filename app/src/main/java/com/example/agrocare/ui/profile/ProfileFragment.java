package com.example.agrocare.ui.profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.agrocare.R;
import com.example.agrocare.splashscreen.screen1;
import com.example.agrocare.userLogin;
import com.example.agrocare.usersdata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView username, fullname, email, mob, coins;
    private ImageView prof_pic, twit, insta, meta;
    private String userName = "";
    private ProgressBar fpb;
    private ImageView si, st, sw, sf;
    private Context context;
    private String s1 = "username", s3 = "isCheck";
    private static final String PREF = "PREFS";
    private Button logout;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        userName = screen1.getUsername();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        context = container.getContext();
        initUI(v);
        setupUI();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageW("Install AgroCare App!");
            }
        });
        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessagei("Install AgroCare App!");
            }
        });
        sf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessagef("Install AgroCare App!");
            }
        });
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessaget("Install AgroCare App");
            }
        });
        return v;
    }

    private void sendMessageW(String message) {

        // Creating new intent
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");

        // Give your message here
        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean temp= list.size() > 0;
        if (temp==true) {
            intent.putExtra(Intent.EXTRA_TEXT, message);
            // Starting Whatsapp
            startActivity(intent);
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://whatsapp.com/")));
        }


    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void sendMessagei(String message) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage("com.instagram.android");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getActivity().getPackageManager()) == null) {
            Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/"));
            intent2.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(intent2);
            return;
        }
        startActivity(intent);
//        try {
//            Intent openNewIntent = new Intent();
//            String mPackage = "com.instagram.android";
//            String mClass = ".DMActivity";
//            openNewIntent.setComponent(new ComponentName(mPackage, mPackage + mClass));
//            openNewIntent.putExtra(Intent.EXTRA_TEXT, message);
//            openNewIntent.putExtra("keyboard_open", true);
//            startActivity(openNewIntent);
//        } catch (Exception e) {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/")));
//            //e.printStackTrace();
//        }

    }

    private void sendMessagef(String message) {

//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.setPackage("fb-messenger");
//        intent.putExtra(Intent.EXTRA_TEXT, message);
//        startActivity(intent);
        try {
            Intent openNewIntent = new Intent();
            String mPackage = "fb-messenger";
            String mClass = ".DMActivity";
            openNewIntent.setComponent(new ComponentName(mPackage, mPackage + mClass));
            openNewIntent.putExtra(Intent.EXTRA_TEXT, message);
            openNewIntent.putExtra("keyboard_open", true);
            startActivity(openNewIntent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/")));
            //e.printStackTrace();
        }

    }


    private void sendMessaget(String message) {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.setPackage("com.instagram.android");
//        intent.putExtra(Intent.EXTRA_TEXT, message);
//        startActivity(intent);
        try {
            Intent openNewIntent = new Intent();
            String mPackage = "com.twitter.android";
            String mClass = ".DMActivity";
            openNewIntent.setComponent(new ComponentName(mPackage, mPackage + mClass));
            openNewIntent.putExtra(Intent.EXTRA_TEXT, message);
            openNewIntent.putExtra("keyboard_open", true);
            startActivity(openNewIntent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/direct_messages/create/")));
            //e.printStackTrace();
        }
    }


    private void setupUI() {
        fpb.setVisibility(View.VISIBLE);
        FirebaseDatabase firebaseDatabase;
        DatabaseReference reference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("UserData").child(userName);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    usersdata ud = snapshot.getValue(usersdata.class);
                    fullname.setText(ud.getFullname());
                    username.setText(ud.getUsername());
                    email.setText(ud.getEmail());
                    mob.setText(ud.getPhonenum());
                }
                fpb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void logout()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(s1, "");
        edit.putBoolean(s3, false);
        edit.commit();

        Intent intent2 = new Intent(getActivity(), userLogin.class);
        startActivity(intent2);
        getActivity().finish();
    }
    private void initUI(View v) {
        fullname = v.findViewById(R.id.full_name);
        username = v.findViewById(R.id.user_name);
        email = v.findViewById(R.id.tv_email);
        mob = v.findViewById(R.id.mobile_no);
        prof_pic = v.findViewById(R.id.imageView);
        fpb = v.findViewById(R.id.pb);
        si = v.findViewById(R.id.share_insta);
        st = v.findViewById(R.id.share_twitter);
        sf = v.findViewById(R.id.share_face);
        sw = v.findViewById(R.id.share_whatsapp);
        logout=v.findViewById(R.id.btn_logout);
    }

}