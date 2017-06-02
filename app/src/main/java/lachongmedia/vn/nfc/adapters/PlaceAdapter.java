package lachongmedia.vn.nfc.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.activities.TutorialActivity;
import lachongmedia.vn.nfc.adapters.viewholders.PlaceViewHolder;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;

/**
 * Created by tranh on 5/18/2017.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {
    public PlaceAdapter() {
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = android.view.LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.place_itemview, parent, false);
        PlaceViewHolder placeViewHolder = new PlaceViewHolder(itemView);
        return placeViewHolder;
    }

    @Override
    public void onBindViewHolder(final PlaceViewHolder holder, final int position) {
        Log.e("vv", String.format("onBindViewHolder: %s", position));
        final Diadiem diadiem = DbContext.instance.getDiadiems().get(position);
        holder.bind(diadiem);
        final LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContext.instance.setDshuongdanList(loginRespon.getKehoach().getDsdiadiem().get(position).getDshuongdan());
                Intent intent = new Intent(holder.itemView.getContext(), TutorialActivity.class);
                intent.putExtra("name", loginRespon.getKehoach().getDsdiadiem().get(position).getDiadiem().getTendiadiem());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DbContext.instance.getDiadiems().size();
    }
}
