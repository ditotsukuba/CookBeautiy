package com.cookhat.cookbeauty.util;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cookhat.cookbeauty.R;

import java.util.ArrayList;

public class SuggestAdapter extends ArrayAdapter<String> {

    static class ViewHolder {
        TextView labelText;
    }

    private LayoutInflater inflater;

    // コンストラクタ
    public SuggestAdapter(Context context, int textViewResourceId, ArrayList<String> labelList) {
        super(context,textViewResourceId, labelList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;

        // Viewを再利用している場合は新たにViewを作らない
        if (view == null) {
            inflater =  (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ribbon_suggest, null);
            TextView label = (TextView)view.findViewById(R.id.tv);
            holder = new ViewHolder();
            holder.labelText = label;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 特定の行のデータを取得
        String str = getItem(position);

        if (!TextUtils.isEmpty(str)) {
            // テキストビューにラベルをセット
            holder.labelText.setText(str);
        }


            holder.labelText.setBackgroundColor(Color.parseColor("#ffa500"));


        // XMLで定義したアニメーションを読み込む
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.animejanai);
        // リストアイテムのアニメーションを開始
        view.startAnimation(anim);

        return view;
    }
}
