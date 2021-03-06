package com.cgthornt.avnet.fragment;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cgthornt.avnet.R;
import com.cgthornt.avnet.lib.BaseFragment;
import com.cgthornt.avnet.lib.Db;
import com.cgthornt.avnet.lib.ListItem;


public class DestinationFragment extends BaseFragment {

	private static Report report;
	
	@Override
	public boolean shouldPreloadData() {
		return report == null;
	}
	
	@Override
	public void preloadData() {
		report = new Report();
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.destination_report, container, false);
		
		// TextView summary = (TextView) v.findViewById(R.id.hours_summary);
		ListView list = (ListView) v.findViewById(R.id.destination_list);

		
		NiceListAdapter adapter = new NiceListAdapter(v.getContext(), R.id.destination_list, report.items);
		list.setAdapter(adapter);
		return v;
	}
	
	protected class Report {
		public double[] data;
		public int[] counts;
		public ArrayList<ListItem> items = new ArrayList<ListItem>();
		
		public Report() {
			data = Db.getDestTypePercents();
			counts = Db.getDestTypeCounts();
			
			for(int i = 0; i < data.length; i++) {
				ListItem l = new ListItem(
						"<b>" + percentFormat.format(data[i]) + "</b> " + Db.DEST_TYPES_STR[i],
						"<i>In " + counts[i] + " records</i>",
						Db.DEST_TYPES_IMG[i]);
				l.setCompareValue(data[i]);
				items.add(l);
			}
			// Sort items
			Collections.sort(items, new ListItem.ListItemComparitor());
		}
	}
	
}
