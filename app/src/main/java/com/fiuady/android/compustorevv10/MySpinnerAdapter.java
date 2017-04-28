package com.fiuady.android.compustorevv10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel on 19/04/2017.
 */
public class MySpinnerAdapter extends ArrayAdapter<StateView> {
    private int getPosition;
    private Context mContext;
    private ArrayList<StateView>listState = new ArrayList<StateView>();
    private MySpinnerAdapter mySpinnerAdapter;
    private boolean isFromView=false;
    public interface OnCheckedChange
    {
        void OnCheckedChange(int value);
        void OnCheckedNegativeChange(int value);
    }
    OnCheckedChange mCheckedListener;

    public int getGetPosition() {
        return getPosition;
    }

    public void setGetPosition(int getPosition) {
        this.getPosition = getPosition;
    }

    public MySpinnerAdapter(Context context, int resource, List<StateView>objects)
    {
        super(context,resource,objects);
        this.mContext = context;
        this.listState = (ArrayList<StateView>)objects;
        this.mySpinnerAdapter = this;
    }
    public void setOnCheckedChange(OnCheckedChange mCheckedListener)
    {
        this.mCheckedListener=mCheckedListener;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    public View getCustomView( int position,View convertView,ViewGroup parent)
    {
        final ViewHolder holder;
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.spinner_item,null);
            holder = new ViewHolder();
            holder.mTextView =(TextView)convertView.findViewById(R.id.txt_OrderStatus);
            holder.mCheckBox =(CheckBox)convertView.findViewById(R.id.chk_OrderStatus);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(listState.get(position).getTitle());
        //To check whether checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView=false;
       /* if(position==0){
            holder.mCheckBox.setVisibility(View.INVISIBLE);


        }
        else {*/
            holder.mCheckBox.setVisibility(View.VISIBLE);

        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getPosition = (Integer) buttonView.getTag();
                /*if(isChecked) {

                    mCheckedListener.OnCheckedChange(getPosition);
                    //listState.set(getPosition,new StateView(listState.get(getPosition).getTitle(),true));
                    listState.get(getPosition).setSelected(true);
                }
                else
                {
                    listState.get(getPosition).setSelected(false);
                }*/
                if(!isFromView)
                {

                        if(isChecked)
                        {
                            listState.get(getPosition).setSelected(true);
                            mCheckedListener.OnCheckedChange(getPosition);
                        }
                        else
                        {
                            if(!isChecked)

                                listState.get(getPosition).setSelected(false);
                                mCheckedListener.OnCheckedNegativeChange(getPosition);

                        }
                    mySpinnerAdapter.notifyDataSetChanged();


                }
            }
        });
        return convertView;
    }

    public ArrayList<StateView> getListState() {
        return listState;
    }

    private class ViewHolder{
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}
