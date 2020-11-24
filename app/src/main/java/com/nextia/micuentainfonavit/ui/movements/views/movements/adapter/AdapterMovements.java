package com.nextia.micuentainfonavit.ui.movements.views.movements.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class AdapterMovements extends BaseExpandableListAdapter {
    private Activity context;
    private Map<String, List<String>> movementsCollections;
    private List<String> groupList;

    public AdapterMovements(Activity context, List<String> groupList, Map<String, List<String>> movementsCollections) {
        this.context = context;
        this.movementsCollections = movementsCollections;
        this.groupList = groupList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return movementsCollections.get(groupList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_moves_movements, null);
        }

        final String child = (String) getChild(groupPosition, childPosition);
        String[] childArr = new String[2];
        childArr = child.split("&");

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);

        title.setText(childArr[0]);
        description.setText(childArr[1]);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return movementsCollections.get(groupList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_moves_movements, null);
        }

        String group = (String) getGroup(groupPosition);
        String[] groupArr = new String[2];
        groupArr = group.split("&");

        TextView fecha = (TextView) convertView.findViewById(R.id.fecha);
        TextView pagoRegular = (TextView) convertView.findViewById(R.id.pagoRegular);
        TextView montoTransaccion = (TextView) convertView.findViewById(R.id.montoTransaccion);
        LinearLayout containerGroup = (LinearLayout) convertView.findViewById(R.id.container_group);

        if (groupPosition % 2 == 0) {
            containerGroup.setBackgroundResource(R.color.colorPrimary);
            if (isExpanded) {
                montoTransaccion.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chevron_white_down, 0);
            } else {
                montoTransaccion.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chevron_white_right, 0);
            }

        } else {
            fecha.setTextColor(this.context.getResources().getColor(R.color.color_black));
            montoTransaccion.setTextColor(this.context.getResources().getColor(R.color.color_black));
            pagoRegular.setTextColor(this.context.getResources().getColor(R.color.color_black));
            if (isExpanded) {
                montoTransaccion.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chevron_red_down, 0);
            } else {
                montoTransaccion.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chevron_red_right, 0);
            }

        }

        //fecha.setText(UtilsDateMCI.textDate(groupArr[0]));
        try {
            fecha.setText(Utils.textDate(groupArr[0]));
        } catch (ParseException e) {
            fecha.setText(groupArr[0]);
            e.printStackTrace();
        }
        montoTransaccion.setText("$"+groupArr[1].trim());
       // montoTransaccion.setGravity(Gravity.CENTER);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
