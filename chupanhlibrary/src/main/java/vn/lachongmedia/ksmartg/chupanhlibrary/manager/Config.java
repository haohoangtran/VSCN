package vn.lachongmedia.ksmartg.chupanhlibrary.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;


public class Config {
    public static final String VersionCode = "1.3";
    public static final String VersionCode_Login = "1.3.9.8";
    public static final String GOOGLE_PROJECT_ID = "534513783462";
    public static final String TAG_RESULT = "result";
    public static final String TAG_DATA = "data";
    public static final String TAG_KHACHHANG = "KhachHang";
    public static final String TAG_ID = "id";
    public static final String TAG_TEN = "ten";
    public static final String TAG_DIACHI = "diachi";
    public static final String TAG_SDT = "sodienthoai";
    public static final String TAG_KINHDO = "kinhdo";
    public static final String TAG_VIDO = "vido";
    public static String MSG_THONGBAO = "";
    public static String thoigianvaodiem = "";
    public static int chophepguibantinketnoi = 0;
    public static int thoigianguibantinketnoi = 0;
    public static final String TAG_KINHDOS = "KinhDo";
    public static final String TAG_VIDOS = "ViDo";

    public static final String TAG_MAHANG = "MaHang";
    public static final String TAG_HANGHOA = "HangHoa";
    public static final String TAG_GIABANBUON = "GiaBanBuon";
    public static final String TAG_GIABANLE = "GiaBanLe";
    public static final String TAG_DONVI = "DonVi";
    public static final String TAG_SOLUONG = "SoLuong";

    public static final String ATTR_ERROR = "error";
    public static final String ATTR_IDNV = "idnv";

    public static final String KEY_IDQLLH = "idqllh";
    public static final String KEY_IDNV = "idnhanvien";
    public static final String KEY_TENNV = "tennv";
    public static final String KEY_BUONORLE = "buonorle";
    public static final String KEY_ACCOUNT = "username";

    public static final String TAG_STATUS = "status";
    public static final String TAG_MSG = "msg";
    public static final String UrlServer = "http://sv.ksmart.vn/";// build

    //	public static String UrlServer = "http://jav.ksmart.vn/";// debug
    public static final String TAG_URLSERVER = "urlserver";
    public static final String TAG_TENCUAHANG = "TenCuaHang";
    public static final String TAG_SDTCUAHANG = "SoDienThoai";
    public static final String TAG_DAIDIEN = "NguoiDaiDien";
    public static final String TAG_EMAIL = "Email";
    public static final String TAG_VIETTAT = "TenVietTat";
    public static final String TAG_STT = "STT";
    public static final String TAG_IDCHECKIN = "idcheckin";
    public static final String TAG_CUAHANGDADI = "cuahangdadi";
    public static final String TAG_LOTRINH = "lotrinhdadi";
    public static final String TAG_THOIGIANBAOMATKETNOI = "TGCanhBaoMatKetNoi";
    public static final String TAG_THOIGIANCAPNHAT = "thoigiancapnhatbantin";

    public static final String TAG_THONGTINVAODIEM = "thongtinvaodiem";
    public static final String TAG_THONGTINCUAHANG = "thongtincuahang";
    public static final String TAG_DIACHICUAHANG = "DiaChi";
    public static final String TAG_IDKHACHHANG = "idkhachhang";
    public static final String TAG_GOIUNGDUNG = "goiungdung";
    public static final String TAG_IDCT = "idct";
    public static final String TAG_SOTINNHANCHUADOC = "sotinnhanchuadoc";
    public static final Integer TAG_THOIGIANGIOIHAN = 10;

    public static final String TAG_DSCUAHANG = "danhsachcuahang";
    public static final String TAG_IDCUAHANG = "idcuahang";
    public static final String TAG_IMGURL = "Imgurl";
    public static final String TAG_NGUOILAPDAT = "NguoiLapDat";
    public static final String TAG_DTNGUOILAPDAT = "DTNguoiLapDat";
    public static final String TAG_DIDONG = "DiDong";

    //Biến check đăng nhập, dăng xuất
    public static boolean TAG_ISLOGING;

    //Biến check activity runable
    public static boolean TAG_ACTIVITY_RUNNING;

    //Biến lưu tên tham chiếu, khách hàng, tổng tiền đơn hàng, ghi chú
    public static String MATHAMCHIEU;
    public static String KHACHHANG;
    public static String TONGTIENDONHANG;
    public static String GHICHU;

    //Biến lưu activity được active hay không
    public static String ACTIVE_ACTIVITY;

    //Biến lưu list đơn hàng tạm thời sau khi filter;
    public static ArrayList<HashMap<String, String>> list_filter = new ArrayList<HashMap<String, String>>();

    //Biến lưu list mặt hàng tạm thời sau khi filter;
    public static Activity Current_Activity;

    static {
        try {
            Current_Activity = new Activity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int messcount = 0;
    public static boolean daDangXuat = false;
}
