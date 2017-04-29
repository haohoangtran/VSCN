package lachongmedia.vn.nfc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.adapters.viewholders.CheckListViewHolder;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.models.CheckMember;

/**
 * Created by hao on 29/04/2017.
 */

public class CheckListAdapter extends RecyclerView.Adapter<CheckListViewHolder>{

    @Override
    public CheckListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = android.view.LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.checklist_item, parent, false);
        CheckListViewHolder checkListViewHolder=new CheckListViewHolder(itemView);
        return checkListViewHolder;
    }

    @Override
    public void onBindViewHolder(CheckListViewHolder holder, int position) {
        CheckMember checkMember=DbContext.instance.getCheckMembers().get(position);
        holder.bind(checkMember);

    }

    @Override
    public int getItemCount() {
        return DbContext.instance.getCheckMembers().size();
    }
}
