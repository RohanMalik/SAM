package com.monkeybusiness.jaaar.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.kbeanie.imagechooser.exceptions.ChooserException;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.Services.CircleImageView;
import com.monkeybusiness.jaaar.cropImageUtils.Crop;
import com.monkeybusiness.jaaar.objectClasses.studentDetailsResponse.StudentsDetailsResponseData;
import com.monkeybusiness.jaaar.objectClasses.checkLoginResponse.CheckLoginResponse;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;
import com.squareup.picasso.Picasso;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rakesh on 4/2/16.
 */
public class StudentDetailsActivity extends BaseActivity implements ImageChooserListener {

    final String TAG = "StudentDetailsActivity";

    LinearLayout linearLayoutMainStudent;

    RelativeLayout relativeLayoutMenu;

    TextView textViewActionTitle;
    TextView textViewName;
    TextView textViewClass;
    TextView textViewContact;
    TextView textViewEmail;

    Button buttonRemarksStudent;

    ProgressBar progressBarStudent;

    CircleImageView imageViewProfilePicStudent;

    Dialog dialog;
    private TextView textViewCamera;
    private TextView textViewGallery;
    private TextView textViewTitle;
    private ImageChooserManager imageChooserManagerGallery;
    private ImageChooserManager imageChooserManagerCamera;
    private Uri inputUri;
    private Uri outputUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
//        Utils.classFlag = 0;
//
//        toggleLayouts(linearlayoutDashboard, textViewDashboard);

        initialization();

        Intent intent = getIntent();
        int studentId = intent.getIntExtra(Constants.STUDENT_ID,0);

        getStudentDetailsServerCall(studentId);
    }

    public void initialization() {
        linearLayoutMainStudent = (LinearLayout) findViewById(R.id.linearLayoutMainStudent);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);

        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);
        textViewName = (TextView) findViewById(R.id.textViewNameStudent);
        textViewClass = (TextView) findViewById(R.id.textViewClassStudent);
        textViewContact = (TextView) findViewById(R.id.textViewContactStudent);
        textViewEmail = (TextView) findViewById(R.id.textViewEmailStudent);

        buttonRemarksStudent = (Button) findViewById(R.id.buttonRemarksStudent);

        progressBarStudent = (ProgressBar) findViewById(R.id.progressBarStudent);

        imageViewProfilePicStudent = (CircleImageView) findViewById(R.id.imageViewProfilePicStudent);

        textViewActionTitle.setText("Student Profile");

//        linearLayoutMainStudent.setVisibility(View.GONE);
        progressBarStudent.setVisibility(View.GONE);

        imageChooserManagerCamera = new ImageChooserManager(this, ChooserType.REQUEST_CAPTURE_PICTURE);
        imageChooserManagerCamera.setImageChooserListener(this);

        imageChooserManagerGallery = new ImageChooserManager(this, ChooserType.REQUEST_PICK_PICTURE);
        imageChooserManagerGallery.setImageChooserListener(this);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);
        buttonRemarksStudent.setOnClickListener(this);
        imageViewProfilePicStudent.setOnClickListener(this);
    }

    private void setUIData() {

        CheckLoginResponse checkLoginResponse = Prefs.with(this).getObject(PrefsKeys.CHECK_LOGIN_DATA,CheckLoginResponse.class);

        progressBarStudent.setVisibility(View.GONE);
        linearLayoutMainStudent.setVisibility(View.VISIBLE);

        if (checkLoginResponse!=null)
        {
            textViewName.setText("Hi "+checkLoginResponse.getData().getUserTypeDetails().getTeacherName());
//            textViewClass.setText(checkLoginResponse.getData().get);
            textViewContact.setText("Contact No : "+checkLoginResponse.getData().getUserTypeDetails().getContactInfo());
            textViewEmail.setText("Email : "+checkLoginResponse.getData().getUserInfo().getEmail());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.buttonRemarksStudent:
//                Intent intent = new Intent(StudentDetailsActivity.this, AttendanceFragment.class);
//                startActivity(intent);
//                finish();
                Log.d(TAG,"button pressed");
                break;
            case R.id.imageViewProfilePicStudent:
                showProfilePicDialog();
                break;

            case R.id.textViewCamera:

                Log.d("abc dialog", "camera");
                dialog.dismiss();
                try {
                    imageChooserManagerCamera.choose();
                } catch (ChooserException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.textViewGallery:

                Log.d("abc dialog", "gallery");
                //            Log.d("abc choose","aa gya");
                dialog.dismiss();
                try {
                    imageChooserManagerGallery.choose();
                } catch (ChooserException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void showProfilePicDialog() {
        dialog = new Dialog(StudentDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        
//            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        textViewCamera = (TextView) dialog.findViewById(R.id.textViewCamera);
        textViewGallery = (TextView) dialog.findViewById(R.id.textViewGallery);
        textViewTitle = (TextView) dialog.findViewById(R.id.txt_title);


        textViewCamera.setOnClickListener(this);
        textViewGallery.setOnClickListener(this);

        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == ChooserType.REQUEST_PICK_PICTURE)) {
            imageChooserManagerGallery.submit(requestCode, data);
            Log.d("ProfiileEdit", "imageChosenResultgallery");
        } else if (resultCode == RESULT_OK && (requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            imageChooserManagerCamera.submit(requestCode, data);
            Log.d("ProfiileEdit", "imageChosenResultcamera");
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
//            Log.i("abc", "success");
//            imageViewEditDetailFeMale.setImageURI(outputUri);
//            imageViewEditDetailMale.setImageURI(outputUri);
            Log.d("ProfiileEdit", "CropResult");
            if (outputUri != null) {
                String filePath = outputUri.toString().substring(7);
                Picasso.with(this).load(new File(filePath)).fit().into(imageViewProfilePicStudent);

//                    postUserImageOnServer(filePath);
//                getAddressGivenLatLongFrom(filePath);
            }
        }
    }

    @Override
    public void onImageChosen(ChosenImage chosenImage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (chosenImage != null) {

                    String path = "file://" + chosenImage.getFilePathOriginal();
                    inputUri = Uri.parse(path);

                    String outPath = "file://" + chosenImage.getFilePathOriginal();
                    outputUri = Uri.parse(outPath);

//                    Crop.of(inputUri, outputUri).asSquare().start(ProfileEditActivity.this)

                    Crop.of(inputUri, outputUri).asSquare().start(StudentDetailsActivity.this);
//                    Crop.of(inputUri, outputUri).asSquare().start(ProfileEditActivity.this);

//                    try {
//                        imageChoose.setImageBitmap(decodeUri(Uri.parse(path)));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
                    String imagethumb = chosenImage.getFileThumbnail();
//                    }
                    Log.d("abc original", "" + path);
                    Log.d("abc thumb", imagethumb);

//
//                    Bitmap bitmap = BitmapFactory.decodeFile(path);
//                    imageChoose.setImageBitmap(bitmap);
//                    Bitmap bmp = BitmapFactory.decodeFile(path);
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos);
//                    InputStream in = new ByteArrayInputStream(bos.toByteArray());
//
                }
            }
        });
    }

    @Override
    public void onError(String s) {

    }


    private void getStudentDetailsServerCall(int studentId) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

        RestClient.getApiServicePojo(xCookies,aCookies).apiCallGetStudentDetails(String.valueOf(studentId), new Callback<StudentsDetailsResponseData>() {
            @Override
            public void success(StudentsDetailsResponseData studentsDetailsResponseData, Response response) {
                Log.d(TAG,"Response : "+new Gson().toJson(studentsDetailsResponseData));
                Prefs.with(StudentDetailsActivity.this).save(PrefsKeys.STUDENT_DETAILS_RESPONSE_DATA,studentsDetailsResponseData);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG,"error : "+error.toString());
            }
        });
    }

}
