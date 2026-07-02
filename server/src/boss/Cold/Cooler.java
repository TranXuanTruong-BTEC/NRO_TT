package boss.Cold;


import boss.Boss;
import boss.BossID;
import boss.BossesData;
import item.Item;
import java.util.List;
import map.ItemMap;
import player.Player;
import services.EffectSkillService;
import services.Service;
import utils.Util;

import java.util.Random;
import services.ItemService;
import services.TaskService;

public class Cooler extends Boss {

    private long st;

    public Cooler() throws Exception {
        super(BossID.COOLER, BossesData.COOLER, BossesData.COOLER_2);
    }

    @Override
   public void reward(Player plKill) {
    int x = this.location.x;
    int y = this.zone.map.yPhysicInTop(x, this.location.y - 24);

    ItemMap drop = null;

    // 🎲 5% đồ thần linh
    if (Util.isTrue(11, 100)) {
        ItemMap it = ItemService.gI().randDoTLBoss(this.zone, 1, x, y, plKill.id);
        if (it != null) {
            it.options.add(new Item.ItemOption(107, rollStar())); // ⭐
            drop = it;
        }

    // 🎲 85% đồ cao cấp
    } else if (Util.isTrue(60, 100)) {
        int group = Util.isTrue(70, 100) ? 0 : 1;
        int[][] drops = {
            {230, 231, 232, 234, 235, 236, 238, 239, 240, 242, 243, 244,
             246, 247, 248, 250, 251, 252, 266, 267, 268, 270, 271, 272,
             274, 275, 276},
            {254, 255, 256, 258, 259, 260, 262, 263, 264, 278, 279, 280}
        };
        int dropId = drops[group][Util.nextInt(0, drops[group].length - 1)];
        drop = new ItemMap(this.zone, dropId, 1, x, y, plKill.id);

        List<Item.ItemOption> ops = ItemService.gI().getListOptionItemShop((short) dropId);
        ops.forEach(op -> op.param = (int) (op.param * Util.nextInt(100, 115) / 100.0));
        drop.options.addAll(ops);

        drop.options.add(new Item.ItemOption(107, rollStar())); // ⭐

    // 🎲 10% đồ thường (15,16)
    } else {
        int[] dropItems = {15, 16};
        int dropId = dropItems[Util.nextInt(0, dropItems.length - 1)];
        drop = new ItemMap(this.zone, dropId, Util.nextInt(1, 3), x, y, plKill.id);
    }

    if (drop != null) {
        Service.gI().dropItemMap(zone, drop);
    }

    // ✅ check nhiệm vụ
    TaskService.gI().checkDoneTaskKillBoss(plKill, this);
}

// ⭐ tỉ lệ sao
private int rollStar() {
    int rand = Util.nextInt(1, 1000);
    if (rand <= 970) return Util.nextInt(1, 3);
    if (rand <= 990) return 4;
    if (rand <= 997) return 5;
    return 6;
}

    @Override
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (Util.isTrue(10, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
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
    public void joinMap() {
        super.joinMap();
        st = System.currentTimeMillis();
    }

    @Override
    public void autoLeaveMap() {
        if (Util.canDoWithTime(st, 900000)) {
            this.leaveMapNew();
        }
        if (this.zone != null && this.zone.getNumOfPlayers() > 0) {
            st = System.currentTimeMillis();
        }
    }

}
