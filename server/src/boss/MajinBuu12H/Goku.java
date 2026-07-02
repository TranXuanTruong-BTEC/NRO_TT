package boss.MajinBuu12H;


import boss.Boss;
import boss.BossID;
import consts.BossStatus;
import boss.BossesData;
import static consts.BossType.FINAL;
import item.Item;

import java.util.ArrayList;
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

public class Goku extends Boss {

    private long lastTimeJoin;

    private long lastTimePetrify;

    private long lastTimeMove;

    private int timeMove;

    private long lastTimeAfk;

    private long lastTimeChatAfk;

    private int timeChat;

    private int indexChat;

    private boolean ttnl;

    public Goku() throws Exception {
        super(FINAL, BossID.GOKU, BossesData.GOKU);
    }

    @Override
    public void joinMap() {
        this.ttnl = false;
        this.lastTimeJoin = System.currentTimeMillis();
        this.zone = this.parentBoss.zoneFinal;
        this.nPoint.hp /= 4;
        ChangeMapService.gI().changeMap(this, this.zone, Util.nextInt(300, 400), 336);
        Service.gI().changeFlag(this, 9);
        this.changeStatus(BossStatus.CHAT_S);
    }

    @Override
    public void doneChatS() {
        this.playerSkill.skillSelect = this.playerSkill.skills.get(2);
        SkillService.gI().useSkill(this, null, null, -1, null);
    }

    @Override
    public void active() {
        this.attack();
    }

    @Override
    public Player getPlayerAttack() {
        List<Player> plNotVoHinh = new ArrayList();
        for (Player pl : this.zone.getNotBosses()) {
            if (pl != null && (pl.effectSkin == null || !pl.effectSkin.isVoHinh) && pl.cFlag != this.cFlag) {
                plNotVoHinh.add(pl);
            }
        }
        for (Player pl : this.zone.getBosses()) {
            if (!pl.equals(this) && pl.cFlag == 10) {
                plNotVoHinh.add(pl);
            }
        }
        if (!plNotVoHinh.isEmpty()) {
            return plNotVoHinh.get(Util.nextInt(0, plNotVoHinh.size() - 1));
        }

        return null;
    }

    private void petrifyPlayersInTheMap() {
        for (Player pl : this.zone.getNotBosses()) {
            if (Util.isTrue(1, 10)) {
                this.chat("phẹt");
                EffectSkillService.gI().setIsStone(pl, 16000);
            }
        }
    }

    @Override
    public void afk() {
        if (Util.canDoWithTime(lastTimeChatAfk, timeChat)) {
            this.chat("Đừng vội mừng, ta sẽ hồi sinh và thịt hết bọn mi");
            this.lastTimeChatAfk = System.currentTimeMillis();
            this.timeChat = Util.nextInt(10000, 15000);
        }
        if (Util.canDoWithTime(lastTimeAfk, 60000)) {
            this.nPoint.hp = this.nPoint.hpMax;
            this.changeStatus(BossStatus.CHAT_S);
        }
    }

    @Override
    public void die(Player plKill) {
        if (plKill != null) {
            reward(plKill);
            ServerNotify.gI().notify(plKill.name + ": Đã tiêu diệt được " + this.name + " mọi người đều ngưỡng mộ.");
        }
        this.lastTimeAfk = System.currentTimeMillis();
        this.changeStatus(BossStatus.AFK);
    }

    @Override
    public void autoLeaveMap() {
        if (Util.canDoWithTime(this.lastTimeJoin, 248500)) {
            if (!this.ttnl) {
                this.nPoint.mp = 100;
                this.nPoint.mpMax = 1_000_000_000;
                this.playerSkill.skillSelect = this.playerSkill.skills.get(2);
                this.playerSkill.skillSelect.lastTimeUseThisSkill = 0;
                SkillService.gI().useSkill(this, null, null, -1, null);
                this.ttnl = true;
            }
        }
        if (Util.canDoWithTime(this.lastTimeJoin, 250000)) {
            this.leaveMap();
        }
    }

    @Override
    public void attack() {
        if (this.effectSkill.isCharging) {
            return;
        }
        if (Util.canDoWithTime(this.lastTimeAttack, 100)) {
            if (Util.canDoWithTime(lastTimePetrify, 10000)) {
                this.lastTimePetrify = System.currentTimeMillis();
            }
            this.lastTimeAttack = System.currentTimeMillis();
            try {
                Player pl = getPlayerAttack();
                if (pl == null || pl.isDie()) {
                    if (Util.canDoWithTime(lastTimeMove, timeMove)) {
                        Player plRand = super.getPlayerAttack();
                        if (plRand != null) {
                            this.moveToPlayer(plRand);
                            this.lastTimeMove = System.currentTimeMillis();
                            this.timeMove = Util.nextInt(5000, 30000);
                        }
                    }
                    return;
                }
                this.playerSkill.skillSelect = this.playerSkill.skills.get(Util.nextInt(0, this.playerSkill.skills.size() - 1));
                int dis = Util.getDistance(this, pl);
                if (dis > 450) {
                    move(pl.location.x - 24, pl.location.y);
                } else if (dis > 100) {
                    int dir = (this.location.x - pl.location.x < 0 ? 1 : -1);
                    int move = Util.nextInt(50, 100);
                    move(this.location.x + (dir == 1 ? move : -move), pl.location.y);
                } else {
                    if (Util.isTrue(30, 100)) {
                        int move = Util.nextInt(50);
                        move(pl.location.x + (Util.nextInt(0, 1) == 1 ? move : -move), this.location.y);
                    }
                    SkillService.gI().useSkill(this, pl, null, -1, null);
                    checkPlayerDie(pl);
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
        if (pl.location != null) {
            moveTo(pl.location.x, pl.location.y);
        }
    }

    @Override
    public void chatM() {
        if (this.effectSkill.isCharging) {
            return;
        }
        if (this.data[this.currentLevel].getTextM().length == 0) {
            return;
        }
        if (!Util.canDoWithTime(this.lastTimeChatM, this.timeChatM)) {
            return;
        }
        String textChat = this.data[this.currentLevel].getTextM()[this.indexChat];
        int prefix = Integer.parseInt(textChat.substring(1, textChat.lastIndexOf("|")));
        textChat = textChat.substring(textChat.lastIndexOf("|") + 1);
        this.chat(prefix, textChat);
        this.indexChat++;
        if (this.indexChat == this.data[this.currentLevel].getTextM().length) {
            this.indexChat = 0;
            this.timeChatM = 5000;
        } else {
            this.timeChatM = 1000;
        }
        this.lastTimeChatM = System.currentTimeMillis();
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
   /* if (itemMap == null) {
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
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1)) {
                this.chat("Xí hụt");
                return 0;
            }
            if (plAtt.isPl() && Util.isTrue(1, 5)) {
                plAtt.fightMabu.changePoint((byte) 1);
            }
            if (damage >= 20000000) {
                damage = 20000000;
            }

            this.nPoint.subHP(damage);

            if (isDie()) {
                this.setDie(plAtt);
                this.lastTimeAfk = System.currentTimeMillis();
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
    }

}
