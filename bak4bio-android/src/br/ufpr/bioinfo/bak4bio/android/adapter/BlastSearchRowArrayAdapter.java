package br.ufpr.bioinfo.bak4bio.android.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.agivis.framework.async.AsyncExecutor;
import br.ufpr.bioinfo.bak4bio.android.R;
import br.ufpr.bioinfo.bak4bio.android.model.Blast;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BlastSearchRowArrayAdapter<T> extends ArrayAdapter<T> {

	private Activity context; 
	private List<T> blasts = new ArrayList<T>();
	protected AsyncExecutor executor = new AsyncExecutor();
	
	public BlastSearchRowArrayAdapter(Activity context, List<T> blasts) {
		super(context, R.layout.blast_search_row, blasts);
		this.context = context;
		this.blasts = blasts;
	}
	
	static class ViewHolder {
		public TextView tvTitle;
		public TextView tvStartAt;
		public TextView tvStatus;
		public TextView tvEndAt;
		public TextView tvOutput;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		Blast blast = (Blast) blasts.get(position);
		final ViewHolder viewHolder;
		
		if (rowView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.blast_search_row, parent, false);
			
			viewHolder.tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
			viewHolder.tvStartAt = (TextView) rowView.findViewById(R.id.tvStartAt);
			viewHolder.tvStatus = (TextView) rowView.findViewById(R.id.tvStatus);
			viewHolder.tvEndAt = (TextView) rowView.findViewById(R.id.tvEndAt);
			viewHolder.tvOutput = (TextView) rowView.findViewById(R.id.tvOutput);
			
			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}
		
		viewHolder.tvTitle.setText(blast.getTitle());
		viewHolder.tvStatus.setText(blast.getStatus());
		
		if (blast.getStartAt() != null) {
			viewHolder.tvStartAt.setText(new SimpleDateFormat("MM/dd/yy HH:MM").format(blast.getStartAt()));
		}else {
			viewHolder.tvStartAt.setText("Not yet");
		}
		
		if (blast.getEndAt() != null) {
			viewHolder.tvEndAt.setText(new SimpleDateFormat("MM/dd/yy HH:MM").format(blast.getEndAt()));
		}else {
			viewHolder.tvEndAt.setText("Not yet");
		}
		
		if (blast.getOutput() == null) {
			viewHolder.tvOutput.setText("Not yet");
		}else {
			viewHolder.tvOutput.setText(blast.getOutput().getDescription());
		}
		
		return rowView;
	}
}
