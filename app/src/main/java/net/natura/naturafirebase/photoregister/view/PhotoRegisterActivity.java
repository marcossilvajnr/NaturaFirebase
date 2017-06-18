package net.natura.naturafirebase.photoregister.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.base.view.BaseActivity;
import net.natura.naturafirebase.main.view.MainActivity;
import net.natura.naturafirebase.photoregister.PhotoRegisterContract;
import net.natura.naturafirebase.photoregister.presenter.PhotoRegisterPresenter;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by marcos on 11/06/17.
 */

public class PhotoRegisterActivity extends BaseActivity implements PhotoRegisterContract.View {
    public static final int REQUEST_IMAGE_CAPTURE = 213;
    public static final int REQUEST_GALERY_CAPTURE = 214;
    private PhotoRegisterPresenter photoRegisterPresenter;
    private Bitmap mainBitmap;
    private ProgressDialog progressDialog;

    @BindView(R.id.photoToolbar) Toolbar photoToolbar;
    @BindView(R.id.photoPreview) ImageView photoPreview;
    @BindView(R.id.emptyPhotoText) TextView emptyPhotoText;

    public static Intent newInstance(Context context){
        Intent intent = new Intent(context, PhotoRegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_register);
        ButterKnife.bind(this);
        setSupportActionBar(photoToolbar);
        photoRegisterPresenter = new PhotoRegisterPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_register_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_ok) {
            if (mainBitmap != null) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                photoRegisterPresenter.uploadUserProfilePhoto(mainBitmap, firebaseUser.getUid());
            } else {
                showToast("Selecione uma foto para continuar!", Toast.LENGTH_LONG);
            }
        }

        if (item.getItemId() == R.id.menu_clear) {
            setupImageEmptyPreview();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        photoRegisterPresenter.attachView(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        photoRegisterPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mainBitmap = (Bitmap) extras.get("data");
            setupImagePreview();
        } else if (requestCode == REQUEST_GALERY_CAPTURE && resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                mainBitmap = BitmapFactory.decodeStream(imageStream);
                setupImagePreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.openCamera)
    public void cameraOnClick() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @OnClick(R.id.openGalery)
    public void galeryOnClick() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_GALERY_CAPTURE);
    }

    @Override
    public void setupImageEmptyPreview() {
        emptyPhotoText.setVisibility(View.VISIBLE);
        photoPreview.setImageBitmap(null);
        mainBitmap = null;
    }

    @Override
    public void setupImagePreview(){
        emptyPhotoText.setVisibility(View.GONE);
        if (mainBitmap != null) {
            photoPreview.setImageBitmap(mainBitmap);
        }
    }

    @Override
    public void startUpload() {
        progressDialog = new ProgressDialog(PhotoRegisterActivity.this);
        progressDialog.setMax(100);
        progressDialog.setTitle("Fazendo upload");
        progressDialog.setMessage("Aguarde...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    @Override
    public void showProgressUpload(Integer progress) {
        if (progressDialog.isShowing()) {
            progressDialog.setProgress(progress);
        }
    }

    @Override
    public void showErrorUpload() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoRegisterActivity.this);
        builder.setTitle("Atenção!");
        builder.setMessage("Erro ao fazer upload, gostaria de tentar novamente ?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                photoRegisterPresenter.uploadUserProfilePhoto(mainBitmap, firebaseUser.getUid());
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void showSuccessUpload() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoRegisterActivity.this);
        builder.setTitle("Sucesso!");
        builder.setMessage("Imagem de perfil atualizada!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openActivity(MainActivity.newInstance(PhotoRegisterActivity.this));
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {}
}
