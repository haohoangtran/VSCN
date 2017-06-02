package lachongmedia.vn.nfc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.adapters.viewholders.PlanViewHolder;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;

/**
 * Created by tranh on 5/19/2017.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanViewHolder> {
    LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();

    public PlanAdapter() {
        dsdiadiems = new Vector<>();
        for (int i = 0; i < loginRespon.getKehoach().getSite().getDsmatbang().size(); i++) {
            for (int i1 = 0; i1 < loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().size(); i1++) {
                dsdiadiems.add(loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().get(i1));
            }
        }
    }

    public List<Dsdiadiem> dsdiadiems;

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = android.view.LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.plan_itemview, parent, false);
        PlanViewHolder planViewHolder = new PlanViewHolder(itemView);
        return planViewHolder;
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        holder.bind(dsdiadiems.get(position));
    }

    @Override
    public int getItemCount() {
        return dsdiadiems.size();
    }
}
