package lachongmedia.vn.nfc.database;

import android.util.Log;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import lachongmedia.vn.nfc.Utils;
import lachongmedia.vn.nfc.database.realm.realm_models.PlanWork;
import lachongmedia.vn.nfc.database.realm.RealmDatabase;
import lachongmedia.vn.nfc.database.respon.login.Dsdiadiem;
import lachongmedia.vn.nfc.database.respon.login.Dshuongdan;
import lachongmedia.vn.nfc.database.respon.login.Dsmatbang;
import lachongmedia.vn.nfc.database.respon.login.LoginRespon;

/**
 * Created by hao on 28/04/2017.
 */

public class DbContext {
    public static DbContext instance = new DbContext();
    private static final String TAG = DbContext.class.getSimpleName();
    private List<Dsdiadiem> listDiadiemMatBang;
    private List<Dshuongdan> dshuongdanList;
    private List<String> pathImageIssue;
    private List<String> paths;
    private List<PlanWork> planWorkList;
    private Date dateJoinPlace;
    private List<Dsdiadiem> listDiaDiemAll;


    public List<PlanWork> getPlanWorkList() {
        return planWorkList;
    }

    public void setPlanWorkList(List<PlanWork> planWorkList) {
        this.planWorkList = planWorkList;
    }

    public Date getDateJoinPlace() {
        return dateJoinPlace;
    }

    public List<Dsdiadiem> getListDiaDiemAll() {
        return listDiaDiemAll;
    }

    public void setListDiaDiemAll(List<Dsdiadiem> listDiaDiemAll) {
        this.listDiaDiemAll = listDiaDiemAll;
    }

    public void setDateJoinPlace(Date dateJoinPlace) {
        this.dateJoinPlace = dateJoinPlace;
    }

    private DbContext() {
        listDiadiemMatBang = new Vector<>();
        pathImageIssue = new Vector<>();
        planWorkList = new Vector<>();
        paths = new Vector<>();
    }

    public List<String> getPaths() {
        LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();
        paths.clear();
        for (int i = 0; i < loginRespon.getKehoach().getSite().getDsmatbang().size(); i++) {

            paths.add(loginRespon.getKehoach().getSite().getDsmatbang().get(i).getAnhmatbang().getPath());
        }
        for (String path : paths) {
            Log.e(TAG, String.format("onResponse: %s", path));
        }
        return paths;
    }


    public Dsmatbang findMbbyId(int id) {
        LoginRespon loginRespon = RealmDatabase.instance.getLoginRespon();
        for (int i = 0; i < loginRespon.getKehoach().getSite().getDsmatbang().size(); i++) {
            if (loginRespon.getKehoach().getSite().getDsmatbang().get(i).getId() == id) {
                return loginRespon.getKehoach().getSite().getDsmatbang().get(i);
            }
        }
        return null;
    }

    public void reset() {
        instance = new DbContext();
    }

    public void createPlanWorks(LoginRespon loginRespon) {

        if (loginRespon == null)
            return;
        try {
            planWorkList.clear();
            for (int i = 0; i < loginRespon.getKehoach().getSite().getDsmatbang().size(); i++) {
                for (int i1 = 0; i1 < loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().size(); i1++) {
                    String str = loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().get(i1).getThoigianlamviec();
                    Calendar cal = Calendar.getInstance();
                    Date date = cal.getTime();
                    Log.e(TAG, String.format("createPlanWorks: %s", date.toString()));
                    Log.e(TAG, String.format("createPlanWorks: %s", cal.toString()));
                    StringBuilder builder = new StringBuilder();
                    if (str != null && str.length() > 0) {
                        String arr[] = str.split(",");
                        for (int j = 0; j < arr.length; j++) {
                            if (arr[j].length() == 0)
                                return;
                            String[] numberArr = arr[j].split(":");
                            builder.append(cal.get(Calendar.YEAR)).append("-").append(cal.get(Calendar.MONTH) + 1).append("-").append(cal.get(Calendar.DAY_OF_MONTH)).append(" ")
                                    .append(numberArr[0]).append(":").append(numberArr[1]).append(":00");
                            Log.e(TAG, String.format("createPlanWorks: %s", builder.toString()));
                            PlanWork planWork = new PlanWork(loginRespon.getKehoach().getSite().getDsmatbang().get(i).getDsdiadiem().get(i1), Utils.stringToDate(builder.toString()));
                            planWorkList.add(planWork);
                            builder = new StringBuilder();
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            Log.e("err", "createPlanWorks: Loi so format");
        }
        Collections.sort(planWorkList, new Comparator<PlanWork>() {
            @Override
            public int compare(PlanWork o1, PlanWork o2) {
                if ((o1.getDate().getTime() - o2.getDate().getTime()) > 0) {
                    return 1;
                } else
                    return -1;
            }
        });
        for (PlanWork planWork : planWorkList) {
            Log.e(TAG, String.format("createPlanWorks: %s", planWork.toString()));
        }
        RealmDatabase.instance.insertOrUpdatePlanWork(planWorkList);

    }

    public PlanWork getPlanWorkWithDate(final Date date, Dsdiadiem dsdiadiem) {
        if (planWorkList.size() == 0) {
            planWorkList = RealmDatabase.instance.getPlanWorkList();
        }
        List<Date> dates = new Vector<>();
        List<PlanWork> dsPlans = new Vector<>();
        for (int i = 0; i < planWorkList.size(); i++) {
            if (planWorkList.get(i).getDsdiadiem().getId() == dsdiadiem.getId() && (planWorkList.get(i).isCompleted() == 0 || planWorkList.get(i).isCompleted()==1)) {
                dsPlans.add(planWorkList.get(i));
                dates.add(planWorkList.get(i).getDate());

            }
        }
        Date closest = Collections.min(dates, new Comparator<Date>() {
            public int compare(Date d1, Date d2) {
                long diff1 = Math.abs(d1.getTime() - date.getTime());
                long diff2 = Math.abs(d2.getTime() - date.getTime());
                return DbContext.this.compare(diff1, diff2);
            }
        });
        PlanWork planWork = null;
        for (int i = 0; i < dsPlans.size(); i++) {
            if (dsPlans.get(i).getDate().getTime() == closest.getTime()) {
                planWork = dsPlans.get(i);
            }
        }
        Log.d(TAG, String.format("getPlanWorkWitohDate123: %s", planWork));
        return planWork;
    }

    private int compare(long x, long y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    public PlanWork getPlaceWorkNext() {
        Date date = new Date();
        Log.e(TAG, String.format("getPlaceWorkNext: %s", date));
        if (planWorkList.size() == 0) {
            planWorkList = RealmDatabase.instance.getPlanWorkList();
        }
        try {
            for (int i = 0; i < planWorkList.size(); i++) {

                if (i < (planWorkList.size() - 1)) {
                    Log.e(TAG, String.format("getPlaceWorkNext: %s %s", date.compareTo(planWorkList.get(i).getDate()), date.compareTo(planWorkList.get(i + 1).getDate())));
                    if (date.compareTo(planWorkList.get(i).getDate()) >= 0 && date.compareTo(planWorkList.get(i + 1).getDate()) < 0) {
                        return planWorkList.get(i + 1);
                    }
                }
            }
            return planWorkList.get(planWorkList.size() - 1);
        } catch (Exception e) {
            Log.e(TAG, String.format("getPlaceWorkNext: %s", e.toString()));
            if (planWorkList.size() != 0)
                return planWorkList.get(planWorkList.size() - 1);
            else
                return null;
        }

    }

    private boolean compareTwoDate(Date dateMax, Date dateMin, Date date) {
        return date.compareTo(dateMin) >= 0 && date.compareTo(dateMax) <= 0;
    }

    public List<Dsdiadiem> getListDiadiemMatBang() {
        return listDiadiemMatBang;
    }

    public void setListDiadiemMatBang(List<Dsdiadiem> listDiadiemMatBang) {
        this.listDiadiemMatBang = listDiadiemMatBang;
    }

    public List<Dshuongdan> getDshuongdanList() {
        return dshuongdanList;
    }

    public void setDshuongdanList(List<Dshuongdan> dshuongdanList) {
        this.dshuongdanList = dshuongdanList;
    }

    public void setPathImageIssue(List<String> pathImageIssue) {
        this.pathImageIssue = pathImageIssue;
    }

    public List<String> getPathImageIssue() {
        return pathImageIssue;
    }
}
