package com.cookhat.cookbeauty;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class KareshiEditActivity extends Activity {

    EditText editName;
    RadioGroup favoriteGenreGroup;
    Spinner favoriteMenuSpinner;
    String[] favoriteMenuItems;
    CheckBox[] allergyCheckBoxes;
    int allergyItems = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kareshi_edit);

        editName = (EditText)findViewById(R.id.edit_name);
        editName.setText("テキストの中身");
        editName.selectAll();

        favoriteGenreGroup = (RadioGroup)findViewById(R.id.favorite_genre_group);
        favoriteGenreGroup.check(R.id.japanese);

        //TODO: Spinnerクラスは1つしか選択できないので、複数選択する場合にはlistクラスに変更する必要あり
        favoriteMenuSpinner = (Spinner)findViewById(R.id.kareshi_favorite_menu_group);
        favoriteMenuItems = getResources().getStringArray(R.array.kareshi_favorite_menu_group_items);
        ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(
          this, android.R.layout.simple_spinner_item, favoriteMenuItems
        );
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favoriteMenuSpinner.setAdapter(menuAdapter);
        int defaultPosition = 3;
        favoriteMenuSpinner.setSelection(defaultPosition);

        //
        allergyCheckBoxes = new CheckBox[allergyItems];
        allergyCheckBoxes[0] = (CheckBox)findViewById(R.id.allergy01);
        allergyCheckBoxes[1] = (CheckBox)findViewById(R.id.allergy02);
        allergyCheckBoxes[2] = (CheckBox)findViewById(R.id.allergy03);






        Button button = (Button)findViewById(R.id.editButton);
        button.setOnClickListener(new editButtonClickListener());

    }

    private class editButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //TODO: 登録された内容を登録
            //editName = (EditText)findViewById(R.id.edit_name);
            SpannableStringBuilder newName = (SpannableStringBuilder)editName.getText();
            Log.v("test", newName.toString());


            int checkedRadioButtonId = favoriteGenreGroup.getCheckedRadioButtonId();
            RadioButton favoriteGenre = (RadioButton)findViewById(checkedRadioButtonId);
            Log.v("test", favoriteGenre.getText().toString());

            //boolean[] checkedAllergy = new boolean[allergyItems];
            /*for(int i=0; i<allergyItems; i++) {
                checkedAllergy[i] = allergyCheckBoxes[i].isChecked();
            }*/
            boolean checked = allergyCheckBoxes[1].isChecked();
            Log.v("test", String.valueOf(checked));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kareshi_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
