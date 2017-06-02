package lachongmedia.vn.nfc.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;

/**
 * Created by tranh on 5/19/2017.
 */

public class PlanViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time_max)
    TextView tvTimeMax;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_matbang)
    TextView tvMatBang;

    public PlanViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Dsdiadiem dsdiadiem) {
        tvName.setText(dsdiadiem.getTendiadiem());
        tvTimeMax.setText(dsdiadiem.getThoigiantoida() + " phút");
        tvTime.setText(Utils.getTimeWorkPlace(dsdiadiem));
        Log.e("bind", "bind: ");
        if (DbContext.instance.findMbbyId(dsdiadiem.getIdMatbang()) != null)
            tvMatBang.setText(DbContext.instance.findMbbyId(dsdiadiem.getIdMatbang()).getTenmatbang());
    }
}
