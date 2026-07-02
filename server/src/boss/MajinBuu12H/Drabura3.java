package boss.MajinBuu12H;

import boss.Boss;
import boss.BossID;
import consts.BossStatus;
import boss.BossesData;
import static consts.BossType.FINAL;
import consts.ConstPlayer;
import item.Item;
import java.util.List;
import map.ItemMap;
import player.Player;
import services.EffectSkillService;
import services.Service;
import utils.Util;

import server.ServerNotify;
import services.ItemService;
import player.Service.PlayerService;
import services.SkillService;
import services.TaskService;
import map.Service.ChangeMapService;
import utils.SkillUtil;

public class Drabura3 extends Boss {

    private long lastTimeJoin;
    private long lastTimePetrify;
    private long lastTimeChatAfk;
    private int timeChat;

    public Drabura3() throws Exception {
        super(FINAL, BossID.DRABURA_3, BossesData.DRABURA_3);
    }

    @Override
    public void joinMap() {
        this.lastTimeJoin = System.currentTimeMillis();
        this.zone = this.parentBoss.zoneFinal;
        ChangeMapService.gI().changeMap(this, this.zone, Util.nextInt(300, 400), 336);
        Service.gI().changeFlag(this, 10);
        this.changeStatus(BossStatus.CHAT_S);
    }

    private void petrifyPlayersInTheMap() {
        for (Player pl : this.zone.getNotBosses()) {
            if (Util.isTrue(1, 10)) {
                this.chat("phẹt");
                EffectSkillService.gI().setIsStone(pl, 22000);
            }
        }
    }

    @Override
   public void reward(Player plKill) {
    int x = this.location.x;
    int y = this.zone.map.yPhysicInTop(x, this.location.y - 24);

    ItemMap itemMap = null;

    // 🎲 Thử rơi đồ thần — tỷ lệ bạn đặt là 35%
    if (Util.isTrue(10, 100)) {
        itemMap = ItemService.gI().randDoTLBoss(this.zone, 1, x, y, plKill.id);
    }

    // Nếu không rơi đồ thần → rơi đồ cao cấp
  /*  if (itemMap == null) {
        // Danh sách đồ cao cấp
        int[] dropItems = {
            230, 231, 232, 234, 235, 236, 238, 239, 240, 242, 243, 244,
            246, 247, 248, 250, 251, 252, 266, 267, 268, 270, 271, 272,
            274, 275, 276, 254, 255, 256, 258, 259, 260, 262, 263, 264,
            278, 279, 280
        };

        int dropItemId = dropItems[Util.nextInt(0, dropItems.length - 1)];
        itemMap = new ItemMap(this.zone, dropItemId, 1, x, y, plKill.id);

        // Thêm option cơ bản
        List<Item.ItemOption> options = ItemService.gI().getListOptionItemShop((short) dropItemId);
        options.forEach(opt -> opt.param = (int) (opt.param * Util.nextInt(100, 115) / 100.0));
        itemMap.options.addAll(options);

        // ⭐ Tính số sao
        int star = 0;
        int rand = Util.nextInt(1, 1000); // random 1–1000
        if (rand <= 5) {            // ~0.5%
            star = 6;
        } else if (rand <= 15) {   // ~1%
            star = 5;
        } else if (rand <= 115) {  // ~10%
            star = 4;
        } else {
            // Sao thấp hơn
            if (Util.isTrue(30, 100)) {
                star = 3;
            } else if (Util.isTrue(30, 100)) {
                star = 2;
            } else if (Util.isTrue(40, 100)) {
                star = 1;
            }
        }

        itemMap.options.add(new Item.ItemOption(107, star));
    }*/

    // Thả đồ ra map
    Service.gI().dropItemMap(zone, itemMap);

   // Cộng điểm & nhiệm vụ
    plKill.fightMabu.changePoint((byte) 10); // bỏ nếu boss không liên quan
    TaskService.gI().checkDoneTaskKillBoss(plKill, this);
}
    @Override
    public void autoLeaveMap() {
        if (Util.canDoWithTime(this.lastTimeJoin, 60000)) {
            this.leaveMap();
        }
    }

    @Override
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            if (damage >= 20_000_000) {
                damage = 20_000_000;
            }

            this.nPoint.subHP(damage);

            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }

            return (int) damage;
        } else {
            return 0;
        }
    }

    @Override
    public void afk() {
        if (Util.canDoWithTime(lastTimeChatAfk, timeChat)) {
            this.chat("Đừng vội mừng, ta sẽ hồi sinh và thịt hết bọn mi");
            this.lastTimeChatAfk = System.currentTimeMillis();
            this.timeChat = Util.nextInt(10000, 15000);
        }
    }

    @Override
    public void die(Player plKill) {
        if (plKill != null) {
            reward(plKill);
            ServerNotify.gI().notify(plKill.name + ": Đã tiêu diệt được " + this.name + " mọi người đều ngưỡng mộ.");
        }
        this.lastTimeChatAfk = System.currentTimeMillis();
        this.changeStatus(BossStatus.AFK);
    }

    @Override
    public void attack() {
        if (Util.canDoWithTime(this.lastTimeAttack, 100) && this.typePk == ConstPlayer.PK_ALL) {
            if (Util.canDoWithTime(lastTimePetrify, 10000)) {
                petrifyPlayersInTheMap();
                this.lastTimePetrify = System.currentTimeMillis();
            }
            this.lastTimeAttack = System.currentTimeMillis();
            try {
                Player pl = getPlayerAttack();
                if (pl == null || pl.isDie()) {
                    return;
                }
                this.playerSkill.skillSelect = this.playerSkill.skills.get(Util.nextInt(0, this.playerSkill.skills.size() - 1));
                if (Util.getDistance(this, pl) <= this.getRangeCanAttackWithSkillSelect()) {
                    if (Util.isTrue(5, 20)) {
                        if (SkillUtil.isUseSkillChuong(this)) {
                            this.moveTo(pl.location.x + (Util.getOne(-1, 1) * Util.nextInt(20, 200)), pl.location.y);
                        } else {
                            this.moveTo(pl.location.x + (Util.getOne(-1, 1) * Util.nextInt(10, 40)), pl.location.y);
                        }
                    }
                    SkillService.gI().useSkill(this, pl, null, -1, null);
                    checkPlayerDie(pl);
                } else {
                    if (Util.isTrue(1, 2)) {
                        this.moveToPlayer(pl);
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
        byte move = (byte) Util.nextInt(50, 100);
        PlayerService.gI().playerMove(this, this.location.x + (dir == 1 ? move : -move), y);
    }

    @Override
    public void moveToPlayer(Player pl) {
        moveTo(pl.location.x, pl.location.y);
    }

    @Override
    public void leaveMap() {
        ChangeMapService.gI().exitMap(this);
        this.lastZone = null;
        this.lastTimeRest = System.currentTimeMillis();
        this.changeStatus(BossStatus.REST);
    }
}