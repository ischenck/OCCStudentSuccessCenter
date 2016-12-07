package edu.orangecoastcollege.cs273.kfrederick5tmorrissey1ischenck.occstudentsuccesscenter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Link on 12/5/2016.
 */

public class ProfileListAdapter extends ArrayAdapter<User> {

    private Context mContext;
    private List<User> mCoursesList = new ArrayList<>();
    private int mResourceId;
    private CheckBox selectedCheckBox;


    public ProfileListAdapter(Context context, int resource, List<User> coursesList) {
        super(context, resource, coursesList);
        mContext =context;
        mResourceId = resource;
        mCoursesList = coursesList;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        final User selectedCourse = mCoursesList.get(pos);
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        selectedCheckBox = (CheckBox) view.findViewById(R.id.selectedCheckBox);
        selectedCheckBox.setText(selectedCourse.getSubject() + " " + selectedCourse.getuClass());
        selectedCheckBox.setChecked(selectedCourse.getIsSelected() == 1);

        selectedCheckBox.setTag(selectedCourse);

        return view;
    }
}