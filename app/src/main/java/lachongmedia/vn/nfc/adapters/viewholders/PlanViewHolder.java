package lachongmedia.vn.nfc.adapters.viewholders;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import lachongmedia.vn.nfc.R;
import lachongmedia.vn.nfc.database.DbContext;
import lachongmedia.vn.nfc.database.realm.realm_models.PlanWork;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;

/**
 * Created by tranh on 5/19/2017.
 */

public class PlanViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_planitemview)
    LinearLayout llPlanItemView;

    public PlanViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void bind(PlanWork planWork) {
        String DATE_FORMAT_NOW = "HH:mm";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT_NOW);
        Dsdiadiem dsdiadiem = planWork.getDsdiadiem();
        long minutemax = dsdiadiem.getThoigiantoida() * 60000;
        Date date = planWork.getDate();
        String stringDateStart = sdf.format(date);
        date.setTime(date.getTime() + minutemax);
        String stringDateStop = sdf.format(date);
        String s = "";
        if (DbContext.instance.findMbbyId(dsdiadiem.getIdMatbang()) != null)
            s = "- " + DbContext.instance.findMbbyId(dsdiadiem.getIdMatbang()).getTenmatbang();
        tvName.setText(String.format("%s %s", dsdiadiem.getTendiadiem(), s));
        tvTime.setText(String.format("%s - %s", stringDateStart, stringDateStop));
        Log.e("bind", "bind: ");
        if (planWork.isCompleted()==1){
            llPlanItemView.setBackgroundColor(Color.GREEN);
        }else  if (planWork.isCompleted()==-1){
            llPlanItemView.setBackgroundColor(Color.GRAY);
        }else if (planWork.isCompleted()==0)
            llPlanItemView.setBackgroundColor(Color.WHITE);
        else llPlanItemView.setBackgroundColor(Color.YELLOW);
    }
}
