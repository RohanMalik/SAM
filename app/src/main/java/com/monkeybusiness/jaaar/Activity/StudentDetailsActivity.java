package com.monkeybusiness.jaaar.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.kbeanie.imagechooser.exceptions.ChooserException;
import com.monkeybusiness.jaaar.Adapter.RemarksListAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.Services.CircleImageView;
import com.monkeybusiness.jaaar.cropImageUtils.Crop;
import com.monkeybusiness.jaaar.objectClasses.addRemarksObject.Remarks;
import com.monkeybusiness.jaaar.objectClasses.addRemarksObject.RemarksRequestObject;
import com.monkeybusiness.jaaar.objectClasses.addRemarksResponseData.AddRemarksResponseData;
import com.monkeybusiness.jaaar.objectClasses.studentDetailsResponse.StudentsDetailsResponseData;
import com.monkeybusiness.jaaar.objectClasses.checkLoginResponse.CheckLoginResponse;
import com.monkeybusiness.jaaar.objectClasses.studentRemarksData.Remark;
import com.monkeybusiness.jaaar.objectClasses.studentRemarksData.StudentsRemarksResponse;
import com.monkeybusiness.jaaar.objectClasses.studentSearchdata.Student;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.NonScrollListView;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

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

    TextView textViewContactStudent;
    TextView textViewRemarks;

    Button buttonRemarksStudent;

    ProgressBar progressBarStudent;

    CircleImageView imageViewProfilePicStudent;

    Dialog dialog;
    private TextView textViewCamera;
    private TextView textViewGallery;
    private TextView textViewTitle;

    TextView textViewRollNoStudent;
    private ImageChooserManager imageChooserManagerGallery;
    private ImageChooserManager imageChooserManagerCamera;
    private Uri inputUri;
    private Uri outputUri;

    NonScrollListView listViewRemarks;

    int studentId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

//        Utils.classFlag = 0;
//
//        toggleLayouts(linearlayoutDashboard, textViewDashboard);

        initialization();

        Intent intent = getIntent();
        studentId = intent.getIntExtra(Constants.STUDENT_ID, 0);

    }



    public void initialization() {
        linearLayoutMainStudent = (LinearLayout) findViewById(R.id.linearLayoutMainStudent);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);

        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);
        textViewName = (TextView) findViewById(R.id.textViewNameStudent);
        textViewClass = (TextView) findViewById(R.id.textViewClassStudent);
        textViewContact = (TextView) findViewById(R.id.textViewContactStudent);
        textViewEmail = (TextView) findViewById(R.id.textViewEmailStudent);
        textViewRollNoStudent  = (TextView) findViewById(R.id.textViewRollNoStudent);

        textViewRemarks = (TextView) findViewById(R.id.textViewRemarks);

        buttonRemarksStudent = (Button) findViewById(R.id.buttonRemarksStudent);

        progressBarStudent = (ProgressBar) findViewById(R.id.progressBarStudent);

        textViewContactStudent = (TextView) findViewById(R.id.textViewParentConatctStudent);

        imageViewProfilePicStudent = (CircleImageView) findViewById(R.id.imageViewProfilePicStudent);

        listViewRemarks = (NonScrollListView) findViewById(R.id.listViewRemarks);

        textViewActionTitle.setText("Student Profile");

        linearLayoutMainStudent.setVisibility(View.GONE);


        imageChooserManagerCamera = new ImageChooserManager(this, ChooserType.REQUEST_CAPTURE_PICTURE);
        imageChooserManagerCamera.setImageChooserListener(this);

        imageChooserManagerGallery = new ImageChooserManager(this, ChooserType.REQUEST_PICK_PICTURE);
        imageChooserManagerGallery.setImageChooserListener(this);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);
        buttonRemarksStudent.setOnClickListener(this);
        imageViewProfilePicStudent.setOnClickListener(this);
    }

    StudentsDetailsResponseData studentsDetailsResponseData;

    private void setUIData(StudentsDetailsResponseData studentsDetailsResponseData) {

        progressBarStudent.setVisibility(View.GONE);
        linearLayoutMainStudent.setVisibility(View.VISIBLE);

        this.studentsDetailsResponseData = studentsDetailsResponseData;

        textViewName.setText("Name : " + studentsDetailsResponseData.getData().getStudent().getStudentName());
        textViewClass.setText("Class : " + studentsDetailsResponseData.getData().getStudent().getBatch().getClassAlias());
        textViewContact.setText("Address : " + studentsDetailsResponseData.getData().getStudent().getAddress());
        textViewEmail.setText("Father's Name : " + studentsDetailsResponseData.getData().getStudent().getFatherName());
        textViewRollNoStudent.setText("Roll No. "+studentsDetailsResponseData.getData().getStudent().getRollno());
        textViewContactStudent.setText("Contact : "+studentsDetailsResponseData.getData().getStudent().getParent().getContactPhone());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.buttonRemarksStudent:
                if (studentsDetailsResponseData!=null)
                {
                    showRemarksDialog(studentsDetailsResponseData.getData().getStudent().getId());
                }
                Log.d(TAG, "button pressed");
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

    Dialog subjectDialog;
    EditText editTextRemarks;
    Button buttonSend;

    public void showRemarksDialog(int id) {

        subjectDialog = new Dialog(this);
        subjectDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        subjectDialog.setContentView(R.layout.dialog_custom_msg_test);
        editTextRemarks = (EditText) subjectDialog.findViewById(R.id.editTextRemarks);

        buttonSend = (Button) subjectDialog.findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextRemarks.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(StudentDetailsActivity.this, "Please Enter Some remarks", Toast.LENGTH_SHORT).show();
                } else {
                    subjectDialog.dismiss();
                    sendRemarksServerCall(id);
                }
            }
        });

        subjectDialog.setCancelable(true);
        subjectDialog.setCanceledOnTouchOutside(true);
        subjectDialog.show();
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
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetStudentDetails(String.valueOf(studentId), new Callback<StudentsDetailsResponseData>() {
            @Override
            public void success(StudentsDetailsResponseData studentsDetailsResponseData, Response response) {
                Log.d(TAG, "Response : " + new Gson().toJson(studentsDetailsResponseData));
                Prefs.with(StudentDetailsActivity.this).save(PrefsKeys.STUDENT_DETAILS_RESPONSE_DATA, studentsDetailsResponseData);
                setUIData(studentsDetailsResponseData);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "error : " + error.toString());
            }
        });
    }



    private void sendRemarksServerCall(int id) {

        RemarksRequestObject remarksRequestObject = new RemarksRequestObject();

        String remarksStr = editTextRemarks.getText().toString();

        Remarks remarks = new Remarks();
        remarks.setDateOfRemark(ISO8601.fromCalendar(Calendar.getInstance()));
        remarks.setRemark(remarksStr);
        ArrayList<Integer> studentList = new ArrayList<>();

        studentList.add(id);

        remarks.setStudents(studentList);

        remarksRequestObject.setRemarks(remarks);

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        String jsonRemarks = new Gson().toJson(remarksRequestObject);

        ProgressDialog progressDialog = ProgressDialog.show(this, "Please Wait", "Sending Remarks...", false);

        try {
            TypedInput typedInput = new TypedByteArray("application/json", jsonRemarks.getBytes("UTF-8"));
            RestClient.getApiServicePojo(xCookies, aCookies).apiCallSendRemarks(typedInput, new Callback<AddRemarksResponseData>() {
                @Override
                public void success(AddRemarksResponseData addRemarksResponseData, Response response) {
                    android.util.Log.d(TAG, "Response : " + new Gson().toJson(addRemarksResponseData));
                    progressDialog.dismiss();

                    if (addRemarksResponseData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                        Utils.failureDialog(StudentDetailsActivity.this, "Success", "You have successfully sent remarks");
                        getStudentRemarks(studentId);
                    } else {
                        Utils.failureDialog(StudentDetailsActivity.this, "Failure", "Something went wrong, please try again.");
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    android.util.Log.d(TAG, "error : " + error.toString());
                    progressDialog.dismiss();
                    Utils.failureDialog(StudentDetailsActivity.this, "Failure", "Something went wrong, please try again.");
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getStudentRemarks(int studentId) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies,aCookies).apiCallFetchSingleStudentRemarks(String.valueOf(studentId), "1",
                new Callback<StudentsRemarksResponse>() {
                    @Override
                    public void success(StudentsRemarksResponse studentsRemarksResponse, Response response) {
                        Log.d(TAG,"Response : "+new Gson().toJson(studentsRemarksResponse));

                        setUiremarks(studentsRemarksResponse);
                    }

                    

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG,"error : "+error.toString());
                    }
                });
    }

    private void setUiremarks(StudentsRemarksResponse studentsRemarksResponse) {

        if (studentsRemarksResponse.getData().getRemarks().isEmpty())
        {
            textViewRemarks.setVisibility(View.GONE);
        }
        else
        {
            textViewRemarks.setVisibility(View.VISIBLE);
        }

        for (Remark remark : studentsRemarksResponse.getData().getRemarks())
        {
            Log.d("adapter","UIremark : "+new Gson().toJson(remark));
        }
        RemarksListAdapter remarksListAdapter = new RemarksListAdapter(this,studentsRemarksResponse.getData().getRemarks());
        listViewRemarks.setAdapter(remarksListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudentDetailsServerCall(studentId);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getStudentRemarks(studentId);
            }
        },200);
    }
}


