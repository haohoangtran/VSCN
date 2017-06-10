package lachongmedia.vn.nfc.adapters.viewholders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.adapters.ImagesAdapter;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.Dschecklist;
import lachongmedia.vn.nfc.eventbus_event.CameraEvent;
import vn.lachongmedia.ksmartg.chupanhlibrary.activities.ChupAnhActivity;

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
    RecyclerView rvImageQuestions;
    public CheckListViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        dialog = new Dialog(itemView.getContext());
        dialog.setContentView(R.layout.dialog_question);
        rvImageQuestions= (RecyclerView) dialog.findViewById(R.id.rv_image_questions);

        dialog.setCancelable(false);
    }

    private void good(Dschecklist dschecklist) {
        cbGood.setVisibility(View.VISIBLE);
        cbGood.setChecked(true);
        ivBad.setVisibility(View.GONE);
        ivGood.setVisibility(View.GONE);
        cbBad.setVisibility(View.GONE);
        RealmDatabase.instance.updateDsCheckList(dschecklist, 1);
    }

    private void bad(Dschecklist dschecklist) {
        cbBad.setVisibility(View.VISIBLE);
        cbBad.setChecked(true);
        ivBad.setVisibility(View.GONE);
        ivGood.setVisibility(View.GONE);
        cbGood.setVisibility(View.GONE);
        RealmDatabase.instance.updateDsCheckList(dschecklist, 0);
    }

    private void none(Dschecklist dschecklist) {
        cbBad.setVisibility(View.GONE);
        cbGood.setVisibility(View.GONE);
        ivBad.setVisibility(View.VISIBLE);
        ivGood.setVisibility(View.VISIBLE);
        RealmDatabase.instance.updateDsCheckList(dschecklist, 2);
    }

    public void bind(final Dschecklist dschecklist) {

        tvNameCv.setText(dschecklist.getTenchecklist());
        tvPP.setText(dschecklist.getPhuongphap());
        dialog.setTitle(dschecklist.getTenchecklist());
        tvRequite.setText(dschecklist.getYeucau());
        if (dschecklist.getTrangthaichupanh()) {
            ivCapture.setVisibility(View.VISIBLE);
        } else {
            ivCapture.setVisibility(View.GONE);
        }
        if (dschecklist.getTrangthai() == 0) {
            bad(dschecklist);
        } else if (dschecklist.getTrangthai() == 1) {
            good(dschecklist);
        } else if (dschecklist.getTrangthai() == 2) {
            none(dschecklist);
        }
        addListenner(dschecklist);
    }

    private void addListenner(final Dschecklist dschecklist) {
        ivGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                good(dschecklist);
            }
        });
        ivBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bad(dschecklist);
            }
        });
        ivCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CameraEvent(dschecklist));
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvImageQuestions.setAdapter(new ImagesAdapter(dschecklist.getPathString()));
                rvImageQuestions.setLayoutManager(new GridLayoutManager(itemView.getContext(),3));
                dialog.show();

                // set the custom dialog components - text, image and button
                LinearLayout llGood = (LinearLayout) dialog.findViewById(R.id.ll_good);
                LinearLayout llBad = (LinearLayout) dialog.findViewById(R.id.ll_bad);
                LinearLayout llNotCheck = (LinearLayout) dialog.findViewById(R.id.ll_notcheck);
                llGood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        good(dschecklist);
                        Log.e(TAG, "onClick: ccccc");
                        dialog.dismiss();
                    }
                });
                llBad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bad(dschecklist);
                        dialog.dismiss();
                    }
                });
                llNotCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        none(dschecklist);
                        dialog.dismiss();
                    }
                });
                Log.e(TAG, String.format("onClick: %s", dschecklist.toString()));

                dialog.show();
            }
        });

    }


}
