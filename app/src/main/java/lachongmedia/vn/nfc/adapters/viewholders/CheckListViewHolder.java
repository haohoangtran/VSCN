package lachongmedia.vn.nfc.adapters.viewholders;

import android.app.Activity;
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
import tyrantgit.explosionfield.ExplosionField;

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
    Dialog dialog;
    @BindView(R.id.iv_capture)
    ImageView ivCapture;

    public CheckListViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        dialog = new Dialog(itemView.getContext());
        dialog.setContentView(R.layout.dialog_question);
        dialog.setCancelable(false);
    }

    private void good(CheckMember checkMember) {
        cbGood.setVisibility(View.VISIBLE);
        cbGood.setChecked(true);
        ivBad.setVisibility(View.GONE);
        ivGood.setVisibility(View.GONE);
        cbBad.setVisibility(View.GONE);
        checkMember.setType(1);
    }

    private void bad(CheckMember checkMember) {
        cbBad.setVisibility(View.VISIBLE);
        cbBad.setChecked(true);
        ivBad.setVisibility(View.GONE);
        ivGood.setVisibility(View.GONE);
        cbGood.setVisibility(View.GONE);
        checkMember.setType(0);
    }

    private void none(CheckMember checkMember) {
        cbBad.setVisibility(View.GONE);
        cbGood.setVisibility(View.GONE);
        ivBad.setVisibility(View.VISIBLE);
        ivGood.setVisibility(View.VISIBLE);
        checkMember.setType(2);
    }

    public void bind(final CheckMember checkMember) {
        tvNameCv.setText(checkMember.getHangMuc());
        tvPP.setText(checkMember.getPhuongPhap());
        Log.e(TAG, String.format("bind: %s", checkMember.toString()));
        dialog.setTitle(checkMember.getHangMuc());
        tvRequite.setText(checkMember.getRequitement());

        if (checkMember.getType() == 1) {
            good(checkMember);
        } else if (checkMember.getType() == 0) {
            bad(checkMember);
        } else {
            none(checkMember);
        }
        addListenner(checkMember);

    }

    private void addListenner(final CheckMember checkMember) {
        ivGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                good(checkMember);
            }
        });
        ivBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bad(checkMember);
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                // set the custom dialog components - text, image and button
                LinearLayout llGood = (LinearLayout) dialog.findViewById(R.id.ll_good);
                LinearLayout llBad = (LinearLayout) dialog.findViewById(R.id.ll_bad);
                LinearLayout llNotCheck = (LinearLayout) dialog.findViewById(R.id.ll_notcheck);
                llGood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        good(checkMember);
                        Log.e(TAG, "onClick: ccccc");
                        dialog.dismiss();
                    }
                });
                llBad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bad(checkMember);
                        dialog.dismiss();
                    }
                });
                llNotCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        none(checkMember);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        ivCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CameraEvent(checkMember));
            }
        });
    }


}
