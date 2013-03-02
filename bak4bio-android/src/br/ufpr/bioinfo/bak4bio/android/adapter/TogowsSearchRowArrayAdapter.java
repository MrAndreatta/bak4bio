package br.ufpr.bioinfo.bak4bio.android.adapter;

import java.util.ArrayList;
import java.util.List;

import br.com.agivis.framework.async.AsyncExecutor;
import br.com.agivis.framework.async.AsyncOperation;
import br.ufpr.bioinfo.bak4bio.android.R;
import br.ufpr.bioinfo.bak4bio.android.model.Database;
import br.ufpr.bioinfo.bak4bio.android.model.Resource;
import br.ufpr.bioinfo.bak4bio.android.operations.TogowsEntryOperation;
import br.ufpr.bioinfo.bak4bio.android.operations.Operations;

import android.app.Activity;
import android.provider.ContactsContract.Contacts.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TogowsSearchRowArrayAdapter<T> extends ArrayAdapter<T> {

	private Activity context; 
	private List<T> entryIds = new ArrayList<T>();
	private Resource resource;
	protected AsyncExecutor executor = new AsyncExecutor();
	private Operations operations;
	private Database database;
	
	public TogowsSearchRowArrayAdapter(Activity context, List<T> entryIds, Resource resource, Operations operations, Database dataBase) {
		super(context, R.layout.togows_search_row, entryIds);
		this.context = context;
		this.entryIds = entryIds;
		this.resource = resource;
		this.operations = operations;
		this.database = dataBase;
	}
	
	static class ViewHolder {
		public TextView tvEntryId;
		public TextView tvDescription;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		String entryId = (String) entryIds.get(position);
		final ViewHolder viewHolder;
		
		if (rowView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.togows_search_row, parent, false);
			
			viewHolder.tvEntryId = (TextView) rowView.findViewById(R.id.tvEntryId);
			viewHolder.tvDescription = (TextView) rowView.findViewById(R.id.tvDescription);
			
			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}
		
		viewHolder.tvEntryId.setText(entryId);
		
		if (viewHolder.tvDescription.getText() != null && !viewHolder.tvDescription.getText().equals("")) {
			return rowView;
		}

		final TogowsEntryOperation entryOperation = operations.togowsEntryOperation();
		entryOperation.idSetting(entryId);
		entryOperation.databaseSetting(database.getName());
		
		if (database.getFields() != null && database.getFields().size() > 0) {
			entryOperation.fieldSetting(database.getFields().get(0).getName());
		}
		
		executor.executeSilent(context, context.getString(R.string.waiting_label), new AsyncOperation() {
			public void execute() throws Exception {
				entryOperation.search();
			}
			
			public void done() {
				viewHolder.tvDescription.setText(entryOperation.getResult());
			}
			
			public void failed(Exception e) {
				viewHolder.tvDescription.setText("Erro");
			}
		}); 
		
		return rowView;
	}
}
