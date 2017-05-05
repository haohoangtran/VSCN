package lachongmedia.vn.nfc.adapters.viewholders;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private static final String TAG = CheckListViewHolder.class.getSimpleName();
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
    @BindView(R.id.iv_happy)
    ImageView ivGood;
    @BindView(R.id.iv_unhapy)
    ImageView ivBad;

    public CheckListViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(itemView.getContext());
                dialog.setContentView(R.layout.dialog_question);
                dialog.setTitle("Title");
                dialog.setCancelable(false);
                dialog.show();

                // set the custom dialog components - text, image and button
                LinearLayout llGood = (LinearLayout) dialog.findViewById(R.id.ll_good);
                LinearLayout llBad = (LinearLayout) dialog.findViewById(R.id.ll_bad);
                LinearLayout llNotCheck = (LinearLayout) dialog.findViewById(R.id.ll_notcheck);
                llGood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        good();
                        Log.e(TAG, "onClick: ccccc");
                        dialog.dismiss();
                    }
                });
                llBad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bad();
                        dialog.dismiss();
                    }
                });
                llNotCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        none();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    private void good() {
        cbGood.setVisibility(View.VISIBLE);
        cbGood.setChecked(true);
        ivBad.setVisibility(View.GONE);
        ivGood.setVisibility(View.GONE);
        cbBad.setVisibility(View.GONE);
    }

    private void bad() {
        cbBad.setVisibility(View.VISIBLE);
        cbBad.setChecked(true);
        ivBad.setVisibility(View.GONE);
        ivGood.setVisibility(View.GONE);
        cbGood.setVisibility(View.GONE);
    }

    public void bind(final CheckMember checkMember) {
        tvNameCv.setText(checkMember.getHangMuc());
        tvPP.setText(checkMember.getPhuongPhap());
        tvRequite.setText(checkMember.getRequitement());
        if (checkMember.getType() == 1) {
            good();
        } else if (checkMember.getType() == 0) {
            bad();
        } else {
            none();
        }

    }

    private void none() {
        cbBad.setVisibility(View.GONE);
        cbGood.setVisibility(View.GONE);
        ivBad.setVisibility(View.VISIBLE);
        ivGood.setVisibility(View.VISIBLE);
    }
}
