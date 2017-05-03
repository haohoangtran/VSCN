package lachongmedia.vn.nfc.database;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import lachongmedia.vn.nfc.database.models.CheckMember;
import lachongmedia.vn.nfc.database.models.Member;
import lachongmedia.vn.nfc.database.models.WC;

/**
 * Created by hao on 28/04/2017.
 */

public class DbContext {
    public static final DbContext instance = new DbContext();
    private List<Member> members;
    private List<WC> wcList;
    private List<CheckMember> checkMembers;
    private Date dateStart;
    private Date dateStop;
    private int posTut;

    public int getPosTut() {
        return posTut;
    }

    public void setPosTut(int posTut) {
        this.posTut = posTut;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateStop(Date dateStop) {
        this.dateStop = dateStop;
    }

    private DbContext() {
        members = new Vector<>();
        wcList = new Vector<>();
        checkMembers=new Vector<>();
        addDumData();
        posTut=0;
        addCheckMembers();
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateStop() {
        return dateStop;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<WC> getWcList() {
        return wcList;
    }

    public List<CheckMember> getCheckMembers() {
        return checkMembers;
    }

    private void addCheckMembers() {
        CheckMember checkMember1=new CheckMember("Giây vệ sinh","Mắt thường, bằng tay","Đầy đủ giấy ở các ngăn, lắp đúng chiều, dễ lấy");
        CheckMember checkMember2=new CheckMember("Bồn cầu/ Bồn tiểu","Mắt thường, bằng tay","Sạch vết bẩn,không cặn vàng, không tắc, không mùi\t");
        CheckMember checkMember3=new CheckMember("Gương","Mắt thường, bằng tay","Sáng trong, không vết bẩn, vân tay\t");
        CheckMember checkMember4=new CheckMember("Vòi nước","Mắt thường, bằng tay","Sạch sẽ, không hỏng hóc rò rỉ\t");
        CheckMember checkMember5=new CheckMember("Bồn rửa tay","Mắt thường, bằng tay","Sạch sẽ, không vết bẩn, khô giáo, không vẩy nước trên mặt\t");
        CheckMember checkMember6=new CheckMember("Thùng rác","Mắt thường, bằng tay","Phải được thay thường xuyên, nắp gọn gàng, sạch sẽ\t");
        CheckMember checkMember7=new CheckMember("Lỗ thoát sàn","Mắt thường","Vệ sinh hàng ngày, sạch sẽ, không tắc");
        CheckMember checkMember8=new CheckMember("Mùi","Ngửi","Thơm, không có mùi nước tiểu, mùi lạ");
        CheckMember checkMember9=new CheckMember("Sàn/Tường/Cửa","Mắt thường, bằng tay","Sạch, không bụi bẩn, không hỏng hóc vỡ sứt góc cạnh\t");
        CheckMember checkMember10=new CheckMember("Máy sấy tay/ Nước rửa tay","Mắt thường, ngửi","Hoạt động bình thường, nước rửa tay đầy đủ\t");
        checkMembers.add(checkMember1);
        checkMembers.add(checkMember2);
        checkMembers.add(checkMember3);
        checkMembers.add(checkMember4);
        checkMembers.add(checkMember5);
        checkMembers.add(checkMember6);
        checkMembers.add(checkMember7);
        checkMembers.add(checkMember8);
        checkMembers.add(checkMember9);
        checkMembers.add(checkMember10);
    }

    public void addDumData() {
        Member member1 = new Member("846AC9470C4002E0", "Nguyễn Đức Thắng");
        Member member2 = new Member("2570C9470C4002E0", "Trần Hán Hiếu");
        Member member3 = new Member("4D70C9470C4002E0", "Bùi Quang Minh");
        Member member4 = new Member("2D72C9470C4002E0", "Nguyễn Thị Lan");
        WC wc=new WC("6B74C9470C4002E0","Nhà vệ sinh tầng 1","Cuối hành lang bên trái",60,null);
        members.add(member1);
        members.add(member2);
        members.add(member3);
        members.add(member4);
        wcList.add(wc);
    }

    public Member findMemberWithId(String id) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getUID().equalsIgnoreCase(id)) {
                return members.get(i);
            }
        }
        return null;
    }
    public WC findWCWithId(String id) {
        for (int i = 0; i < wcList.size(); i++) {
            if (wcList.get(i).getUID().equalsIgnoreCase(id)) {
                return wcList.get(i);
            }
        }
        return null;
    }

}
