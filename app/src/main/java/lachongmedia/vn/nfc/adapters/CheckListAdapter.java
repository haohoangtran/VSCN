package lachongmedia.vn.nfc.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.adapters.viewholders.CheckListViewHolder;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;

import static android.content.ContentValues.TAG;

/**
 * Created by hao on 29/04/2017.
 */

public class CheckListAdapter extends RecyclerView.Adapter<CheckListViewHolder> {

    @Override
    public CheckListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = android.view.LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.checklist_item, parent, false);
        CheckListViewHolder checkListViewHolder = new CheckListViewHolder(itemView);
        return checkListViewHolder;
    }


    @Override
    public void onBindViewHolder(CheckListViewHolder holder, int position) {
        if (RealmDatabase.instance.getDiaDiemSave().size() != 0) {
            holder.bind(RealmDatabase.instance.getDiaDiemSave().get(0).getDsdiadiem().getDschecklist().get(position));
            Log.e("hihihi", String.format("onBindViewHolder: %s", RealmDatabase.instance.getDiaDiemSave().get(0).getDsdiadiem().getDschecklist().get(position)));
        }
        Log.e(TAG, String.format("onBindViewHolder: %s", RealmDatabase.instance.getDiaDiemSave().size()));
    }

    @Override
    public int getItemCount() {
        int a;
        if (RealmDatabase.instance.getDiaDiemSave().size() != 0) {
            a = RealmDatabase.instance.getDiaDiemSave().get(0).getDsdiadiem().getDschecklist().size();
        } else {
            a = 0;
        }
        Log.e(TAG, "getItemCount: " + a);
        return a;
    }
}
