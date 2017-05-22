package lachongmedia.vn.nfc.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.respon.login.Diadiem;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;

/**
 * Created by tranh on 5/18/2017.
 */

public class PlaceViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time_max)
    TextView tvTimeMax;
    @BindView(R.id.tv_time)
    TextView tvTime;

    public PlaceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Diadiem diadiem) {
        tvName.setText(diadiem.getTendiadiem());
        tvTimeMax.setText(diadiem.getThoigiantoida() + " phút");
        tvTime.setText(Utils.getTimeWorkPlace(diadiem));
        Log.e("bind", "bind: ");
    }

}
