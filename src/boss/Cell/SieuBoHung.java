package boss.Cell;

import boss.*;
import consts.BossStatus;
import consts.ConstPlayer;
import item.Item;
import item.Item.ItemOption;
import map.ItemMap;
import mob.Mob;
import player.Player;
import player.Service.PlayerService;
import services.*;
import utils.Util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SieuBoHung extends Boss {

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private long st;
    public boolean callCellCon;
    private long lastTimeChat;
    private long lastTimeMove;
    private int indexChat = 0;
    private final String[] text = {
            "Thưa quý vị và các bạn, đây đúng là trận đấu trời long đất lở",
            "Vượt xa mọi dự đoán của chúng tôi",
            "Eo ơi toàn thân lão Xên bốc cháy kìa"
    };

    public SieuBoHung() throws Exception {
        super(BossID.SIEU_BO_HUNG, BossesData.SIEU_BO_HUNG_1, BossesData.SIEU_BO_HUNG_2);
    }

    @Override
    protected void resetBase() {
        super.resetBase();
        this.callCellCon = false;
    }

    public void callCellCon() {
        executor.submit(() -> {
            try {
                this.changeStatus(BossStatus.AFK);
                this.changeToTypeNonPK();
                this.recoverHP();
                this.callCellCon = true;
                this.chat("Hãy đấu với 7 đứa con của ta, chúng đều là siêu cao thủ");
                Thread.sleep(2000);
                this.chat("Cứ chưởng tiếp đi haha");
                Thread.sleep(2000);
                this.chat("Liệu mà giữ mạng đấy");
                Thread.sleep(2000);
                for (Boss boss : this.bossAppearTogether[this.currentLevel]) {
                    switch ((int) boss.id) {
                        case BossID.XEN_CON_1, BossID.XEN_CON_2, BossID.XEN_CON_3,
                             BossID.XEN_CON_4, BossID.XEN_CON_5, BossID.XEN_CON_6,
                             BossID.XEN_CON_7 -> boss.changeStatus(BossStatus.RESPAWN);
                    }
                }
            } catch (Exception ignored) {}
        });
    }

    public void recoverHP() {
        PlayerService.gI().hoiPhuc(this, this.nPoint.hpMax, 0);
    }
    @Override
    public void reward(Player plKill) {
    int x = this.location.x;
    int y = this.zone.map.yPhysicInTop(x, this.location.y - 24);

    // 🌟 5% rơi đồ thần linh
    if (Util.isTrue(10, 100)) {
        ItemMap it = ItemService.gI().randDoTLBoss(this.zone, 1, x, y, plKill.id);
        if (it != null) {
            it.options.add(new Item.ItemOption(107, rollStar()));
            Service.gI().dropItemMap(zone, it);
            return;
        }
    }

    // 🌟 rơi đồ cao cấp (2 nhóm)
   /* int group = Util.isTrue(70, 100) ? 0 : 1;
    int[][] drops = {
        {230, 231, 232, 234, 235, 236, 238, 239, 240, 242, 243, 244,
         246, 247, 248, 250, 251, 252, 266, 267, 268, 270, 271, 272,
         274, 275, 276},
        {254, 255, 256, 258, 259, 260, 262, 263, 264, 278, 279, 280}
    };
   int dropId = drops[group][Util.nextInt(0, drops[group].length - 1)];
    ItemMap itemMap = new ItemMap(this.zone, dropId, 1, x, y, plKill.id);

    List<Item.ItemOption> options = ItemService.gI().getListOptionItemShop((short) dropId);
    options.forEach(op -> op.param = (int) (op.param * Util.nextInt(100, 115) / 100.0));
    itemMap.options.addAll(options);

    // ⭐ gán sao theo tỉ lệ mới
    itemMap.options.add(new Item.ItemOption(107, rollStar()));

    Service.gI().dropItemMap(zone, itemMap);
*/
    // 🌟 10% rơi đồ thường (chỉ 15,16, không sao)
    if (Util.isTrue(50, 100)) {
        int[] normalItems = {15, 16};  // đã xóa 17,18,19,20,992
        int dropNormal = normalItems[Util.nextInt(0, normalItems.length - 1)];
        ItemMap normalItem = new ItemMap(this.zone, dropNormal, Util.nextInt(1, 3), x, y, plKill.id);
        Service.gI().dropItemMap(zone, normalItem);
    }

    TaskService.gI().checkDoneTaskKillBoss(plKill, this);
}

// 📄 phương thức random sao
private int rollStar() {
    int rand = Util.nextInt(1, 1000);
    if (rand <= 970) return Util.nextInt(1, 3);  // ~97%: 1–3⭐
    if (rand <= 990) return 4;                   // ~2%: 4⭐
    if (rand <= 997) return 5;                   // ~0.7%: 5⭐
    return 6;                                    // ~0.3%: 6⭐
}
    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.attack();
    }

    @Override
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (prepareBom) return 0;

        if (!callCellCon && damage >= this.nPoint.hp) {
            callCellCon();
            return 0;
        }

        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }

            damage = this.nPoint.subDameInjureWithDeff(damage / 3);

            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage /= 4;
            }

            this.nPoint.subHP(damage);

            if (isDie()) {
                setBom(plAtt);
                return 0;
            }

            return (int) damage;
        }
        return 0;
    }

    @Override
    public void joinMap() {
        super.joinMap();
        st = System.currentTimeMillis();
    }

    @Override
    public void autoLeaveMap() {
        this.mc();
        if (this.currentLevel > 0 && this.bossStatus == BossStatus.AFK) {
            this.changeStatus(BossStatus.ACTIVE);
        }
        if (Util.canDoWithTime(st, 900000)) {
            this.leaveMapNew();
        }
        if (this.zone != null && this.zone.getNumOfPlayers() > 0) {
            st = System.currentTimeMillis();
        }
    }

    public void mc() {
        Player mc = zone.getNpc();
        if (mc != null) {
            if (Util.canDoWithTime(lastTimeChat, 3000)) {
                Service.gI().chat(mc, text[indexChat]);
                indexChat = (indexChat + 1) % text.length;
                lastTimeChat = System.currentTimeMillis() + (indexChat == 0 ? 7000 : 0);
            }

            if (Util.canDoWithTime(lastTimeMove, 15000) && Util.isTrue(2, 3)) {
                int x = this.location.x + Util.nextInt(-100, 100);
                int y = (x > 156 && x < 611) ? 288 : 312;
                PlayerService.gI().playerMove(mc, x, y);
                lastTimeMove = System.currentTimeMillis();
            }
        }
    }
}
