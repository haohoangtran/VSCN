package lachongmedia.vn.nfc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.adapters.viewholders.CheckListViewHolder;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.models.CheckMember;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.Diadiem;
import lachongmedia.vn.nfc.eventbus_event.CameraEvent;

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
        if (RealmDatabase.instance.getDiaDiemSave().size() != 0)
            holder.bind(RealmDatabase.instance.getDiaDiemSave().get(0).getDsdiadiem().getDschecklist().get(position));
    }

    @Override
    public int getItemCount() {
        if (RealmDatabase.instance.getDiaDiemSave().size() != 0) {
            return RealmDatabase.instance.getDiaDiemSave().get(0).getDsdiadiem().getDschecklist().size();
        }
        return 0;
    }
}
