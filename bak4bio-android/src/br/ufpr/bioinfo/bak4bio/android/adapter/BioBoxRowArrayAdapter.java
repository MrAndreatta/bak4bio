package br.ufpr.bioinfo.bak4bio.android.adapter;

import java.util.ArrayList;
import java.util.List;

import br.com.agivis.framework.async.AsyncExecutor;
import br.ufpr.bioinfo.bak4bio.android.R;
import br.ufpr.bioinfo.bak4bio.android.model.Content;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class BioBoxRowArrayAdapter<T> extends ArrayAdapter<T> {

	private Activity context; 
	private List<T> contents = new ArrayList<T>();
	protected AsyncExecutor executor = new AsyncExecutor();
	
	public BioBoxRowArrayAdapter(Activity context, List<T> contents) {
		super(context, R.layout.biobox_row, contents);
		this.context = context;
		this.contents = contents;
	}
	
	static class ViewHolder {
		public TextView tvName;
		public TextView tvDescription;
		public TextView tvSize;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		Content content = (Content) contents.get(position);
		final ViewHolder viewHolder;
		
		if (rowView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.biobox_row, parent, false);
			
			viewHolder.tvName = (TextView) rowView.findViewById(R.id.tvName);
			viewHolder.tvDescription = (TextView) rowView.findViewById(R.id.tvDescription);
			viewHolder.tvSize = (TextView) rowView.findViewById(R.id.tvSize);
			
			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}
		
		viewHolder.tvName.setText(content.getSourceFileName());
		viewHolder.tvDescription.setText(content.getDescription());
		viewHolder.tvSize.setText((content.getSourceFileSize()) + " bytes");
		
		return rowView;
	}
}
