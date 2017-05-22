package lachongmedia.vn.nfc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.adapters.viewholders.PlaceViewHolder;
import lachongmedia.vn.nfc.adapters.viewholders.PlanViewHolder;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;

/**
 * Created by tranh on 5/19/2017.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanViewHolder> {
    LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = android.view.LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.plan_itemview, parent, false);
        PlanViewHolder planViewHolder = new PlanViewHolder(itemView);
        return planViewHolder;
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        holder.bind(loginRespon.getKehoach().getDsdiadiem().get(position));
    }

    @Override
    public int getItemCount() {
        return loginRespon.getKehoach().getDsdiadiem().size();
    }
}
