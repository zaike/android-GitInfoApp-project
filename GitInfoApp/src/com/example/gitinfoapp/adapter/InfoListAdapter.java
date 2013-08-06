package com.example.gitinfoapp.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gitinfoapp.R;
import com.example.gitinfoapp.dto.InfoDto;

public class InfoListAdapter extends ArrayAdapter<InfoDto> {

	public InfoListAdapter(Context context, List<InfoDto> objects) {
		super(context, 0, objects);
	}

	public InfoListAdapter(Context context, InfoDto[] objects) {
		super(context, 0, objects);
	}

	public InfoListAdapter(Context context, int resource, int textViewResourceId, InfoDto[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public InfoListAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

    public InfoListAdapter(Context context, int textViewResourceId, List<InfoDto> objects) {
        super(context, textViewResourceId, objects);
    }

    public InfoListAdapter(Context context, int textViewResourceId, InfoDto[] objects) {
        super(context, textViewResourceId, objects);
    }

    public InfoListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row, null);
        }

        InfoDto item = getItem(position);
        
        TextView id = (TextView) convertView.findViewById(R.id.idInList);
        id.setText(String.valueOf(item.getInfoId()));

        TextView name = (TextView) convertView.findViewById(R.id.titleInList);
        name.setText(item.getInfoTitle());

        TextView comment = (TextView) convertView
                .findViewById(R.id.detailInList);
        comment.setText(item.getInfoDetail());

        return convertView;
    }
}
