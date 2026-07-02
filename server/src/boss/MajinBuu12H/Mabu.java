package boss.MajinBuu12H;


import boss.Boss;
import boss.BossID;
import consts.BossStatus;
import boss.BossesData;
import consts.AppearType;
import static consts.BossType.FINAL;
import consts.ConstPlayer;
import item.Item;
import map.ItemMap;
import player.Player;
import services.Service;
import utils.Util;
import java.util.ArrayList;
import java.util.List;
import Deputyhead.Service.MajinBuuService;
import services.EffectSkillService;
import services.ItemTimeService;
import services.SkillService;
import services.TaskService;
import map.Service.ChangeMapService;
import services.ItemService;
import utils.SkillUtil;

public class Mabu extends Boss {

    private long lastTimePetrify;

    private int percent;

    public Mabu() throws Exception {
        super(FINAL, BossID.MABU_12H, BossesData.MABU_12H);
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
        int group = Util.nextInt(1, 100) <= 70 ? 0 : 1;
        int[][] dropItems = {
            {230, 231, 232, 234, 235, 236, 238, 239, 240, 242, 243, 244,
            246, 247, 248, 250, 251, 252, 266, 267, 268, 270, 271, 272,
            274, 275, 276}, 
            {254, 255, 256, 258, 259, 260, 262, 263, 264,
            278, 279, 280}
    };

        int dropOptional = dropItems[group][Util.nextInt(0, dropItems[group].length - 1)];
        ItemMap optionalItemMap = new ItemMap(this.zone, dropOptional, 1, x, y, plKill.id);
        List<Item.ItemOption> optionalOps = ItemService.gI().getListOptionItemShop((short) dropOptional);
        optionalOps.forEach(option -> option.param = (int) (option.param * Util.nextInt(100, 115) / 100.0));
        optionalItemMap.options.addAll(optionalOps);
               
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
    }
*/
    // Thả đồ ra map
    Service.gI().dropItemMap(zone, itemMap);

   // Cộng điểm & nhiệm vụ
    plKill.fightMabu.changePoint((byte) 10); // bỏ nếu boss không liên quan
    TaskService.gI().checkDoneTaskKillBoss(plKill, this);
}
    @Override
    public void joinMap() {
        if (zoneFinal != null) {
            this.zone = zoneFinal;
        }
        ChangeMapService.gI().changeMap(this, this.zone, Util.nextInt(300, 400), 336);
        this.changeStatus(BossStatus.CHAT_S);
        MajinBuuService.gI().getNpcBabiday(this.zone).npcChat(this.zone, "Mabư ! Hãy theo lệnh ta, giết hết bọn chúng đi");
    }

    private void petrifyPlayersInTheMap() {
        for (Player pl : this.zone.getNotBosses()) {
            if (Util.isTrue(1, 10)) {
                EffectSkillService.gI().setIsStone(pl, 22000);
            } else if (Util.isTrue(1, 5)) {
                this.chat("Úm ba la xì bùa");
                EffectSkillService.gI().setSocola(pl, System.currentTimeMillis(), 30000);
                Service.gI().Send_Caitrang(pl);
                ItemTimeService.gI().sendItemTime(pl, 4133, 30);
            }
        }
    }

    @Override
    public void attack() {
        if (Util.canDoWithTime(this.lastTimeAttack, 100) && this.typePk == ConstPlayer.PK_ALL) {
            if (Util.canDoWithTime(lastTimePetrify, 30000)) {
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
    public void autoLeaveMap() {
    }

    @Override
    public void rest() {
        int nextLevel = this.currentLevel + 1;
        if (nextLevel >= this.data.length) {
            nextLevel = 0;
        }
        if (this.data[nextLevel].getTypeAppear() == AppearType.DEFAULT_APPEAR
                && Util.canDoWithTime(lastTimeRest, secondsRest * 1000)) {
            this.changeStatus(BossStatus.RESPAWN);
        }
        long currentTimeMillis = System.currentTimeMillis();
        long elapsedTime = currentTimeMillis - lastTimeRest;

        this.percent = (int) (elapsedTime * 100 / ((secondsRest - 3) * 1000));
        if (percent <= 100) {
            Service.gI().SendMabu(this.zoneFinal, this.percent);
        }
    }

    @Override
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(20, 100)) {
                this.chat("Xí hụt");
                return 0;
            }

            if (plAtt.isPl() && Util.isTrue(1, 5)) {
                plAtt.fightMabu.changePercentPoint((byte) 1);
            }
            if (damage >= 50000000) {
                damage = 50000000 + Util.nextInt(-10000, 10000);
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
    public void leaveMap() {
        ChangeMapService.gI().exitMap(this);
        this.lastZone = null;
        this.lastTimeRest = System.currentTimeMillis();
        this.changeStatus(BossStatus.REST);
        for (Boss boss : this.bossAppearTogether[this.currentLevel]) {
            boss.changeStatus(BossStatus.RESPAWN);
        }
    }
}
