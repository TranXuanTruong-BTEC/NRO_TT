package boss.MajinBuu12H;


import boss.Boss;
import boss.BossID;
import consts.BossStatus;
import boss.BossesData;
import static consts.BossType.FINAL;
import item.Item;
import java.util.List;
import map.ItemMap;

import player.Player;
import services.Service;
import utils.Util;

import server.ServerNotify;
import services.EffectSkillService;
import services.ItemService;
import services.TaskService;
import skill.Skill;

public class BuiBui extends Boss {

    private long lastTimeAfk;

    private long lastTimeChatAfk;

    private int timeChat;

    public BuiBui() throws Exception {
        super(FINAL, BossID.BUI_BUI, BossesData.BUI_BUI);
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
    /*if (itemMap == null) {
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
    }
*/
    // Thả đồ ra map
    Service.gI().dropItemMap(zone, itemMap);

   // Cộng điểm & nhiệm vụ
    plKill.fightMabu.changePoint((byte) 10); // bỏ nếu boss không liên quan
    TaskService.gI().checkDoneTaskKillBoss(plKill, this);
}
    @Override
    public synchronized int injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(200, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }

            if (plAtt != null) {
                switch (plAtt.playerSkill.skillSelect.template.id) {
                    case Skill.KAMEJOKO:
                    case Skill.MASENKO:
                    case Skill.ANTOMIC:
                    case Skill.LIEN_HOAN:
                        return 0;
                }
            }

            if (plAtt.isPl() && Util.isTrue(1, 5)) {
                plAtt.fightMabu.changePercentPoint((byte) 1);
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
        if (Util.canDoWithTime(lastTimeAfk, 60000)) {
            Service.gI().hsChar(this, this.nPoint.hpMax, this.nPoint.mpMax);
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

}
