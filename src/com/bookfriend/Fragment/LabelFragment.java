package com.bookfriend.Fragment;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.bookfriend.R;
import com.bookfriend.AsyncTask.AddLabelTask;
import com.bookfriend.AsyncTask.LabelTask;
import com.bookfriend.AsyncTask.RemoveLabelTask;
import com.bookfriend.model.Label;

public class LabelFragment extends Fragment{
	 
	    private GridView gridView;
	    private EditText editText;
	    private  Button addButton;
	    private List<Label> labelList;
		private LabelTask asyTask;
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.fragment_label, container, false);
	        setGridView((GridView) rootView.findViewById(R.id.label_gridview));
	        showLableList();
	        editText = (EditText) rootView.findViewById(R.id.label_input);
	        addButton = (Button) rootView.findViewById(R.id.label_add_button);
	        return rootView;
	        
	    }
	    private void addLabel(){
	    	String labelName = editText.getText().toString();
			if(labelName == null || labelName.length()==0||labelName.equals("")){
				editText.setFocusable(true);
				return;
			}else{
				try{
	                AddLabelTask network = new AddLabelTask(labelName,this);
					network.execute();
					}catch(Exception e){
						Log.e("book", e.getMessage(),e);
					}
			}
			showLableList();
		}
	    @Override  
		public void onActivityCreated(Bundle savedInstanceState) {  
		        super.onActivityCreated(savedInstanceState);  
		        addButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						addLabel();
					}
				});
		        getGridView().setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View viem, int position, long id) {
								dialog(labelList.get(position).getId());
						return false;
					}
		        	
				});
		        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						BookUnderLabelFragment fragment = new BookUnderLabelFragment();
						fragment.setLableId(labelList.get(position).getId());
		                FragmentManager fragmentManager = getFragmentManager();
		                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
					}
				});
		 }
		public void showLableList() {
			try{
				asyTask = new LabelTask(this);
				asyTask.execute();
				}catch(Exception e){
					Log.d("message", e.getMessage());
				}
		}

		@Override
		public void onPause() {
			super.onPause();
			if(asyTask!=null)asyTask.cancel(true);
		}
		protected void dialog( final Long  labelId) {
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setMessage("确认删除该标签");
			builder.setTitle("提示");
			builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					RemoveLabelTask task = new RemoveLabelTask(LabelFragment.this);
					String para = labelId.toString();
					task.execute(para);
					dialog.dismiss();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					dialog.dismiss();
				}
			});
           builder.create().show();
		 
		}
		public GridView getGridView() {
			return gridView;
		}
		public void setGridView(GridView gridView) {
			this.gridView = gridView;
		}
		public List<Label> getLabelList() {
			return labelList;
		}
		public void setLabelList(List<Label> labelList) {
			this.labelList = labelList;
		}


}
