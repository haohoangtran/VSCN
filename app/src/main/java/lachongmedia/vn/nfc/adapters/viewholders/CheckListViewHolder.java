package lachongmedia.vn.nfc.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.database.models.CheckMember;
import lachongmedia.vn.nfc.eventbus_event.CameraEvent;

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
    public void bind(final CheckMember checkMember){
        tvNameCv.setText(checkMember.getHangMuc());
        tvPP.setText(checkMember.getPhuongPhap());
        tvRequite.setText(checkMember.getRequitement());
        if (checkMember.isGood()){
            cbGood.setChecked(true);
            cbBad.setChecked(false);
        }else {
            cbGood.setChecked(false);
            cbBad.setChecked(true);
        }
        cbBad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbGood.setChecked(false);
                    checkMember.setGood(false);
                } else {
                    cbGood.setChecked(true);
                    checkMember.setGood(true);
                }
                EventBus.getDefault().post(new CameraEvent(checkMember));
            }
        });
        cbGood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbBad.setChecked(false);
                } else {
                    cbBad.setChecked(true);
                }
            }
        });
    }
}
