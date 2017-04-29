package lachongmedia.vn.nfc.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.database.models.CheckMember;

/**
 * Created by hao on 29/04/2017.
 */

public class CheckListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_name)
    TextView tvNameCv;
    @BindView(R.id.tv_pp)
    TextView tvPP;
    @BindView(R.id.tv_requitement)
    TextView tvRequite;
    @BindView(R.id.cb_bad)
    CheckBox cbBad;
    @BindView(R.id.cb_good)
    CheckBox cbGood;
    public CheckListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void bind(CheckMember checkMember){
        tvNameCv.setText("Tên: "+checkMember.getHangMuc());
        tvPP.setText("Phương pháp: "+checkMember.getPhuongPhap());
        tvRequite.setText("Yêu cầu: "+checkMember.getRequitement());
        cbBad.setChecked(true);
        cbGood.setChecked(false);
    }
}
