
package boss.Bossmini;

import boss.Boss;
import boss.BossData;
import boss.BossID;
import consts.BossStatus;
import static consts.BossType.ANTROM;
import consts.ConstPlayer;
import item.Item;
import java.util.List;
import map.ItemMap;
import map.Zone;
import player.Player;
import services.EffectSkillService;
import services.Service;
import services.SkillService;
import map.Service.ChangeMapService;
import map.Service.MapService;
import player.Service.PlayerService;
import services.ItemService;
import skill.Skill;
import utils.Util;

public class Shark extends Boss {

   
    private long lastTimeJoinMap;
    private static final long timeChangeMap = 1000;

    public Shark() throws Exception {
        super(BossID.SHARK, new BossData(
                "Shark ",
                ConstPlayer.TRAI_DAT,
                new short[]{1496, 1497, 1498, -1, -1, -1},
                0,
                new int[]{300},
                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 79, 80, 81, 82, 83, 84, 92, 93, 94, 96, 97, 98, 99, 100, 102, 103, 104, 105, 106, 107, 108, 109, 110},
                new int[][]{
               
                    {Skill.THAI_DUONG_HA_SAN, 3, 50000}},
                new String[]{"|-1|Tui là người của Làng Sương Mù, là con trai của Kisame", "|-1|Bầy Bi Sắc đu đu đu đu"}, //text chat 1
            new String[]{"|-1|Thủy Độn: Chạy Là Thượng Sách", "|-2|Bí thuật: Thằn Lằn Ngoằn Nghoèo"}, //text chat 2
            new String[]{"|-1|A A,ĐAU ĐAU", "|-2|Về ăn thêm cá đi nhóc"},
            60));

    }

    @Override
    public Zone getMapJoin() {
        int mapId = this.data[this.currentLevel].getMapJoin()[Util.nextInt(0, this.data[this.currentLevel].getMapJoin().length - 1)];
        return MapService.gI().getMapById(mapId).zones.get(0);
    }

    @Override
    public Player getPlayerAttack() {
        return super.getPlayerAttack();
    }


    @Override
    public int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            damage = 1;
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            this.playerSkill.skillSelect = this.playerSkill.skills.get(Util.nextInt(0, this.playerSkill.skills.size() - 1));
            SkillService.gI().useSkill(this, plAtt, null, -1, null);
            return (int) damage;
        } else {
            return 0;
        }
    }
   @Override
public void attack() {
    if (Util.canDoWithTime(this.lastTimeAttack, 100) && this.typePk == ConstPlayer.PK_ALL) {
        this.lastTimeAttack = System.currentTimeMillis();
        try {
            Player pl = this.getPlayerAttack();
            if (pl != null && !pl.isDie()) {
                if (Util.isTrue(1, 2)) {
                    this.moveToPlayer(pl); // Boss chỉ di chuyển đến gần player
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
                    }
    }
}


    
    @Override
    public void moveTo(int x, int y) {
        byte dir = (byte) (this.location.x - x < 0 ? 1 : -1);
        byte move = (byte) Util.nextInt(30, 40);
        PlayerService.gI().playerMove(this, this.location.x + (dir == 1 ? move : -move), y);
    }

    @Override
    public void die(Player plKill) {
        this.reward(plKill);
        this.changeStatus(BossStatus.DIE);
    }

     @Override
    public void reward(Player plKill) {
    int x = this.location.x;
    int y = this.zone.map.yPhysicInTop(x, this.location.y - 24);

    // Tạo ItemMap với ID 1622, số lượng 1
    ItemMap itemMap = new ItemMap(this.zone, 1622, 1, x, y, plKill.id);

    // Thêm 3 chỉ số: 77, 103, 50 (mỗi chỉ số random 1 - 15)
    itemMap.options.add(new Item.ItemOption(77, Util.nextInt(1, 15)));
    itemMap.options.add(new Item.ItemOption(103, Util.nextInt(1, 15)));
    itemMap.options.add(new Item.ItemOption(50, Util.nextInt(1, 15)));

    // Thêm chỉ số 231: hạn sử dụng (-1 là vĩnh viễn)
    itemMap.options.add(new Item.ItemOption(231,1)); // hoặc đổi -1 thành số ngày nếu muốn giới hạn

    // Rơi vật phẩm xuống bản đồ
    Service.gI().dropItemMap(zone, itemMap);
}

        
    

    private long st;

     @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.attack();
        if (Util.canDoWithTime(st, 900000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }

    @Override
    public void joinMap() {
        this.name = "Shark " + Util.nextInt(1, 999999999);
        this.nPoint.hpMax = 100;
        this.nPoint.hp = this.nPoint.hpMax;
        this.nPoint.dameg = this.nPoint.hpMax / 10;
        
        this.joinMap2();
        st = System.currentTimeMillis();
    }

    public void joinMap2() {
        if (this.zone == null) {
            if (this.parentBoss != null) {
                this.zone = parentBoss.zone;
            } else if (this.lastZone == null) {
                this.zone = getMapJoin();
            } else {
                this.zone = this.lastZone;
            }
        }
        if (this.zone != null) {
            try {
                int zoneid = 0;
                this.zone = this.zone.map.zones.get(zoneid);
                ChangeMapService.gI().changeMap(this, this.zone, -1, -1);

                this.changeStatus(BossStatus.CHAT_S);
            } catch (Exception e) {
                this.changeStatus(BossStatus.REST);
            }
        } else {
            this.changeStatus(BossStatus.RESPAWN);
        }
    }

    @Override
    public void leaveMap() {
        ChangeMapService.gI().exitMap(this);
        this.lastZone = null;
        this.lastTimeRest = System.currentTimeMillis();
        this.changeStatus(BossStatus.REST);
    }

}
